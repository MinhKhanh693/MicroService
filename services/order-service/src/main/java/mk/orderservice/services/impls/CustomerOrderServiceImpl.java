package mk.orderservice.services.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.orderservice.controllers.requests.CustomerOrderRequestsDto;
import mk.orderservice.controllers.requests.PaymentRequestDto;
import mk.orderservice.controllers.responses.CustomerOrderResponseDto;
import mk.orderservice.entities.CustomerOrderLine;
import mk.orderservice.exceptions.BusinessException;
import mk.orderservice.feigns.CustomerClientFeign;
import mk.orderservice.feigns.PaymentClientFeign;
import mk.orderservice.feigns.ProductClientFeign;
import mk.orderservice.kafka.OrderConfirmation;
import mk.orderservice.kafka.OrderProducerKafkaService;
import mk.orderservice.mappers.CustomerOrderLineMapper;
import mk.orderservice.mappers.CustomerOrderMapper;
import mk.orderservice.repositories.CustomerOrderRepository;
import mk.orderservice.services.ICustomerOrderLineService;
import mk.orderservice.services.ICustomerOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "CUSTOMER_ORDER_SERVICE")
public class CustomerOrderServiceImpl implements ICustomerOrderService {
    private final CustomerClientFeign customerClientFeign;
    private final ProductClientFeign productClientFeign;
    private final PaymentClientFeign paymentClientFeign;
    private final CustomerOrderRepository customerOrderRepository;
    private final ICustomerOrderLineService customerOrderLineService;
    private final CustomerOrderMapper customerOrderMapper;
    private final CustomerOrderLineMapper customerOrderLineMapper;
    private final OrderProducerKafkaService orderProducerKafkaService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerOrderResponseDto createOrder(CustomerOrderRequestsDto order) {
        // Check if customer exists
        var customer = this.customerClientFeign.findById(order.customerId())
                .orElseThrow(() -> new BusinessException("Can't create order. Customer not found! with id: " + order.customerId()));
        // Save products
        var purchaseProducts = this.productClientFeign.purchaseProducts(order.products())
                .orElseThrow(() -> new BusinessException("Can't create order. Products not found!"));
        // Save order
        var newOrder = this.customerOrderMapper.toEntity(order);
        newOrder.setCreatedDate(Instant.now());
        var customerOder = this.customerOrderRepository.save(
                newOrder
        );
        log.info("Order saved: {}", customerOder);
        // Save order lines
        List<CustomerOrderLine> customerOrderLines = order.products().stream()
                .map(product -> CustomerOrderLine.builder()
                        .quantity(product.quantity())
                        .productId(product.id())
                        .order(customerOder)
                        .build()
                ).toList();
        var customerOrderLineSaved = this.customerOrderLineService.saveListCustomerOrderLine(this.customerOrderLineMapper.toDtoList(customerOrderLines));
        log.info("List Customer Order Line created: {}", customerOrderLineSaved);

        // todo: create payment
        var paymentRequest = PaymentRequestDto.builder()
                .amount(order.amount())
                .paymentMethod(order.paymentMethod())
                .orderId(customerOder.getId())
                .orderReference(customerOder.getReference())
                .customer(customer)
                .build();

        var paymentResponse = this.paymentClientFeign.createPayment(paymentRequest);
        log.info("Payment created: {}", paymentResponse);

        // todo: start payment process and send message with kafka
        var OrderConfirmationMessage = OrderConfirmation.builder()
                .customer(customer)
                .products(purchaseProducts)
                .orderReference(order.reference())
                .totalAmount(order.amount())
                .paymentMethod(order.paymentMethod())
                .build();
        this.orderProducerKafkaService.sendOrderConfirmation(OrderConfirmationMessage);
        log.info("Order confirmation message sent: {}", OrderConfirmationMessage);

        return this.customerOrderMapper.toDto1(customerOder);
    }

    @Override
    public CustomerOrderResponseDto findById(Integer id) {
        var customerOrder = this.customerOrderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Order not found with id: " + id));
        log.info("Order found: {}", customerOrder);
        return this.customerOrderMapper.toDto1(customerOrder);
    }

    @Override
    public List<CustomerOrderResponseDto> findAll() {
        var customerOrders = this.customerOrderRepository.findAll();
        return this.customerOrderMapper.toDtoList(customerOrders);
    }
}

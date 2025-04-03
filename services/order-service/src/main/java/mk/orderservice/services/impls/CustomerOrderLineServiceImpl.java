package mk.orderservice.services.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.orderservice.controllers.responses.CustomerOrderLineResponseDto;
import mk.orderservice.mappers.CustomerOrderLineMapper;
import mk.orderservice.repositories.CustomerOrderLineRepository;
import mk.orderservice.services.ICustomerOrderLineService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "CUSTOMER_ORDER_LINE_SERVICE")
public class CustomerOrderLineServiceImpl implements ICustomerOrderLineService {
    private final CustomerOrderLineRepository customerOrderLineRepository;
    private final CustomerOrderLineMapper customerOrderLineMapper;

    @Override
    public List<CustomerOrderLineResponseDto> saveListCustomerOrderLine(List<CustomerOrderLineResponseDto> customerOrderLineRequestDTOs) {
        if (customerOrderLineRequestDTOs != null && !customerOrderLineRequestDTOs.isEmpty()) {
            var customerOrderLineEntities = this.customerOrderLineMapper.toEntityList(customerOrderLineRequestDTOs);
            var customerOrderLineSaved = this.customerOrderLineRepository.saveAll(customerOrderLineEntities);
            var customerOrderLineResponseDTOs = this.customerOrderLineMapper.toDtoList(customerOrderLineSaved);
            log.info("Customer order lines created: {}", customerOrderLineResponseDTOs);
            return customerOrderLineResponseDTOs;
        }
        throw new RuntimeException("Customer order lines are empty");
    }

    @Override
    public CustomerOrderLineResponseDto saveCustomerOrderLine(CustomerOrderLineResponseDto customerOrderLineRequestDto) {
        var customerOrderLineEntity = this.customerOrderLineMapper.toEntity(customerOrderLineRequestDto);
        var customerOrderLineSaved = this.customerOrderLineRepository.save(customerOrderLineEntity);
        var customerOrderLineResponseDto = this.customerOrderLineMapper.toDto(customerOrderLineSaved);
        log.info("Customer order line created: {}", customerOrderLineResponseDto);
        return customerOrderLineResponseDto;
    }

    @Override
    public List<CustomerOrderLineResponseDto> findAllCustomerOrderLinesByOrderId(Integer orderId) {
        var customerOrderLine = this.customerOrderLineRepository.findCustomerOrderLinesByOrderId(orderId);
        return customerOrderLine.stream()
                .map(this.customerOrderLineMapper::toDto)
                .toList();
    }
}

package mk.orderservice.mappers;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import mk.orderservice.Enums.PaymentMethodTypeEnum;
import mk.orderservice.controllers.requests.CustomerOrderRequestsDto;
import mk.orderservice.controllers.requests.ProductPurchaseRequestDto;
import mk.orderservice.controllers.responses.CustomerOrderResponseDto;
import mk.orderservice.entities.CustomerOrder;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-02T17:07:06+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class CustomerOrderMapperImpl implements CustomerOrderMapper {

    @Override
    public CustomerOrder toEntity(CustomerOrderRequestsDto customerOrderDto) {
        if ( customerOrderDto == null ) {
            return null;
        }

        CustomerOrder customerOrder = new CustomerOrder();

        if ( customerOrderDto.id() != null ) {
            customerOrder.setId( Integer.parseInt( customerOrderDto.id() ) );
        }
        customerOrder.setCustomerId( customerOrderDto.customerId() );
        customerOrder.setPaymentMethod( customerOrderDto.paymentMethod() );
        customerOrder.setReference( customerOrderDto.reference() );

        return customerOrder;
    }

    @Override
    public CustomerOrderRequestsDto toDto(CustomerOrder customerOrder) {
        if ( customerOrder == null ) {
            return null;
        }

        String id = null;
        String customerId = null;
        PaymentMethodTypeEnum paymentMethod = null;
        String reference = null;

        if ( customerOrder.getId() != null ) {
            id = String.valueOf( customerOrder.getId() );
        }
        customerId = customerOrder.getCustomerId();
        paymentMethod = customerOrder.getPaymentMethod();
        reference = customerOrder.getReference();

        BigDecimal amount = null;
        List<ProductPurchaseRequestDto> products = null;

        CustomerOrderRequestsDto customerOrderRequestsDto = new CustomerOrderRequestsDto( id, amount, customerId, paymentMethod, reference, products );

        return customerOrderRequestsDto;
    }

    @Override
    public List<CustomerOrder> toEntityList(List<CustomerOrderRequestsDto> customerOrderDTOs) {
        if ( customerOrderDTOs == null ) {
            return null;
        }

        List<CustomerOrder> list = new ArrayList<CustomerOrder>( customerOrderDTOs.size() );
        for ( CustomerOrderRequestsDto customerOrderRequestsDto : customerOrderDTOs ) {
            list.add( toEntity( customerOrderRequestsDto ) );
        }

        return list;
    }

    @Override
    public List<CustomerOrderResponseDto> toDtoList(List<CustomerOrder> customerOrders) {
        if ( customerOrders == null ) {
            return null;
        }

        List<CustomerOrderResponseDto> list = new ArrayList<CustomerOrderResponseDto>( customerOrders.size() );
        for ( CustomerOrder customerOrder : customerOrders ) {
            list.add( toDto1( customerOrder ) );
        }

        return list;
    }

    @Override
    public CustomerOrder toEntity(CustomerOrderResponseDto customerOrderResponseDto) {
        if ( customerOrderResponseDto == null ) {
            return null;
        }

        CustomerOrder customerOrder = new CustomerOrder();

        if ( customerOrderResponseDto.id() != null ) {
            customerOrder.setId( Integer.parseInt( customerOrderResponseDto.id() ) );
        }
        customerOrder.setTotalAmount( customerOrderResponseDto.totalAmount() );
        customerOrder.setCreatedDate( customerOrderResponseDto.createdDate() );
        customerOrder.setLastModifiedDate( customerOrderResponseDto.lastModifiedDate() );
        customerOrder.setCustomerId( customerOrderResponseDto.customerId() );
        customerOrder.setPaymentMethod( customerOrderResponseDto.paymentMethod() );
        customerOrder.setReference( customerOrderResponseDto.reference() );

        return customerOrder;
    }

    @Override
    public CustomerOrderResponseDto toDto1(CustomerOrder customerOrder) {
        if ( customerOrder == null ) {
            return null;
        }

        String id = null;
        BigDecimal totalAmount = null;
        Instant createdDate = null;
        Instant lastModifiedDate = null;
        String customerId = null;
        PaymentMethodTypeEnum paymentMethod = null;
        String reference = null;

        if ( customerOrder.getId() != null ) {
            id = String.valueOf( customerOrder.getId() );
        }
        totalAmount = customerOrder.getTotalAmount();
        createdDate = customerOrder.getCreatedDate();
        lastModifiedDate = customerOrder.getLastModifiedDate();
        customerId = customerOrder.getCustomerId();
        paymentMethod = customerOrder.getPaymentMethod();
        reference = customerOrder.getReference();

        CustomerOrderResponseDto customerOrderResponseDto = new CustomerOrderResponseDto( id, totalAmount, createdDate, lastModifiedDate, customerId, paymentMethod, reference );

        return customerOrderResponseDto;
    }

    @Override
    public CustomerOrder partialUpdate(CustomerOrderResponseDto customerOrderResponseDto, CustomerOrder customerOrder) {
        if ( customerOrderResponseDto == null ) {
            return customerOrder;
        }

        if ( customerOrderResponseDto.id() != null ) {
            customerOrder.setId( Integer.parseInt( customerOrderResponseDto.id() ) );
        }
        if ( customerOrderResponseDto.totalAmount() != null ) {
            customerOrder.setTotalAmount( customerOrderResponseDto.totalAmount() );
        }
        if ( customerOrderResponseDto.createdDate() != null ) {
            customerOrder.setCreatedDate( customerOrderResponseDto.createdDate() );
        }
        if ( customerOrderResponseDto.lastModifiedDate() != null ) {
            customerOrder.setLastModifiedDate( customerOrderResponseDto.lastModifiedDate() );
        }
        if ( customerOrderResponseDto.customerId() != null ) {
            customerOrder.setCustomerId( customerOrderResponseDto.customerId() );
        }
        if ( customerOrderResponseDto.paymentMethod() != null ) {
            customerOrder.setPaymentMethod( customerOrderResponseDto.paymentMethod() );
        }
        if ( customerOrderResponseDto.reference() != null ) {
            customerOrder.setReference( customerOrderResponseDto.reference() );
        }

        return customerOrder;
    }
}

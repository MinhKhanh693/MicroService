package mk.orderservice.mappers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import mk.orderservice.controllers.responses.CustomerOrderLineResponseDto;
import mk.orderservice.entities.CustomerOrderLine;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-02T17:07:06+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class CustomerOrderLineMapperImpl implements CustomerOrderLineMapper {

    @Override
    public CustomerOrderLineResponseDto toDto(CustomerOrderLine customerOrderLine) {
        if ( customerOrderLine == null ) {
            return null;
        }

        Integer id = null;
        Integer productId = null;
        Double quantity = null;

        id = customerOrderLine.getId();
        productId = customerOrderLine.getProductId();
        quantity = customerOrderLine.getQuantity();

        CustomerOrderLineResponseDto customerOrderLineResponseDto = new CustomerOrderLineResponseDto( id, productId, quantity );

        return customerOrderLineResponseDto;
    }

    @Override
    public CustomerOrderLine toEntity(CustomerOrderLineResponseDto customerOrderLineResponseDto) {
        if ( customerOrderLineResponseDto == null ) {
            return null;
        }

        CustomerOrderLine.CustomerOrderLineBuilder customerOrderLine = CustomerOrderLine.builder();

        customerOrderLine.id( customerOrderLineResponseDto.id() );
        customerOrderLine.productId( customerOrderLineResponseDto.productId() );
        customerOrderLine.quantity( customerOrderLineResponseDto.quantity() );

        return customerOrderLine.build();
    }

    @Override
    public List<CustomerOrderLineResponseDto> toDtoList(List<CustomerOrderLine> customerOrderLines) {
        if ( customerOrderLines == null ) {
            return null;
        }

        List<CustomerOrderLineResponseDto> list = new ArrayList<CustomerOrderLineResponseDto>( customerOrderLines.size() );
        for ( CustomerOrderLine customerOrderLine : customerOrderLines ) {
            list.add( toDto( customerOrderLine ) );
        }

        return list;
    }

    @Override
    public List<CustomerOrderLine> toEntityList(List<CustomerOrderLineResponseDto> customerOrderLineResponseDTOs) {
        if ( customerOrderLineResponseDTOs == null ) {
            return null;
        }

        List<CustomerOrderLine> list = new ArrayList<CustomerOrderLine>( customerOrderLineResponseDTOs.size() );
        for ( CustomerOrderLineResponseDto customerOrderLineResponseDto : customerOrderLineResponseDTOs ) {
            list.add( toEntity( customerOrderLineResponseDto ) );
        }

        return list;
    }
}

package mk.orderservice.mappers;

import mk.orderservice.controllers.responses.CustomerOrderLineResponseDto;
import mk.orderservice.entities.CustomerOrderLine;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerOrderLineMapper {

    CustomerOrderLineResponseDto toDto(CustomerOrderLine customerOrderLine);

    CustomerOrderLine toEntity(CustomerOrderLineResponseDto customerOrderLineResponseDto);

    List<CustomerOrderLineResponseDto> toDtoList(List<CustomerOrderLine> customerOrderLines);

    List<CustomerOrderLine> toEntityList(List<CustomerOrderLineResponseDto> customerOrderLineResponseDTOs);
}

package mk.orderservice.mappers;

import mk.orderservice.controllers.requests.CustomerOrderRequestsDto;
import mk.orderservice.controllers.responses.CustomerOrderResponseDto;
import mk.orderservice.entities.CustomerOrder;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerOrderMapper {
    CustomerOrder toEntity(CustomerOrderRequestsDto customerOrderDto);

    CustomerOrderRequestsDto toDto(CustomerOrder customerOrder);

    List<CustomerOrder> toEntityList(List<CustomerOrderRequestsDto> customerOrderDTOs);

    List<CustomerOrderResponseDto> toDtoList(List<CustomerOrder> customerOrders);

    CustomerOrder toEntity(CustomerOrderResponseDto customerOrderResponseDto);

    CustomerOrderResponseDto toDto1(CustomerOrder customerOrder);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CustomerOrder partialUpdate(CustomerOrderResponseDto customerOrderResponseDto, @MappingTarget CustomerOrder customerOrder);
}

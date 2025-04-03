package mk.orderservice.services;

import mk.orderservice.controllers.responses.CustomerOrderLineResponseDto;

import java.util.List;

public interface ICustomerOrderLineService {
    List<CustomerOrderLineResponseDto> saveListCustomerOrderLine(List<CustomerOrderLineResponseDto> customerOrderLineRequestDTOs);

    CustomerOrderLineResponseDto saveCustomerOrderLine(CustomerOrderLineResponseDto customerOrderLineRequestDto);

    List<CustomerOrderLineResponseDto> findAllCustomerOrderLinesByOrderId(Integer orderId);
}

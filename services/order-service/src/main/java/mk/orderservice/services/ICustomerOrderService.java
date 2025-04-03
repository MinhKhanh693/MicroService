package mk.orderservice.services;

import mk.orderservice.controllers.requests.CustomerOrderRequestsDto;
import mk.orderservice.controllers.responses.CustomerOrderResponseDto;
import mk.orderservice.entities.CustomerOrder;

import java.util.List;

public interface ICustomerOrderService {
    CustomerOrderResponseDto createOrder(CustomerOrderRequestsDto order);
    CustomerOrderResponseDto findById(Integer id);
    List<CustomerOrderResponseDto> findAll();

}

package mk.orderservice.repositories;

import mk.orderservice.entities.CustomerOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerOrderLineRepository extends JpaRepository<CustomerOrderLine, Integer> {
    /**
     * Finds all customer order lines by the given order ID.
     *
     * @param orderId the ID of the order
     * @return a list of customer order lines associated with the specified order ID
     */
    List<CustomerOrderLine> findCustomerOrderLinesByOrderId(Integer orderId);
}
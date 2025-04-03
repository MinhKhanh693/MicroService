package mk.orderservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_order_line")
public class CustomerOrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_line_id_gen")
    @SequenceGenerator(name = "customer_line_id_gen", sequenceName = "customer_line_seq")
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private mk.orderservice.entities.CustomerOrder order;

    @Column(name = "product_id")
    private Integer productId;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Double quantity;

}
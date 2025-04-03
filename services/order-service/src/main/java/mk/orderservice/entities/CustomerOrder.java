package mk.orderservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import mk.orderservice.Enums.PaymentMethodTypeEnum;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "customer_order")
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_order_id_gen")
    @SequenceGenerator(name = "customer_order_id_gen", sequenceName = "customer_order_seq")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "total_amount", precision = 38, scale = 2)
    private BigDecimal totalAmount;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Size(max = 255)
    @Column(name = "customer_id")
    private String customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethodTypeEnum paymentMethod;

    @Size(max = 255)
    @NotNull
    @Column(name = "reference", nullable = false)
    private String reference;

    @OneToMany(mappedBy = "order")
    private Set<CustomerOrderLine> customerOrderLines = new LinkedHashSet<>();

}
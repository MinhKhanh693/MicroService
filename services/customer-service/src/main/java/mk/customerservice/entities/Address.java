package mk.customerservice.entities;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Address {
    private String street;
    private String houseNumber;
    private String zipCode;
}

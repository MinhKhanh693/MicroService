package mk.auth.service.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email; // Example: Added for email validation
import jakarta.validation.constraints.NotBlank; // Example: Added for required string fields
import jakarta.validation.constraints.NotNull; // Example: Added for required non-string fields
import lombok.Getter;
import lombok.Setter;
import mk.auth.service.enums.UserStatusEnum;
import mk.auth.service.enums.UserTypeEnum;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "tbl_user")
public class TblUserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_user_id_gen")
    @SequenceGenerator(name = "tbl_user_id_gen", sequenceName = "tbl_user_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @jakarta.validation.constraints.Size(max = 255)
    @NotBlank // Example: Add NotBlank if first name is required
    @Column(name = "first_name")
    private String firstName;

    @jakarta.validation.constraints.Size(max = 255)
    @NotBlank // Example: Add NotBlank if last name is required
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @jakarta.validation.constraints.Size(max = 255)
    @Column(name = "gender")
    private String gender; // Consider using an Enum for better type safety

    @jakarta.validation.constraints.Size(max = 255)
    @Column(name = "phone")
    private String phone;

    @jakarta.validation.constraints.Size(max = 255)
    @Email // Example: Add Email validation
    @NotBlank // Example: Add NotBlank if email is required
    @Column(name = "email", unique = true) // Added unique constraint if email should be unique
    private String email;

    @jakarta.validation.constraints.Size(max = 255)
    @NotBlank // Example: Add NotBlank if username is required
    @Column(name = "username", unique = true) // Added unique constraint if username should be unique
    private String username;

    @jakarta.validation.constraints.Size(max = 255)
    @NotBlank // Example: Add NotBlank if password is required
    @Column(name = "password")
    private String password;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", updatable = false) // Mark as not updatable
    private Instant createdAt;

    @ColumnDefault("gen_random_uuid()")
    @Column(name = "active_code", updatable = false) // Mark as not updatable
    private UUID activeCode;

    @ColumnDefault("CURRENT_TIMESTAMP") // Consider using @UpdateTimestamp for automatic updates
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "e_user_status")
    @NotNull // Status should likely not be null
    private UserStatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "e_user_type")
    @NotNull // Type should likely not be null
    private UserTypeEnum type;

    // FetchType.EAGER is convenient here for getAuthorities(), but consider FetchType.LAZY
    // for performance if users are loaded in contexts where roles aren't immediately needed.
    // If changed to LAZY, ensure roles are fetched explicitly before getAuthorities() is called.
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true) // Added orphanRemoval = true
    @JsonManagedReference
    private Set<TblUserHasRoleEntity> tblUserHasRoles = new LinkedHashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
        if (activeCode == null) {
            activeCode = UUID.randomUUID();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Simplified stream pipeline
        return tblUserHasRoles.stream()
                .map(TblUserHasRoleEntity::getRole)
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        // Explicitly return true if the account never expires based on time
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Explicitly return true if the account locking is handled only by the 'status' field
        // Or add a specific 'locked' field if needed
        return !UserStatusEnum.INACTIVE.equals(status); // Example: Map LOCKED status
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Explicitly return true if credentials never expire based on time
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Check if status is ACTIVE
        return UserStatusEnum.ACTIVE.equals(status);
    }

    // Consider implementing equals() and hashCode() based on the 'id' field
    // Be cautious with Lombok's @EqualsAndHashCode with JPA entities and relationships
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TblUserEntity that = (TblUserEntity) o;
        // Use ID for equality check if it's not null, otherwise rely on object identity
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        // Use ID for hash code if available, otherwise use default hash code
        return id != null ? Objects.hash(id) : super.hashCode();
    }
}
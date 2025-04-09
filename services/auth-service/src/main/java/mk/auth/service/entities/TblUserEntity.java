package mk.auth.service.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
    @Column(name = "first_name")
    private String firstName;

    @jakarta.validation.constraints.Size(max = 255)
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @jakarta.validation.constraints.Size(max = 255)
    @Column(name = "gender")
    private String gender;

    @jakarta.validation.constraints.Size(max = 255)
    @Column(name = "phone")
    private String phone;

    @jakarta.validation.constraints.Size(max = 255)
    @Column(name = "email")
    private String email;

    @jakarta.validation.constraints.Size(max = 255)
    @Column(name = "username")
    private String username;

    @jakarta.validation.constraints.Size(max = 255)
    @Column(name = "password")
    private String password;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "active_code")
    private UUID activeCode;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "e_user_status")
    private UserStatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "e_user_type")
    private UserTypeEnum type;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<mk.auth.service.entities.TblUserHasRoleEntity> tblUserHasRoles = new LinkedHashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> listRoleNames;
        List<TblRoleEntity> roles = tblUserHasRoles.stream().map(TblUserHasRoleEntity::getRole).toList();
        listRoleNames = roles.stream().map(tblRole -> tblRole.getName().name()).toList();
        return listRoleNames.stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserStatusEnum.ACTIVE.equals(status);
    }
}
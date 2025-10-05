package mk.auth.service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_permission")
public class TblPermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_permission_id_gen")
    @SequenceGenerator(name = "tbl_permission_id_gen", sequenceName = "tbl_permission_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @jakarta.validation.constraints.Size(max = 255)
    @Column(name = "method")
    private String method;

    @jakarta.validation.constraints.Size(max = 255)
    @Column(name = "\"group\"")
    private String group;

    @jakarta.validation.constraints.Size(max = 255)
    @Column(name = "description")
    private String description;

    @jakarta.validation.constraints.Size(max = 255)
    @Column(name = "path")
    private String path;

    @ManyToMany(mappedBy = "permissions", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<TblRoleEntity> roles = new LinkedHashSet<>();

    public void addRole(TblRoleEntity role) {
        if (roles == null) {
            roles = new LinkedHashSet<>();
        }
        roles.add(role);
        if (role.getPermissions() == null) {
            role.setPermissions(new LinkedHashSet<>());
        }
        role.getPermissions().add(this);
    }
}
package mk.auth.service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
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

    @OneToMany(mappedBy = "permission")
    private Set<mk.auth.service.entities.TblRoleHasPermissionEntity> tblRoleHasPermissions = new LinkedHashSet<>();

}
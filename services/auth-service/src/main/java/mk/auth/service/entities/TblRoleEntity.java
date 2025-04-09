package mk.auth.service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mk.auth.service.enums.RoleNameEnum;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbl_role")
public class TblRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_role_id_gen")
    @SequenceGenerator(name = "tbl_role_id_gen", sequenceName = "tbl_role_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToMany(mappedBy = "role")
    private Set<TblGroupEntity> tblGroups = new LinkedHashSet<>();

    @OneToMany(mappedBy = "role")
    private Set<mk.auth.service.entities.TblRoleHasPermissionEntity> tblRoleHasPermissions = new LinkedHashSet<>();
    @OneToMany(mappedBy = "role")
    private Set<mk.auth.service.entities.TblUserHasRoleEntity> tblUserHasRoles = new LinkedHashSet<>();

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private RoleNameEnum name;

}
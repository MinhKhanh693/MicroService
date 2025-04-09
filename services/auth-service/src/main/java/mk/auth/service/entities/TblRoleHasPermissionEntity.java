package mk.auth.service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_role_has_permission")
public class TblRoleHasPermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_role_has_permission_id_gen")
    @SequenceGenerator(name = "tbl_role_has_permission_id_gen", sequenceName = "tbl_role_has_permission_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private TblRoleEntity role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id")
    private TblPermissionEntity permission;

}
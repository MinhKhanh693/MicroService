package mk.auth.service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbl_group")
public class TblGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_group_id_gen")
    @SequenceGenerator(name = "tbl_group_id_gen", sequenceName = "tbl_group_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @jakarta.validation.constraints.Size(max = 255)
    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private mk.auth.service.entities.TblRoleEntity role;

    @OneToMany(mappedBy = "group")
    private Set<mk.auth.service.entities.TblUserHasGroupEntity> tblUserHasGroups = new LinkedHashSet<>();

}
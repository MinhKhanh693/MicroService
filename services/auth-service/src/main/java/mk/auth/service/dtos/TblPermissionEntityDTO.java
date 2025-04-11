package mk.auth.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TblPermissionEntityDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int id;
    private String description;
    private String group;
    private String method;
    private String path;
}
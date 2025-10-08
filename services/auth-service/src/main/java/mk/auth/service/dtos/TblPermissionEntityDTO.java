package mk.auth.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TblPermissionEntityDTO {
    private int id;
    private String description;
    private String group;
    private String method;
    private String path;
}
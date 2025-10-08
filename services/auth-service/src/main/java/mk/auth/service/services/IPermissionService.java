package mk.auth.service.services;

import mk.auth.service.dtos.TblPermissionEntityDTO;
import mk.auth.service.dtos.requests.CreateTblPermissionEntityRequestDTO;
import mk.auth.service.dtos.requests.UpdateTblPermissionEntityRequestDTO;
import mk.auth.service.dtos.response.TblPermissionEntityResponseDTO;

import java.util.List;

public interface IPermissionService {

    /**
     * Lấy danh sách Permission DTOs dựa trên danh sách tên Role (authorities).
     * Kết quả nên được cache.
     *
     * @param authorities Danh sách tên các vai trò
     * @return Danh sách các TblPermissionEntityDTO liên quan.
     */
    List<TblPermissionEntityDTO> getPermissionsByRoleNames(List<String> authorities);

    TblPermissionEntityResponseDTO createPermission(CreateTblPermissionEntityRequestDTO permissionDTO);

    TblPermissionEntityResponseDTO updatePermission(UpdateTblPermissionEntityRequestDTO permissionDTO);

    TblPermissionEntityResponseDTO DeletePermission(Integer id);
}
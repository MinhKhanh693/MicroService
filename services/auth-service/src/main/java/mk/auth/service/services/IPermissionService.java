package mk.auth.service.services;
import mk.auth.service.dtos.TblPermissionEntityDTO;

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

    // Có thể thêm các phương thức khác liên quan đến quản lý/lấy permission nếu cần
    // Ví dụ: void clearRolePermissionsCache(); // Để quản lý việc xóa cache tập trung
}
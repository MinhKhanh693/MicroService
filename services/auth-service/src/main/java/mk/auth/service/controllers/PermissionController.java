package mk.auth.service.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.auth.service.dtos.requests.CreateTblPermissionEntityRequestDTO;
import mk.auth.service.dtos.requests.UpdateTblPermissionEntityRequestDTO;
import mk.auth.service.dtos.response.TblPermissionEntityResponseDTO;
import mk.auth.service.services.IPermissionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auths/permissions")
@Slf4j(topic = "PERMISSION_CONTROLLER")
@RequiredArgsConstructor
@Tag(name = "Permission Controller", description = "This is a simple permission controller")
public class PermissionController {
    private final IPermissionService permissionService;

    @Operation(summary = "Get permission by ID", description = "This is a simple endpoint to get permission by ID")
    @PostMapping("/create")
    public TblPermissionEntityResponseDTO createPermission(
            @RequestBody CreateTblPermissionEntityRequestDTO permissionDTO
    ) {
        log.info("Creating a new permission");
        return permissionService.createPermission(permissionDTO);
    }

    @Operation(summary = "Update permission", description = "This is a simple endpoint to update permission")
    @PutMapping("/update")
    public TblPermissionEntityResponseDTO updatePermission(
            @RequestBody UpdateTblPermissionEntityRequestDTO permissionDTO
    ) {
        log.info("Updating a permission");
        return permissionService.updatePermission(permissionDTO);
    }

    @Operation(summary = "Delete permission", description = "This is a simple endpoint to delete permission")
    @DeleteMapping("/delete/{id}")
    public TblPermissionEntityResponseDTO deletePermission(
            @PathVariable Integer id
    ) {
        log.info("Deleting a permission with ID: {}", id);
        return permissionService.DeletePermission(id);
    }
}

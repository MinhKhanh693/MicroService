package mk.user.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.user.service.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j(topic = "USER_CONTROLLER")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping("/import/excel")
    public ResponseEntity importListUserFromExcel(@RequestParam("file") MultipartFile file) throws Exception {
        log.info("Importing user list from Excel");
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vui lòng chọn một file Excel để upload.");
        }

        // Kiểm tra định dạng file (ví dụ: .xlsx, .xls)
        String contentType = file.getContentType();
        if (contentType == null ||
                (!contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") && // .xlsx
                        !contentType.equals("application/vnd.ms-excel"))) { // .xls
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Chỉ hỗ trợ file định dạng Excel (.xls, .xlsx).");
        }

        var listUserSaved = this.userService.importListUserFromExcel(file.getInputStream());
        return ResponseEntity.ok(listUserSaved);
    }
}

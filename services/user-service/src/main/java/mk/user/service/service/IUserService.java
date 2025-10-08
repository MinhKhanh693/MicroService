package mk.user.service.service;

import mk.user.service.dto.response.TblUserEntityResponseDTO;

import java.io.InputStream;
import java.util.List;

public interface IUserService {
    List<TblUserEntityResponseDTO> importListUserFromExcel(InputStream excelFile) throws Exception;
}

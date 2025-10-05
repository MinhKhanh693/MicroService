package mk.user.service.service;

import mk.user.service.dto.TblUserEntityExcelExportDTO;
import mk.user.service.dto.TblUserEntityExcelImportDTO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.List;

public interface IExcelService {
    List<Map<String, String>> readExcelData(InputStream inputStream) throws Exception;

    List<TblUserEntityExcelImportDTO> readAndMapToObject(InputStream inputStream);

    public ByteArrayInputStream writeExcelData(List<TblUserEntityExcelExportDTO> dataList) throws IOException;
}

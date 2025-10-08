package mk.user.service.service.impl;

import mk.user.service.dto.TblUserEntityExcelExportDTO;
import mk.user.service.dto.TblUserEntityExcelImportDTO;
import mk.user.service.enums.UserStatusEnum;
import mk.user.service.enums.UserTypeEnum;
import mk.user.service.service.IExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

@Service
public class ExcelServiceImpl implements IExcelService {

    @Override
    public List<Map<String, String>> readExcelData(InputStream inputStream) throws Exception {
        List<Map<String, String>> dataList = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();
            Iterator<Row> rowIterator = sheet.iterator();

            Row headerRow = rowIterator.hasNext() ? rowIterator.next() : null;

            while (rowIterator.hasNext()) {
                Row currentRow = rowIterator.next();
                Map<String, String> rowData = new HashMap<>();
                Iterator<Cell> cellIterator = currentRow.cellIterator();

                int cellIndex = 0;
                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    String cellValue = dataFormatter.formatCellValue(currentCell);
                    String headerName = (headerRow != null && cellIndex < headerRow.getLastCellNum()) ?
                            dataFormatter.formatCellValue(headerRow.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)) :
                            "Column_" + cellIndex;

                    rowData.put(headerName, cellValue);
                    cellIndex++;
                }
                if (!rowData.isEmpty()) {
                    dataList.add(rowData);
                }
            }
        } catch (IOException e) {
            throw new Exception("Error reading Excel file: " + e.getMessage(), e);
        }
        return dataList;
    }

    @Override
    public List<TblUserEntityExcelImportDTO> readAndMapToObject(InputStream inputStream) {
        List<TblUserEntityExcelImportDTO> dtoList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();
            Iterator<Row> rowIterator = sheet.iterator();

            if (!rowIterator.hasNext()) {
                return dtoList; // Return empty list if sheet is empty
            }

            rowIterator.next(); // Skip header row

            while (rowIterator.hasNext()) {
                Row currentRow = rowIterator.next();
                dtoList.add(mapRowToDto(currentRow, dataFormatter));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading Excel file: " + e.getMessage(), e);
        }

        return dtoList;
    }

    private TblUserEntityExcelImportDTO mapRowToDto(Row currentRow, DataFormatter dataFormatter) {
        return new TblUserEntityExcelImportDTO(
                dataFormatter.formatCellValue(currentRow.getCell(0)),
                dataFormatter.formatCellValue(currentRow.getCell(1)),
                LocalDate.parse(dataFormatter.formatCellValue(currentRow.getCell(2))),
                dataFormatter.formatCellValue(currentRow.getCell(3)),
                dataFormatter.formatCellValue(currentRow.getCell(4)),
                dataFormatter.formatCellValue(currentRow.getCell(5)),
                dataFormatter.formatCellValue(currentRow.getCell(6)),
                dataFormatter.formatCellValue(currentRow.getCell(7)),
                UserStatusEnum.valueOf(dataFormatter.formatCellValue(currentRow.getCell(8))),
                UserTypeEnum.valueOf(dataFormatter.formatCellValue(currentRow.getCell(9)))
        );
    }

    @Override
    public ByteArrayInputStream writeExcelData(List<TblUserEntityExcelExportDTO> dataList) throws IOException {
        String[] columns = {"ID", "Tên Dữ Liệu", "Giá Trị"};

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Dữ liệu");

            // Header Style
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerFont.setColor(IndexedColors.BLACK.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

            // Create Header Row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                createCell(headerRow, i, columns[i], headerCellStyle);
            }

            // Write Data Rows
            int rowNum = 1;
            for (TblUserEntityExcelExportDTO data : dataList) {
                Row row = sheet.createRow(rowNum++);
                createCell(row, 0, data.id(), null);
                createCell(row, 1, data.firstName(), null);
                createCell(row, 2, data.lastName(), null);
            }

            // Auto-size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        }
    }

    private void createCell(Row row, int column, Object value, CellStyle style) {
        Cell cell = row.createCell(column);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value != null) {
            cell.setCellValue(value.toString());
        }

        if (style != null) {
            cell.setCellStyle(style);
        }
    }
}
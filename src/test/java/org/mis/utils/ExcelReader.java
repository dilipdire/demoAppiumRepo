package org.mis.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {
    private String fName;
    private String sName;

    public ExcelReader(String excelFileName, String sheetName) {
        fName = excelFileName;
        sName = sheetName;
    }

    public List<String> getExcelDataByColumn(int columnIndex) {
        List<String> columndata = null;
        try {
            FileInputStream fis = new FileInputStream(getfileName());
            Workbook workbook = null;
            if (getfileName().toLowerCase().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (getfileName().toLowerCase().endsWith("xls")) {
                workbook = new HSSFWorkbook(fis);
            }
            columndata = new ArrayList<>();
            Sheet sheet = workbook.getSheet(getsheetName());
            int totalColumns = sheet.getRow(0).getPhysicalNumberOfCells();
            int lastRowNum = sheet.getLastRowNum();
            int lastCellNum = sheet.getRow(0).getLastCellNum();
            //System.out.println(totalColumns);
            for (int i = 0; i == totalColumns; i++){
                Iterator<Row> rowIterator = sheet.iterator();
                columndata = new ArrayList<>();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        if (row.getRowNum() > 0) { //To filter column headings
                            if (cell.getColumnIndex() == columnIndex) {// To match column index
                                columndata.add(cell.getStringCellValue());
                            }
                        }
                    }
                }
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columndata;
    }

    public HashMap<String, List<String>> getDataInMap(List<String> listToProcess) {

            String key = listToProcess.get(0);
            listToProcess.remove(0);
            HashMap<String, List<String>> toReturn = new HashMap<>();
            toReturn.put(key, listToProcess);
            System.out.println(toReturn);
            return toReturn;
        }


    public String getfileName() {
        return fName;
    }

    public String getsheetName() {
        return sName;
    }

    public static void main(String[] args) {
        ExcelReader reader = new ExcelReader(System.getProperty("user.dir") + "/src/test/resources/MoveInSyncETS_Excel.xls", "EscortDataForTripBased");
        reader.getDataInMap(reader.getExcelDataByColumn(0));
    }
}
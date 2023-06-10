package org.mis.utils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
public class downloadedFileHelper {
    public static Map<String, Map<Integer, Row>> excelDataMap;
    public static void readExcelData() {
        if (excelDataMap == null) {
            FileInputStream file;
            HSSFWorkbook workBook = null;
            excelDataMap = new TreeMap<String, Map<Integer, Row>>(String.CASE_INSENSITIVE_ORDER);
            try {
                file = new FileInputStream(new File(System.getProperty("user.dir") + "/home/Downloads/01-05-2020-31-05-2020TripWise_Costing_8681.zip"));
                workBook = new HSSFWorkbook(file);
                Iterator<Sheet> iterator = workBook.iterator();
                while (iterator.hasNext()) {
                    Sheet sheet = iterator.next();
                    int rowNumbers = sheet.getPhysicalNumberOfRows();
                    Map<Integer, Row> sheetDataMap = new HashMap<Integer, Row>();
                    for (int i = 1; i < rowNumbers; i++) {
                        sheetDataMap.put(i, sheet.getRow(i));
                    }
                    System.out.println("name- " + sheet.getSheetName());
                    excelDataMap.put(sheet.getSheetName(), sheetDataMap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    workBook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static String getCellValue(String sheetName, int rowNum, int colNum)
    {
        readExcelData();
        String val = "noVal";
        try {
            val = excelDataMap.get(sheetName).get(rowNum).getCell(colNum).getStringCellValue();
        } catch (Exception e) {
            val = String.valueOf(excelDataMap.get(sheetName).get(rowNum).getCell(colNum).getNumericCellValue());
            val = val.substring(0, val.length() - 2);
        }
        System.out.println(val);
        return val;
    }
}
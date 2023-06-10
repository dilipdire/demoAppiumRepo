package org.mis.utils;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.common.collect.Table;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
public class ExcelUtilities {
    public static Map<String, Map<Integer, Row>> excelDataMap;
    public static Map<String, Map<Integer, List<String>>> csvMap;
    public static void readExcelData(String fileName) {
        FileInputStream file;
        HSSFWorkbook workBook = null;
        excelDataMap = new TreeMap<String, Map<Integer, Row>>(String.CASE_INSENSITIVE_ORDER);
        try {
            file = new FileInputStream(new File(FileConstant.USER_DIR + fileName));
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
    public static void readCSVData(String fileName) {
        FileInputStream file;
        HSSFWorkbook workBook = null;
        csvMap = new HashMap<String, Map<Integer, List<String>>>();
        try {
            File inputF = new File(FileConstant.DOWNLOAD_DIR + fileName);
            InputStream inputFS = new FileInputStream(inputF);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
            String line = br.readLine();
            // loop until all lines are read
            Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();
            int lineNum = 0;
            while (line != null) {
                if (lineNum < 2) {
                    lineNum++;
                    line = br.readLine();
                    continue;
                }
                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                try {
                    String[] attributes = line.split(",");
                    String tripId = attributes[0].replaceAll("^\"|\"$", "");
                    map.put(Integer.parseInt(tripId), Arrays.asList(attributes));
                } catch (Exception e) {
                    System.out.println("some issue in input");
                }
                lineNum++;
                line = br.readLine();
            }
            csvMap.put(fileName, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //    public static void testing() {
//        getCsvVal("01-06-2020-30-06-2020TripWise_Costing_58a5.csv", 597832,10);
//    }
    public static String getCellValue(String fileName, String sheetName, int rowNum, int colNum) {
        System.out.println(" file " + fileName + " sheet " + sheetName);
        if (excelDataMap == null || !excelDataMap.containsKey(sheetName)) {
            readExcelData(fileName);
        }
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
    public static String getCsvVal(String fileName, int rowNum, int colNum) {
        System.out.println(" file " + fileName + " sheet " + fileName);
        if (csvMap == null || !csvMap.containsKey(fileName)) {
            readCSVData(fileName);
        }
        String val2 = csvMap.get(fileName).get(rowNum).get(colNum);
        String val = csvMap.get(fileName).get(rowNum).get(colNum).replaceAll("^\"|\"$", "");
        System.out.println(val);
        return val;
    }
}
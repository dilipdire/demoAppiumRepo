package org.mis.utils;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class DataProviderMethod {
    @DataProvider(name ="getdata")
    public Object[][] dataproviderMethod() {

        FileInputStream fis = null;
        HSSFSheet ws = null;
        HSSFWorkbook wb = null;
        String filePath = System.getProperty("user.dir") + "/src/test/resources/Escort.xls";

        File xls = new File(filePath);
        try {
            fis = new FileInputStream(xls);
        } catch (FileNotFoundException e) {
            //
            e.printStackTrace();
        }
        // XSSFWorkbook wb;
        try {
            wb = new HSSFWorkbook(fis);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        ws = wb.getSheet("TripBased");
        int rowcount = ws.getLastRowNum();
        int colcount = ws.getRow(0).getLastCellNum();
        Object[][] data = new Object[rowcount][1];

        for (int r = 0; r < rowcount; r++) {
            // Create Hashmap after every row iteration
            HashMap<Object, Object> hm = new HashMap<Object, Object>();
            for (int c = 0; c < colcount; c++) {
                hm.put(ws.getRow(0).getCell(c).toString(), ws.getRow(r + 1).getCell(c).toString());

            }

            data[r][0] = hm;
            // Add every row in hashmap
        }

        try {
            wb.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return data;
    }

}

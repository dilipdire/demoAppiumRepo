package org.mis.utils;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataProviderTest {

    @DataProvider(name = "addCabContract")
    public Object[][] AddCabContract() throws IOException {
        System.out.println("CabContract:");
        Object[][] arrayObject = getExcelData("/src/test/resources/CabContract.xls", "CabContract");
        System.out.println("filename2:");
        return arrayObject;
    }
    @DataProvider(name = "uploadCabContract")
    public Object[][] UploadCabContract() throws IOException {
        System.out.println("CabContract:");
        Object[][] arrayObject = getExcelData("cabcontract-template.csv", "cabcontract-template");
        System.out.println("filename2:");
        return arrayObject;
    }
    @DataProvider(name = "parking")
    public Object[][] Parking() throws IOException {
        System.out.println("parking:");
        Object[][] arrayObject = getExcelData("/src/test/resources/Parking.xls", "Parking");
        System.out.println("parking:");
        return arrayObject;
    }
    @DataProvider(name = "acSlabAll")
    public Object[][] TripBasedACSlabAll() throws IOException {
        System.out.println("filename1:");
        Object[][] arrayObject = getExcelData("/src/test/resources/ACTripbased.xls", "TripBasedACSlabAll");
        System.out.println("filename2:");
        return arrayObject;
    }
    @DataProvider(name = "addAdjustment")
    public Object[][] addAdjustmentBullUpload() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/adjustment-download.xlsx", "data");
        return arrayObject;
    }
    @DataProvider(name = "addVendorContractUpload")
    public Object[][] addVendorContractBullUpload() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/Downloaded-VendorContract.csv", "data");
        return arrayObject;
    }
    @DataProvider(name = "addManualTollUpload")
    public Object[][] addManualTollBullUpload() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/Downloaded-manualContract.csv", "data");
        return arrayObject;
    }
    @DataProvider(name = "addFuelHikeUpload")
    public Object[][] addFuelHikeBulkUpload() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/Downloaded-Fuelhike.csv", "Downloaded-Fuelhike");
        return arrayObject;
    }

    @DataProvider(name = "acSlabTripOnly")
    public Object[][] TripBasedACSlabTripOnly() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/ACTripbased.xls", "TripBasedACSlabTrip_Only");
        return arrayObject;
    }
    @DataProvider(name = "billCycleGarageSlab")
    public Object[][] BillCycleBasedGarageSlab() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/Garage.xls", "GarageBillCycle");
        return arrayObject;
    }

    @DataProvider(name = "acSlabTripNA")
    public Object[][] TripBasedACSlabNA() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/ACTripbased.xls", "TripBasedACSlabNA");
        return arrayObject;
    }

    @DataProvider(name = "KmSlabEscortData")
    public Object[][] KMSlabEscort() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/Escort.xls", "KmSlab");
        return arrayObject;
    }

    @DataProvider(name = "TotalCalTripPackageData")
    public Object[][] TotalCalTripPackage() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/contractForTotalCalcuation.xls", "TripBased");
        return arrayObject;
    }

    @DataProvider(name = "TotalCalZoneBasedData")
    public Object[][] TotalCalZoneBased() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/contractForTotalCalcuation.xls", "ZoneBased");
        return arrayObject;
    }

    @DataProvider(name = "TotalCalDailyPackageData")
    public Object[][] TotalCalDailyPackage() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/contractForTotalCalcuation.xls", "Sheet6");
        return arrayObject;
    }

    @DataProvider(name = "TotalCalBillCyclePackageData")
    public Object[][] TotalCalBillCyclePackage() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/contractForTotalCalcuation.xls", "BillCyclePackage");
        return arrayObject;
    }
    @DataProvider(name = "TotalCalEmployeeBasedData")
    public Object[][] TotalCalEmployeeBased() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/contractForTotalCalcuation.xls", "EmployeeBased");
        return arrayObject;
    }

    @DataProvider(name = "EmployeeBasedEscortData")
    public Object[][] employeeBasedEscort() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/Escort.xls", "EmployeeBased");
        return arrayObject;
    }

    @DataProvider(name = "EmployeeACSlab")
    public Object[][] employeeBasedAc() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/ACTripBased.xls", "EmployeeBasedACSlab");
        return arrayObject;
    }

    @DataProvider(name = "ZoneACSlab")
    public Object[][] zoneBasedAc() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/ACTripBased.xls", "ZoneBasedACSlab");
        return arrayObject;
    }

    @DataProvider(name = "DailyACSlab")
    public Object[][] dailyBasedAc() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/ACTripBased.xls", "DailyBasedACSlab");
        return arrayObject;
    }

    @DataProvider(name = "TripPackageFirstLastEscortData")
    public Object[][] TripPackageFirstLastEscort() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/Escort.xls", "FirstLastTripForTripBased");
        return arrayObject;
    }

    @DataProvider(name = "zoneBasedEscort")
    public Object[][] zoneBasedEscort() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/Escort.xls", "ZoneBased");
        return arrayObject;
    }

    @DataProvider(name = "kmSlabFirstLastTripEscortData")
    public Object[][] kmSlabFirstLastTripEscort() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/Escort.xls", "FirstLastTripForKmSlab");
        return arrayObject;
    }

    @DataProvider(name = "kmSlabB2BData")
    public Object[][] kmSlabB2B() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/B2B.xls", "KmSlab");
        return arrayObject;
    }
    @DataProvider(name = "fuelHikeDailyPackageData")
    public Object[][] fuelHikeDailyPackage() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/FuelHike.xls", "DailyPackage");
        return arrayObject;
    }

    @DataProvider(name = "kmSlabTripAdjustmentData")
    public Object[][] kmSlabTripAdjustment() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/TripAdjustmentData.xls", "Sheet3");
        return arrayObject;
    }

    @DataProvider(name = "tripPackageTripAdjustmentData")
    public Object[][] tripPackageTripAdjustment() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/TripAdjustmentData.xls", "TripPackage");
        return arrayObject;
    }

    @DataProvider(name = "kmSlabEmployeeAdjustmentData")
    public Object[][] kmSlabEmployeeAdjustment() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/EmployeeAdjustmentData.xls", "Sheet3");
        return arrayObject;
    }
    @DataProvider(name = "ContractDataForValidation")
    public Object[][] contractDataForValidation() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/ContractForValidation.xls", "Data");
        return arrayObject;
    }
    @DataProvider(name = "ContractData")
    public Object[][] contractData() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/ContractForValidation.xls", "Contract");
        return arrayObject;
    }


    @DataProvider(name = "tripSlabEmployeeAdjustmentData")
    public Object[][] tripSlabEmployeeAdjustment() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/EmployeeAdjustmentData.xls", "Sheet3");
        return arrayObject;
    }

    @DataProvider(name = "kmSlabShiftsData")
    public Object[][] kmSlabShifts() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/ShiftsSlabData.xls", "Sheet3");
        return arrayObject;
    }

    @DataProvider(name = "zoneBasedFirstLastEscortData")
    public Object[][] zoneBasedFirstLastTripEscort() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/Escort.xls", "FirstLastForZoneBased");
        return arrayObject;
    }

    @DataProvider(name = "kmSlabData")
    public Object[][] kmSlab() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/KmSlabBillingModel.xls", "KmSlab");
        return arrayObject;
    }

    @DataProvider(name = "tripPackageEscortData")
    public Object[][] tripPackageEscort() throws IOException {
        Object[][] arrayObject = getExcelData("/src/test/resources/Escort.xls", "Sheet12");
        return arrayObject;
    }


    public Object[][] getExcelData(String fileName, String sheetName) throws IOException {
        Object[][] obj = null;
        try {
            System.out.println("filename:" + fileName);
            FileInputStream fis = new FileInputStream(new File(FileConstant.USER_DIR + fileName));
            System.out.println("filename:" + fileName);
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            System.out.println("filename:" + sheetName);
            HSSFSheet ws = wb.getSheet(sheetName);
            System.out.println("filename:" + sheetName);
            //identify row and column count
            int rowcount = ws.getLastRowNum();
            int colcount = ws.getRow(0).getLastCellNum();
            //define object array
            obj = new Object[rowcount][1];
            //to access the data from sheet
            for (int i = 0; i < rowcount; i++) {
                //define map
                Map<Object, Object> datamap = new HashMap<Object, Object>();
                for (int j = 0; j < colcount; j++) {
                    //read cell data and store in map
                    datamap.put(ws.getRow(0).getCell(j).toString(), ws.getRow(i + 1).getCell(j).toString());
                }
                obj[i][0] = datamap;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
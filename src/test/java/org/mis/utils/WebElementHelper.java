package org.mis.utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class WebElementHelper {
    private static final String TRIP_BASED_CONTRACT_SHEET = "TripBased";
    //private static final String SHEET_NAME = "Sheet0";
    private static final String KM_SLAB = "KmSlab";
    private static final String ZONE_BASED = "ZoneBased";
    private static final String EMPLOYEE_BASED = "EmployeeBased";
    private static final String ZONE_VENDOR_BASED = "ZoneVendorBased";
    private static final String BILL_CYCLE_PACKAGE = "BillCyclePackage";
    private static final String DAILY_BASED_CONTRACT_SHEET = "DailyBased";
    private static final String OUTSTATION_BASED_CONTRACT_SHEET = "OutstationBased";
    private static final String GRID_BASED_CONTRACT_SHEET = "GridBased";

    private static final String ESCORT = "Escort";
    private static final String SHEET_NAME1 = "01-05-2020-31-05-2020TripWise_Costing_8681";

    public static void putValueInDropdown(int row, int col, WebElement webElement) {
        String val = getTripBasedCellValue(row, col);
        Select dropdown = new Select(webElement);
        dropdown.selectByVisibleText(val);
    }
    public static String getTripBasedCellValue(int row, int col) {
        return getCellValue(TRIP_BASED_CONTRACT_SHEET, row, col);
    }
    public static void putValueInDropdownKm(int row, int col, WebElement webElement) {
        String val = getKmSlabCellValue( row, col);
        Select dropdown = new Select(webElement);
        dropdown.selectByVisibleText(val);
    }
    public static String getKmSlabCellValue(int row, int col) {
        return getCellValue(KM_SLAB, row, col);
    }
    public static void putValueInDropdownZone(int row, int col, WebElement webElement) {
        String val = getZoneBasedCellValue( row, col);
        Select dropdown = new Select(webElement);
        dropdown.selectByVisibleText(val);
    }
    public static String getZoneBasedCellValue(int row, int col) {
        return getCellValue(ZONE_BASED, row, col);
    }
    public static void putValueInDropdownEmployee(int row, int col, WebElement webElement) {
        String val = getEmployeeBasedCellValue( row, col);
        Select dropdown = new Select(webElement);
        dropdown.selectByVisibleText(val);
    }
    public static String getEmployeeBasedCellValue(int row, int col) {
        return getCellValue(EMPLOYEE_BASED, row, col);
    }
    //public static void getValueInDropdown(int row, int col) {
     //   String val = ExcelUtilities.getCellValue(SHEET_NAME1, row, col);

   // }
   public static void putValueInDropdownZoneVendor(int row, int col, WebElement webElement) {
       String val = getZoneVendorBasedCellValue( row, col);
       Select dropdown = new Select(webElement);
       dropdown.selectByVisibleText(val);
   }
    public static String getZoneVendorBasedCellValue(int row, int col) {
        return getCellValue(ZONE_VENDOR_BASED, row, col);
    }
    public static void putValueInDropdownBillCyclePackage(int row, int col, WebElement webElement) {
        String val = getBillCyclePackageellValue( row, col);
        Select dropdown = new Select(webElement);
        dropdown.selectByVisibleText(val);
    }
    public static String getBillCyclePackageellValue(int row, int col) {
        return getCellValue(BILL_CYCLE_PACKAGE, row, col);
    }
    public static void putValueInDropdownEscort(int row, int col, WebElement webElement) {
        String val = getEscortValue( row, col);
        Select dropdown = new Select(webElement);
        dropdown.selectByVisibleText(val);
    }
    public static String getEscortValue(int row, int col) {
        return getCellValue(ESCORT, row, col);
    }
    private static String getCellValue(String sheetName, int row, int col) {
        return ExcelUtilities.getCellValue(FileConstant.CONTRACT_FILE, sheetName, row, col);
    }
    public static void putValueInGridDropdown(int row, int col, WebElement webElement) {
        String val = getZoneBasedCellValue(row, col);
        Select dropdown = new Select(webElement);
        dropdown.selectByVisibleText(val);
    }
    public static void putValueInDailyDropdown(int row, int col, WebElement webElement) {
        String val = getDailyBasedCellValue(row, col);
        Select dropdown = new Select(webElement);
        dropdown.selectByVisibleText(val);
    }
    public static void putValueInOutstationDropdown(int row, int col, WebElement webElement) {
        String val = getOutstationBasedCellValue(row, col);
        Select dropdown = new Select(webElement);
        dropdown.selectByVisibleText(val);
    }
    public static String getDailyBasedCellValue(int row, int col) {
        return getCellValue(DAILY_BASED_CONTRACT_SHEET, row, col);
    }
    public static String getOutstationBasedCellValue(int row, int col) {
        return getCellValue(OUTSTATION_BASED_CONTRACT_SHEET, row, col);
    }
    public static String getGridBasedCellValue(int row, int col) {
        return getCellValue(GRID_BASED_CONTRACT_SHEET, row, col);
    }


    }

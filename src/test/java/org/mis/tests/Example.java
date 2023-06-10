//package org.mis.tests;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.MappingIterator;
//import com.fasterxml.jackson.dataformat.csv.CsvMapper;
//import com.fasterxml.jackson.dataformat.csv.CsvSchema;
////import com.jayway.restassured.RestAssured;
//import com.jayway.restassured.http.ContentType;
////import com.jayway.restassured.http.Method;
////import com.jayway.restassured.response.Response;
////import com.jayway.restassured.specification.RequestSpecification;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.mis.pom.LoginPage;
//import org.mis.pom.NewBillingReportPage;
//import org.mis.utils.FileConstant;
//import org.mis.utils.PropertyReader;
//import org.openqa.selenium.WebElement;
//import org.testng.Assert;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//import io.restassured.RestAssured;
//import io.restassured.http.Method;
//import io.restassured.response.Response;
//import io.restassured.specification.RequestSpecification;
//
//import java.io.*;
//import java.util.*;
//
//public class Example extends BaseTest {
//
//    @Test(dataProvider = "getdata")
//    public void integrationTest(Map<Object, Object> map) {
//        System.out.println("-------------Test case started ----------------");
//        System.out.println(map.get("ContractName"));
//        //String output = "";
//
//        // try {
//        // output = map.get("OutputResultFirstTrip").toString();
//        // } catch (Exception e) {
//        //output = map.get("OutputResultFirstTrip").toString();
//        //output = output.replaceAll("\\.0*$", "");
//        //output=output.substring(0, output.length() - 1);
//        //}
//        //String output = map.get("OutputResultFirstTrip").toString();
//        // String Output=output.substring(0, output.length() - 2);
//        //System.out.println(output);
//        //System.out.println(map.get("OutputResultFirstTrip").toString());
//        System.out.println("-------------Test case Ended ----------------");
//
//      /* Iterator<Map.Entry<Object, Object>> itr = map.entrySet().iterator();
//
//       while(itr.hasNext())
//       {
//           Map.Entry<Object, Object> entry = itr.next();
//           Object key = entry.getKey();
//           System.out.println(key);
//           System.out.println("Key = " + entry.getKey() +
//                   ", Value = " + entry.getValue());
//            key = entry.getValue();
//           System.out.println(key);
//       }*/
//
//    }
//
//    @DataProvider(name = "data")
//    public Object[][] dataSupplier() throws IOException {
//
//        File file = new File(System.getProperty("user.dir") + "/src/test/resources/contractTest.xls");
//        FileInputStream fis = new FileInputStream(file);
//
//        HSSFWorkbook wb = new HSSFWorkbook(fis);
//        HSSFSheet sheet = wb.getSheetAt(0);
//        // wb.close();
//        int lastRowNum = sheet.getLastRowNum();
//        int lastCellNum = sheet.getRow(0).getLastCellNum();
//        Object[][] obj = new Object[lastRowNum][1];
//
//        for (int i = 0; i < lastRowNum - 1; i++) {
//            Map<Object, Object> datamap = new HashMap<>();
//            for (int j = 0; j < lastCellNum; j++) {
//                datamap.put(sheet.getRow(0).getCell(j).toString(), sheet.getRow(i + 1).getCell(j).toString());
//            }
//            obj[i][0] = datamap;
//
//        }
//        return obj;
//
//    }
//
//    @DataProvider(name = "getdata")
//    public Object[][] dataproviderMethod() {
//
//        FileInputStream fis = null;
//        HSSFSheet ws = null;
//        HSSFWorkbook wb = null;
//        String filePath = System.getProperty("user.dir") + "/src/test/resources/Demo.xls";
//
//        File xls = new File(filePath);
//        try {
//            fis = new FileInputStream(xls);
//        } catch (FileNotFoundException e) {
//            //
//            e.printStackTrace();
//        }
//        // XSSFWorkbook wb;
//        try {
//            wb = new HSSFWorkbook(fis);
//        } catch (IOException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        ws = wb.getSheet("Sheet2");
//        int rowcount = ws.getLastRowNum();
//        int colcount = ws.getRow(0).getLastCellNum();
//        Object[][] data = new Object[rowcount][1];
//
//        for (int r = 0; r < rowcount; r++) {
//
//            // Create Hashmap after every row iteration
//            HashMap<Object, Object> hm = new HashMap<Object, Object>();
//            for (int c = 0; c < colcount; c++) {
//                if (ws.getRow(r).getCell(c).toString().equalsIgnoreCase("true")) {
//                    hm.put(ws.getRow(0).getCell(c).toString(), ws.getRow(r + 1).getCell(c).toString());
//                }
//            data[r][0] = hm;
//            // String.format("%.0f", data);
//            // Add every row in hashmap
//        }
//
//        try {
//            wb.close();
//        } catch (IOException e) {
//
//            e.printStackTrace();
//        }
//
//        return data;
//    }
//
//    public static void main(String[]Object args;
//        args) {
//        String startDate = "2020-07-01";
//        String endDate = "2020-07-31";
//        String callerId = "mistm@moveinsync.com";
//
//        // Specify base URI
//        System.out.println("************************RegisterMobileApp****************************");
//        /*RestAssured.baseURI = "http://staging2.moveinsync.com:8082/bill-calculation/qa-Test6/downloadbill";
//        System.out.println(RestAssured.baseURI);
//        RequestSpecification httpRequest = RestAssured.given().log().all();
//        // Response objec
//        Response response = (Response) httpRequest.request();
//        String responseBody = response.getBody().asString();
//        System.out.println("Response Body is:" + responseBody);
//        int statusCode = response.getStatusCode();
//        System.out.println("Status code is: " + statusCode);
//        Assert.assertEquals(statusCode, 200);
//        System.out.println("********************************************************");*/
//        // status line verification
//
//
//
//        /*RestAssured
//                .given()
//                .queryParam("startDate", "2020-07-01")
//                .queryParam("endDate", "2020-07-31")
//                .queryParam("callerId", "mistm@moveinsync.com")
//                .baseUri("http://staging2.moveinsync.com:8082/bill-calculation")
//                .when()
//                .get("qa-Test6/br")
//                .then()
//
//                .assertThat()
//                .statusCode(200);
//
//        System.out.println("********************************************************");*/
//     /*  System.out.println("************************billCalculationAPI****************************");
//        RestAssured.baseURI = "http://staging2.moveinsync.com:8082/bill-calculation";
//        System.out.println(RestAssured.baseURI);
//        RequestSpecification httpRequest = RestAssured.given().log().all();
//        httpRequest.queryParam("startDate", "2020-07-01");
//        httpRequest.queryParam("endDate", "2020-07-31");
//        httpRequest.queryParam("callerId", "mistm@moveinsync.com");
//        Response response = httpRequest.request(Method.GET, "qa-Test6/br");
//        String responseBody = response.getBody().asString();
//        System.out.println("response body is: " + responseBody);
//        int statusCode = response.getStatusCode();
//        Assert.assertEquals(statusCode, 200,"status code ");
//        System.out.println("********************************************************");*/
//
//
//        RequestSpecification httpRequest = RestAssured.given().log().all();
//        // RestAssured.baseURI = "http://staging2.moveinsync.com:8082/bill-calculation/";
//
//        Response res = httpRequest.given()
//                .queryParam("startDate", "2020-07-01")
//                .queryParam("endDate", "2020-07-31")
//                .queryParam("callerId", "mistm@moveinsync.com")
//                .baseUri("http://staging2.moveinsync.com:8082/bill-calculation")
//                .when()
//                .get("qa-Test6/downloadbill")
//                .then()
//                .assertThat()
//                .contentType(String.valueOf(ContentType.JSON))
//                .and()
//                .statusCode(200).extract().response();
//
//    /*String data = res.jsonPath().getString("data[0].first_name");
//
//    System.out.println(data);
//    */
//        int index = 0;
//        List<String> ids = res.jsonPath().getList("parentEntity");
//        for(String map : ids) {
//            if (map.contains("598673")) {
//                index = ids.indexOf(map);
//                System.out.println(index);
//            }
//
//        }
//        List<Float> marshalAmount = res.jsonPath().getList("marshalAmount");
//        Float actualEscortCost = marshalAmount.get(index);
//        System.out.println(actualEscortCost);
//    }
//
//  /*  @Test
//    public void displayCabIdForProrate() {
//        RequestSpecification httpRequest = RestAssured.given().log().all();
//        Response res = httpRequest.given()
//                .header("Authorization", "Bearer LrmXabm55R")
//                .baseUri("https://staging2.moveinsync.com/mis-billing-contract/")
//                .when()
//                .get("api/masterData/cabs/auto-Test1/")
//                .then()
//                .assertThat()
//                .contentType(String.valueOf(ContentType.JSON))
//                .and()
//                .statusCode(200).extract().response();
//        List<String> stickerId = res.jsonPath().getList("stickerId");
//        List<Map<String,Object>> vendorId = res.jsonPath().getList("vendor");
//        String sId = stickerId.get(0);
//        Object vId = "";
//        for(Map<String,Object> map : vendorId) {
//            if (map.containsKey("vendorId")) {
//                vId = map.get("vendorId");
//                //System.out.println("id is:  "+vId);
//                break;
//            }
//        }
//        String displayId = vId + "-" + sId;
//        System.out.println("id is:  " + displayId);
//    }*/
//
//    @Test
//    public void abc() throws InterruptedException {
//        getChromeDriver().get(PropertyReader.getProperty("base.url"));
//        getNgWebDriver().waitForAngularRequestsToFinish();
//        LoginPage loginPage = new LoginPage(getChromeDriver());
//        loginPage.login();
//        Thread.sleep(10000);
//        NewBillingReportPage newBillingReportPage = new NewBillingReportPage(getChromeDriver());
//        newBillingReportPage.getTotalNoOfDutiesForBillCyclePackage();
//    }
//}
//
//
//
//
//
//

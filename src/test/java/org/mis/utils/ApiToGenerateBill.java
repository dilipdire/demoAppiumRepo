package org.mis.utils;

import com.jayway.restassured.RestAssured;

public class ApiToGenerateBill {

    public static void genetareBill() {
        String firstDateOfMonth = DateAndTime.currentMonthFirstDate();
        String lastDateOfMonth = DateAndTime.currentMonthLastDate();
        //System.out.println(tripId);
        String startDate = "2020-07-01";
        String endDate = "2020-07-31";
        String callerId = "mistm@moveinsync.com";
        RestAssured
                .given()
                .queryParam("startDate", "2020-10-01")
                .queryParam("endDate", "2020-10-31")
                .queryParam("callerId", "mistm@moveinsync.com")
                .baseUri("http://staging2.moveinsync.com:8082/bill-calculation")
                .when()
                .get("qa-Test6/br")
                .then()
                .assertThat()
                .statusCode(200);
        System.out.println("********************************************************");
    }
    public static void main(String[] args) {
    genetareBill();
    }
}


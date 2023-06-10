package org.mis.utils;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import java.util.List;

public class BillCalculationUtils {

    public static  String firstDateOfMonth = DateAndTime.currentMonthFirstDate();
    public static String lastDateOfMonth = DateAndTime.currentMonthLastDate();
    public static String apiContractPrice(String tripId){
        RequestSpecification httpRequest = RestAssured.given().log().all();
        Response res = httpRequest.given()
                .queryParam("startDate", "2020-10-01")
                .queryParam("endDate", "2020-10-31")
                .queryParam("callerId", "mistm@moveinsync.com")
                .baseUri("http://staging2.moveinsync.com:8082/bill-calculation")
                .when()
                .get("qa-Test6/downloadbill")
                .then()
                .assertThat()
                .contentType(String.valueOf(ContentType.JSON))
                .and()
                .statusCode(200).extract().response();
        int index = 0;
        List<String> ids = res.jsonPath().getList("parentEntity");
        for (String map : ids) {
            if (map.contains(tripId)) {
                index = ids.indexOf(map);
                System.out.println(index);
            }

        }
        List<Float> contractAmount = res.jsonPath().getList("contractAmount");
        Float actualContractCost = contractAmount.get(index);
        System.out.println(actualContractCost);
        String actualResult=String.valueOf(actualContractCost);
        return actualResult;
    }

    public static String calculationForKmSlabCost(String totalKm, String perKmCost){
        int expectedBill = 0;
        totalKm= totalKm.replaceAll("\\.0*$", "");
        perKmCost= perKmCost.replaceAll("\\.0*$", "");
        System.out.println(totalKm);
        System.out.println(perKmCost);
        int km=Integer.parseInt(totalKm);
        int cost=Integer.parseInt(perKmCost);
        expectedBill = km * cost;
        String str1 = Integer.toString(expectedBill);
        System.out.println(str1);
        return str1;
    }
    public static String dailyPackageSlab(String fixedCost,String actualFixedKm,String slabFixedKm,String extraKmCost,String extraHourCost,String dutyParameter,String distributionLogic){
        String expectedBill="0";
        fixedCost= fixedCost.replaceAll("\\.0*$", "");
        slabFixedKm= slabFixedKm.replaceAll("\\.0*$", "");
        extraKmCost= extraKmCost.replaceAll("\\.0*$", "");
        actualFixedKm= actualFixedKm.replaceAll("\\.0*$", "");
        int fixedPrice=Integer.parseInt(fixedCost);
        int fixedKm=Integer.parseInt(slabFixedKm);
        int actualKm=Integer.parseInt(actualFixedKm);
        int extraKmPrice=Integer.parseInt(extraKmCost);
        if(dutyParameter.equals("DUTYID")&&distributionLogic.equals("EQUAL")) {
            if (actualKm <= fixedKm) {
                int contractCost = fixedPrice;
                expectedBill = Integer.toString(contractCost);
            } else {
                int contractCost = (fixedPrice + (actualKm - fixedKm) * extraKmPrice);
                expectedBill = Integer.toString(contractCost);
            }
        }else{

        }
        return expectedBill;
    }
    public static String fuelHikeCalculation(String baseFuelPrice,String actualFuelPrice,String mileage,String travelledKm,String FixedKM,String fuelHikeCalType){
        String expectedBill="0";
        baseFuelPrice= baseFuelPrice.replaceAll("\\.0*$", "");
        actualFuelPrice= actualFuelPrice.replaceAll("\\.0*$", "");
        mileage= mileage.replaceAll("\\.0*$", "");
        travelledKm= travelledKm.replaceAll("\\.0*$", "");
        FixedKM= FixedKM.replaceAll("\\.0*$", "");
        //float PerKmCost=Float.parseFloat(perKmCost);
        float BaseFuelPrice=Float.parseFloat(baseFuelPrice);
        float ActualFuelPrice=Float.parseFloat(actualFuelPrice);
        float Mileage=Float.parseFloat(mileage);
        float TravelledKm=Float.parseFloat(travelledKm);
        float fixedKM=Float.parseFloat(FixedKM);
        if(fuelHikeCalType.equals("ACTUAL")) {
            float fuelTotal = TravelledKm / Mileage;     //in case of Actual According To Duty Km Fuel Hike is Calculating
            float fuelHikeCost = ActualFuelPrice - BaseFuelPrice;
            float fuelHikeCost1 = fuelTotal * fuelHikeCost;
            expectedBill = String.format("%.0f", fuelHikeCost1);
        }else{
            float fuelTotal = fixedKM / Mileage;     //in case of Fixed According To Fixed Km defined in Slab Fuel Hike is Calculating
            float fuelHikeCost = ActualFuelPrice - BaseFuelPrice;
            float fuelHikeCost1 = fuelTotal * fuelHikeCost;
            expectedBill = String.format("%.0f", fuelHikeCost1);
        }
        //expectedBill = String.valueOf(fuelHikeCost1);
        return expectedBill;
    }
    public static String fuelHikeTotalcal(String ContractAmount,String fuelHikeAmount){
        int expectedBill = 0;
        String contractAmount = "";
        ContractAmount = ContractAmount.replaceAll("\\.0*$", "");
        fuelHikeAmount = fuelHikeAmount.replaceAll("\\.0*$", "");
        int contractCost = Integer.parseInt(ContractAmount);
        int FuelHikeAmount = Integer.parseInt(fuelHikeAmount);
        expectedBill = contractCost+ FuelHikeAmount;
        contractAmount = Integer.toString(expectedBill);
        return contractAmount;
    }
    public static String calculationForEmployeeBasedCost(String employeeLogic,String empCount, String perHeadCost,String plannedEmpCount,String travelledEmp){
        int expectedBill = 0;
        String contractAmount = "";
        plannedEmpCount = plannedEmpCount.replaceAll("\\.0*$", "");
        travelledEmp = travelledEmp.replaceAll("\\.0*$", "");
        perHeadCost = perHeadCost.replaceAll("\\.0*$", "");
        int totalEmpTravelled=Integer.parseInt(empCount);
        int perEmpCost=Integer.parseInt(perHeadCost);
        int travelledEmpCount = Integer.parseInt(travelledEmp);
        int PlannedEmpCount = Integer.parseInt(plannedEmpCount);
        if(employeeLogic.equals("PLANNED_AND_ADHOC")||employeeLogic.equals("PLANNED")) {
            expectedBill = PlannedEmpCount * perEmpCost;
            contractAmount = Integer.toString(expectedBill);
        }
        else if(employeeLogic.equals("TRAVELLED")){
            expectedBill = travelledEmpCount * perEmpCost;
            contractAmount = Integer.toString(expectedBill);
        }
        return contractAmount;
    }

   /*public static void main(String[] args){
        String s = escortMultiplicationFactor("10","200.0");
        System.out.println(s);
    }*/


    public static String apiEscortPrice(String tripId){
        RequestSpecification httpRequest = RestAssured.given().log().all();
        Response res = httpRequest.given()
                .queryParam("startDate", "2020-10-01")
                .queryParam("endDate", "2020-10-31")
                .queryParam("callerId", "mistm@moveinsync.com")
                .baseUri("http://staging2.moveinsync.com:8082/bill-calculation")
                .when()
                .get("qa-Test6/downloadbill")
                .then()
                .assertThat()
                .contentType(String.valueOf(ContentType.JSON))
                .and()
                .statusCode(200).extract().response();
        int index = 0;
        List<String> ids = res.jsonPath().getList("parentEntity");
        for (String map : ids) {
            if (map.contains(tripId)) {
                index = ids.indexOf(map);
                System.out.println(index);
            }

        }
        List<Float> marshalAmount = res.jsonPath().getList("marshalAmount");
        Float actualEscortCost = marshalAmount.get(index);
        System.out.println(actualEscortCost);
        String actualResult=String.valueOf(actualEscortCost);
        return actualResult;
    }

    public static String escortNa(){
        int expectedBill = 0;
        String str1 = Integer.toString(expectedBill);
        return str1;
    }

    public static String escortFixed(String fixedCost){
        int expectedBill = 0;
        int fixedCostEscort=Integer.parseInt(fixedCost);
        expectedBill = fixedCostEscort;
        String str1 = Integer.toString(expectedBill);
        return str1;
    }
    public static String escortMultiplicationFactor(String cost,String fixedCostForPerticularBillModel){
        int expectedBill = 0;
        fixedCostForPerticularBillModel= fixedCostForPerticularBillModel.replaceAll("\\.0*$", "");
        int multiplicationFactor=Integer.parseInt(cost);
        int fixedCost=Integer.parseInt(fixedCostForPerticularBillModel);
        expectedBill = fixedCost*multiplicationFactor/100;
        String str1 = Integer.toString(expectedBill);
        return str1;
    }
    public static String apiACPrice(String tripId){
        RequestSpecification httpRequest = RestAssured.given().log().all();
        Response res = httpRequest.given()
                .queryParam("startDate", "2020-10-01")
                .queryParam("endDate", "2020-10-31")
                .queryParam("callerId", "mistm@moveinsync.com")
                .baseUri("http://staging2.moveinsync.com:8082/bill-calculation")
                .when()
                .get("qa-Test6/downloadbill")
                .then()
                .assertThat()
                .contentType(String.valueOf(ContentType.JSON))
                .and()
                .statusCode(200).extract().response();
        int index = 0;
        List<String> ids = res.jsonPath().getList("parentEntity");
        for (String map : ids) {
            if (map.contains(tripId)) {
                index = ids.indexOf(map);
                System.out.println(index);
            }

        }
        List<Float> marshalAmount = res.jsonPath().getList("acAmount");
        Float actualACCost = marshalAmount.get(index);
        System.out.println(actualACCost);
        String actualResult=String.valueOf(actualACCost);
        return actualResult;
    }
    public static String acFixed(String fixedCost){
        int expectedBill = 0;
        int fixedCostAC=Integer.parseInt(fixedCost);
        expectedBill = fixedCostAC;
        String str1 = Integer.toString(expectedBill);
        return str1;
    }
    public static String acMultiplicationFactor(String cost,String fixedCostForPerticularBillModel){
        int expectedBill = 0;
        int multiplicationFactor=Integer.parseInt(cost);
        int fixedCost=Integer.parseInt(fixedCostForPerticularBillModel);
        expectedBill = fixedCost*multiplicationFactor/100;
        String str1 = Integer.toString(expectedBill);
        return str1;
    }
    public static String acAdditionalPerKm(String cost,String fixedCostForPerticularBillModel){
        int expectedBill = 0;
        int additionalPerKm=Integer.parseInt(cost);
        int fixedKm=Integer.parseInt(fixedCostForPerticularBillModel);
        expectedBill = fixedKm*additionalPerKm;
        String str1 = Integer.toString(expectedBill);
        return str1;
    }

    public static String calculationForTripPackageBasedSlabCost(String fixedcost,String maxKmSlabOne,String jumpCrieteria,String traveledKm,String maxKmSlabTwo){
        String str1="";
        if(jumpCrieteria.equals("NA")) {
            int expectedBill = 0;
            fixedcost = fixedcost.replaceAll("\\.0*$", "");
            int fixedCost = Integer.parseInt(fixedcost);
            expectedBill = fixedCost;
            str1 = Integer.toString(expectedBill);
        }
        else {
            int expectedBill = 0;
            fixedcost = fixedcost.replaceAll("\\.0*$", "");
            int fixedCost = Integer.parseInt(fixedcost);
            expectedBill = fixedCost;
            str1 = Integer.toString(expectedBill);
        }
        return str1;
    }

    public static String calculationForZoneBasedSlabCost(String cost){
        int expectedBill = 0;
        cost= cost.replaceAll("\\.0*$", "");
        int fixedCost=Integer.parseInt(cost);
        expectedBill = fixedCost;           //Zone Price is Given For ContractPrice which is updated in contract
        String str1 = Integer.toString(expectedBill);
        return str1;
    }
    public static String calculationForEscort(String escortCostType,String cost , String contractPrice){
        String amount = "";
        int expectedBill = 0;
        contractPrice= contractPrice.replaceAll("\\.0*$", "");
        cost= cost.replaceAll("\\.0*$", "");
        int fixedCost=Integer.parseInt(contractPrice);
        int multiplicationFactor=Integer.parseInt(cost);
        if (escortCostType.equals("MULTIPLICATION_FACTOR")){
            expectedBill = fixedCost*multiplicationFactor/100;
            amount = Integer.toString(expectedBill);
        }
        else if(escortCostType.equals("FIXED")) {
            expectedBill = multiplicationFactor;
            amount = Integer.toString(expectedBill);
        }
        else{
            amount = Integer.toString(expectedBill);
        }
        return amount;
    }

    public static String calculationForAc(String ACPriceType,String ACPrice , String contractPrice){
        String amount = "";
        int expectedBill = 0;
        contractPrice= contractPrice.replaceAll("\\.0*$", "");
        ACPrice= ACPrice.replaceAll("\\.0*$", "");
        int fixedCost=Integer.parseInt(contractPrice);
        int multiplicationFactor=Integer.parseInt(ACPrice);
        if (ACPriceType.equals("MULTIPLICATION_FACTOR")){
            expectedBill = fixedCost*multiplicationFactor/100;
            amount = Integer.toString(expectedBill);
        }
        else if(ACPriceType.equals("FIXED")) {
            expectedBill = multiplicationFactor;
            amount = Integer.toString(expectedBill);
        }
        else{
            amount = Integer.toString(expectedBill);
        }
        return amount;
    }

    public static String shiftsSlab(){
        int expectedBill = 0;
        String amount = Integer.toString(expectedBill);
        return amount;
    }
    public static String b2bSlabForConfigurablePercent(String loginCost,String logoutCost,String contractPrice){
        String expectedCost="";
        int LogoutPrice=Integer.parseInt(logoutCost);
        int LoginPrice=Integer.parseInt(loginCost);
        int ContractPrice=Integer.parseInt(contractPrice);
        ContractPrice=ContractPrice + ContractPrice;
        int b2bCost = ContractPrice*LoginPrice/100;
        expectedCost=Integer.toString(b2bCost);
        return expectedCost;
    }

    public static String b2BSlab(String b2bType,String cost,String distributionLogic,String contractPrice1,String contractPrice2,String coficient,String Constant){
        String expectedCost="";
        cost= cost.replaceAll("\\.0*$", "");
        coficient= coficient.replaceAll("\\.0*$", "");
        Constant= Constant.replaceAll("\\.0*$", "");
        if(b2bType.equals("NA")){
            expectedCost="0";        //For NA Cost is always 0
        }
        else if(b2bType.equals("FIXED")&&distributionLogic.equals("NA")){
            expectedCost="0";         //For NA Cost is always 0
        }
        else if(b2bType.equals("FORMULA_BASED")&&distributionLogic.equals("EQUAL")){
            int Cost=Integer.parseInt(cost);
            int ContractPrice1 = Integer.parseInt(contractPrice1);
            int Coficient = Integer.parseInt(coficient);
            int constant = Integer.parseInt(Constant);
            int b2bCost1 = ContractPrice1*Coficient;    //Formula=Coefficient*Tripcost+Constant
            int b2bCost = (b2bCost1 + constant)/2;//This Price Will be Distributed Among Both Trip
            double b2bCostInDouble = b2bCost;
            //expectedCost=Integer.toString(b2bCost);
            expectedCost = String.format("%.0f", b2bCostInDouble);
            //expectedCost= expectedCost.replaceAll("\\.*$", "");
        }
        else if(b2bType.equals("FORMULA_BASED")&&distributionLogic.equals("ONLY_LOGIN")){
            int Cost=Integer.parseInt(cost);
            int ContractPrice1 = Integer.parseInt(contractPrice1);
            int Coficient = Integer.parseInt(coficient);
            int constant = Integer.parseInt(Constant);
            int b2bCost1 = ContractPrice1*Coficient;    //Formula=Coefficient*Tripcost+Constant
            int b2bCost = (b2bCost1 + constant);     //This Price Will be Given To Only Login Trip
            expectedCost=Integer.toString(b2bCost);
        }
        else if(b2bType.equals("FORMULA_BASED")&&distributionLogic.equals("ONLY_LOGOUT")){
            int Cost=Integer.parseInt(cost);
            int ContractPrice1 = Integer.parseInt(contractPrice1);
            int Coficient = Integer.parseInt(coficient);
            int constant = Integer.parseInt(Constant);
            int b2bCost1 = ContractPrice1*Coficient;    //Formula=Coefficient*Tripcost+Constant
            int b2bCost = (b2bCost1 + constant);     //This Price Will be Given To Only Logout Trip
            expectedCost=Integer.toString(b2bCost);
        }
        else if(b2bType.equals("FIXED")&&distributionLogic.equals("EQUAL")){
            int Cost=Integer.parseInt(cost);
            int b2bCost = Cost/2;                   //Fixed Cost Is Distributed Among Both Login & Logout Trip
            expectedCost=Integer.toString(b2bCost);
        }
        else if(b2bType.equals("FIXED")&&distributionLogic.equals("ONLY_LOGIN")){
            expectedCost=cost;                        //Fixed Cost is Given To Only Login Trip
        }else if(b2bType.equals("FIXED")&&distributionLogic.equals("ONLY_LOGOUT")){
            expectedCost=cost;                        //Fixed Cost is Given To Only Logout Trip
        }
        else if(b2bType.equals("MULTIPLICATION_FACTOR")&&distributionLogic.equals("NA")){
            expectedCost="0";                        //For NA Cost is always 0
        }
        else if(b2bType.equals("MULTIPLICATION_FACTOR")&&distributionLogic.equals("EQUAL")){
            int Cost=Integer.parseInt(cost);
            int ContractPrice1 = Integer.parseInt(contractPrice1);
            ContractPrice1=ContractPrice1 + ContractPrice1;       //Multiplication Factor Is Coming From Contract Price of Both Trip (loginTripPrice + LogoutTripPrice)
            int b2bCost1 = ContractPrice1*Cost/100;
            int b2bCost = b2bCost1/2;                            //This Price Will be Distributed Among Both Trip
            expectedCost=Integer.toString(b2bCost);
        }
        else if(b2bType.equals("MULTIPLICATION_FACTOR")&&distributionLogic.equals("ONLY_LOGIN")){
            int Cost=Integer.parseInt(cost);
            int ContractPrice1 = Integer.parseInt(contractPrice1);
            ContractPrice1=ContractPrice1 + ContractPrice1;     //Multiplication Factor Is Coming From Contract Price of Both Trip (loginTripPrice + LogoutTripPrice)
            int b2bCost = ContractPrice1*Cost/100;   //Fixed Cost is Given To Only Login Trip
            expectedCost=Integer.toString(b2bCost);;
        }else if(b2bType.equals("MULTIPLICATION_FACTOR")&&distributionLogic.equals("ONLY_LOGOUT")){
            int Cost=Integer.parseInt(cost);
            int ContractPrice1 = Integer.parseInt(contractPrice1);
            ContractPrice1=ContractPrice1 + ContractPrice1;         //Multiplication Factor Is Coming From Contract Price of Both Trip (loginTripPrice + LogoutTripPrice)
            int b2bCost = ContractPrice1*Cost/100;  //Fixed Cost is Given To Only Logout Trip
            expectedCost=Integer.toString(b2bCost);;
        }
        else if(b2bType.equals("HIGHER_COST")&&distributionLogic.equals("NA")){
            expectedCost="0";
        }
        else if(b2bType.equals("HIGHER_COST")&&distributionLogic.equals("EQUAL")){
            int ContractPrice2=Integer.parseInt(contractPrice2);
            int ContractPrice1 = Integer.parseInt(contractPrice1);
            int b2bCost = 0;
            if(ContractPrice1>ContractPrice2) {
                b2bCost = ContractPrice1/2;         //Higher Cost of The Trip Either login or logout Cost is given for B2B Trip
            }else
            {
                b2bCost = ContractPrice2/2;
            }
            expectedCost=Integer.toString(b2bCost);;
        }
        else if(b2bType.equals("HIGHER_COST")&&distributionLogic.equals("ONLY_LOGIN")||distributionLogic.equals("ONLY_LOGOUT")){
            int ContractPrice2=Integer.parseInt(contractPrice2);
            int ContractPrice1 = Integer.parseInt(contractPrice1);
            int b2bCost = 0;
            if(ContractPrice1>ContractPrice2) {
                b2bCost = ContractPrice1;       //Higher Cost of The Trip Either login or logout Cost is given for B2B Trip
            }else
            {
                b2bCost = ContractPrice2;
            }
            expectedCost=Integer.toString(b2bCost);
        }

        return expectedCost;
    }

    public static String tripAdjustmentSlab(String calculationType,String serviceType,String adjustmentValue,String km,String perKmCost,String contractPrice){
        String expectedCost="";
        contractPrice= contractPrice.replaceAll("\\.0*$", "");
        adjustmentValue= adjustmentValue.replaceAll("\\.0*$", "");
        km= km.replaceAll("\\.0*$", "");
        perKmCost= perKmCost.replaceAll("\\.0*$", "");
        int contractPriceInInt= Integer.parseInt(contractPrice);
        int cost = Integer.parseInt(adjustmentValue);
        int Km = Integer.parseInt(km);
        int PerKmCost = Integer.parseInt(perKmCost);
        if(calculationType.equals("PRICE")){
            if(serviceType.equals("NA")){
                expectedCost="0";
            }else if(serviceType.equals("FIXED")){
                int result = contractPriceInInt + cost;
                expectedCost=Integer.toString(result);
            }else{
                int result = contractPriceInInt + (contractPriceInInt*cost/100);
                expectedCost = Integer.toString(result);
            }
        }
        else{
            if(serviceType.equals("NA")){
                expectedCost="0";
            }else if(serviceType.equals("FIXED")){
                int result = (Km + cost)*PerKmCost;
                expectedCost=Integer.toString(result);
            }else{
                int result = (Km + (Km*cost/100))*PerKmCost;
                expectedCost = Integer.toString(result);
            }
        }
        return expectedCost;
    }
    public static String employeeAdjustmentSlab(String empType,String calculationType,String adjustmentValue,String km,String perKmCost,String contractPrice,String plannedEmpCount,String travelledEmp){
        String expectedCost="";
        contractPrice= contractPrice.replaceAll("\\.0*$", "");
        adjustmentValue= adjustmentValue.replaceAll("\\.0*$", "");
        km= km.replaceAll("\\.0*$", "");
        perKmCost= perKmCost.replaceAll("\\.0*$", "");
        plannedEmpCount = plannedEmpCount.replaceAll("\\.0*$", "");
        travelledEmp = travelledEmp.replaceAll("\\.0*$", "");
        int contractPriceInInt= Integer.parseInt(contractPrice);
        int cost = Integer.parseInt(adjustmentValue);
        int Km = Integer.parseInt(km);
        int plannedEmp=Integer.parseInt(plannedEmpCount);
        int travelledEmpCount = Integer.parseInt(travelledEmp);
        int PerKmCost = Integer.parseInt(perKmCost);
        if(calculationType.equals("PRICE") && empType.equals("PLANNED")){
            int result = contractPriceInInt + (cost*plannedEmp);
            expectedCost=Integer.toString(result);

        }else if(calculationType.equals("PRICE") && empType.equals("TRAVELLED")){
            int result = contractPriceInInt + (cost*travelledEmpCount);
            expectedCost=Integer.toString(result);

        }else if(calculationType.equals("KM") && empType.equals("PLANNED")) {
            int result = (Km + (cost * plannedEmp)) * PerKmCost;
            expectedCost = Integer.toString(result);
        }else if (calculationType.equals("KM") && empType.equals("TRAVELLED")) {
            int result = (Km + (cost * travelledEmpCount)) * PerKmCost;
            expectedCost = Integer.toString(result);
        }else if(calculationType.equals("KM") && empType.equals("PLANNED_AND_ADHOC")){
            int result = (Km + cost)*PerKmCost;
            expectedCost=Integer.toString(result);
        }else{
            int result = contractPriceInInt + cost;
            expectedCost=Integer.toString(result);
        }
        return expectedCost;
    }


    public static String driverBataSlab(String bataDayCost,String bataNightCost){
        String expectedCost = "";
        bataDayCost= bataDayCost.replaceAll("\\.0*$", "");
        bataNightCost= bataNightCost.replaceAll("\\.0*$", "");
        int nightCost = Integer.parseInt(bataNightCost);
        int dayCost = Integer.parseInt(bataDayCost);
        if(nightCost!=0 && dayCost==0){
            int result = nightCost;
            expectedCost=Integer.toString(result);
        }else{
            int result = dayCost;
            expectedCost=Integer.toString(result);
        }
        return expectedCost;
    }
    public static String apiDriverBataAmount(String tripId){
        RequestSpecification httpRequest = RestAssured.given().log().all();
        Response res = httpRequest.given()
                .queryParam("startDate", "2020-10-01")
                .queryParam("endDate", "2020-10-31")
                .queryParam("callerId", "mistm@moveinsync.com")
                .baseUri("http://staging2.moveinsync.com:8082/bill-calculation")
                .when()
                .get("qa-Test6/downloadbill")
                .then()
                .assertThat()
                .contentType(String.valueOf(ContentType.JSON))
                .and()
                .statusCode(200).extract().response();
        int index = 0;
        List<String> ids = res.jsonPath().getList("parentEntity");
        for (String map : ids) {
            if (map.contains(tripId)) {
                index = ids.indexOf(map);
                System.out.println(index);
            }

        }
        List<Float> driverBataAmount = res.jsonPath().getList("driverBataAmount");
        Float actualCost = driverBataAmount.get(index);
        System.out.println(actualCost);
        String actualResult=String.valueOf(actualCost);
        System.out.println(actualResult);
        return actualResult;
    }


    public static String apiGrandTotal(String tripId){
        RequestSpecification httpRequest = RestAssured.given().log().all();
        Response res = httpRequest.given()
                .queryParam("startDate", "2020-10-01")
                .queryParam("endDate", "2020-10-31")
                .queryParam("callerId", "mistm@moveinsync.com")
                .baseUri("http://staging2.moveinsync.com:8082/bill-calculation")
                .when()
                .get("qa-Test6/downloadbill")
                .then()
                .assertThat()
                .contentType(String.valueOf(ContentType.JSON))
                .and()
                .statusCode(200).extract().response();
        int index = 0;
        List<String> ids = res.jsonPath().getList("parentEntity");
        for (String map : ids) {
            if (map.contains(tripId)) {
                index = ids.indexOf(map);
                System.out.println(index);
            }

        }
        List<Float> driverBataAmount = res.jsonPath().getList("grandTotal");
        Float actualCost = driverBataAmount.get(index);
        System.out.println(actualCost);
        String actualResult=String.valueOf(actualCost);
        System.out.println(actualResult);
        return actualResult;
    }


    public static String inclusionLogicSlab(String includingTripType,String costFlavour , String contractPrice){
        String amount = "";
        if (includingTripType.equals("MULTIPLICATION_FACTOR")){
            int expectedBill = 0;
            contractPrice= contractPrice.replaceAll("\\.0*$", "");
            int multiplicationFactor=Integer.parseInt(costFlavour);
            int fixedCost=Integer.parseInt(contractPrice);
            expectedBill = fixedCost*multiplicationFactor/100;
            amount = Integer.toString(expectedBill);
        }
        else if(includingTripType.equals("FIXED")) {
            int expectedBill = 0;
            int fixedCostEscort=Integer.parseInt(costFlavour);
            expectedBill = fixedCostEscort;
            amount = Integer.toString(expectedBill);
        }
        else{
            int expectedBill = 0;
            amount = Integer.toString(expectedBill);
        }
        return amount;
    }



    public static String apiGrandTotalBillWithGST(String tripId){
        RequestSpecification httpRequest = RestAssured.given().log().all();
        Response res = httpRequest.given()
                .queryParam("startDate", "2020-10-01")
                .queryParam("endDate", "2020-10-31")
                .queryParam("callerId", "mistm@moveinsync.com")
                .baseUri("http://staging2.moveinsync.com:8082/bill-calculation")
                .when()
                .get("qa-Test6/downloadbill")
                .then()
                .assertThat()
                .contentType(String.valueOf(ContentType.JSON))
                .and()
                .statusCode(200).extract().response();
        int index = 0;
        List<String> ids = res.jsonPath().getList("parentEntity");
        for (String map : ids) {
            if (map.contains(tripId)) {
                index = ids.indexOf(map);
                System.out.println(index);
            }

        }
        List<Float> driverBataAmount = res.jsonPath().getList("grandTotalWithGst");
        Float actualCost = driverBataAmount.get(index);
        System.out.println(actualCost);
        String actualResult=String.valueOf(actualCost);
        System.out.println(actualResult);
        return actualResult;
    }
    public String tripPackageSlab(String JumpCriteria,String FixedCost,String ExtraKmCost,String maxKm,String travelledKm){
        String expectedBill = "";
        int fixedCost=Integer.parseInt(FixedCost);
        int MaxKm=Integer.parseInt(maxKm);
        int TravelledKm=Integer.parseInt(travelledKm);
        int extraKmCost=Integer.parseInt(ExtraKmCost);
        int contractPrice=0;
        if(JumpCriteria.equals("NA") && TravelledKm>=MaxKm){
            contractPrice = fixedCost;
            expectedBill = Integer.toString(contractPrice);
        }else{
            contractPrice = fixedCost + (TravelledKm - MaxKm)*extraKmCost;
            expectedBill = Integer.toString(contractPrice);
        }
        return expectedBill;
    }

    public static String grandTotalBillWithGst(String inclusionSlabPrice,String b2bPrice,String ShiftsIgnoreTripType,String b2b,
                                               String inclusionLogic,String acPrice,String contractPrice,String escortPrice,String tripAdjustPrice,String empAdjustPrice){
        String expectedBill = "";
        int actualContractPrice=0;
        int escortCost=Integer.parseInt(escortPrice);
        int tripAdjustCost=Integer.parseInt(tripAdjustPrice);
        int empAdjustCost=Integer.parseInt(empAdjustPrice);
        int contractCost=Integer.parseInt(contractPrice);
        int b2bCost=Integer.parseInt(b2bPrice);
        int inclusionSlabCost=Integer.parseInt(inclusionSlabPrice);
        int acCost=Integer.parseInt(acPrice);
        if(ShiftsIgnoreTripType.equals("NA") && b2b.equals("NA") && inclusionLogic.equals("NA") && tripAdjustCost!=0 && empAdjustCost!=0){
            actualContractPrice =acCost + escortCost+(tripAdjustCost-contractCost)+(empAdjustCost-contractCost)+contractCost;
            expectedBill = Integer.toString(actualContractPrice);
        }
        else if(ShiftsIgnoreTripType.equals("NA") && b2b.equals("NA") && inclusionLogic.equals("NA") && tripAdjustCost==0 && empAdjustCost==0){
            actualContractPrice =acCost + escortCost + contractCost;
            expectedBill = Integer.toString(actualContractPrice);
        }
        else{
            if(ShiftsIgnoreTripType.equals("NA")&&!b2b.equals("NA")&&inclusionLogic.equals("NA")){
                actualContractPrice=b2bCost;
                expectedBill = Integer.toString(actualContractPrice);
            }else if(ShiftsIgnoreTripType.equals("NA")&&b2b.equals("NA")&&!inclusionLogic.equals("NA")){
                actualContractPrice = inclusionSlabCost;
                expectedBill = Integer.toString(actualContractPrice);
            }else {
                // actualContractPrice = 0;
                expectedBill = Integer.toString(actualContractPrice);
            }
        }
        return expectedBill;
    }



    public static String grandTotalForZoneBased(String fixedCost,String b2bType,String b2bPrice,String escortPrice,String tripAdjustPrice,String acPrice,
                                                String inclusionSlabPrice,String ShiftsIgnoreTripType,String inclusionLogic){
        String expectedBill ="0";
        int actualContractPrice=0;
        int escortCost=Integer.parseInt(escortPrice);
        fixedCost= fixedCost.replaceAll("\\.0*$", "");
        int contractCost=Integer.parseInt(fixedCost);
        int tripAdjustCost=Integer.parseInt(tripAdjustPrice);
        int b2bCost=Integer.parseInt(b2bPrice);
        int inclusionSlabCost=Integer.parseInt(inclusionSlabPrice);
        int acCost=Integer.parseInt(acPrice);
        if(ShiftsIgnoreTripType.equals("NA") && b2bType.equals("NA")  && tripAdjustCost!=0 && inclusionLogic.equals("NA")){
            actualContractPrice =acCost + escortCost+(tripAdjustCost-contractCost)+contractCost;
            expectedBill = Integer.toString(actualContractPrice);
        }
        else if(ShiftsIgnoreTripType.equals("NA") && b2bType.equals("NA")  && tripAdjustCost==0 && inclusionLogic.equals("NA")){
            actualContractPrice =acCost + escortCost + contractCost;
            expectedBill = Integer.toString(actualContractPrice);
        }
        else{
            if(ShiftsIgnoreTripType.equals("NA")&&!b2bType.equals("NA")&&inclusionLogic.equals("NA")){
                actualContractPrice=b2bCost;
                expectedBill = Integer.toString(actualContractPrice);
            }else if(ShiftsIgnoreTripType.equals("NA")&&!inclusionLogic.equals("NA")&&b2bType.equals("NA")){
                actualContractPrice = inclusionSlabCost;
                expectedBill = Integer.toString(actualContractPrice);
            }else {
                // actualContractPrice = 0;
                expectedBill = Integer.toString(actualContractPrice);
            }
        }
        return expectedBill;
    }

    public static String grandTotalForDailyPackage(String fixedCost,String b2bType,String b2bPrice,String escortPrice,String tripAdjustPrice,String empAdjustPrice,String acPrice,
                                                   String inclusionSlabPrice,String ShiftsIgnoreTripType,String inclusionLogic,String escortUsage){
        String expectedBill ="0";
        int actualContractPrice=0;
        int escortCost=Integer.parseInt(escortPrice);
        fixedCost= fixedCost.replaceAll("\\.0*$", "");
        int contractCost=Integer.parseInt(fixedCost);
        int tripAdjustCost=Integer.parseInt(tripAdjustPrice);
        int empAdjustCost=Integer.parseInt(empAdjustPrice);
        int b2bCost=Integer.parseInt(b2bPrice);
        int inclusionSlabCost=Integer.parseInt(inclusionSlabPrice);
        int acCost=Integer.parseInt(acPrice);
        if(ShiftsIgnoreTripType.equals("NA") && b2bType.equals("NA")  && tripAdjustCost!=0 && empAdjustCost!=0 && inclusionLogic.equals("NA")){
            actualContractPrice =acCost + escortCost+(tripAdjustCost-contractCost)+(empAdjustCost-contractCost)+contractCost;
            expectedBill = Integer.toString(actualContractPrice);
        }
        else if(ShiftsIgnoreTripType.equals("NA") && b2bType.equals("NA")  && tripAdjustCost==0 && inclusionLogic.equals("NA") && empAdjustCost==0){
            actualContractPrice =acCost + escortCost + contractCost;
            expectedBill = Integer.toString(actualContractPrice);
        }
        else{
            if(ShiftsIgnoreTripType.equals("NA")&&!b2bType.equals("NA")&&inclusionLogic.equals("NA")){
                actualContractPrice=b2bCost;
                expectedBill = Integer.toString(actualContractPrice);
            }else if(ShiftsIgnoreTripType.equals("NA")&&!inclusionLogic.equals("NA")&&b2bType.equals("NA")){
                actualContractPrice = inclusionSlabCost;
                expectedBill = Integer.toString(actualContractPrice);
            }else {
                // actualContractPrice = 0;
                expectedBill = Integer.toString(actualContractPrice);
            }
        }
        return expectedBill;
    }

    public static String grandTotalForEmployeeBased(String contactAmount,String b2bType,String b2bPrice,String escortPrice,String tripAdjustPrice,String empAdjustPrice,String acPrice,
                                                    String inclusionSlabPrice,String ShiftsIgnoreTripType,String inclusionLogic){
        String expectedBill ="0";
        int actualContractPrice=0;
        int escortCost=Integer.parseInt(escortPrice);
        contactAmount= contactAmount.replaceAll("\\.0*$", "");
        int contractCost=Integer.parseInt(contactAmount);
        int tripAdjustCost=Integer.parseInt(tripAdjustPrice);
        int empAdjustCost=Integer.parseInt(empAdjustPrice);
        int b2bCost=Integer.parseInt(b2bPrice);
        int inclusionSlabCost=Integer.parseInt(inclusionSlabPrice);
        int acCost=Integer.parseInt(acPrice);
        if(ShiftsIgnoreTripType.equals("NA") && b2bType.equals("NA")  && tripAdjustCost!=0 && empAdjustCost!=0 && inclusionLogic.equals("NA")){
            actualContractPrice =acCost + escortCost+(tripAdjustCost-contractCost)+(empAdjustCost-contractCost)+contractCost;
            expectedBill = Integer.toString(actualContractPrice);
        }
        else if(ShiftsIgnoreTripType.equals("NA") && b2bType.equals("NA")  && tripAdjustCost==0 && inclusionLogic.equals("NA") && empAdjustCost==0){
            actualContractPrice =acCost + escortCost + contractCost;
            expectedBill = Integer.toString(actualContractPrice);
        }
        else{
            if(ShiftsIgnoreTripType.equals("NA")&&!b2bType.equals("NA")&&inclusionLogic.equals("NA")){
                actualContractPrice=b2bCost;
                expectedBill = Integer.toString(actualContractPrice);
            }else if(ShiftsIgnoreTripType.equals("NA")&&!inclusionLogic.equals("NA")&&b2bType.equals("NA")){
                actualContractPrice = inclusionSlabCost;
                expectedBill = Integer.toString(actualContractPrice);
            }else {
                // actualContractPrice = 0;
                expectedBill = Integer.toString(actualContractPrice);
            }
        }
        return expectedBill;
    }
    public static String billCyclePackageSlab(String fixedCost,String noOfDuties){
        String expectedBill="0";
        fixedCost= fixedCost.replaceAll("\\.0*$", "");
        int totalDuties=Integer.parseInt(noOfDuties);
        totalDuties=totalDuties+1;
        int FixedCost=Integer.parseInt(fixedCost);
        float actualContractPrice=FixedCost/totalDuties;
        expectedBill = String.format("%.2f", actualContractPrice);
        return expectedBill;
    }
    public static String grandTotalForBillCyclePackage(String totalNoOfDuties,String totalKm,String duties,String fixedKm,String fixedCost,String perDutyCost,String perKmCost
            ,String escort,String escortCostType,String escortCost,String b2bType,String distributionLogic,String b2bCost){
        String expectedBill="0";
        totalKm=totalKm.replaceAll("[$,]", "");
        System.out.println(totalKm);
        float totalDuties=Float.parseFloat(totalNoOfDuties);
        //totalDuties=totalDuties+1;
        float fullKm=Float.parseFloat(totalKm);
        System.out.println("****************************");
        System.out.println(fullKm);
        System.out.println(perKmCost);
        System.out.println(duties);
        System.out.println(totalDuties);
        System.out.println(fixedKm);
        System.out.println(escortCost);
        System.out.println(fixedCost);
        System.out.println(perDutyCost);
        System.out.println("****************************");
        float PerKmCost=Float.parseFloat(perKmCost);
        float B2bCost=Float.parseFloat(b2bCost);
        float slabDuties=Float.parseFloat(duties);
        float FixedKm=Float.parseFloat(fixedKm);
        float EscortCost=Float.parseFloat(escortCost);
        float FixedCost=Float.parseFloat(fixedCost);
        float PerDutyCost=Float.parseFloat(perDutyCost);
        float n1=(FixedCost/totalDuties);
        float n2;
        float n3;
        if(fullKm>FixedKm){
            n3=(fullKm-FixedKm)*PerKmCost;
            n3=n3/totalDuties;
        }else{
            n3=0;
        }
        if(totalDuties>slabDuties){
            n2=(totalDuties-slabDuties)*PerDutyCost;
            n2=n2/totalDuties;
        }else{
            n2=0;
        }
        float actualContractPrice=n1+n2+n3;
        if(escort.equals("NA")&&b2bType.equals("NA")){
            expectedBill = String.format("%.0f", actualContractPrice);
        }
        else {
            if(escort.equals("ALL")||escort.equals("LOGIN")||escort.equals("LOGOUT")) {
                if (escortCostType.equals("FIXED")) {
                    actualContractPrice = actualContractPrice + EscortCost;
                    expectedBill = String.format("%.0f", actualContractPrice);
                } else if (escortCostType.equals("MULTIPLICATION_FACTOR")) {
                    actualContractPrice = actualContractPrice + n1 * EscortCost / 100;
                    expectedBill = String.format("%.0f", actualContractPrice);
                } else {
                    expectedBill = String.format("%.0f", actualContractPrice);
                }
            }else{
                actualContractPrice=actualContractPrice/2;
                if (escortCostType.equals("FIXED")) {
                    actualContractPrice = actualContractPrice + EscortCost;
                    expectedBill = String.format("%.0f", actualContractPrice);
                } else if (escortCostType.equals("MULTIPLICATION_FACTOR")) {
                    actualContractPrice = actualContractPrice + n1/2 * EscortCost / 100;
                    expectedBill = String.format("%.0f", actualContractPrice);
                } else {
                    expectedBill = String.format("%.0f", actualContractPrice);
                }
            }

        }
        if(escort.equals("NA")&&!b2bType.equals("NA")){
            if(b2bType.equals("FIXED")&&distributionLogic.equals("EQUAL")){
                actualContractPrice = B2bCost/2 + n2/2 + n3/2;
                expectedBill = String.format("%.0f", actualContractPrice);
            }else if(b2bType.equals("FIXED")&&distributionLogic.equals("ONLY_LOGIN")){
                actualContractPrice = B2bCost + n2/2 + n3/2;
                expectedBill = String.format("%.0f", actualContractPrice);
            }
            else if(b2bType.equals("FIXED")&&distributionLogic.equals("ONLY_LOGOUT")){
                actualContractPrice = B2bCost + n2/2 + n3/2;
                expectedBill = String.format("%.0f", actualContractPrice);
            }
            else if(b2bType.equals("MULTIPLICATION_FACTOR")&&distributionLogic.equals("EQUAL")){
                n1=n1/2;
                float b2bCostActual = n1*B2bCost/100;
                actualContractPrice = b2bCostActual/2 + n2/2 + n3/2;
                expectedBill = String.format("%.0f", actualContractPrice);
            }
            else if(b2bType.equals("MULTIPLICATION_FACTOR")&&distributionLogic.equals("ONLY_LOGIN")){
                n1=n1/2;
                float b2bCostActual = n1*B2bCost/100;
                actualContractPrice = b2bCostActual + n2/2 + n3/2;
                expectedBill = String.format("%.0f", actualContractPrice);
            }
            else if(b2bType.equals("MULTIPLICATION_FACTOR")&&distributionLogic.equals("ONLY_LOGOUT")){
                n1=n1/2;
                float b2bCostActual = n1*B2bCost/100;
                actualContractPrice = b2bCostActual + n2/2 + n3/2;
                expectedBill = String.format("%.0f", actualContractPrice);
            }
        }
        //expectedBill=String.format("%.2f", actualContractPrice);
        return expectedBill;
    }


    public static String apiGrandTotalForBillCyclePackage(String tripId){
        RequestSpecification httpRequest = RestAssured.given().log().all();
        Response res = httpRequest.given()
                .queryParam("startDate", "2020-10-01")
                .queryParam("endDate", "2020-10-31")
                .queryParam("callerId", "mistm@moveinsync.com")
                .baseUri("http://staging2.moveinsync.com:8082/bill-calculation")
                .when()
                .get("qa-Test6/downloadbill")
                .then()
                .assertThat()
                .contentType(String.valueOf(ContentType.JSON))
                .and()
                .statusCode(200).extract().response();
        int index = 0;
        List<String> ids = res.jsonPath().getList("parentEntity");
        for (String map : ids) {
            if (map.contains(tripId)) {
                index = ids.indexOf(map);
                System.out.println(index);
            }

        }
        List<Float> driverBataAmount = res.jsonPath().getList("grandTotal");
        Float actualCost = driverBataAmount.get(index);
        System.out.println(actualCost);
        String actualResult=String.format("%.0f", actualCost);
        System.out.println(actualResult);
        return actualResult;
    }



    public static void main(String[] args) {
        //System.out.println(grandTotalForZoneBased("100","SAPNA","10","20","130","20","0","sapna","NA"));
        //System.out.println(grandTotalForBillCyclePackage("11","185.5","5","300","3000","100","10","ALL","109.09091071649031","50","NA","EQUAL","20"));
        //System.out.println(b2BSlab("MULTIPLICATION_FACTOR","10", "EQUAL", "500", "500"));;
        //System.out.println(grandTotalForDailyPackage("1000", "MULTIPLICATION_FACTOR", "100", "0", "0", "0", "0", "0","NA", "NA"));
        //apiDriverBataAmount("603720");
        //System.out.println(fuelHikeCalculation("60","65","10","10"));
        //System.out.println(employeeAdjustmentSlab("PLANNED","KM","20","10","10","100","2","1"));
        //System.out.println(tripAdjustmentSlab("PRICE","NA","20","10","10","100"));
        //System.out.println(tripAdjustmentSlab("PRICE","FIXED","20","10","10","100"));
        //System.out.println(tripAdjustmentSlab("PRICE","MULTIPLICATION_FACTOR","20","10","10","100"));
        //System.out.println(tripAdjustmentSlab("KM","NA","20","10","10","100"));
        //System.out.println(tripAdjustmentSlab("KM","FIXED","20","10","10","100"));
        //System.out.println(employeeAdjustmentSlab("KM","20","10","10","100"));
        int b2bCost=512;
        double d=b2bCost;
        //expectedCost=Integer.toString(512);
        String expectedCost = String.format("%.0f", d);
        System.out.println(expectedCost);

        //expectedCost= expectedCost.replaceAll("\\.*$", "");
    }
}
package org.mis.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateAndTime {
    public String hour24formate() throws ParseException {
        //public static void main(String[] args) throws ParseException {
        String now = new SimpleDateFormat("hh:mm aa").format(new java.util.Date().getTime());
        System.out.println("time in 12 hour format : " + now);
        SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm aa");
        SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
        String time24 = outFormat.format(inFormat.parse(now));
        System.out.println("time in 24 hour format : " + time24);
        String[] hour = time24.split(":");
        String hour1= hour[0];
        if(hour1.equals("1") || hour1.equals("2") || hour1.equals("3") || hour1.equals("4") || hour1.equals("5") || hour1.equals("6") || hour1.equals("7") ||
                hour1.equals("8") || hour1.equals("9") ) {
            hour1 = "0"+hour1;
            //System.out.println("time in 24 hour format : " + hour1);
            return hour1;
        }
        else {
            // System.out.println("time in 24 hour format : " + hour1);
            return hour1;
        }
        // System.out.println("time in 24 hour format : " + hour1);
                /*for (int i = 0; i < hour.length; i++) {
                    //String hour1= hour[i];
                    System.out.println("str[" + i + "] : " + hour[i]);
                }
                //System.out.println("time in 24 hour format : " + hour);*/

    }
    public String minTwoHourLater(){
        //public static void main(String[] args) throws ParseException {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, 2);
        String result = LocalTime.parse("03:30 PM" , DateTimeFormatter.ofPattern("hh:mm a", Locale.US)).format( DateTimeFormatter.ofPattern("HH:mm") );
        int min = now.get(Calendar.MINUTE);
        int hour = now.get(Calendar.HOUR);
        if(min==0 || min==1 || min==2 || min==3 || min==4 || min==5 || min==6 || min==7 || min==8 || min==9) {
            String time = "0"+String.valueOf(min);
            return time;
            //System.out.println(time);
        }
        else {
            String time = String.valueOf(min);
            return time;
            //System.out.println(time);
        }

    }
    public String tenMinBeforeMin(){
        Calendar calendar = Calendar.getInstance();
        System.out.println("Original = " + calendar.getTime());
        calendar.add(Calendar.MINUTE, -10);
        Date updated = calendar.getTime();
        System.out.println("Updated  = " + updated);
        int i =calendar.get(Calendar.MINUTE);
        int j =calendar.get(Calendar.HOUR_OF_DAY);
        // System.out.println(i);
        // System.out.println(j);
        //System.out.println("Hour: " + calendar.get(Calendar.HOUR_OF_DAY));
        String time = String.valueOf(i);
        return time;
    }

    public static String tenMinAfterMin(){
        Calendar calendar = Calendar.getInstance();
        System.out.println("Original = " + calendar.getTime());
        calendar.add(Calendar.MINUTE, 2);
        Date updated = calendar.getTime();
        System.out.println("Updated  = " + updated);
        int i =calendar.get(Calendar.MINUTE);
        int j =calendar.get(Calendar.HOUR_OF_DAY);
        // System.out.println(i);
        // System.out.println(j);
        //System.out.println("Hour: " + calendar.get(Calendar.HOUR_OF_DAY));
        String time;
        if(i==0 || i==1 || i==2 || i==3 || i==4 || i==5 || i==6 || i==7 || i==8 || i==9) {
            time = "0"+String.valueOf(i);
            return time;
        }else {
            time = String.valueOf(i);
        }
        System.out.println(time);
        return time;
    }
    public String tenMinBeforeHour(){
        Calendar calendar = Calendar.getInstance();
        System.out.println("Original = " + calendar.getTime());
        calendar.add(Calendar.MINUTE, -10);
        Date updated = calendar.getTime();
        System.out.println("Updated  = " + updated);
        int i =calendar.get(Calendar.MINUTE);
        int j =calendar.get(Calendar.HOUR_OF_DAY);
        // System.out.println(i);
        // System.out.println(j);
        //System.out.println("Hour: " + calendar.get(Calendar.HOUR_OF_DAY));
        String time = String.valueOf(j);
        System.out.println(time);
        return time;
    }
    public static String tenMinAfterHour(){
        Calendar calendar = Calendar.getInstance();
        System.out.println("Original = " + calendar.getTime());
        int actualHour =calendar.get(Calendar.HOUR_OF_DAY);
        calendar.add(Calendar.MINUTE, 2);
        Date updated = calendar.getTime();
        System.out.println("Updated  = " + updated);
        int i =calendar.get(Calendar.MINUTE);
        int j =calendar.get(Calendar.HOUR_OF_DAY);
        String time;
        // System.out.println(i);
        // System.out.println(j);
        System.out.println("Hour: " + calendar.get(Calendar.HOUR_OF_DAY));
        if(actualHour==11){
            time = "11";
        }else {
            time = String.valueOf(j);
        }
        System.out.println(time);
        return time;
    }

    public  static String getCurrentDate() {
        String pattern = "dd/MM/yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        return dateInString;
    }
    public  static String getCabContractCurrentDate() {
        String pattern = "MM-dd-yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        return dateInString;
    }
    public  static String getParkingCurrentDate() {
        String pattern = "MM/dd/yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        return dateInString;
    }
    public String switchDateFormat(String date, String originalFormat, String requiredFormat) throws ParseException {
        SimpleDateFormat dt = new SimpleDateFormat(originalFormat);
        Date date_s = dt.parse(date);
        SimpleDateFormat dt1 = new SimpleDateFormat(requiredFormat);
        return dt1.format(date_s);
    }

    public static String startTimeForDriverAlowance() throws ParseException {
        Calendar n = Calendar.getInstance();
        int min = n.get(Calendar.MINUTE);
        String now = new SimpleDateFormat("hh:mm aa").format(new java.util.Date().getTime());
        //System.out.println("time in 12 hour format : " + now);
        SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm aa");
        SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
        String time24 = outFormat.format(inFormat.parse(now));
        //System.out.println("time in 24 hour format : " + time24);
        String[] hour = time24.split(":");
        String hour1 = hour[0];
        //System.out.println("time in 24 hour format : " + hour1);
        if (min <= 30) {
            String h = hour1 + ":00";
            System.out.println("time in 24 hour format : " + h);
            return h;
        }
        else{
            String h =hour1+ ":30";
            System.out.println("time in 24 hour format : " + h);
            return h;
        }
    }
    public static String endTimeForDriverAlowance() throws ParseException {
        String startTime = startTimeForDriverAlowance();
        String[] hour = startTime.split(":");
        String hour1 = hour[0];
        int h = Integer.parseInt(hour1);
        Calendar n = Calendar.getInstance();
        int min = n.get(Calendar.MINUTE);
        //System.out.println("time in 24 hour format : " + hour1);
        if (min >= 30) {
            h = h+1;
            String h1=Integer.toString(h);
            String endTime = h1 + ":00";
            System.out.println("time in 24 hour format : " + endTime);
            return endTime;
        }
        else{
            String endTime =hour1+ ":30";
            System.out.println("time in 24 hour format : " + endTime);
            return endTime;
        }
    }

    public static String currentMonthFirstDate(){
        LocalDate todaydate = LocalDate.now();
        LocalDate date=todaydate.withDayOfMonth(1);
        String currentMontFirstDate=date.toString();
        //System.out.println("Months first date in yyyy-mm-dd: " +todaydate.withDayOfMonth(1));
        return currentMontFirstDate;
    }

    public static String currentMonthLastDate() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        Date lastDayOfMonth = calendar.getTime();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println("Today            : " + sdf.format(today));
        //System.out.println("Last Day of Month: " + sdf.format(lastDayOfMonth));
        String lastDateOfMonth = sdf.format(lastDayOfMonth);
        return lastDateOfMonth;
    }

    public static String getCurrentDateForHistoryPetternCheck(){
        String pattern = "EEE MMM dd yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        System.out.println(dateInString);
        return dateInString;
    }

    public static String getDateForAdjustmentSearchCheck(){
        String pattern = "MMM dd, yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        System.out.println(dateInString);
        return dateInString;
    }
    public static void main(String[] args) throws ParseException {
        String pattern = "MMM dd, yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        System.out.println(dateInString);
        String string = " Fri Oct 16 2020 10:16:50";
        System.out.println(string.substring(0, string.length() - 8));
        string="changed" + " from";
//creating a constructor of StringBuffer class
        // StringBuffer sb= new StringBuffer(string);
//invoking the method
        // sb.deleteCharAt(sb.length()-17);

//prints the string after deleting the character
        System.out.println(string);
    }}
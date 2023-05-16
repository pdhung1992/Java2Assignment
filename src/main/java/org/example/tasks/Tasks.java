package org.example.tasks;

import org.apache.log4j.Logger;
import org.example.util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tasks {
    Logger logger = Logger.getLogger(Tasks.class);
    Scanner input = new Scanner(System.in);
    int taskID;
    String taskName;
    int typeID;
    String typeName;
    String date;
    float from;
    float to;
    float time;
    String assignee;
    String reviewer;

    public Tasks(int taskID, String taskName, int typeID, String date, float time, String assignee, String reviewer) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.typeID = typeID;
        this.date = date;
        this.time = time;
        this.assignee = assignee;
        this.reviewer = reviewer;
    }
    public Tasks(int taskID, String taskName, String typeName, String date, float time, String assignee, String reviewer){
        this.taskID = taskID;
        this.taskName = taskName;
        this.typeName = typeName;
        this.date = date;
        this.time = time;
        this.assignee = assignee;
        this.reviewer = reviewer;
    }

    public Tasks(){

    }

    public void inputTask(){
        System.out.print("Enter Task's name: ");
        String sName = input.nextLine();
        try {
            taskName = checkName(sName);
        }catch (Exception ex){
            logger.error(ex.getMessage());
        }
        System.out.print("Enter Type ID: ");
        String sType = input.nextLine();
        try {
            typeID = checkType(sType);
        }catch (Exception ex){
            logger.error(ex.getMessage());
        }
        System.out.print("Enter Date (yyyy-MM-dd): ");
        String sDate = input.nextLine();
        try {
            date = checkDate(sDate);
        }catch (Exception ex){
            logger.error(ex.getMessage());
        }
        System.out.print("Enter begin time: ");
        String sFrom = input.nextLine();
        try {
            from = checkTime(sFrom);
        }catch (Exception ex){
            logger.error(ex.getMessage());
        }
        System.out.print("Enter end time: ");
        String sTo = input.nextLine();
        try {
            to = checkTime(sTo);
        }catch (Exception ex){
            logger.error(ex.getMessage());
        }
        try {
            time = calPlanTime();
        }catch (Exception ex){
            logger.error(ex.getMessage());
        }

        System.out.print("Enter Assignee's name: ");
        String sAssignee = input.nextLine();
        try {
            assignee = checkName(sAssignee);
        }catch (Exception ex){
            logger.error(ex.getMessage());
        }
        System.out.print("Enter reviewer's name: ");
        String sReviewer = input.nextLine();
        try {
            reviewer = checkName(sReviewer);
        }catch (Exception ex){
            logger.error(ex.getMessage());
        }
    }

    public void display(){
        System.out.printf("%-10s %-20s %-20s %-20s %-10s %-20s %-20s %n", taskID, taskName, typeName, date, time, assignee, reviewer);
    }

    public String checkName(String sName) throws Exception{
        if(!sName.isEmpty()){
            return sName;
        }else {
            throw new Exception("Name can not be empty!");
        }
    }
    public int checkType(String sType) throws Exception{
        if (sType.isEmpty()){
            throw new Exception("Type ID can not be empty!");
        }
        int typeID;
        try {
            typeID = Integer.parseInt(sType);
        }catch (Exception ex){
            throw new Exception("Type ID must be a number!");
        }
        if(typeID > 0){
            Connection connection = DBUtil.getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call findType(?)}");
            callableStatement.setInt(1, typeID);

            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()){
                resultSet.close();
                callableStatement.close();
                connection.close();
                return typeID;
            }
            else {
                throw new Exception("Type ID is not valid!");
            }
        }else {
            throw new Exception("Type ID must be a positive number!");
        }
    }

    public String checkDate(String sDate) throws Exception{
        if (sDate.isEmpty()){
            throw new Exception("Date input can not be empty!");
        }
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(sDate);
        if (matcher.matches()){
            return sDate;
        }else {
            throw new Exception("Date input is not correct format!");
        }
    }
    public float checkTime(String sTime) throws Exception{
        if(sTime.isEmpty()){
            throw new Exception("Time input can not be empty!");
        }
        float time;
        try {
            time = Float.parseFloat(sTime);
        }catch (Exception ex){
            throw new Exception("Time must be a number!");
        }
        if (time >= 8 && time <= 17.5){
            return time;
        }else {
            throw new Exception("Plan time must be from 8 to 17.5!");
        }
    }
    public float calPlanTime() throws Exception{
        if (from < to){
            return to-from;
        }else {
            throw new Exception("End time must be greater than begin time!");
        }
    }
    public void displayTask(){
        System.out.printf("%-10s %-20s %n", taskID, taskName);
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getFrom() {
        return from;
    }

    public void setFrom(float from) {
        this.from = from;
    }

    public float getTo() {
        return to;
    }

    public void setTo(float to) {
        this.to = to;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }
}

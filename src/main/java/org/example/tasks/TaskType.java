package org.example.tasks;

import org.apache.log4j.Logger;

import java.util.Scanner;

public class TaskType {
    Logger logger = Logger.getLogger(TaskType.class);
    Scanner input = new Scanner(System.in);
    int typeID;
    String typeName;

    public TaskType(int typeID, String typeName) {
        this.typeID = typeID;
        this.typeName = typeName;
    }
    public TaskType(){

    }

    public void inputType(){
        System.out.print("Enter Task type's name: ");
        String sName = input.nextLine();
        try {
            typeName = checkName(sName);
        }catch (Exception ex){
            logger.error(ex.getMessage());
        }
    }

    public String checkName(String sName) throws Exception{
        if (sName.isEmpty()){
            throw new Exception("Name can not be empty!");
        }else {
            return sName;
        }
    }

    public void displayType(){
        System.out.printf("%-10s %-20s %n", typeID, typeName);
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}

/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.thirdtask.orgstructure;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import ru.thevalidator.thirdtask.orgstructure.entity.Employee;
import ru.thevalidator.thirdtask.orgstructure.service.OrgStructureParser;
import ru.thevalidator.thirdtask.orgstructure.service.impl.OrgStructureParserImpl;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class OrgStructureDemo {
    
    public static void main(String[] args) throws IOException, URISyntaxException {
        
        OrgStructureDemo demo = new OrgStructureDemo();
        File csvFile = demo.getCsvFile("employees.csv");
        //File csvFile = demo.getCsvFile("test.csv");
        
        OrgStructureParser parser = new OrgStructureParserImpl();
        Employee boss = parser.parseStructure(csvFile);
        
        printOrgStructure(boss);
        
    }
    
    public File getCsvFile(String filename) throws URISyntaxException {
        return new File(getClass().getClassLoader().getResource(filename).toURI());
    }
    
    public static void printOrgStructure(Employee e) {
        printEmployee(e, "");
    }
    
    private static void printEmployee(Employee e, String tab) {
        System.out.println(tab 
                + "ID = " + e.getId() 
                + ", NAME = " + e.getName() 
                + ", POS = " + e.getPosition());
        
        for (Employee s : e.getSubordinate()) {
            printEmployee(s, tab + "\t");
        }
    }
    
}

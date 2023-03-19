/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.thirdtask.orgstructure;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import ru.thevalidator.thirdtask.orgstructure.service.OrgStructureParser;
import ru.thevalidator.thirdtask.orgstructure.service.impl.OrgStructureParserImpl;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class OrgStructureDemo {
    
    public static void main(String[] args) throws IOException, URISyntaxException {
        
        OrgStructureDemo demo = new OrgStructureDemo();
        File csvFile = demo.getCsvFile();
        
        OrgStructureParser parser = new OrgStructureParserImpl();
        parser.parseStructure(csvFile);
        
    }
    
    public File getCsvFile() throws URISyntaxException {
        return new File(getClass().getClassLoader().getResource("employees.csv").toURI());
    }

    
}

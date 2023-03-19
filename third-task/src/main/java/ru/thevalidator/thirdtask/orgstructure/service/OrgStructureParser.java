/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.thirdtask.orgstructure.service;

import java.io.File;
import java.io.IOException;
import ru.thevalidator.thirdtask.orgstructure.entity.Employee;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface OrgStructureParser {

    public Employee parseStructure(File csvFile) throws IOException;
    
}

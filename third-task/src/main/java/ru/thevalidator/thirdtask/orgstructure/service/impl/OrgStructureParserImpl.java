/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.thirdtask.orgstructure.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.thevalidator.thirdtask.orgstructure.entity.Employee;
import ru.thevalidator.thirdtask.orgstructure.service.OrgStructureParser;

public class OrgStructureParserImpl implements OrgStructureParser {

    private Map<Long, Employee> employees = new HashMap<>(); 

    @Override
    public Employee parseStructure(File csvFile) throws IOException {
        Employee boss = null;
        try ( FileInputStream fis = new FileInputStream(csvFile); 
                BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"))) {

            String line;
            br.readLine();
            while ((line = br.readLine()) != null && !line.isBlank()) {
                Employee em = readEmployee(line);
                if (em.getBossId() == null) {
                    boss = em;
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(OrgStructureParserImpl.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            employees = new HashMap<>();
        }

        return boss;
    }

    private Employee readEmployee(String line) {
        String[] data = line.split(";");
        Long id = Long.valueOf(data[0]);
        Long bossId = data[1].isEmpty() ? null : Long.valueOf(data[1]);
        String name = data[2];
        String position = data[3];

        Employee em = getEmployeeOrCreateIfAbsent(id);

        Employee boss = null;
        if (bossId != null) {
            boss = getEmployeeOrCreateIfAbsent(bossId);
            boss.getSubordinate().add(em);
        }
        
        em.setBoss(boss);
        em.setBossId(bossId);
        em.setId(id);
        em.setName(name);
        em.setPosition(position);

        return em;
    }

    private Employee getEmployeeOrCreateIfAbsent(Long id) {
        Employee e;
        if (employees.containsKey(id)) {
            e = employees.get(id);
        } else {
            e = new Employee();
            e.setId(id);
        }
        employees.put(id, e);
        
        return e;
    }

}

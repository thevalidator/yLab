/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.thirdtask.transliterator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransliteratorImpl implements Transliterator {

    private static final String DICT_FILE_NAME = "transliterator.dict";

    private final Map<Character, String> dictionary;

    public TransliteratorImpl() {
        dictionary = new HashMap<>();
        initData(dictionary);
    }

    @Override
    public String transliterate(String source) {
        if (source == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (char c : source.toCharArray()) {
            if (dictionary.containsKey(c)) {
                String replace = dictionary.get(c);
                sb.append(replace);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private void initData(Map<Character, String> dictionary) {
        try ( InputStream is = getClass().getClassLoader().getResourceAsStream(DICT_FILE_NAME);  
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {

            String line;
            while ((line = br.readLine()) != null && !line.isBlank()) {
                if (!validateLine(line)) {
                    throw new WrongLineFormatException(line);
                }
                Character c = line.charAt(0);
                String v = line.substring(2).startsWith("-") ? "" : line.substring(2);
                dictionary.put(c, v);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TransliteratorImpl.class.getName()).log(Level.SEVERE, "ERROR FOUND", ex);
            System.exit(1);
        } catch (IOException | WrongLineFormatException ex) {
            Logger.getLogger(TransliteratorImpl.class.getName()).log(Level.SEVERE, ex.getMessage());
            System.exit(1);
        }
    }

    //  simple input string validation
    private boolean validateLine(String line) {
        return line.length() > 2 && line.charAt(1) == ':';
    }

}

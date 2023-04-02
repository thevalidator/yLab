/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson05.messagefilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class DictionaryCreator {

    public static void main(String[] args) {
        
        File rawData = new File("words.txt");
        File dictionary = new File("words.dict");
        
        try (InputStream is = new FileInputStream(rawData); 
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8")); 
                OutputStream os = new FileOutputStream(dictionary); 
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, "UTF-8"))) {

            Set<String> words = new TreeSet<>();
            String line;
            while ((line = br.readLine()) != null && !line.isBlank()) {
                String[] separatedWords = line.trim().split(",( )?");
                for (String w: separatedWords) {
                    if (!w.contains(" ")) {
                        words.add(w.toLowerCase());
                    }
                }
            }
            
            for (String word: words) {
                pw.println(word);
            }
            pw.flush();
            
        } catch (IOException ex) {
            Logger.getLogger(MessageFilterApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}

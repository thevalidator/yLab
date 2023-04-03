/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson05.messagefilter.service.impl;

import io.ylab.intensive.lesson05.messagefilter.service.DbService;
import io.ylab.intensive.lesson05.messagefilter.service.SwearingSubstitutionService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwearingSubstitutionServiceImpl implements SwearingSubstitutionService {

    private static final Set<Character> SEPARATORS = new HashSet(Arrays.asList(' ', ',', '.', ';', '!', '?', '\n', '\r', '\t', '\f'));
    private static final String MASK_SYMBOL = "*";
    private final DbService dbService;

    @Autowired
    public SwearingSubstitutionServiceImpl(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public String censor(String input) {
        StringBuilder transformedInput = new StringBuilder(input.length());
        StringBuilder actualWord = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (SEPARATORS.contains(c)) {
                appendWord(actualWord, transformedInput);
                transformedInput.append(c);
            } else {
                actualWord.append(c);
            }
        }
        appendWord(actualWord, transformedInput);

        return transformedInput.toString();
    }

    private void appendWord(StringBuilder actualWord, StringBuilder transformedInput) {
        if (actualWord.length() > 0) {
            String word = filterSwearing(actualWord.toString());
            transformedInput.append(word);
            actualWord.setLength(0);
        }
    }

    private String filterSwearing(String word) {
        if (word.length() > 2 && dbService.isWordExists(word)) {
            StringBuilder sb = new StringBuilder(word.length());
            sb.append(word.charAt(0));
            for (int i = 1; i < word.length() - 1; i++) {
                sb.append(MASK_SYMBOL);
            }
            sb.append(word.charAt(word.length() - 1));

            return sb.toString();
        }

        return word;
    }

}

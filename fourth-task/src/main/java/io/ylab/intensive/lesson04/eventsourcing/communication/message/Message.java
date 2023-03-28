/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson04.eventsourcing.communication.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.ylab.intensive.lesson04.eventsourcing.Person;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */

public class Message {

    private final Person person;
    private final ActionType action;

    public Message(@JsonProperty("person") Person person, 
            @JsonProperty("action") ActionType action) {
        this.person = person;
        this.action = action;
    }

    public Person getPerson() {
        return person;
    }

    public ActionType getAction() {
        return action;
    }
    
}

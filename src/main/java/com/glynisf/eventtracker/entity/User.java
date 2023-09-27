package com.glynisf.eventtracker.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class User {
    private final Logger logger = LogManager.getLogger(this.getClass());

    String name;


    public User() {
        logger.debug("debug");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

}
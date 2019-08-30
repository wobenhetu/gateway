package com.yss.gateway.entity;

import java.util.HashMap;
import java.util.Map;

public class Users {

    public static final Map<String, String> USERS_MAP = new HashMap<String, String>();

    static {
        USERS_MAP.put("admin", "admin");
        USERS_MAP.put("jamin", "jamin");
        USERS_MAP.put("allen", "allen");
        USERS_MAP.put("james", "james");
    }
}
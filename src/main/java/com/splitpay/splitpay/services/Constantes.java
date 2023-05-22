package com.splitpay.splitpay.services;

import io.github.cdimascio.dotenv.Dotenv;

public class Constantes {
    public static final String USERNAME = Dotenv.load().get("DB_USERNAME");
    public static final String PASSWORD = Dotenv.load().get("DB_PASSWORD");
    public static final String THINCONN = Dotenv.load().get("DB_URI");
}

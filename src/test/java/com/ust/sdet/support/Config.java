package com.ust.sdet.support;

public final class Config {
    private Config() {
    }

    public static String baseUrl() {
        return "http://localhost:5173";
    }

    public static String catalogUrl() {
        return baseUrl() + "/catalog";
    }

    public static boolean headless() {
        return Boolean.parseBoolean(System.getProperty("headless", "false"));
    }
}

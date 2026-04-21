package com.andersonmesq.autosavi.utils;

import org.slf4j.Logger;
import org.slf4j.MDC;

public class LogMarkers {
    private static final String UI = "ui";

    public static void user(Logger log, String msg, Object... args) {
        try {
            MDC.put(UI, "true");
            log.info(msg, args);
        } finally {
            MDC.remove(UI);
        }
    }
}
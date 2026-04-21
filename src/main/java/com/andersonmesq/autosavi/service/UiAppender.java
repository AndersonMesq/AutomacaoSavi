package com.andersonmesq.autosavi.service;

import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.classic.spi.ILoggingEvent;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UiAppender extends AppenderBase<ILoggingEvent> {
    private static Consumer<String> uiConsumer;
    private static boolean debugMode = false;

    //Oque é logBuffer?
    private static final List<String> logBuffer = new ArrayList<>();

    public static void setUiConsumer(Consumer<String> consumer) {
        uiConsumer = consumer;
    }

    public static void setDebugMode(boolean enabled) {
        debugMode = enabled;
    }

    public static String getFullLog() {
        return String.join("\n", logBuffer);
    }

    //Oque é Protected?
//    @Override
//    protected void append(ILoggingEvent event) {
//        String msg = event.getFormattedMessage();
//
//        logBuffer.add(msg);
//        boolean isUserLog = event.getMarker() != null &&
//                event.getMarker().contains("USER");
//
//        if (uiConsumer != null) {
//            if (debugMode || isUserLog) {
//                Platform.runLater(() -> uiConsumer.accept(msg));
//            }
//        }
//    }

    @Override
    protected void append(ILoggingEvent event) {
        String msg = event.getFormattedMessage();
        logBuffer.add(msg);
        String uiFlag = event.getMDCPropertyMap().get("ui");
        boolean isUserLog = "true".equals(uiFlag);
        if (uiConsumer != null) {
            if (
                    (!debugMode && isUserLog) ||
                            (debugMode && !isUserLog)
            ) {
                Platform.runLater(() -> uiConsumer.accept(msg));
            }
        }
    }
}
package com.andersonmesq.autosavi.utils;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class TooltipFactory {
    private static final Duration SHOW_DELAY = Duration.millis(300);
    private static final Duration SHOW_DURATION = Duration.seconds(5);

    public static Tooltip create(String text) {
        Tooltip tooltip = new Tooltip(text);
        tooltip.setShowDelay(SHOW_DELAY);
        tooltip.setShowDuration(SHOW_DURATION);
        return tooltip;
    }

    public static void install(Node node, String text) {
        Tooltip.install(node, create(text));
    }
}

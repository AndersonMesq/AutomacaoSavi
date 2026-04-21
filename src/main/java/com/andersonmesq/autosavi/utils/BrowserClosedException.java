package com.andersonmesq.autosavi.utils;

public class BrowserClosedException extends RuntimeException{
    public BrowserClosedException() {
        super("Browser fechado");
    }
}

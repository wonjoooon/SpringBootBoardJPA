package com.wonjun.service;

import org.aspectj.bridge.IMessage;

import javax.xml.crypto.Data;

public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException() {}
    public DataNotFoundException(String message){
        super(message);
    }
}

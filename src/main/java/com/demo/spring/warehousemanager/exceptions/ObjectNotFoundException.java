package com.demo.spring.warehousemanager.exceptions;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String s) {
        super(s);
    }
}

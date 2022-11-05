package com.zhao.exception;

import org.springframework.security.access.AccessDeniedException;

public class MyAccessDeniedException extends AccessDeniedException {

    /**
     * Constructs an <code>AccessDeniedException</code> with the specified message.
     * @param msg the detail message
     */
    public MyAccessDeniedException(String msg) {
        super(msg);
    }
}

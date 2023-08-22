package com.visionrent.exception;

public class ResourceNotFoundException extends RuntimeException {
    /**
     *
     * @param message that will be sent when we throw this exception
     *                By calling the super constructor, we have ability to throw our own custom exception
     *                with custom messages
     */
    public ResourceNotFoundException(String message){
        super(message);
    }

}

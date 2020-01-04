package com.bhanu.psychiba.exceptions;

/**
 * IllegalGameException
 */
public class InsufficientPlayersException extends Throwable {
    public InsufficientPlayersException(String message){
        super(message);
    }
}
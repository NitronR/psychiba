package com.bhanu.psychiba.exceptions;

/**
 * IllegalGameException
 */
public class InsufficientPlayersException extends Exception {
    public InsufficientPlayersException(String message){
        super(message);
    }
}
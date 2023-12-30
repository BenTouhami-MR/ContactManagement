package org.example.bll;

import org.example.data.DataBaseException;

import java.sql.SQLException;

public class BuiesnessLogicException extends Exception {
    public BuiesnessLogicException(){

    }
    public BuiesnessLogicException(String message){
        super(message);
    }
}

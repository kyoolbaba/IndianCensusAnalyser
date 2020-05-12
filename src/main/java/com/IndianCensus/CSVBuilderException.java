package com.IndianCensus;

public class CSVBuilderException extends Exception {
    enum ExceptionType {
        CENSUS_FILE_PROBLEM, FILE_NOT_PRESENT,DATA_NOT_APPROPRIATE,CORRECT_FILE,INCORRECT_DELIMITER
        ,NOT_ABLE_TO_PARSE
    }
    ExceptionType type;
    CSVBuilderException(String message,ExceptionType type){
        super(message);
        this.type = type;
    }
}

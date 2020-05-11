package com.IndianCensus;

public class CensusAnalyserException extends Exception {

    enum ExceptionType {
        CENSUS_FILE_PROBLEM, FILE_NOT_PRESENT,DATA_NOT_APPROPRIATE,CORRECT_FILE,INCORRECT_DELIMITER
        ,NOT_ABLE_TO_PARSE
    }

    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}

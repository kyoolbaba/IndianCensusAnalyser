package com.IndianCensus;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class CensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "C:\\Users\\KYOOLBABAA\\Desktop\\New folder\\StateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "milan.txt";
    private static final String INCOMPATIBLE_CSV_FILE_PATH = "C:\\Users\\KYOOLBABAA\\Desktop\\New folder\\Users.csv";
    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.testFileIfIncorrect(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException  e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.FILE_NOT_PRESENT,e.type);
        }
    }

}

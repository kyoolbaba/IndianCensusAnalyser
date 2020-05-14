package com.IndianCensus;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class CensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "C:\\Users\\KYOOLBABAA\\Desktop\\New folder\\StateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "milan.txt";
    private static final String INCOMPATIBLE_CSV_FILE_PATH = "C:\\Users\\KYOOLBABAA\\Desktop\\New folder\\Users.csv";
    private static final String INDIAN_STATE_CODES = "C:\\Users\\KYOOLBABAA\\Desktop\\New folder\\StateCode.csv";

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

    @Test
    public void givenCSVFileWithIncorrectHeader_ShouldThrowAnException(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.testFileIfIncorrect(INCOMPATIBLE_CSV_FILE_PATH);
        } catch (CensusAnalyserException  e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DATA_NOT_APPROPRIATE,e.type);
        }
    }

    @Test
    public void givenCSVFileWithIncorrectDelimter_ShouldThrowException(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.checkDelimiter(INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException  e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DELIMITER,e.type);
        }
    }

    @Test
    public void givenIndianStateCodeCSVFileReturnsCorrectRecord() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser=new CensusAnalyser();
        int numberOfStateCodes= censusAnalyser.loadIndianStateCode(INDIAN_STATE_CODES);
        Assert.assertEquals(37,numberOfStateCodes);
    }

    @Test
    public void givenIndianCensusDataWhenSortedStateShouldReturnFirstResult() throws CensusAnalyserException {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData= censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV= new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh",censusCSV[0].state);
    }catch(CensusAnalyserException e ){ }
    }

    @Test
    public void givenIndianCensusDataWhenSortedStateShouldReturnLastResult() throws CensusAnalyserException {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData= censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV= new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("West Bengal",censusCSV[censusCSV.length-1].state);
        }catch(CensusAnalyserException e ){ }
    }

    @Test
    public void  givenIndianCensusDataWhenSortedPopulationShouldReturnMaxPopulatedState() {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData= censusAnalyser.getPopulationWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV= new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh",censusCSV[censusCSV.length-1].state);
        }catch(CensusAnalyserException e ){ }
    }

    @Test
    public void  givenIndianCensusDataWhenSortedPopulationShouldReturnLessPopulatedState() {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData= censusAnalyser.getPopulationWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV= new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Sikkim",censusCSV[0].state);
        }catch(CensusAnalyserException e ){ }
    }

    @Test
    public void givenIndianCensusDataWhenSortedByDensityShouldReturnMaxDensityState() {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData= censusAnalyser.getDensityWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV= new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar",censusCSV[censusCSV.length-1].state);
        }catch(CensusAnalyserException e ){ }
    }

    @Test
    public void givenIndianCensusDataWhenSortedByDensityShouldReturnMinDensityState() {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData= censusAnalyser.getDensityWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV= new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Arunachal Pradesh",censusCSV[0].state);
        }catch(CensusAnalyserException e ){ }
    }

    @Test
    public void givenIndianCensusDataWhenSortedByAreaShouldReturnMinAreaState() {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData= censusAnalyser.getAreaWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV= new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Arunachal Pradesh",censusCSV[0].state);
        }catch(CensusAnalyserException e ){ }
    }

    @Test
    public void givenIndianCensusDataWhenSortedPopulationShouldReturnMaxAreaState() {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData= censusAnalyser.getAreaWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV= new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan",censusCSV[censusCSV.length-1].state);
        }catch(CensusAnalyserException e ){ }
    }

}

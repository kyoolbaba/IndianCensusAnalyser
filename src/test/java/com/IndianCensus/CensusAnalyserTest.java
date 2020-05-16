package com.IndianCensus;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class CensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "C:\\Users\\KYOOLBABAA\\Desktop\\New folder\\StateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "milan.txt";
    private static final String INCOMPATIBLE_CSV_FILE_PATH = "C:\\Users\\KYOOLBABAA\\Desktop\\New folder\\Users.csv";
    private static final String INDIAN_STATE_CODES = "C:\\Users\\KYOOLBABAA\\Desktop\\New folder\\StateCode.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "C:\\Users\\KYOOLBABAA\\Desktop\\New folder\\USCensusData.csv";
    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int noOfEnteries=censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CODES);
            Assert.assertEquals(29,noOfEnteries);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException  e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenCSVFileWithIncorrectHeader_ShouldThrowAnException(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INCOMPATIBLE_CSV_FILE_PATH);
        } catch (CensusAnalyserException  e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenCSVFileWithIncorrectDelimter_ShouldThrowException(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CODES    );
        } catch (CensusAnalyserException  e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianStateCodeCSVFileReturnsCorrectRecord() throws CensusAnalyserException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numberOfStateCodes=censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CODES);
            Assert.assertEquals(29,numberOfStateCodes);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndianCensusDataWhenSortedStateShouldReturnFirstResult() throws CensusAnalyserException {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CODES);
            String sortedCensusData= censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV= new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh",censusCSV[0].state);
        }catch(CensusAnalyserException e ){ }
    }

    @Test
    public void givenIndianCensusDataWhenSortedStateShouldReturnLastResult() throws CensusAnalyserException {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CODES);
            String sortedCensusData= censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV= new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("West Bengal",censusCSV[censusCSV.length-1].state);
        }catch(CensusAnalyserException e ){ }
    }

    @Test
    public void  givenIndianCensusDataWhenSortedPopulationShouldReturnMaxPopulatedState() {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CODES);
            String sortedCensusData= censusAnalyser.getPopulationWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV= new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh",censusCSV[censusCSV.length-1].state);
        }catch(CensusAnalyserException e ){ }
    }

    @Test
    public void  givenIndianCensusDataWhenSortedPopulationShouldReturnLessPopulatedState() {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CODES);
            String sortedCensusData= censusAnalyser.getPopulationWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV= new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Sikkim",censusCSV[0].state);
        }catch(CensusAnalyserException e ){ }
    }

    @Test
    public void givenIndianCensusDataWhenSortedByDensityShouldReturnMaxDensityState() {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CODES);
            String sortedCensusData= censusAnalyser.getDensityWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV= new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar",censusCSV[censusCSV.length-1].state);
        }catch(CensusAnalyserException e ){ }
    }

    @Test
    public void givenIndianCensusDataWhenSortedByDensityShouldReturnMinDensityState() {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CODES);
            String sortedCensusData= censusAnalyser.getDensityWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV= new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Arunachal Pradesh",censusCSV[0].state);
        }catch(CensusAnalyserException e ){ }
    }

    @Test
    public void givenIndianCensusDataWhenSortedByAreaShouldReturnMinAreaState() {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CODES);
            String sortedCensusData= censusAnalyser.getAreaWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV= new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Arunachal Pradesh",censusCSV[0].state);
        }catch(CensusAnalyserException e ){ }
    }

    @Test
    public void givenIndianCensusDataWhenSortedPopulationShouldReturnMaxAreaState() {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CODES);
            String sortedCensusData= censusAnalyser.getAreaWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV= new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan",censusCSV[censusCSV.length-1].state);
        }catch(CensusAnalyserException e ){ }
    }

    @Test
    public void loadUSCensusDataShouldReturnCorrectRecord() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int noOfUSCensusRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, noOfUSCensusRecords);
        }catch(CensusAnalyserException e){}
    }
}
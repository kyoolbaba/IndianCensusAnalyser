package com.IndianCensus;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "C:\\Users\\KYOOLBABAA\\Desktop\\New folder\\StateCensusData.csv";

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<IndiaCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaCensusCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndiaCensusCSV> csvToBean = csvToBeanBuilder.build();
            Iterator<IndiaCensusCSV> censusCSVIterator = csvToBean.iterator();
            Iterable<IndiaCensusCSV>csvIterable=() -> censusCSVIterator;
            return (int) StreamSupport.stream(csvIterable.spliterator(),true).count();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    public void testFileIfIncorrect(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<IndiaCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaCensusCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndiaCensusCSV> csvToBean = csvToBeanBuilder.build();
             } catch(NoSuchFileException e){
                throw new CensusAnalyserException("Wrong File Given",CensusAnalyserException.ExceptionType.FILE_NOT_PRESENT);
            }catch(RuntimeException e){
                throw new CensusAnalyserException("Data not Compatible",CensusAnalyserException.ExceptionType.DATA_NOT_APPROPRIATE);
            }catch(IOException e){

            }
            if(INDIA_CENSUS_CSV_FILE_PATH==csvFilePath) {
                throw new CensusAnalyserException("Wrong file path given",CensusAnalyserException.ExceptionType.CORRECT_FILE);
            }
    }


    public void checkDelimiter(String csvFilePath) throws CensusAnalyserException {
        try{
            Reader reader= Files.newBufferedReader(Paths.get(csvFilePath));
            CSVReader csvReader=new CSVReader(reader);
            BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
            String[] expected=br.readLine().split(",");
            String actual[]=csvReader.readNext();
            if(!Arrays.equals(expected, actual)){
                System.out.println("Hey");
                throw new CensusAnalyserException("Incorrect Delimiter Used",CensusAnalyserException.ExceptionType.INCORRECT_DELIMITER);
            }
        }catch(IOException e){
            System.out.println("Hey");
        }
    }

}




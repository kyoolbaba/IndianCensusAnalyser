package com.IndianCensus;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusCSV> censusCSVList=null;
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "C:\\Users\\KYOOLBABAA\\Desktop\\New folder\\StateCensusData.csv";

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
        censusCSVList = csvBuilder.getCSVFileList(reader,IndiaCensusCSV.class);
            return censusCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch(CSVBuilderException e){
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    public void testFileIfIncorrect(String csvFilePath) throws CensusAnalyserException {
        try ( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
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

//    public void checkDelimiter(String csvFilePath) throws CensusAnalyserException {
//        try{
//            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
//            CsvToBean<IndiaStateCodeCSV> csvToBean = new CsvToBeanBuilder<IndiaStateCodeCSV>(reader)
//                    .withSeparator(',')
//                    .withType(IndiaStateCodeCSV.class)
//                    .withIgnoreLeadingWhiteSpace(true).build();
//            Iterator<IndiaStateCodeCSV> censusCSVIterator = csvToBean.iterator();
//            Iterator<IndiaStateCodeCSV> firstline=csvToBean.iterator();
//
//            if(!Arrays.equals(expected, actual)){
//                throw new CensusAnalyserException("Incorrect Delimiter Used",CensusAnalyserException.ExceptionType.INCORRECT_DELIMITER);
//            }
//        }catch(IOException e){
//            System.out.println("Hey");
//        }
//    }

    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException{
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            censusCSVList =  csvBuilder.getCSVFileList(reader,IndiaStateCodeCSV.class);
            return censusCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch(CSVBuilderException e){
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    private <E>int getCount(Iterator<E> iterator){
        Iterable<E>csvIterable=() -> iterator;
        return (int) StreamSupport.stream(csvIterable.spliterator(),true).count();
    }


    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if(censusCSVList==null||censusCSVList.size()==0){
            throw new CensusAnalyserException("No Census data ",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusCSV> censusCSVComparator=Comparator.comparing(census->census.state);
        this.sort(censusCSVComparator);
        String sortedStateensusjson=new Gson().toJson(censusCSVList);
        return sortedStateensusjson;
    }

    private void sort( Comparator<IndiaCensusCSV> censusCSVComparator) {
        for(int i=0;i<censusCSVList.size();i++){
            for(int j=0;j<censusCSVList.size()-i-1;j++){
                IndiaCensusCSV census1=censusCSVList.get(j);
                IndiaCensusCSV census2=censusCSVList.get(j+1);
                if(censusCSVComparator.compare(census1,census2)>0){
                    censusCSVList.set(j,census2);
                    censusCSVList.set(j+1,census1);
                }
            }
        }
    }
}




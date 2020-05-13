package com.IndianCensus;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusDAO> censusList=null;
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "C:\\Users\\KYOOLBABAA\\Desktop\\New folder\\StateCensusData.csv";

    public CensusAnalyser() {
        this.censusList = new ArrayList<IndiaCensusDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while(csvFileIterator.hasNext()){
                this.censusList.add(new IndiaCensusDAO( csvFileIterator.next()));
            }
            return this.censusList.size();
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
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
            }
            if(INDIA_CENSUS_CSV_FILE_PATH==csvFilePath) {
                throw new CensusAnalyserException("Wrong file path given",CensusAnalyserException.ExceptionType.CORRECT_FILE);
            }
    }

    public static void main(String[] args) throws CensusAnalyserException {
        CensusAnalyser censusAnalyser=new CensusAnalyser();
        censusAnalyser.checkDelimiter(INDIA_CENSUS_CSV_FILE_PATH);
    }

    public void checkDelimiter(String csvFilePath) throws CensusAnalyserException {
        try{
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBean<IndiaStateCodeCSV> csvToBean = new CsvToBeanBuilder<IndiaStateCodeCSV>(reader)
                    .withSeparator(',')
                    .withType(IndiaStateCodeCSV.class)
                    .withIgnoreLeadingWhiteSpace(true).build();
        }catch(IOException e){
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch(RuntimeException e){
            throw new CensusAnalyserException("Incorrect delimiter used",
                    CensusAnalyserException.ExceptionType.INCORRECT_DELIMITER);
        }
    }

    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException{
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            censusList =  csvBuilder.getCSVFileList(reader,IndiaStateCodeCSV.class);
            return this.censusList.size();
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
        Comparator<IndiaCensusDAO> censusCSVComparator=Comparator.comparing(census->census.state);
        return this.sortWithRespectToColumn(censusCSVComparator);
    }

    public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        Comparator<IndiaCensusDAO> censusCSVComparator=Comparator.comparing(census->census.population);
        return this.sortWithRespectToColumn(censusCSVComparator);
    }

    public String sortWithRespectToColumn(Comparator censusSortColumn) throws CensusAnalyserException {
        if(censusList==null||censusList.size()==0){
            throw new CensusAnalyserException("No Census data ",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        this.sort(censusSortColumn);
        String sortedColumnensusjson=new Gson().toJson(this.censusList);
        return sortedColumnensusjson;
    }

    private void sort( Comparator<IndiaCensusDAO> censusCSVComparator) {
        for(int i=0;i<censusList.size();i++){
            for(int j=0;j<censusList.size()-i-1;j++){
                IndiaCensusDAO census1=censusList.get(j);
                IndiaCensusDAO census2=censusList.get(j+1);
                if(censusCSVComparator.compare(census1,census2)>0){
                    censusList.set(j,census2);
                    censusList.set(j+1,census1);
                }
            }
        }
    }


}




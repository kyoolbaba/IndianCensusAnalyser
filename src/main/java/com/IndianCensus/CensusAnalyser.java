package com.IndianCensus;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    Map<String, IndiaCensusDAO> censusStateMap =null;
    List<IndiaCensusCSV> censusList= new ArrayList<>();


    public CensusAnalyser() {
        this.censusStateMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> csvIterable= () ->csvFileIterator;
            StreamSupport.stream(csvIterable.spliterator(),false)
                    .forEach(censusCSV -> censusStateMap.put(censusCSV.state,new IndiaCensusDAO(censusCSV)));
            return censusStateMap.size();
        } catch (IOException |RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch(CSVBuilderException e){
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException{
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> csvStateCodeIterator = csvBuilder.getCSVFileIterator(reader,IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvStateCodeIterable=()-> csvStateCodeIterator;
            StreamSupport.stream(csvStateCodeIterable.spliterator(),false)
                    .filter(csvStateCode->censusStateMap.get(csvStateCode.state)!=null)
                    .forEach(csvStateCode->censusStateMap.get(csvStateCode.state).stateCode=csvStateCode.stateCode);
            return censusStateMap.size();

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

    public int loadUSCensusData(Object usCensusCsvFilePath) {

        return 0;
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        Comparator<IndiaCensusDAO> censusCSVComparator=Comparator.comparing(census->census.state);
        return this.sortWithRespectToColumn(censusCSVComparator);
    }

    public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        Comparator<IndiaCensusDAO> censusCSVComparator=Comparator.comparing(census->census.population);
        return this.sortWithRespectToColumn(censusCSVComparator);
    }


    public String getDensityWiseSortedCensusData() throws CensusAnalyserException {
        Comparator<IndiaCensusDAO> censusCSVComparator=Comparator.comparing(census->census.densityPerSqKm);
        return this.sortWithRespectToColumn(censusCSVComparator);
    }

    public String getAreaWiseSortedCensusData() throws CensusAnalyserException {
        Comparator<IndiaCensusDAO> censusCSVComparator=Comparator.comparing(census->census.areaInSqKm);
        return this.sortWithRespectToColumn(censusCSVComparator);
    }

    public String sortWithRespectToColumn(Comparator censusSortColumn) throws CensusAnalyserException {
        if(censusStateMap==null||censusStateMap.size()==0){
            throw new CensusAnalyserException("No Census data ",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List <IndiaCensusDAO> sortedCensusDataWithRespectToColumn= (List<IndiaCensusDAO>) censusStateMap.values().stream().sorted(censusSortColumn)
                                                                        .collect(Collectors.toList());
        String sortedColumnensusjson=new Gson().toJson(sortedCensusDataWithRespectToColumn);
        return sortedColumnensusjson;
    }


}




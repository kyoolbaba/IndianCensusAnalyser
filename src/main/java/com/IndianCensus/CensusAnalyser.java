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
    Map<String, CensusDAO> censusStateMap =null;
    List<IndiaCensusCSV> censusList= new ArrayList<>();


    public CensusAnalyser() {
        this.censusStateMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
      return this.loadCensusData(csvFilePath,IndiaCensusCSV.class);
    }


    public int loadUSCensusData(String  csvFilePath) throws CensusAnalyserException {
        return this.loadCensusData(csvFilePath,USCensusCSV.class);
    }


    private<E> int loadCensusData(String csvFilePath, Class<E> censusCSVCLass) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvFileIterator = csvBuilder.getCSVFileIterator(reader, censusCSVCLass);
            Iterable<E> csvIterable= () ->csvFileIterator;
            if(censusCSVCLass.getName().equals("com.IndianCensus.IndiaCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }else  if(censusCSVCLass.getName().equals("com.IndianCensus.USCensusCSV")){
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            return censusStateMap.size();
        } catch (IOException |RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch(CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
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


    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        Comparator<CensusDAO> censusCSVComparator=Comparator.comparing(census->census.state);
        return this.sortWithRespectToColumn(censusCSVComparator);
    }

    public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        Comparator<CensusDAO> censusCSVComparator=Comparator.comparing(census->census.population);
        return this.sortWithRespectToColumn(censusCSVComparator);
    }


    public String getDensityWiseSortedCensusData() throws CensusAnalyserException {
        Comparator<CensusDAO> censusCSVComparator=Comparator.comparing(census->census.populationDensity);
        return this.sortWithRespectToColumn(censusCSVComparator);
    }

    public String getAreaWiseSortedCensusData() throws CensusAnalyserException {
        Comparator<CensusDAO> censusCSVComparator=Comparator.comparing(census->census.totalArea);
        return this.sortWithRespectToColumn(censusCSVComparator);
    }

    public String sortWithRespectToColumn(Comparator censusSortColumn) throws CensusAnalyserException {
        if(censusStateMap==null||censusStateMap.size()==0){
            throw new CensusAnalyserException("No Census data ",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List <CensusDAO> sortedCensusDataWithRespectToColumn= (List<CensusDAO>) censusStateMap.values().stream().sorted(censusSortColumn)
                .collect(Collectors.toList());
        String sortedColumnensusjson=new Gson().toJson(sortedCensusDataWithRespectToColumn);
        return sortedColumnensusjson;
    }


}
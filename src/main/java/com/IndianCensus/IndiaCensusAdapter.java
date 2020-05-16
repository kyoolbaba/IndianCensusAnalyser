package com.IndianCensus;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusAdapter extends CensusAdapter {

    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String,CensusDAO> censusStateMap=super.loadCensusData(IndiaCensusCSV.class,csvFilePath[0]);
        this.loadIndianStateCode(csvFilePath[1],censusStateMap);
        return censusStateMap;
    }

    private int loadIndianStateCode(String csvFilePath,Map<String,CensusDAO> censusStateMap) throws CensusAnalyserException{
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



}

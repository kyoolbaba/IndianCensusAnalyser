package com.IndianCensus;

import com.google.gson.Gson;
import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {
    public enum Country{INDIA,US}
    Map<String, CensusDAO> censusStateMap =null;

    public CensusAnalyser() { }

    public int loadCensusData(Country country,String ... csvFilePath) throws CensusAnalyserException {
     censusStateMap=CensusAdapterFactory.getCensusData(country,csvFilePath);
     return censusStateMap.size();
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
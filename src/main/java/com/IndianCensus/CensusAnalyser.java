package com.IndianCensus;

import com.google.gson.Gson;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

public class CensusAnalyser {

    public enum Country{INDIA,US}
    private Country country;
    Map<String, CensusDAO> censusStateMap =null;

    public CensusAnalyser(Country country) {
        this.country=country;
    }

    public int loadCensusData(String ... csvFilePath) throws CensusAnalyserException {
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

    public String sortWithRespectToColumn(Comparator<CensusDAO> censusSortColumn) throws CensusAnalyserException {
        if(censusStateMap==null||censusStateMap.size()==0){
            throw new CensusAnalyserException("No Census data ",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List sortedCensusDataWithRespectToColumn = censusStateMap.values().stream().
                sorted(censusSortColumn).
                map(censusDAO -> censusDAO.getCensusDTO(country)).
                collect(Collectors.toList());
        String sortedColumnensusjson=new Gson().toJson(sortedCensusDataWithRespectToColumn);
        return sortedColumnensusjson;
    }


}
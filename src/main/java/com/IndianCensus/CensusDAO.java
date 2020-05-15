
package com.IndianCensus;

import jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode;

public class CensusDAO {

    public int population;
    public double populationDensity;
    public double totalArea;
    public String state;
    public String stateCode;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        totalArea = indiaCensusCSV.areaInSqKm;
        populationDensity = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;

    }

    public CensusDAO(USCensusCSV usCensusCSV) {
        state = usCensusCSV.state;
        stateCode = usCensusCSV.stateId;
        population = usCensusCSV.population;
        populationDensity = usCensusCSV.populationDensity;
        totalArea = usCensusCSV.totalArea;
    }

    public IndiaCensusCSV getIndiaCensusCSV(){
        return new IndiaCensusCSV(state,population,(int )populationDensity, (int) totalArea);
    }

    public USCensusCSV getUSCensusCSV(){
        return new USCensusCSV(state,stateCode,population,(int )populationDensity, (int) totalArea);
    }
}

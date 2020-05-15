package com.IndianCensus;

public class IndiaCensusDAO {

    public int population;
    public int densityPerSqKm;
    public int areaInSqKm;
    public  String state;
    public String stateCode;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaInSqKm=indiaCensusCSV.areaInSqKm;
        densityPerSqKm=indiaCensusCSV.densityPerSqKm;
        population=indiaCensusCSV.population;

    }
    public int getPopulation() {
        return population;
    }

    public int getDensityPerSqKm() {
        return densityPerSqKm;
    }

    public int getAreaInSqKm() {
        return areaInSqKm;
    }

    public String getState() {
        return state;
    }
}

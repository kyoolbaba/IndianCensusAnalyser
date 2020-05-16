package com.IndianCensus;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "State Id", required = true)
    public String stateId;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "Population Density", required = true)
    public double populationDensity;

    @CsvBindByName(column = "Total area", required = true)
    public double totalArea;

    public USCensusCSV(String state, int population, double populationDensity, double totalArea) {
        this.state = state;
        this.population = population;
        this.populationDensity = populationDensity;
        this.totalArea = totalArea;
    }
public USCensusCSV(){}

    @Override
    public String toString() {
        return "USCensusCSV{" +
                "state='" + state + '\'' +
                ", stateId='" + stateId + '\'' +
                ", population=" + population +
                ", populationDensity=" + populationDensity +
                ", totalArea=" + totalArea +
                '}';
    }
}

package com.IndianCensus;

public class IndianStateCodeDAO {
    public String state;
    public String stateCode;

    public IndianStateCodeDAO(IndiaStateCodeCSV indiaStateCodeCSV) {
        state = indiaStateCodeCSV.state;
        stateCode = indiaStateCodeCSV.stateCode;
    }
}

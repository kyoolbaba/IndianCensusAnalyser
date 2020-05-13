package com.IndianCensus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.Iterator;

public class CommonCSVAnalyser {

    public void loadCSVFile(String csvFilePath,Class csvClass) throws CensusAnalyserException {
        ICSVBuilder icsvBuilder = CSVBuilderFactory.createCSVBuilder();
        try(Reader reader =new BufferedReader((Reader) Paths.get(csvFilePath))){
            Iterator<?> iterator=icsvBuilder.getCSVFileIterator(reader,csvClass);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CSVBuilderException e){
            throw new CensusAnalyserException(e.getMessage(),
                    e.type.name());
        }
    }

}

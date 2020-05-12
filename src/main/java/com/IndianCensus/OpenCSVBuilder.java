package com.IndianCensus;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder <E> implements ICSVBuilder {

    public Iterator<E> getCSVFileIterator
            (Reader reader, Class csvClass)
            throws CensusAnalyserException {
      try{
            CsvToBean<E> csvToBean= new CsvToBeanBuilder<E>(reader)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withType(csvClass).build();
            return (Iterator<E>) csvToBean.iterator();
        }catch(IllegalStateException e){
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.NOT_ABLE_TO_PARSE);
        }
    }
}


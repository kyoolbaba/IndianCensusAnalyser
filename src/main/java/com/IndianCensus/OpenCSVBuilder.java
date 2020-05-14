package com.IndianCensus;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.opencsv.bean.MappingStrategy;

import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OpenCSVBuilder <E> implements ICSVBuilder {
    @Override
    public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException {
        return this.getCSVToBean(reader,csvClass).iterator();
    }

    @Override
    public List getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException {
        return this.getCSVToBean(reader, csvClass).parse();
    }

    @Override
    public List setCSVMapAndGetList(Reader reader, Class csvClass, Map mapping) throws CSVBuilderException {
        HeaderColumnNameTranslateMappingStrategy<E> strategy= new HeaderColumnNameTranslateMappingStrategy<E>();
        strategy.setType(csvClass);
        strategy.setColumnMapping(mapping);
        return this.getCSVToBean(reader, csvClass).parse(strategy,reader);
    }


    private CsvToBean getCSVToBean(Reader reader, Class csvClass) throws CSVBuilderException {
        try{
            CsvToBean<E> csvToBean= new CsvToBeanBuilder<E>(reader)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withType(csvClass).build();
            return csvToBean;
        }catch(IllegalStateException e){
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.NOT_ABLE_TO_PARSE);
        }
    }
}


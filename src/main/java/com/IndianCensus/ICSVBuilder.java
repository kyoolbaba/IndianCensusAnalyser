package com.IndianCensus;

import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface ICSVBuilder <E>{
    public  Iterator getCSVFileIterator
            (Reader reader, Class csvClass)
            throws CSVBuilderException;
    public List<E> getCSVFileList
            (Reader reader, Class csvClass)
            throws CSVBuilderException;
    public List<E> setCSVMapAndGetList
            (Reader reader, Class csvClass, Map<String,String> mapping)
            throws CSVBuilderException;
}

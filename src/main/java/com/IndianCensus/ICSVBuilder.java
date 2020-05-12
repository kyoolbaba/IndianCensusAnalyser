package com.IndianCensus;

import java.io.Reader;
import java.util.Iterator;

public interface ICSVBuilder <E>{
    public  Iterator getCSVFileIterator
            (Reader reader, Class csvClass)
            throws CSVBuilderException;
}

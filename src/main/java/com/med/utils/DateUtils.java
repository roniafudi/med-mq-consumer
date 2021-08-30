package com.med.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static Date parseAsDate(String s) throws ParseException {
        SimpleDateFormat fmt =new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        return fmt.parse(s);
    }
}

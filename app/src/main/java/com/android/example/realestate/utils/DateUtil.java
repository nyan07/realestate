package com.android.example.realestate.utils;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil
{
    public static long parseMsTimestampToMilliseconds(final String msJsonDateTime)
    {
        String regexp = "\\/(Date\\([-|+]([\\-|\\+]?.*?)((\\+|\\-).*)?\\))\\/";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(msJsonDateTime);
        matcher.matches();
        Long time = new Long(matcher.group(2));
        return time;
    }
}

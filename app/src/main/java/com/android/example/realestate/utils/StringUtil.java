package com.android.example.realestate.utils;

public class StringUtil
{
    public static Boolean isNullOrEmpty(String value)
    {
        if (value != null)
        {
            return value.length() == 0;
        }

        return true;
    }

    public static Boolean isNullOrWhiteSpace(String value)
    {
        if (value == null)
        {
            return true;
        }

        return (value.trim().length() == 0);
    }
}

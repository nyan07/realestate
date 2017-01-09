package com.android.example.realestate.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;

public class FormatterUtil
{
    public static String getFormattedCurrencyString(String isoCurrencyCode, double amount)
    {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

        Currency currency = Currency.getInstance(isoCurrencyCode);
        String symbol = currency.getSymbol();

        DecimalFormatSymbols decimalFormatSymbols =
                ((DecimalFormat) currencyFormat).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol(symbol);

        ((java.text.DecimalFormat) currencyFormat).setDecimalFormatSymbols(decimalFormatSymbols);

        return currencyFormat.format(amount);
    }
}

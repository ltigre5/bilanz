package com.example.leand.bilanztracker.DatabaseHelper;

import java.text.DecimalFormat;

public class GeneralFormatter {
    private GetColumnHelper getColumnHelper;

    public DecimalFormat currencyFormat = new DecimalFormat("#,###,###,###,###,###,##0.00");

    public GeneralFormatter() {
        getColumnHelper = new GetColumnHelper();
        getColumnHelper.getCurrency();
    }

    private String getCurrency() {
        return " " + getColumnHelper.getCurrency();
    }

    /**
     * get the currency format in string with the currency out of a double value exp: 0.00 CHF, 12.43 CHF, 0.43 CHF
     *
     * @param value the value to format
     * @return currency format
     */
    public String getCurrencyFormat(Double value) {
        return currencyFormat.format(value) + getCurrency();
    }

    /**
     * get the currency format per month in string with the currency out of a double value exp: 0.00 CHF, 12.43 CHF, 0.43 CHF
     *
     * @param value the value to format
     * @return currency format
     */
    public String getCurrencyFormatMonth(Double value) {
        return currencyFormat.format(getMonthValueDouble(value)) + getCurrency();
    }

    /**
     * get the currency format per week in string with the currency out of a double value exp: 0.00 CHF, 12.43 CHF, 0.43 CHF
     *
     * @param value the value to format
     * @return currency format
     */
    public String getCurrencyFormatWeek(Double value) {
        return currencyFormat.format(getWeekValueDouble(value)) + getCurrency();
    }

    /**
     * get the currency format per day in string with the currency out of a double value exp: 0.00 CHF, 12.43 CHF, 0.43 CHF
     *
     * @param value the value to format
     * @return currency format
     */
    public String getCurrencyFormatDay(Double value) {
        return currencyFormat.format(getDayValueDouble(value)) + getCurrency();
    }

    /**
     * get the Double value per month from a Double value per year
     *
     * @param value the Double value to format
     * @return the Double value per month
     */
    public Double getMonthValueDouble(Double value) {
        return value.doubleValue() / 12.0;
    }

    /**
     * get the Double value per week from a Double value per year
     *
     * @param value the Double value to format
     * @return the Double value per week
     */
    public Double getWeekValueDouble(Double value) {
        return value.doubleValue() / 52.0;
    }

    /**
     * get the Double value per day from a Double value per year
     *
     * @param value the Double value to format
     * @return the Double value per day
     */
    public Double getDayValueDouble(Double value) {
        return value.doubleValue() / 365.0;
    }

}

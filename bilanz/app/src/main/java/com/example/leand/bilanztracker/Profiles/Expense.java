package com.example.leand.bilanztracker.Profiles;

public class Expense {
    private String expense_type;
    private double expense_year;

    public Expense(String expense_type, double expense_year) {
        this.expense_type = expense_type;
        this.expense_year = expense_year;
    }

    public String getExpense_type() {
        return expense_type;
    }

    public double getExpense_year() {
        return expense_year;
    }
}

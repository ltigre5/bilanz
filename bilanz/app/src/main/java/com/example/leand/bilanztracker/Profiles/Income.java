package com.example.leand.bilanztracker.Profiles;

public class Income {
    private String income_type;
    private double income_year_gross;
    private double income_year_net;

    public Income(String income_type, double income_year_gross, double income_year_net) {
        this.income_type = income_type;
        this.income_year_gross = income_year_gross;
        this.income_year_net = income_year_net;
    }

    public String getIncome_type() {
        return income_type;
    }

    public double getIncome_year_gross() {
        return income_year_gross;
    }

    public double getIncome_year_net() {
        return income_year_net;
    }
}

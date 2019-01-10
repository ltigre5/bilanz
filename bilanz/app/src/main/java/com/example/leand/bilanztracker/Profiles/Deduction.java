package com.example.leand.bilanztracker.Profiles;

public class Deduction {
    private String deduction_type;
    private double percentage;

    public Deduction(String deduction_type, double percentage) {
        this.deduction_type = deduction_type;
        this.percentage = percentage;
    }

    public String getDeduction_type() {
        return deduction_type;
    }

    public double getPercentage() {
        return percentage;
    }
}

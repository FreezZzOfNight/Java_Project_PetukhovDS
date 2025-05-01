package org.example;

public class Country {
    private String country;
    private String region;
    private int happinessRank;
    private double happinessScore;
    private double standardError;
    private double economy;
    private double family;
    private double health;
    private double freedom;
    private double trust;
    private double generosity;
    private double dystopiaResidual;

    // Геттеры и сеттеры для всех полей
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getHappinessRank() {
        return happinessRank;
    }

    public void setHappinessRank(int happinessRank) {
        this.happinessRank = happinessRank;
    }

    public double getHappinessScore() {
        return happinessScore;
    }

    public void setHappinessScore(double happinessScore) {
        this.happinessScore = happinessScore;
    }

    public double getStandardError() {
        return standardError;
    }

    public void setStandardError(double standardError) {
        this.standardError = standardError;
    }

    public double getEconomy() {
        return economy;
    }

    public void setEconomy(double economy) {
        this.economy = economy;
    }

    public double getFamily() {
        return family;
    }

    public void setFamily(double family) {
        this.family = family;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getFreedom() {
        return freedom;
    }

    public void setFreedom(double freedom) {
        this.freedom = freedom;
    }

    public double getTrust() {
        return trust;
    }

    public void setTrust(double trust) {
        this.trust = trust;
    }

    public double getGenerosity() {
        return generosity;
    }

    public void setGenerosity(double generosity) {
        this.generosity = generosity;
    }

    public double getDystopiaResidual() {
        return dystopiaResidual;
    }

    public void setDystopiaResidual(double dystopiaResidual) {
        this.dystopiaResidual = dystopiaResidual;
    }
}
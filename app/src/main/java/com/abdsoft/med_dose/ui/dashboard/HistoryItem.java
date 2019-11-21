package com.abdsoft.med_dose.ui.dashboard;

public class HistoryItem {
    private String name;
    private String date;
    private int timesPerDay;
    private int totalDosage;
    private String timings;

    public HistoryItem(String name, String date, int timesPerDay, int totalDosage, String timings) {
        this.name = name;
        this.date = date;
        this.timesPerDay = timesPerDay;
        this.totalDosage = totalDosage;
        this.timings = timings;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getTimesPerDay() {
        return timesPerDay;
    }

    public int getTotalDosage() {
        return totalDosage;
    }

    public String getTimings() {
        return timings;
    }
}

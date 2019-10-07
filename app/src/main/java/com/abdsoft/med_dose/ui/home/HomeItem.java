package com.abdsoft.med_dose.ui.home;

class HomeItem {

    private String medicineName;
    private String dosageSummary;

    HomeItem(String medicineName, String dosageSummary) {
        this.medicineName = medicineName;
        this.dosageSummary = dosageSummary;
    }

    String getMedicineName() {
        return medicineName;
    }

    String getDosageSummary() {
        return dosageSummary;
    }
}

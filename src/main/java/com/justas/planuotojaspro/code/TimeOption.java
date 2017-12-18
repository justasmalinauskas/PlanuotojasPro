package com.justas.planuotojaspro.code;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeOption {

    private String reccuringWeekDays;
    private List<String> dates = new ArrayList<>();
    private boolean isReccuring;

    TimeOption() {

    }

    public void addReccuringDays(boolean monday,
                                 boolean tuesday,
                                 boolean wedensday,
                                 boolean thursday,
                                 boolean friday,
                                 boolean saturday,
                                 boolean sunday) {
        if (monday) {
            if (!reccuringWeekDays.contains("MO"))
                reccuringWeekDays += "MO";
        }
        if (tuesday) {
            if (!reccuringWeekDays.contains("TU"))
                reccuringWeekDays += "TU";
        }
        if (wedensday) {
            if (!reccuringWeekDays.contains("WE"))
                reccuringWeekDays += "WE";
        }
        if (thursday) {
            if (!reccuringWeekDays.contains("TH"))
                reccuringWeekDays += "TH";
        }
        if (friday) {
            if (!reccuringWeekDays.contains("FR"))
                reccuringWeekDays += "FR";
        }
        if (saturday) {
            if (!reccuringWeekDays.contains("SA"))
                reccuringWeekDays += "SA";
        }
        if (sunday) {
            if (!reccuringWeekDays.contains("SU"))
                reccuringWeekDays += "SU";
        }
        isReccuring = true;
    }

    public String getReccuringWeekDays() {
        return reccuringWeekDays;
    }

    public void addDates(List<String> dates) {
        this.dates = dates;
        isReccuring = false;
    }

    public List<String> getDates() {
        return dates;
    }

    public boolean reccuringStatus() {
        return isReccuring;
    }
}

package org.example;

import java.util.HashMap;
import java.util.Map;

public class ProgrammedSettings {
    private Map<String, Integer> settings;

    public ProgrammedSettings() {
        settings = new HashMap<>();
    }

    // Set the temperature for a specific period and day
    public void setSetting(Period period, DayType day, int temperature) {
        String key = period.toString() + "_" + day.toString();
        settings.put(key, temperature);
    }

    // Get the temperature for a specific period and day
    public int getSetting(Period period, DayType day) {
        String key = period.toString() + "_" + day.toString();
        return settings.getOrDefault(key, 70); // Default temperature is 70 degrees
    }
}


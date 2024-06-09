package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ThermostatTest {

    private Thermostat thermostat;
    private ProgrammedSettings settings;

    @BeforeEach
    public void setUp() {
        // Initialize Thermostat and ProgrammedSettings before each test
        thermostat = new Thermostat();
        settings = new ProgrammedSettings();

        // Initialize ProgrammedSettings, assume getSetting method returns 70
        settings.setSetting(Period.MORNING, DayType.WEEKDAY, 70);
        thermostat.setPeriod(Period.MORNING);
        thermostat.setDay(DayType.WEEKDAY);
    }

    // Predicate Coverage (PC): Test the overall decision to be true and false
    @Test
    public void testTurnHeaterOn_PredicateCoverage() {
        // Predicate: whole condition is true
        thermostat.setCurrentTemp(64); // Adjusted curTemp to 64 to ensure true condition
        thermostat.setThresholdDiff(5);
        thermostat.setTimeSinceLastRun(10);
        thermostat.setMinLag(5);
        thermostat.setOverride(false);
        assertTrue(thermostat.turnHeaterOn(settings), "Predicate Coverage: Condition should be true");
        assertTrue(thermostat.getHeaterOn());

        // Reinitialize to ensure independent test
        setUp();

        // Predicate: whole condition is false
        thermostat.setCurrentTemp(75); // Adjusted curTemp to ensure false condition
        thermostat.setThresholdDiff(5);
        thermostat.setTimeSinceLastRun(3); // Adjusted to ensure false condition
        thermostat.setMinLag(5);
        thermostat.setOverride(false);
        assertFalse(thermostat.turnHeaterOn(settings), "Predicate Coverage: Condition should be false");
        assertFalse(thermostat.getHeaterOn());
    }

    // Clause Coverage (CC): Test each individual clause to be true and false
    @Test
    public void testTurnHeaterOn_ClauseCoverage() {
        // Clause 1: curTemp < dTemp - thresholdDiff = true, Clause 2: override = false, Clause 3: timeSinceLastRun > minLag = true
        thermostat.setCurrentTemp(64); // Adjusted curTemp to 64 to ensure true condition
        thermostat.setThresholdDiff(5);
        thermostat.setTimeSinceLastRun(10);
        thermostat.setMinLag(5);
        thermostat.setOverride(false);
        assertTrue(thermostat.turnHeaterOn(settings), "Clause Coverage: Condition should be true");
        assertTrue(thermostat.getHeaterOn());

        // Reinitialize to ensure independent test
        setUp();

        // Clause 1: curTemp < dTemp - thresholdDiff = false, Clause 2: override = true, Clause 3: curTemp < overTemp - thresholdDiff = true, timeSinceLastRun > minLag = true
        thermostat.setCurrentTemp(75); // Adjusted curTemp to ensure false condition
        thermostat.setThresholdDiff(5);
        thermostat.setTimeSinceLastRun(10);
        thermostat.setMinLag(5);
        thermostat.setOverride(true);
        thermostat.setOverTemp(80); // Ensure overTemp > curTemp to meet the true condition
        assertFalse(thermostat.turnHeaterOn(settings), "Clause Coverage: Condition should be false");
        assertFalse(thermostat.getHeaterOn());

        // Reinitialize to ensure independent test
        setUp();

        // Clause 1: curTemp < dTemp - thresholdDiff = true, Clause 2: override = true, Clause 3: curTemp < overTemp - thresholdDiff = false, timeSinceLastRun > minLag = true
        thermostat.setCurrentTemp(75);
        thermostat.setThresholdDiff(5);
        thermostat.setTimeSinceLastRun(10);
        thermostat.setMinLag(5);
        thermostat.setOverride(true);
        thermostat.setOverTemp(70); // Ensure overTemp < curTemp to meet the false condition
        assertFalse(thermostat.turnHeaterOn(settings), "Clause Coverage: Condition should be false");
        assertFalse(thermostat.getHeaterOn());

        // Reinitialize to ensure independent test
        setUp();

        // Clause 1: curTemp < dTemp - thresholdDiff = false, Clause 2: override = false, Clause 3: timeSinceLastRun > minLag = true
        thermostat.setCurrentTemp(75);
        thermostat.setThresholdDiff(5);
        thermostat.setTimeSinceLastRun(10);
        thermostat.setMinLag(5);
        thermostat.setOverride(false);
        assertFalse(thermostat.turnHeaterOn(settings), "Clause Coverage: Condition should be false");
        assertFalse(thermostat.getHeaterOn());

        // Reinitialize to ensure independent test
        setUp();

        // Clause 1: curTemp < dTemp - thresholdDiff = true, Clause 2: override = false, Clause 3: timeSinceLastRun > minLag = false
        thermostat.setCurrentTemp(64); // Adjusted curTemp to 64 to ensure true condition
        thermostat.setThresholdDiff(5);
        thermostat.setTimeSinceLastRun(3); // Adjusted to ensure false condition
        thermostat.setMinLag(5);
        thermostat.setOverride(false);
        assertFalse(thermostat.turnHeaterOn(settings), "Clause Coverage: Condition should be false");
        assertFalse(thermostat.getHeaterOn());

        // Reinitialize to ensure independent test
        setUp();

        // Clause 1: curTemp < dTemp - thresholdDiff = false, Clause 2: override = true, Clause 3: curTemp < overTemp - thresholdDiff = true, timeSinceLastRun > minLag = false
        thermostat.setCurrentTemp(75); // Adjusted curTemp to ensure false condition
        thermostat.setThresholdDiff(5);
        thermostat.setTimeSinceLastRun(3); // Adjusted to ensure false condition
        thermostat.setMinLag(5);
        thermostat.setOverride(true);
        thermostat.setOverTemp(80); // Ensure overTemp > curTemp to meet the true condition
        assertFalse(thermostat.turnHeaterOn(settings), "Clause Coverage: Condition should be false");
        assertFalse(thermostat.getHeaterOn());

        // Reinitialize to ensure independent test
        setUp();

        // Clause 1: curTemp < dTemp - thresholdDiff = false, Clause 2: override = true, Clause 3: curTemp < overTemp - thresholdDiff = false, timeSinceLastRun > minLag = true
        thermostat.setCurrentTemp(75); // Adjusted curTemp to ensure false condition
        thermostat.setThresholdDiff(5);
        thermostat.setTimeSinceLastRun(10);
        thermostat.setMinLag(5);
        thermostat.setOverride(true);
        thermostat.setOverTemp(70); // Ensure overTemp < curTemp to meet the false condition
        assertFalse(thermostat.turnHeaterOn(settings), "Clause Coverage: Condition should be false");
        assertFalse(thermostat.getHeaterOn());

        // Reinitialize to ensure independent test
        setUp();

        // Clause 1: curTemp < dTemp - thresholdDiff = false, Clause 2: override = false, Clause 3: timeSinceLastRun > minLag = false
        thermostat.setCurrentTemp(75); // Adjusted curTemp to ensure false condition
        thermostat.setThresholdDiff(5);
        thermostat.setTimeSinceLastRun(3); // Adjusted to ensure false condition
        thermostat.setMinLag(5);
        thermostat.setOverride(false);
        assertFalse(thermostat.turnHeaterOn(settings), "Clause Coverage: Condition should be false");
        assertFalse(thermostat.getHeaterOn());
    }

    // Correlated Active Clause Coverage (CACC): Test all correlated active clause combinations
    @Test
    public void testTurnHeaterOn_CACC() {
        // Base values: curTemp < dTemp - thresholdDiff = true, override = false, timeSinceLastRun > minLag = true
        thermostat.setCurrentTemp(64); // Adjusted curTemp to 64 to ensure true condition
        thermostat.setThresholdDiff(5);
        thermostat.setTimeSinceLastRun(10);
        thermostat.setMinLag(5);
        thermostat.setOverride(false);
        assertTrue(thermostat.turnHeaterOn(settings), "CACC: Base condition should be true");
        assertTrue(thermostat.getHeaterOn());

        // Reinitialize to ensure independent test
        setUp();

        // Change curTemp < dTemp - thresholdDiff to false, expect false
        thermostat.setCurrentTemp(75);  // Now curTemp < dTemp - thresholdDiff = false
        thermostat.setThresholdDiff(5);
        thermostat.setTimeSinceLastRun(10);
        thermostat.setMinLag(5);
        thermostat.setOverride(false);
        assertFalse(thermostat.turnHeaterOn(settings), "CACC: Changing curTemp < dTemp - thresholdDiff to false should result in false");
        assertFalse(thermostat.getHeaterOn());

        // Reinitialize to ensure independent test
        setUp();

        // Change override to true and curTemp < overTemp - thresholdDiff = true, expect true
        thermostat.setCurrentTemp(65);  // Now curTemp < dTemp - thresholdDiff = true
        thermostat.setThresholdDiff(5);
        thermostat.setTimeSinceLastRun(10);
        thermostat.setMinLag(5);
        thermostat.setOverride(true);
        thermostat.setOverTemp(75);
        assertTrue(thermostat.turnHeaterOn(settings), "CACC: Changing override to true and curTemp < overTemp - thresholdDiff to true should result in true");
        assertTrue(thermostat.getHeaterOn());

        // Reinitialize to ensure independent test
        setUp();

        // Change curTemp < overTemp - thresholdDiff to false, expect false
        thermostat.setCurrentTemp(70);  // Now curTemp < overTemp - thresholdDiff = false
        thermostat.setThresholdDiff(5);
        thermostat.setTimeSinceLastRun(10);
        thermostat.setMinLag(5);
        thermostat.setOverride(true);
        thermostat.setOverTemp(75);
        assertFalse(thermostat.turnHeaterOn(settings), "CACC: Changing curTemp < overTemp - thresholdDiff to false should result in false");
        assertFalse(thermostat.getHeaterOn());

        // Reinitialize to ensure independent test
        setUp();

        // Change timeSinceLastRun > minLag to false, expect false
        thermostat.setCurrentTemp(64);  // Now curTemp < overTemp - thresholdDiff = true
        thermostat.setThresholdDiff(5);
        thermostat.setTimeSinceLastRun(3);  // Now timeSinceLastRun > minLag = false
        thermostat.setMinLag(5);
        thermostat.setOverride(true);
        thermostat.setOverTemp(75);
        assertFalse(thermostat.turnHeaterOn(settings), "CACC: Changing timeSinceLastRun > minLag to false should result in false");
        assertFalse(thermostat.getHeaterOn());

        // Reinitialize to ensure independent test
        setUp();

        // Change back to all true to ensure base values
        thermostat.setCurrentTemp(64);
        thermostat.setThresholdDiff(5);
        thermostat.setTimeSinceLastRun(10);  // Now timeSinceLastRun > minLag = true
        thermostat.setOverride(true);
        thermostat.setOverTemp(75);
        assertTrue(thermostat.turnHeaterOn(settings), "CACC: Base condition should be true");
        assertTrue(thermostat.getHeaterOn());
    }
}

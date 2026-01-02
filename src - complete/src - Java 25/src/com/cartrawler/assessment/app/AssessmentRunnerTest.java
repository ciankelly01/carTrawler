package com.cartrawler.assessment.app;

import com.cartrawler.assessment.car.CarResult;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AssessmentRunnerTest {

    @Test
    void categoryOrder_shouldReturnCorrectCategory() {
        assertEquals(0, invokeCategoryOrder("MCMR")); // Mini
        assertEquals(1, invokeCategoryOrder("EDMR")); // Economy
        assertEquals(2, invokeCategoryOrder("CDMR")); // Compact
        assertEquals(3, invokeCategoryOrder("IVMR")); // Other
    }

    @Test
    void removeExpensiveFullFullByCategory_shouldRemoveAboveMedian() {
        CarResult cheap =
                new CarResult("Cheap", "AVIS", "EDMR", 10.0, CarResult.FuelPolicy.FULLFULL);
        CarResult median =
                new CarResult("Median", "AVIS", "EDMR", 20.0, CarResult.FuelPolicy.FULLFULL);
        CarResult expensive =
                new CarResult("Expensive", "AVIS", "EDMR", 30.0, CarResult.FuelPolicy.FULLFULL);

        Set<CarResult> cars = Set.of(cheap, median, expensive);

        List<CarResult> filtered =
                AssessmentRunner.removeExpensiveFullFullByCategory(cars);

        assertTrue(filtered.contains(cheap));
        assertTrue(filtered.contains(median));
        assertFalse(filtered.contains(expensive));
    }

    @Test
    void removeExpensiveFullFullByCategory_shouldKeepNonFullFullCars() {
        CarResult fullEmpty =
                new CarResult("FE", "AVIS", "EDMR", 1000.0, CarResult.FuelPolicy.FULLEMPTY);

        List<CarResult> filtered =
                AssessmentRunner.removeExpensiveFullFullByCategory(Set.of(fullEmpty));

        assertEquals(1, filtered.size());
        assertEquals(fullEmpty, filtered.getFirst());
    }

    @Test
    void removeExpensiveFullFullByCategory_evenMedianIsCalculatedCorrectly() {
        CarResult a =
                new CarResult("A", "AVIS", "EDMR", 10.0, CarResult.FuelPolicy.FULLFULL);
        CarResult b =
                new CarResult("B", "AVIS", "EDMR", 20.0, CarResult.FuelPolicy.FULLFULL);
        CarResult c =
                new CarResult("C", "AVIS", "EDMR", 30.0, CarResult.FuelPolicy.FULLFULL);
        CarResult d =
                new CarResult("D", "AVIS", "EDMR", 40.0, CarResult.FuelPolicy.FULLFULL);

        List<CarResult> filtered =
                AssessmentRunner.removeExpensiveFullFullByCategory(Set.of(a, b, c, d));

        assertTrue(filtered.contains(a));
        assertTrue(filtered.contains(b));
        assertFalse(filtered.contains(c));
        assertFalse(filtered.contains(d));
    }

    private int invokeCategoryOrder(String sipp) {
        return AssessmentRunner.categoryOrder(
                new CarResult("Test", "AVIS", sipp, 0.0, CarResult.FuelPolicy.FULLEMPTY)
        );
    }
}
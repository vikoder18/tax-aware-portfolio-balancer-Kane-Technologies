package com.company.taxbalancer.data;

import com.company.taxbalancer.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleData {
    public static Portfolio portfolio() {

        //Long-term
        List<TaxLots> aaplLots = List.of(new TaxLots(
                        "AAPL",
                        Assets.STOCKS,
                        new BigDecimal("50"),
                        new BigDecimal("120"),
                        LocalDate.now().minusYears(2)),

                // short-term
                new TaxLots(
                        "AAPL",
                        Assets.STOCKS,
                        new BigDecimal("30"),
                        new BigDecimal("160"),
                        LocalDate.now().minusMonths(6)));

        List<TaxLots> bondLots = List.of(new TaxLots(
                        "BND", Assets.BONDS,
                        new BigDecimal("100"),
                        new BigDecimal("85"),
                        LocalDate.now().minusYears(3)));

        Holding stocks = new Holding("AAPL", Assets.STOCKS, aaplLots);
        Holding bonds = new Holding("BND", Assets.BONDS, bondLots);

        return new Portfolio("PORT-001", List.of(stocks, bonds));
    }

    public static AllocateTargets allocateTargets() {

        Map<Assets, BigDecimal> targets = new EnumMap<>(Assets.class);
        targets.put(Assets.STOCKS, new BigDecimal("0.60"));
        targets.put(Assets.BONDS, new BigDecimal("0.40"));

        return new AllocateTargets(targets);
    }

    public static Map<String, BigDecimal> prices() {

        Map<String, BigDecimal> prices = new HashMap<>();
        prices.put("AAPL", new BigDecimal("250"));
        prices.put("BND", new BigDecimal("90"));

        return prices;
    }
}

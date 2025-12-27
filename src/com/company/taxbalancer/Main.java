package com.company.taxbalancer;

import com.company.taxbalancer.data.SampleData;
import com.company.taxbalancer.engine.BalancingEngine;
import com.company.taxbalancer.model.AllocateTargets;
import com.company.taxbalancer.model.BalancePlan;
import com.company.taxbalancer.model.Portfolio;

import java.math.BigDecimal;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Portfolio portfolio = SampleData.portfolio();
        AllocateTargets target = SampleData.allocateTargets();
        Map<String, BigDecimal> prices = SampleData.prices();

        BalancingEngine engine = new BalancingEngine();
        BalancePlan plan = engine.rebalance(portfolio, target, prices);
        plan.print();
    }
}
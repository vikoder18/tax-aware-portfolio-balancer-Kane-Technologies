package com.company.taxbalancer.engine;

import java.math.BigDecimal;

//Request as per the assignment which clearly states to trigger a rebalance
// only if an asset class has drifted more than 5% from target
//designed separately in a class for clean and testable design
public class DriftChecker {
    private static final BigDecimal DRIFT_THRESHOLD = new BigDecimal("0.05");

    public boolean shouldRebalance(BigDecimal currentPercentage, BigDecimal targetPercentage) {

        return currentPercentage.subtract(targetPercentage).abs().compareTo(DRIFT_THRESHOLD) > 0;
    }
}

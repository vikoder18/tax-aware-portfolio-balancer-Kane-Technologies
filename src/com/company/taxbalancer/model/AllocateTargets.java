package com.company.taxbalancer.model;

import java.math.BigDecimal;
import java.util.Map;

//to store target percentage per asset class
public class AllocateTargets {
    private final Map<Assets, BigDecimal> targetPercent;

    public AllocateTargets(Map<Assets, BigDecimal> targetPercent){
        this.targetPercent = targetPercent;
    }

    public BigDecimal getTargetFor(Assets assets){
        return targetPercent.get(assets);
    }
}

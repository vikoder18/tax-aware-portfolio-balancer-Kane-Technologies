package com.company.taxbalancer.model;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.List;

//represent complete plan of execution for a portfolio
public class BalancePlan {
    private final List<Trades> tradeList;
    private final BigDecimal totalRealizedGainLoss;

    public BalancePlan(List<Trades> tradeList, BigDecimal totalRealizedGainLoss){
        this.totalRealizedGainLoss = totalRealizedGainLoss;
        this.tradeList = tradeList;
    }

    public BigDecimal getTotalRealizedGainLoss() {
        return totalRealizedGainLoss;
    }

    public List<Trades> getTradeList() {
        return tradeList;
    }

    public void print(){
        System.out.println("----Balancing Plan----");
        for(Trades trades: tradeList){
            System.out.println(trades);
        }
        System.out.println(" Total Realized Gain/Loss: "+ totalRealizedGainLoss);
    }
}

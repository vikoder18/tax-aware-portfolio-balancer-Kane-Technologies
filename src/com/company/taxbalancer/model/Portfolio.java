package com.company.taxbalancer.model;

import java.util.List;

//Represents portfolio of one client containing muultiple Holdings
public class Portfolio {
    private final String portfolioId;
    private final List<Holding> holdingList;

    public Portfolio(String portfolioId, List<Holding> holdingList){
        this.portfolioId = portfolioId;
        this.holdingList = holdingList;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public List<Holding> getHoldingList() {
        return holdingList;
    }
}

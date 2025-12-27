package com.company.taxbalancer.model;

import java.util.List;

// Represents one asset that holds multiple tax slots
public class Holding {
    private final String assetId;
    private final Assets assets;
    private final List<TaxLots> taxLots;

    public Holding(String assetId, Assets assets, List<TaxLots> taxLots){
        this.assetId = assetId;
        this.assets = assets;
        this.taxLots = taxLots;
    }

    public String getAssetId(){
        return assetId;
    }

    public Assets getAssets() {
        return assets;
    }

    public List<TaxLots> getTaxLots() {
        return taxLots;
    }
}

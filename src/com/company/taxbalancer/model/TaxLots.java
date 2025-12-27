package com.company.taxbalancer.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TaxLots {
    private final Assets assets;
    private final String assetId;
    private BigDecimal units;
    private final BigDecimal costBasedPerUnit;
    private final LocalDate buyingDate;

    public TaxLots(String assetId, Assets assets, BigDecimal units, BigDecimal costBasedPerUnit, LocalDate buyingDate){
        this.assets = assets;
        this.assetId = assetId;
        this.units = units;
        this.costBasedPerUnit = costBasedPerUnit;
        this.buyingDate = buyingDate;
    }

    public BigDecimal marketValue(BigDecimal presentPrice){
        return units.multiply(presentPrice);
    }

    public BigDecimal gainLoss(BigDecimal purchasePrice){
        return purchasePrice.subtract(costBasedPerUnit).multiply(units);
    }

    public Boolean isLongTerm(LocalDate present){
        return buyingDate.isBefore(present.minusYears(1));
    }

    public boolean washSaleBlocked(LocalDate present, BigDecimal presentPrice){
        return gainLoss(presentPrice).compareTo(BigDecimal.ZERO) < 0 && buyingDate.isAfter(present.minusDays(30));
    }

    public void reduceUnits(BigDecimal soldUnits){
        this.units = this.units.subtract(soldUnits);
    }

    public BigDecimal getUnits(){
        return units;
    }

    public String getAssetId(){
        return assetId;
    }

    public Assets getAssets() {
        return assets;
    }

    public BigDecimal getCostBasedPerUnit() {
        return costBasedPerUnit;
    }
}

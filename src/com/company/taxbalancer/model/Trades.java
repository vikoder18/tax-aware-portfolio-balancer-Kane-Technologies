package com.company.taxbalancer.model;

import java.math.BigDecimal;

//represents one buy or sell of order that includes realised gain/loss
public class Trades {
    public enum Type {
        BUY,
        SELL
    }

    private final String assetId;
    private final Type type;
    private final BigDecimal units;
    private final BigDecimal amount;
    private final BigDecimal realizedGainOrLoss;

    public Trades(String assetId, Type type, BigDecimal units, BigDecimal amount, BigDecimal realizedGainOrLoss) {
        this.assetId = assetId;
        this.amount = amount;
        this.type = type;
        this.units = units;
        this.realizedGainOrLoss = realizedGainOrLoss;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getRealizedGainOrLoss() {
        return realizedGainOrLoss;
    }

    public BigDecimal getUnits() {
        return units;
    }

    public String getAssetId() {
        return assetId;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString(){
        return "Trade{" +
                "assetId='" + assetId + '\'' +
                ", type=" + type +
                ", units=" + units +
                ", amount=" + amount +
                ", realizedGainLoss=" + realizedGainOrLoss +
                '}';
    }
}

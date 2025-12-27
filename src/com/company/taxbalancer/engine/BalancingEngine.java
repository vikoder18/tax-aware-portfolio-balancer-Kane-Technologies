package com.company.taxbalancer.engine;

import com.company.taxbalancer.model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

public class BalancingEngine {
    private static final BigDecimal MIN_TRADE_AMOUNT = new BigDecimal("100.00");

    public BalancePlan rebalance(Portfolio portfolio, AllocateTargets allocateTargets, Map<String, BigDecimal> prices) {

        List<Trades> trades = new ArrayList<>();
        BigDecimal totalRealizedGainLoss = BigDecimal.ZERO;
        LocalDate today = LocalDate.now();

        BigDecimal totalPortfolioValue = calculateTotalPortfolioValue(portfolio, prices);
        Map<Assets, BigDecimal> currentClassValues = calculateAssetClassValues(portfolio, prices);

        DriftChecker driftChecker = new DriftChecker();
        TaxLotPriorityQueue taxLotQueueBuilder = new TaxLotPriorityQueue();

        for (Assets assetClass : currentClassValues.keySet()) {

            BigDecimal currentValue = currentClassValues.get(assetClass);
            BigDecimal currentPercentage = currentValue.divide(totalPortfolioValue, 6, RoundingMode.HALF_UP);

            BigDecimal targetPercentage = allocateTargets.getTargetFor(assetClass);

            if (!driftChecker.shouldRebalance(currentPercentage, targetPercentage)) {
                continue;
            }
            BigDecimal targetValue = totalPortfolioValue.multiply(targetPercentage);
            BigDecimal delta = currentValue.subtract(targetValue);

            if (delta.abs().compareTo(MIN_TRADE_AMOUNT) < 0) {
                continue;
            }

            if (delta.compareTo(BigDecimal.ZERO) > 0) {
                totalRealizedGainLoss = totalRealizedGainLoss.add(executeSell(portfolio, assetClass, delta, prices, trades, today, taxLotQueueBuilder));
            }
        }
        return new BalancePlan(trades, totalRealizedGainLoss);
    }

    private BigDecimal executeSell(Portfolio portfolio, Assets assets, BigDecimal amountToSell, Map<String, BigDecimal> prices, List<Trades> trades, LocalDate today, TaxLotPriorityQueue queueBuilder) {

        BigDecimal realizedGainLoss = BigDecimal.ZERO;
        for (Holding holding : portfolio.getHoldingList()) {
            if (holding.getAssets() != assets) {
                continue;
            }
            BigDecimal price = prices.get(holding.getAssetId());
            PriorityQueue<TaxLots> queue = queueBuilder.buildQueue(holding.getTaxLots(), price, today);

            while (!queue.isEmpty() && amountToSell.compareTo(BigDecimal.ZERO) > 0) {
                TaxLots lot = queue.poll();

                BigDecimal lotMarketValue = lot.marketValue(price);
                BigDecimal sellValue = lotMarketValue.min(amountToSell);
                BigDecimal unitsToSell = sellValue.divide(price, 6, RoundingMode.HALF_UP);
                lot.reduceUnits(unitsToSell);
                amountToSell = amountToSell.subtract(sellValue);
                BigDecimal gainLoss = price.subtract(lot.getCostBasedPerUnit()).multiply(unitsToSell);
                realizedGainLoss = realizedGainLoss.add(gainLoss);
                trades.add(new Trades(holding.getAssetId(), Trades.Type.SELL, unitsToSell, sellValue, gainLoss));
            }
        }
        return realizedGainLoss;
    }


    private BigDecimal calculateTotalPortfolioValue(Portfolio portfolio, Map<String, BigDecimal> prices) {

        BigDecimal total = BigDecimal.ZERO;

        for (Holding holding : portfolio.getHoldingList()) {
            BigDecimal price = prices.get(holding.getAssetId());
            for (TaxLots lot : holding.getTaxLots()) {
                total = total.add(lot.marketValue(price));
            }
        }
        return total;
    }

    private Map<Assets, BigDecimal> calculateAssetClassValues(Portfolio portfolio, Map<String, BigDecimal> prices) {

        Map<Assets, BigDecimal> values =
                new EnumMap<>(Assets.class);

        for (Holding holding : portfolio.getHoldingList()) {

            BigDecimal price = prices.get(holding.getAssetId());
            BigDecimal holdingValue = BigDecimal.ZERO;

            for (TaxLots lot : holding.getTaxLots()) {
                holdingValue = holdingValue.add(lot.marketValue(price));
            }

            values.merge(holding.getAssets(), holdingValue, BigDecimal::add);
        }
        return values;
    }
}

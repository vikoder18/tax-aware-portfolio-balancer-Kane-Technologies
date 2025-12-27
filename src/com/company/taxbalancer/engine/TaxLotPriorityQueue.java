package com.company.taxbalancer.engine;


import com.company.taxbalancer.model.TaxLots;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.PriorityQueue;

//to enforce the exact lot selling priority required excluding wash sale blocked lots
public class TaxLotPriorityQueue {

    public PriorityQueue<TaxLots> buildQueue(
            Iterable<TaxLots> lots,
            BigDecimal currentPrice,
            LocalDate today) {

        Comparator<TaxLots> comparator = Comparator
                .comparing((TaxLots l) -> isShortTermLoss(l, currentPrice, today)).reversed()
                .thenComparing(l -> isLongTermLoss(l, currentPrice, today)).reversed()
                .thenComparing(l -> isLongTermGain(l, currentPrice, today))
                .thenComparing(l -> isShortTermGain(l, currentPrice, today));

        PriorityQueue<TaxLots> queue = new PriorityQueue<>(comparator);

        for (TaxLots lot : lots) {
            if (!lot.washSaleBlocked(today, currentPrice)) {
                queue.add(lot);
            }
        }

        return queue;
    }

    private boolean isShortTermLoss(TaxLots l, BigDecimal price, LocalDate today) {
        return !l.isLongTerm(today)
                && l.gainLoss(price).compareTo(BigDecimal.ZERO) < 0;
    }

    private boolean isLongTermLoss(TaxLots l, BigDecimal price, LocalDate today) {
        return l.isLongTerm(today)
                && l.gainLoss(price).compareTo(BigDecimal.ZERO) < 0;
    }

    private boolean isLongTermGain(TaxLots l, BigDecimal price, LocalDate today) {
        return l.isLongTerm(today)
                && l.gainLoss(price).compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean isShortTermGain(TaxLots l, BigDecimal price, LocalDate today) {
        return !l.isLongTerm(today)
                && l.gainLoss(price).compareTo(BigDecimal.ZERO) > 0;
    }
}

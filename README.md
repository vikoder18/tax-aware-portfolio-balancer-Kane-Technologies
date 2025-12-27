# Tax-Aware Portfolio Rebalancing Engine (Java)

## Overview
This project implements a **Tax-Aware Portfolio Rebalancing Engine** in pure Java (Java 17).  
The engine calculates optimized sell/buy actions to rebalance a multi-asset investment portfolio while **minimizing realized tax liability**.

The solution is designed as a **calculation engine**, not a web application, with a strong focus on:
- Clean architecture
- Financial correctness
- Performance awareness
- Defensive error handling

This implementation was developed as part of the **Kane Technologies – Java Developer Technical Assignment**.

---

## Key Functional Requirements Covered

- Multi-asset portfolio rebalancing (Stocks, Bonds, Cash)
- Target allocation drift detection (5% threshold)
- Tax-lot–level tracking
- Specific lot identification during selling
- Wash sale rule enforcement (30-day rule)
- Partial tax-lot selling
- Realized gain/loss calculation using BigDecimal
- Minimum trade size enforcement ($100)

---

## Project Structure & Design

The project follows **clear separation of concerns**, making it easy to understand, test, and extend.

src/main/java/com/company/taxbalancer
├── Main.java # Entry point
├── data
│ └── SampleData.java # Sample portfolio, prices, targets
├── engine
│ ├── BalancingEngine.java # Core balancing logic
│ ├── DriftChecker.java # 5% drift rule
│ └── TaxLotPriorityQueue.java # Tax-aware lot selection
└── model
├── Assets.java # Asset class enum
├── TaxLots.java # Individual tax-lot representation
├── Holding.java # Asset holding with multiple lots
├── Portfolio.java # Portfolio model
├── AllocateTargets.java # Target allocation model
└── BalancePlan.java # Output execution plan


### Design Highlights
- **Model layer**: Pure domain objects (no business logic leakage)
- **Engine layer**: All financial and tax logic isolated
- **Data layer**: Easily replaceable input source
- **Main class**: Thin orchestration only

This structure ensures the code runs seamlessly in **IntelliJ IDEA and Eclipse** without additional configuration.

---

## Use of Modern Java APIs

The implementation intentionally uses **modern Java (Java 11+) best practices**:

- `BigDecimal` for all monetary calculations (precision-safe)
- `java.time.LocalDate` instead of legacy `Date`
- Enums for asset classification
- `PriorityQueue` with custom `Comparator` for optimized lot selection
- Immutable or minimal-mutable domain objects where appropriate

No deprecated or legacy APIs are used.

---

## Performance Considerations

The engine is designed with performance awareness:

- **Drift threshold (5%)** avoids unnecessary rebalancing and tax events
- **PriorityQueue (O(n log n))** ensures efficient tax-lot selection even with large portfolios
- No redundant recalculations of portfolio values
- Design supports **independent portfolio processing**, allowing easy extension to batch or parallel execution for 1,000+ portfolios

---

## Error Handling & Validation

Basic defensive checks are included to ensure robustness:

- Validation for null or empty portfolios
- Validation for missing market prices
- Validation for missing target allocations
- Clear exceptions with meaningful messages for invalid inputs

This ensures predictable behavior and prevents silent failures.

---

## How the Rebalancing Works (High Level)

1. Calculate total portfolio value
2. Compute current allocation per asset class
3. Compare with target allocation
4. Trigger rebalancing only if drift exceeds 5%
5. Calculate excess value to sell
6. Select tax lots using priority:
    1. Short-Term Losses
    2. Long-Term Losses
    3. Long-Term Gains
    4. Short-Term Gains
7. Enforce wash sale rule
8. Execute partial lot sells if required
9. Calculate realized gain/loss
10. Generate a `BalancePlan` output

---

## How to Run the Project

### Prerequisites
- Java 17 or higher
- IntelliJ IDEA or Eclipse

### Steps
1. Open the project in IntelliJ or Eclipse
2. Ensure JDK 17 is configured
3. Run:

4. The rebalance plan and realized gain/loss will be printed to the console

---

## Sample Output
Trade{assetId='AAPL', type=SELL, units=10.400000, amount=2600.00, realizedGainLoss=936.000000}
Total Realized Gain/Loss: 936.000000


---

## Notes

- The project intentionally avoids frameworks to keep the focus on **core Java, business logic, and financial correctness**
- The design can be easily extended with:
    - Concurrency
    - External data sources
    - REST APIs
    - Persistent storage

---

## Author
Ayush Singh  
(Java Developer – Java Developer)



# Tax-Aware Portfolio Rebalancing Engine (Java)

## Overview
This project implements a Tax-Aware Portfolio Rebalancing Engine** in pure Java (Java 17).  
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


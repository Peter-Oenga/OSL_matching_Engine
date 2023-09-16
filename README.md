# Order Matching Engine Project

## Introduction

The Order Matching Engine is a critical component of financial trading systems that efficiently processes buy and sell orders for various financial instruments such as BTC, ETH, and USDT. This documentation provides a comprehensive guide to understanding and using the Order Matching Engine, including its design, functionality, and instructions for running the code using the Eclipse IDE and JUnit.

## Design Overview

The Order Matching Engine is designed as a Spring Boot application to ensure scalability, maintainability, and ease of integration with other components. It receives incoming orders through a message queue, stores them in an order book, and processes these orders based on their price and instrument. The engine can efficiently handle high volumes of orders by utilizing appropriate data structures and algorithms.

## Components and Functionalities

The Order Matching Engine consists of the following main components:

1. **OrderProcessor**: This class is responsible for processing incoming orders, managing order books, executing trades, and sending acknowledgment messages.
2. **OrderBook**: Each financial instrument (e.g., BTC, ETH) has a corresponding order book. The order book maintains a list of buy and sell orders, allowing efficient matching of orders.
3. **Trade**: Represents a trade executed between two orders. Trade details are recorded for later retrieval.
4. **AcknowledgmentMessage**: Sent to acknowledge the receipt and processing of an order.
5. **JUnit Test Suite**: A set of JUnit test cases to validate the functionality of the OrderProcessor class.

## Functionality

The Order Matching Engine supports the following key functionalities:

1. **Placing Orders**: Users can place new buy or sell orders with a specified instrument, quantity, and price. The engine stores these orders in the appropriate order book.
2. **Retrieving Order Book**: Users can retrieve the current order book for a specific instrument, providing insights into available buy and sell orders.
3. **Retrieving Executed Trades**: The engine records executed trades and allows users to retrieve trade details for a specific instrument.
4. **Canceling Orders**: Users can cancel existing orders, removing them from the order book.

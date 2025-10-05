package com.example.Order_Project.entity;

import com.example.Order_Project.Enum.TransactionEnum;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double totalAmount;

    private Date transactionDate;

    private TransactionEnum status;

    @OneToOne(mappedBy = "transaction")
    private Order order;

    public Transaction() {
    }

    public Transaction(Long id, Double totalAmount, Date transactionDate, TransactionEnum status, Order order) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.transactionDate = transactionDate;
        this.status = status;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TransactionEnum getStatus() {
        return status;
    }

    public void setStatus(TransactionEnum status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

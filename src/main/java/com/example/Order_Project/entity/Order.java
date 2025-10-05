package com.example.Order_Project.entity;

import com.example.Order_Project.Enum.OrderEnum;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders") 
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double totalAmount;

    private Date orderDate;

    @Enumerated(EnumType.STRING)
    private OrderEnum status;

    public Order() {
    }

    public Order(Long id, Double totalAmount, Date orderDate, OrderEnum status) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.status = status;
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderEnum getStatus() {
        return status;
    }

    public void setStatus(OrderEnum status) {
        this.status = status;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Order(Long id, Double totalAmount, Date orderDate, OrderEnum status, Account account) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.status = status;
        this.account = account;
    }

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    @OneToOne
    private Transaction transaction;
}

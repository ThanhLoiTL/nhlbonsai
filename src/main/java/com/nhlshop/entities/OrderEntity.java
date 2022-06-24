package com.nhlshop.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_phone")
    private String receiverPhone;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+7")
    @Column(name = "order_date")
    private Date orderDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+7")
    @Column(name = "delivery_date")
    private Date deliveryDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+7")
    @Column(name = "receive_date")
    private Date receiveDate;

    @Column(name = "status")
    private String status;

    @Column(name = "total_money")
    private Long totalMoney;

    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id")
    private UserEntity orderUser;

    @ManyToOne(optional = true)
    @JoinColumn(name = "shipper_id")
    private UserEntity shipper;

    @OneToMany(mappedBy = "order")
    private Collection<OrderDetailEntity> orderDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Long totalMoney) {
        this.totalMoney = totalMoney;
    }

    public UserEntity getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(UserEntity orderUser) {
        this.orderUser = orderUser;
    }

    public UserEntity getShipper() {
        return shipper;
    }

    public void setShipper(UserEntity shipper) {
        this.shipper = shipper;
    }

    public Collection<OrderDetailEntity> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Collection<OrderDetailEntity> orderDetails) {
        this.orderDetails = orderDetails;
    }

}

package com.rubincon.wateruseapi.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * ORM mapping entity for Rubincon Water Use API.
 *
 *
 * @author Izzy Chen
 * @version 1.0, 2020-02-09
 * @since 1.0
 *
 */
@Entity
@Table(name="WATERORDER")
public class WaterOrder implements Serializable {

    @JsonProperty("ORDER_ID")
    @Id
    @Column(name="ORDER_ID")
    @GeneratedValue
    private int orderID;

    @JsonProperty("FARM_ID")
    @Column(name="FARM_ID")
    private int farmID;


    @JsonProperty("START_DATE_TIME")
    @Column(name="START_DATE_TIME")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startDateTime;


    @JsonProperty("DURATION")
    @Column(name="DURATION")
    private int duration;


    @JsonProperty("STATUS")
    @Column(name="STATUS")
    private String status;

    @JsonProperty("ORDER_PLACE_TIME")
    @Column(name="ORDER_PLACE_TIME")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date orderPlaceTime;


    @JsonProperty("ORDER_CANCEL_TIME")
    @Column(name="ORDER_CANCEL_TIME")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date orderCancelTime;

    public WaterOrder(int orderID ,int farmID, Date startDateTime, int duration, String status, Date orderPlaceTime, Date orderCancelTime) {
        this.orderID = orderID;
        this.farmID = farmID;
        this.startDateTime = startDateTime;
        this.duration = duration;
        this.status = status;
        this.orderPlaceTime = orderPlaceTime;
        this.orderCancelTime = orderCancelTime;
    }

    public Date getOrderPlaceTime() {
        return orderPlaceTime;
    }

    public void setOrderPlaceTime(Date orderPlaceTime) {
        this.orderPlaceTime = orderPlaceTime;
    }


    public Date getOrderCancelTime() {
        return orderCancelTime;
    }

    public void setOrderCancelTime(Date orderCancelTime) {
        this.orderCancelTime = orderCancelTime;
    }

    public int getFarmID() {
        return farmID;
    }

    public void setFarmID(int farmID) {
        this.farmID = farmID;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public WaterOrder() {
    }
}

package com.rubincon.wateruseapi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rubincon.wateruseapi.domain.WaterOrder;
import com.rubincon.wateruseapi.domain.WaterUseAPIException;
import com.rubincon.wateruseapi.service.WaterOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;


/**
 *  restful service endpoints for Rubincon Water Use API.
 *
 *
 * @author Izzy Chen
 * @version 1.0, 2020-02-09
 * @since 1.0
 *
 */
@RestController
public class WaterOrderController {

    @Autowired
    WaterOrderService waterOrderService;

    private static final Logger logger = LoggerFactory.getLogger(WaterOrderController.class);

    //fetch all orders in H2 database
    @GetMapping("/orders")
    public JSONObject getOrders() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orders", this.waterOrderService.getWaterOrderList());
        logger.info("get order request, fetch the list of orders in DB");
        return jsonObject;
    }

    //create order and return response message,  request body is an Order in json format
    @PostMapping("/createorder")
    public JSONObject createOrder(@RequestBody JSONObject order) {
        WaterOrder waterOrder = JSON.toJavaObject(order, WaterOrder.class);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", this.waterOrderService.createOrder(waterOrder));
        logger.info("create order request, order ID is : " + waterOrder.getOrderID());
        return jsonObject;
    }

    //cancel order
    @PutMapping("/cancelorder")
    public JSONObject cancelOrder(@RequestParam("orderID") int orderID){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", this.waterOrderService.cancelOrder(orderID));
            logger.info("cancel order request, cancelled order ID is : " + orderID);
            return jsonObject;
        } catch (WaterUseAPIException ex){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("404 Error", ex.toString());
            return jsonObject;
        }
    }
    //get target order status time line  by given order ID
    @GetMapping("/orderlog")
    public JSONObject getOrderLog(@RequestParam("orderID") int orderID) {
        try {
        Date requestDate = new Date();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", this.waterOrderService.getOrderLog(requestDate, orderID));
        logger.info("fetch order log request,  target order ID is : " + orderID);
        return jsonObject;
        } catch (WaterUseAPIException ex){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("404 Error", ex.toString());
            return jsonObject;
        }
    }
}

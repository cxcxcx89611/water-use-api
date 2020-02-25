package com.rubincon.wateruseapi.service;


import com.rubincon.wateruseapi.domain.WaterOrder;
import com.rubincon.wateruseapi.domain.WaterUseAPIException;

import java.util.Date;
import java.util.List;

/**
 *  service interface
 *
 *
 * @author Izzy Chen
 * @version 1.0, 2020-02-09
 * @since 1.0
 *
 */
public interface WaterOrderService {
     List<WaterOrder> getWaterOrderList();
     String createOrder(WaterOrder waterOrder);
     String cancelOrder(int orderID) throws WaterUseAPIException;
     List<String> getOrderLog(Date requestDate, int orderID) throws WaterUseAPIException;
}

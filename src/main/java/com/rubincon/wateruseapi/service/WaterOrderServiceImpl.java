package com.rubincon.wateruseapi.service;

import com.rubincon.wateruseapi.dao.WaterOrderRepository;
import com.rubincon.wateruseapi.domain.WaterOrder;
import com.rubincon.wateruseapi.domain.WaterUseAPIException;
import com.rubincon.wateruseapi.util.WaterUseDateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *  service implementation
 *
 *
 * @author Izzy Chen
 * @version 1.0, 2020-02-09
 * @since 1.0
 *
 */
@Service
public class WaterOrderServiceImpl implements WaterOrderService{

    @Autowired
    WaterOrderRepository waterOrderRepository;

    private WaterUseDateHelper waterUseDateHelper;

    public WaterOrderServiceImpl() {
        this.waterUseDateHelper = new WaterUseDateHelper();
    }

    @Override
    public List<WaterOrder> getWaterOrderList() {
        return this.waterOrderRepository.findAll();
    }

    // logic: given order,  query DB to fetch all the orders on the same day for same farm, then iterate all these orders
    // check if order status is "Requested" then check if overlapped for time range.
    @Override
    public String createOrder(WaterOrder waterOrder) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate = sdf.parse(sdf.format(waterOrder.getStartDateTime()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.DATE, 1);
            Date endDate=calendar.getTime();
            int farmID = waterOrder.getFarmID();
            waterOrder.setOrderPlaceTime(new Date());
            List<WaterOrder> waterOrderList = this.waterOrderRepository.findOrderByTimeRageAndFarmID(startDate, endDate, farmID);

            if(!waterOrder.getStatus().equalsIgnoreCase("Requested")){
                return "when create new order, status can't be:  " + waterOrder.getStatus();
            }

            for(int i=0; i< waterOrderList.size(); i++){
                if(this.waterUseDateHelper.checkOverlap(waterOrder.getStartDateTime(), waterOrder.getDuration(), waterOrderList.get(i).getStartDateTime(), waterOrderList.get(i).getDuration())){
                    return "This order duration time for farm ID: " + waterOrder.getFarmID() + "  has been overlapped with other orders";
                }
            }
            return "Order ID: " +this.waterOrderRepository.save(waterOrder).getOrderID()+ "  has been created";
        }catch (Exception e){
            System.out.println("exception");
            e.printStackTrace();
            return "Create order fail, exception happened";
        }
    }
    // check order status firstly, if it's already cancelled or delivered, can't cancel again.
    @Override
    public String cancelOrder(int orderID) throws WaterUseAPIException {
        WaterOrder order = this.waterOrderRepository.findByOrderID(orderID);

        if(order == null) {
            throw new WaterUseAPIException("No such order please check oder id again", 3, "404");
        }
        String status = order.getStatus();
        if (status.equalsIgnoreCase("Cancelled")){
            return "Order ID:"+order.getOrderID()+"  has been cancelled, can't cancel again";
        }
        else if (status.equalsIgnoreCase("Delivered")){
            return "Order ID:"+order.getOrderID()+"  has been delivered, can't cancel";
        }
        else{
            this.waterOrderRepository.updateOrderStatusByID(orderID, "Cancelled");
            this.waterOrderRepository.updateOrderCancelledTimeByID(orderID, new Date());
            return "Order ID:"+order.getOrderID()+"  has been cancelled";
        }

    }

    // get the target order time line, response payload will include a few scenarios introduced by API specs
    @Override
    public List<String> getOrderLog(Date requestDate, int orderID) throws WaterUseAPIException{
        WaterOrder waterOrder = this.waterOrderRepository.findByOrderID(orderID);
        WaterOrder order = this.waterOrderRepository.findByOrderID(orderID);

        if(order == null) {
            throw new WaterUseAPIException("No such order please check oder id again", 3, "404");
        }
        List<String> messageList  = new ArrayList<String>();
        Date orderDeliverEndTime = this.waterUseDateHelper.getEndDate(waterOrder.getStartDateTime(), waterOrder.getDuration());
        String logMessage = null;
        messageList.add("Order ID: " + orderID + " status is " + waterOrder.getStatus() + ".  This order place time is : " + waterOrder.getOrderPlaceTime());
        messageList.add("Order ID: " + orderID + " start time is: " + waterOrder.getStartDateTime());
        if(waterOrder.getStatus().equalsIgnoreCase("Cancelled")){
             logMessage =  "Order ID: "
                    + orderID + " for Farm ID: " + waterOrder.getFarmID() + " has been cancelled at " + waterOrder.getOrderCancelTime();
        }else if (waterOrder.getStatus().equalsIgnoreCase("Delivered") || requestDate.after(orderDeliverEndTime)){
             logMessage =  "Order ID: "
                    + orderID + " for Farm ID: " + waterOrder.getFarmID() +  " stopped, has been delivered by " + orderDeliverEndTime;
        }else if (waterOrder.getStatus().equalsIgnoreCase("InProgress") || waterOrder.getStatus().equalsIgnoreCase("Requested")
        || (requestDate.after(waterOrder.getStartDateTime())&& requestDate.before(orderDeliverEndTime))){
            logMessage =  "Order ID: "
                    + orderID + " for Farm ID: " + waterOrder.getFarmID() +  " started ";
        }
        messageList.add(logMessage);
        return messageList;
    }

}

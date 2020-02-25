package com.rubincon.wateruseapi.dao;

import com.rubincon.wateruseapi.domain.WaterOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
/**
 *  H2 DB repository for Rubincon Water Use API.
 *
 *
 * @author Izzy Chen
 * @version 1.0, 2020-02-09
 * @since 1.0
 *
 */
public interface WaterOrderRepository extends JpaRepository<WaterOrder, Integer> {
    WaterOrder findByOrderID(int orderID);
    WaterOrder save(WaterOrder waterOrder);

    @Modifying
    @Query("update WaterOrder m set m.status = ?2 where m.orderID = ?1")
    @Transactional
    void updateOrderStatusByID(int orderid, String status);

    @Modifying
    @Query("update WaterOrder m set m.orderCancelTime = ?2 where m.orderID = ?1")
    @Transactional
    void updateOrderCancelledTimeByID(int orderid, Date orderCancelTime);

    @Modifying
    @Query("select t from WaterOrder t where t.farmID = ?3 and t.startDateTime between ?1 and ?2 ")
    @Transactional
    List<WaterOrder> findOrderByTimeRageAndFarmID(Date startDate, Date endDate, int farmID);
}

package com.rubincon.wateruseapi.util;

import com.alibaba.fastjson.JSONObject;
import com.rubincon.wateruseapi.controller.WaterOrderController;
import com.rubincon.wateruseapi.domain.WaterUseAPIException;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Controller
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(WaterOrderController.class);

    @ExceptionHandler(RuntimeException.class)
    public JSONObject getGeneralExceptionHandler(RuntimeException ex) {
        WaterUseAPIException waterUseAPIException = new WaterUseAPIException("General Exception : "+ex.toString(),1,"Internal 500");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Internal Error", waterUseAPIException.toString());
        logger.error("Internal Error" + ex.getMessage());
        return jsonObject;
    }

    @ExceptionHandler(HibernateException.class)
    public JSONObject getJPAExceptionHandler(HibernateException ex) {
        WaterUseAPIException waterUseAPIException = new WaterUseAPIException("Spring JPA Exception : "+ex.getMessage(),2,"500");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("500 Internal Error", waterUseAPIException.toString());
        logger.error("Internal Error" + ex.getMessage());
        return jsonObject;
    }

    @ExceptionHandler(WaterUseAPIException.class)
    public JSONObject getNoResourceExceptionHandler(WaterUseAPIException ex) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Internal Error", ex.toString());
        logger.error("Internal Error" + ex.getMessage());
        return jsonObject;
    }

}

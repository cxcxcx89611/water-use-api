package com.rubincon.wateruseapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *  tailored exception for Rubincon Water Use API.
 *
 *
 * @author Izzy Chen
 * @version 1.0, 2020-02-09
 * @since 1.0
 *
 */
public class WaterUseAPIException extends Exception{

    @JsonProperty("EXCEPTION_MESSAGE")
    private String exceptionMessage;

    @JsonProperty("EXCEPTION_ID")
    private int exceptionID;

    @JsonProperty("EXCEPTION_RESPONSE_CODE")
    private String exceptionReponseCode;

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public int getExceptionID() {
        return exceptionID;
    }

    public void setExceptionID(int exceptionID) {
        this.exceptionID = exceptionID;
    }

    public String getExceptionReponseCode() {
        return exceptionReponseCode;
    }

    public void setExceptionReponseCode(String exceptionReponseCode) {
        this.exceptionReponseCode = exceptionReponseCode;
    }

    public WaterUseAPIException(String exceptionMessage, int exceptionID, String exceptionReponseCode) {
        this.exceptionMessage = exceptionMessage;
        this.exceptionID = exceptionID;
        this.exceptionReponseCode = exceptionReponseCode;
    }

    public String toString(){
        return " WaterUserAPI Exception ID is : " + this.getExceptionID() +"  Exception response code is : "
                + this.getExceptionReponseCode()+"  Exception message is : " + this.getExceptionMessage();
    }
}

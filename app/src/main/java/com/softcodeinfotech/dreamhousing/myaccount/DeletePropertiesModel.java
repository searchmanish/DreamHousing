package com.softcodeinfotech.dreamhousing.myaccount;

public class DeletePropertiesModel {

    String status,msg,information;

    public DeletePropertiesModel(String status, String msg, String information) {
        this.status = status;
        this.msg = msg;
        this.information = information;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}

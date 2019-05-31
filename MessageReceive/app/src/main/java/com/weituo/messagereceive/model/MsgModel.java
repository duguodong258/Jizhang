package com.weituo.messagereceive.model;

import java.io.Serializable;

/**
 * @author duguodong
 * @time 2019/5/28
 * @des ${TODO}
 */
public class MsgModel implements Serializable {
    private String code;
    private String from_phone;
    private String my_phone;
    private String time;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFrom_phone() {
        return from_phone;
    }

    public void setFrom_phone(String from_phone) {
        this.from_phone = from_phone;
    }

    public String getMy_phone() {
        return my_phone;
    }

    public void setMy_phone(String my_phone) {
        this.my_phone = my_phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

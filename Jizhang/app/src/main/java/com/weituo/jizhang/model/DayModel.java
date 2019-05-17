package com.weituo.jizhang.model;

/**
 * @author duguodong
 * @time 2019/5/17
 * @des ${TODO}
 */
public class DayModel {
    private int launch;
    private int dinner;
    private int other;
    private int total;
    private String remark;

    public int getLaunch() {
        return launch;
    }

    public void setLaunch(int launch) {
        this.launch = launch;
    }

    public int getDinner() {
        return dinner;
    }

    public void setDinner(int dinner) {
        this.dinner = dinner;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

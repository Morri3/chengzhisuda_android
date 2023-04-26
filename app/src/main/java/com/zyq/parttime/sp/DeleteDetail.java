package com.zyq.parttime.sp;

import java.io.Serializable;

public class DeleteDetail implements Serializable {
    private String telephone;
    private int rd_id;

    @Override
    public String toString() {
        return "DeleteDetail{" +
                "telephone='" + telephone + '\'' +
                ", rd_id=" + rd_id +
                '}';
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getRd_id() {
        return rd_id;
    }

    public void setRd_id(int rd_id) {
        this.rd_id = rd_id;
    }
}

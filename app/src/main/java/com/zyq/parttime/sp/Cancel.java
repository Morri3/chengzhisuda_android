package com.zyq.parttime.sp;

import java.io.Serializable;

public class Cancel implements Serializable {
    private String telephone;
    private int s_id;

    @Override
    public String toString() {
        return "Cancel{" +
                "telephone='" + telephone + '\'' +
                ", s_id=" + s_id +
                '}';
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }
}

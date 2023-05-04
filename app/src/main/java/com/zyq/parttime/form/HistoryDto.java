package com.zyq.parttime.form;

import java.io.Serializable;

public class HistoryDto implements Serializable {
    private String telephone;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "HistoryDto{" +
                "telephone='" + telephone + '\'' +
                '}';
    }
}

package com.zyq.parttime.form;

import java.io.Serializable;

public class EditPersonalDto implements Serializable {
    private String telephone;
    private String exp;
    private String current_area;

    @Override
    public String toString() {
        return "EditPersonalDto{" +
                "telephone='" + telephone + '\'' +
                ", exp='" + exp + '\'' +
                ", current_area='" + current_area + '\'' +
                '}';
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getCurrent_area() {
        return current_area;
    }

    public void setCurrent_area(String current_area) {
        this.current_area = current_area;
    }
}

package com.zyq.parttime.form;

import java.io.Serializable;

public class SignupDto implements Serializable {
    private String telephone;
    private int p_id;

    @Override
    public String toString() {
        return "SignupDto{" +
                "telephone='" + telephone + '\'' +
                ", p_id=" + p_id +
                '}';
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }
}

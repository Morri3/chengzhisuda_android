package com.zyq.parttime.form;

import java.io.Serializable;

public class Logout implements Serializable {
    private String input_telephone;

    @Override
    public String toString() {
        return "Logout{" +
                "input_telephone='" + input_telephone + '\'' +
                '}';
    }

    public String getInput_telephone() {
        return input_telephone;
    }

    public void setInput_telephone(String input_telephone) {
        this.input_telephone = input_telephone;
    }
}
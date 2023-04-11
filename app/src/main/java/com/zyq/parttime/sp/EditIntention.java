package com.zyq.parttime.sp;

import java.io.Serializable;
import java.util.Arrays;

public class EditIntention implements Serializable {
    private String telephone;
    private String[] intentions;

    @Override
    public String toString() {
        return "EditIntention{" +
                "telephone='" + telephone + '\'' +
                ", intentions=" + Arrays.toString(intentions) +
                '}';
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String[] getIntentions() {
        return intentions;
    }

    public void setIntentions(String[] intentions) {
        this.intentions = intentions;
    }
}

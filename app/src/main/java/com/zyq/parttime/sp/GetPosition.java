package com.zyq.parttime.sp;

import java.io.Serializable;
import java.util.List;

public class GetPosition implements Serializable {
    private List<String> intentions;//意向兼职列表

    @Override
    public String toString() {
        return "GetPosition{" +
                "intentions=" + intentions +
                '}';
    }

    public List<String> getIntentions() {
        return intentions;
    }

    public void setIntentions(List<String> intentions) {
        this.intentions = intentions;
    }
}
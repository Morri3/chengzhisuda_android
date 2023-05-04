package com.zyq.parttime.form;

import java.io.Serializable;
import java.util.Date;

public class CommentInfo implements Serializable {
    private int p_id;
    private String content;
    private String memo;

    @Override
    public String toString() {
        return "CommentInfo{" +
                "p_id=" + p_id +
                ", content='" + content + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}

package com.zyq.parttime.sp;

import java.io.Serializable;
import java.util.Date;

public class CommentPost implements Serializable {
    private int s_id;
    private String content;
    private Date create_time;

    @Override
    public String toString() {
        return "CommentPost{" +
                "s_id=" + s_id +
                ", content='" + content + '\'' +
                ", create_time=" + create_time +
                '}';
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}

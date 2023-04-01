package com.zyq.parttime.sp;

import java.io.Serializable;
import java.util.Date;

public class MarkPost implements Serializable {
    private int s_id;
    private int pf;
    private int pl;
    private int we;
    private int lt;
    private int pt;
    private int ods;
    private int dsps;
    private Date create_time;

    @Override
    public String toString() {
        return "MarkPost{" +
                "s_id=" + s_id +
                ", pf=" + pf +
                ", pl=" + pl +
                ", we=" + we +
                ", lt=" + lt +
                ", pt=" + pt +
                ", ods=" + ods +
                ", dsps=" + dsps +
                ", create_time=" + create_time +
                '}';
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public int getPf() {
        return pf;
    }

    public void setPf(int pf) {
        this.pf = pf;
    }

    public int getPl() {
        return pl;
    }

    public void setPl(int pl) {
        this.pl = pl;
    }

    public int getWe() {
        return we;
    }

    public void setWe(int we) {
        this.we = we;
    }

    public int getLt() {
        return lt;
    }

    public void setLt(int lt) {
        this.lt = lt;
    }

    public int getPt() {
        return pt;
    }

    public void setPt(int pt) {
        this.pt = pt;
    }

    public int getOds() {
        return ods;
    }

    public void setOds(int ods) {
        this.ods = ods;
    }

    public int getDsps() {
        return dsps;
    }

    public void setDsps(int dsps) {
        this.dsps = dsps;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
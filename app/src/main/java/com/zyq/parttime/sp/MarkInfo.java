package com.zyq.parttime.sp;

import java.io.Serializable;

public class MarkInfo implements Serializable {
    private int m_id ;
    private int p_id ;
    private float total_score;
    private int pf ;
    private int pl ;
    private int we ;
    private int lt ;
    private int pt ;
    private int ods ;
    private int dsps;

    @Override
    public String toString() {
        return "MarkInfo{" +
                "m_id=" + m_id +
                ", p_id=" + p_id +
                ", total_score=" + total_score +
                ", pf=" + pf +
                ", pl=" + pl +
                ", we=" + we +
                ", lt=" + lt +
                ", pt=" + pt +
                ", ods=" + ods +
                ", dsps=" + dsps +
                '}';
    }

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public float getTotal_score() {
        return total_score;
    }

    public void setTotal_score(float total_score) {
        this.total_score = total_score;
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
}

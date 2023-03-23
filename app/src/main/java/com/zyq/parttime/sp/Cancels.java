package com.zyq.parttime.sp;

public class Cancels {
    int pos;
    boolean flag;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Cancels{" +
                "pos=" + pos +
                ", flag=" + flag +
                '}';
    }
}

package com.zyq.parttime.util;

//兼职种类筛选下拉框
public class PositionCategoryData {
    private String category;//选择的兼职种类

    public PositionCategoryData(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

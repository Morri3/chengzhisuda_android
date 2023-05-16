package com.zyq.parttime.util;

//出生年月、入学年月、毕业年月的下拉框
public class DateData {
    private int[] month = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};//每月的天数
    private boolean leapryear;//是否是闰年
    private int birthYear;//出生年份
    private int birthMonth;//出生月份
    private int startYear;//入学年份
    private int endYear;//入学月份
    private int startMonth;//毕业年份
    private int endMonth;//毕业月份

    private int age;//年龄
    private String start;//入学日期
    private String end;//毕业日期

    public DateData(int birthYear, int birthMonth, int startYear, int endYear, int startMonth, int endMonth) {
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.startYear = startYear;
        this.endYear = endYear;
        this.startMonth = startMonth;
        this.endMonth = endMonth;
    }

    //根据年份判断是否是闰年，然后获得二月的天数
    public DateData(int year) {
        //判断是否是闰年
        if ((year % 100 == 0 && year % 400 == 0) || year % 4 == 0) {
            leapryear = true;
        } else {
            leapryear = false;
        }

        //获取二月的天数
        if (leapryear) {
            month[1] = 29;
        } else {
            month[1] = 28;
        }
    }

    //获取当前月份
    public int getMonth(int i) {
        return month[i];
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(int birthMonth) {
        this.birthMonth = birthMonth;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}

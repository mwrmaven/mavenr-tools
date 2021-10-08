package com.mavenr.enums;

/**
 * @author mavenr
 * @Classname SortType
 * @Description sort type
 * @Date 2021/10/8 9:26
 */
public enum SortType {

    DESC(-1, "倒序排序"),
    ASC(1, "升序排序");

    private int code;
    private String desc;
    
    SortType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }
}

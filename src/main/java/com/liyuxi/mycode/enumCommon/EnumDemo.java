package com.liyuxi.mycode.enumCommon;

import lombok.Getter;

/**
 * 枚举类demo
 */
public enum EnumDemo {
    ONE(1, "齐"),
    TWO(2,"楚"),
    THREE(3, "燕"),
    FOUR(4, "赵"),
    FIVE(5, "魏"),
    SIX(6, "韩");

    @Getter
    private Integer code;
    @Getter
    private String name;
    EnumDemo(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static EnumDemo forEach(int index){
        EnumDemo[] enumDemos = EnumDemo.values();
        for(EnumDemo enumDemo : enumDemos){
            if(index == enumDemo.getCode()){
                return enumDemo;
            }
        }
        return null;
    }
}

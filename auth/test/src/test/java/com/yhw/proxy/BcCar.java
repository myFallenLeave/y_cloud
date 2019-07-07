package com.yhw.proxy;

/**
 * Created by YHW on 2019/7/8.
 */
public class BcCar implements Car {
    @Override
    public void name() {
        System.out.println("奔驰");
    }

    @Override
    public void color() {
        System.out.println("红色");
    }
}

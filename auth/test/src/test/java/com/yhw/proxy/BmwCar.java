package com.yhw.proxy;

/**
 * Created by YHW on 2019/7/7.
 */
public class BmwCar implements Car {
    @Override
    public void name() {
        System.out.println("宝马..............");
    }

    @Override
    public void color() {
        System.out.println("黑色...............");
    }
}

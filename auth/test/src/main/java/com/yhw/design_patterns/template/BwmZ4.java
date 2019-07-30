package com.yhw.design_patterns.template;

/**
 * Created by YHW on 2019/7/12.
 */
public class BwmZ4 extends BwmCar {
    @Override
    protected void start() {
        System.out.println("宝马Z4启动");
    }

    @Override
    protected void stop() {
        System.out.println("宝马Z4停止");
    }

    @Override
    protected void alarm() {
        System.out.println("宝马Z4按喇叭");
    }

    @Override
    protected void engineBoom() {
        System.out.println("宝马Z4引擎启动");
    }
}

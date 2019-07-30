package com.yhw.design_patterns.template;

/**
 * Created by YHW on 2019/7/12.
 */
public class Bwm5 extends BwmCar {
    @Override
    protected void start() {
        System.out.println("宝马5启动");
    }

    @Override
    protected void stop() {
        System.out.println("宝马5停止");
    }

    @Override
    protected void alarm() {
        System.out.println("宝马5按喇叭");
    }

    @Override
    protected void engineBoom() {
        System.out.println("宝马5引擎启动");
    }
}

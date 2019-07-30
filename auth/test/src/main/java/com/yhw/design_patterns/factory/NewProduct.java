package com.yhw.design_patterns.factory;

/**
 * Created by YHW on 2019/7/11.
 */
public class NewProduct implements Product {
    @Override
    public void name() {
        System.out.println("我是新产品");
    }

    @Override
    public void number() {
        System.out.println("本次总共产出10件");
    }
}

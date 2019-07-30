package com.yhw.design_patterns.factory;

/**
 * Created by YHW on 2019/7/11.
 */
public class OldProduct implements Product {
    @Override
    public void name() {
        System.out.println("我是旧产品");
    }

    @Override
    public void number() {
        System.out.println("我还有10件库存");
    }
}

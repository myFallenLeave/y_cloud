package com.yhw.design_patterns.single;

/**
 * single patterns
 * 核心要求是内存中只有一个对象
 * Created by YHW on 2019/7/11.
 */
public final class SingleDemo {

    private SingleDemo(){}

    // use  double check  need volatile
    private static SingleDemo singleDemo;

    private static class Single{
         private static SingleDemo INSTANCE = new SingleDemo();
    }

    public static SingleDemo getInstance(){
        return Single.INSTANCE;
    }

}

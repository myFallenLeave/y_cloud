package com.yhw.design_patterns.template;

/**
 * Created by YHW on 2019/7/12.
 */
public abstract class BwmCar {
    protected abstract void start();
    protected abstract void stop();
    //报警
    protected abstract void alarm();
    //发动引擎
    protected abstract void  engineBoom();

    public void run(){
        //启动
        this.start();
        //引擎
        this.engineBoom();
        //如果有人则按喇叭
        if(isAlarm()){
            this.alarm();
        }
        //到达目的地
        this.stop();
    }

    //钩子方法，默认喇叭是会响的
    protected boolean isAlarm(){
        return true;
    }
}

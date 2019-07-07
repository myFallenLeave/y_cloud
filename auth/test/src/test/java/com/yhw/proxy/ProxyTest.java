package com.yhw.proxy;

import java.lang.reflect.Proxy;

/**
 * Created by YHW on 2019/7/7.
 */
public class ProxyTest {

    public static void main(String[] args){
        BmwCar bmwCar = new BmwCar();

        Car car = (Car) Proxy.newProxyInstance(bmwCar.getClass().getClassLoader(),bmwCar.getClass().getInterfaces(),(proxy, method, args1) -> {
            if(method.getName().equals("name")){
                return method.invoke(new BcCar(),args1);
            }
            if(method.getName().equals("color")){
                return method.invoke(bmwCar,args1);
            }
            return null;
        });
        car.name();
        car.color();
    }
}

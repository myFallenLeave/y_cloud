package com.yhw.design_patterns.factory;

/**
 * Created by YHW on 2019/7/11.
 */
public abstract class AbstractProductFactory {

    public abstract <T extends Product> T createProduct(Class<T> tClass);
}

package com.yhw.design_patterns.factory;

/**
 * Created by YHW on 2019/7/11.
 */
public class TestProductFactory extends AbstractProductFactory {

    @Override
    public <T extends Product> T createProduct(Class<T> tClass) {
        Product product = null;
        try {
            product = (Product) Class.forName(tClass.getName()).newInstance();
        } catch (Exception e) {
            System.out.println("生产产品错误");
        }
        return (T) product;
    }
}

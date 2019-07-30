package com.yhw.design_patterns.factory;

/**
 * Created by YHW on 2019/7/11.
 */
public class Test {
    public static void main(String[] args){
        AbstractProductFactory productFactory = new TestProductFactory();
        Product  product = productFactory.createProduct(NewProduct.class);
        product.name();
        product.number();
        System.out.println("================================");
        product = productFactory.createProduct(OldProduct.class);
        product.name();
        product.number();
    }
}

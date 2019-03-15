package com.isagod.exceptions;

/**
 * 自定义的店铺操作错误信息类
 */
public class ProductCategoryOperationException extends RuntimeException{
    public ProductCategoryOperationException(String message) {
        super(message);
    }
}

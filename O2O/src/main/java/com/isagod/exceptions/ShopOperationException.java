package com.isagod.exceptions;

/**
 * 封装的商店操作类
 */
public class ShopOperationException extends RuntimeException {
    public ShopOperationException(String message) {
        super(message);
    }
}
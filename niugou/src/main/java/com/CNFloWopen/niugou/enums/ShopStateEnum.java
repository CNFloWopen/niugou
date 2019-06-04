package com.CNFloWopen.niugou.enums;

/**
 * 枚举类型
 */
public enum ShopStateEnum {
    SUCCESS(1,"操作成功"),
    PASS(2,"通过验证"),INNER_ERROR(-1001,"内部系统错误"),NULL_SHOP(-1003,"shop为空"),
    NULL_SHOPID(-1002,"ShopId为空");
    private int state;
    private String stateInfo;
    private ShopStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    /**
     * 依据传入的state返回相应的enum值
     * @param state
     * @return
     */
    public static ShopStateEnum stateOf(int state)
    {
        for (ShopStateEnum stateEnum : values() ) {
            if (stateEnum.getState() == state)
                return stateEnum;
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
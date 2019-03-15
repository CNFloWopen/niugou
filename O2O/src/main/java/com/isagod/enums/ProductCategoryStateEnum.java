package com.isagod.enums;

import com.isagod.entity.ProductCategory;
//店铺的状态
public enum ProductCategoryStateEnum {
    SUCCESS(1,"创建成功"),INNER_ERROR(-1001,"操作失败"),EMPTY_LIST(-1002,"添加数量少于1");
     private int state;
     private String stateInfo;
     private ProductCategoryStateEnum(int state,String stateInfo)
     {
         this.state = state;
         this.stateInfo = stateInfo;
     }
    public int getState()
    {
        return state;
    }
    public String getStateInfo()
    {
        return stateInfo;
    }
    //根据传入的值，来返回相对性的信息。比如输入1，返回创建成功
    public static ProductCategoryStateEnum stateOf(int index)
    {
        for (ProductCategoryStateEnum state:values())
        {
            if (state.getState() == index)
            {
                return state;
            }
        }
        return null;
    }
}

package com.isagod.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用到的数据源的key
 */
public class DynamicDataSourceHolder {
    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceHolder.class);
//    线程安全
    private static ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    public static final String DB_MASTER = "master";
    public static final String Db_SLAVE = "slave";
    public static String getDbType(){
        String db = contextHolder.get();
        if (db == null){
            db = DB_MASTER;
        }
        return db;
    }

    /**
     * 添加设置数据源
     * @param str
     */
    public static void setDbType(String str){
        logger.debug("所使用的数据源为"+str);
        contextHolder.set(str);
    }
    /**
     * 清空数据源
     */
    public static void clearDbType()
    {
        contextHolder.remove();
    }
}

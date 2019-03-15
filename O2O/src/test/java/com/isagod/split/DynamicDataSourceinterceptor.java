package com.isagod.split;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Properties;

/**
 * 用拦截器使用读和写的分离（及用的是哪个数据源（数据库））
 */
@Intercepts({@Signature(type = Executor.class,method = "update",args = {MappedStatement.class,Object.class}),
        @Signature(type = Executor.class,method = "query",args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class})})
public class DynamicDataSourceinterceptor  implements Interceptor {
//    u0020空格
    private static final String REGEX = ".*insert\\u0020.|.delete\\u0020.*|.update\\u0020.*";
    private Logger logger = LoggerFactory.getLogger(DynamicDataSourceinterceptor.class);
//    拦截方法
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
//        支持事务管理用的,用事务管理就是true
        boolean synchronizationActive = TransactionSynchronizationManager.isSynchronizationActive();
        Object[] objects = invocation.getArgs();
        MappedStatement ms = (MappedStatement) objects[0];//获取头部看是<select>还是<update>.....
        String lookupKey = DynamicDataSourceHolder.DB_MASTER;
        if (synchronizationActive != true)
        {
            //读的方法
            if (ms.getSqlCommandType().equals(SqlCommandType.SELECT))
            {
//                selectKey为自增id查询主键（SELECT_KEY_SUFFIX_ID()）方法，使用主库
                if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX))
                {
                    lookupKey = DynamicDataSourceHolder.DB_MASTER;
                }else {
//                    获取数据库语句
                    BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
                    String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replace("[\\t\\n\\r]"," ");
                    if (sql.matches(REGEX))
                    {
                        lookupKey = DynamicDataSourceHolder.DB_MASTER;
                    }else {
                        lookupKey = DynamicDataSourceHolder.Db_SLAVE;
                    }
                }
            }
        } else {
            lookupKey = DynamicDataSourceHolder.DB_MASTER;
        }
        logger.debug("设置方法[{}] use [{}] Strategy,SqlCommanType [{}]..",ms.getId(),lookupKey);
        DynamicDataSourceHolder.setDbType(lookupKey);
        return invocation.proceed();
    }
//    返回封装对象，看是代理对象还是本体对象
    @Override
    public Object plugin(Object o) {
//        Executor是增删改查操作就拦截
        if (o instanceof Executor){
            return Plugin.wrap(o,this);
        }else {
            return o;
        }
    }
//类初始化的时候，做的动作
    @Override
    public void setProperties(Properties properties) {

    }
}

package com.CNFloWopen.niugou.config.dao;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class SessionFactoryConfiguration {

    //配置mapper-config的路径
//    private static String mybatis_Config_File;
    private static String mybatis_Config_File;
    @Value("${mybatis_Config_File}")
    public  void setMybatis_Config_File(String mybatis_Config_File) {
        SessionFactoryConfiguration.mybatis_Config_File = mybatis_Config_File;
    }

    //mybatis的mapper所在的位置
    //    private static String mapper_Path;
    private static String mapper_Path;
    @Value("${mapper_Path}")
    public  void setMapper_Path(String mapper_Path) {
        SessionFactoryConfiguration.mapper_Path = mapper_Path;
    }
    //实体类所在的package
    @Value("${type_Aliases_Package}")
    private String type_Aliases_Package;

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;
    /**
     *创建sqlSessionFactoryBean实例，并设置mapper映射路径
     * 设置dataSource的数据源
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //设置扫描路径
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybatis_Config_File));
        //设置mapper扫描路径
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX+mapper_Path;
        sqlSessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources(packageSearchPath));

        //设置dataSource
        sqlSessionFactoryBean.setDataSource(dataSource);
//        设置包扫描路径
        sqlSessionFactoryBean.setTypeAliasesPackage(type_Aliases_Package);

        return sqlSessionFactoryBean;
    }
}

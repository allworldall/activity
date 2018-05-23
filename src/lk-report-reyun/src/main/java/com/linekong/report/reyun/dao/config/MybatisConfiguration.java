package com.linekong.report.reyun.dao.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: maxuan
 * Date: 2017/12/13
 * Time: 17:22
 */
@Configuration
public class MybatisConfiguration implements TransactionManagementConfigurer {

    private static final Logger logger = Logger.getLogger(MybatisConfiguration.class);

    //  配置类型别名
    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;

    //  配置mapper的扫描，找到所有的mapper.xml映射文件
    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;

    //  加载全局的配置文件
    @Value("${mybatis.config-locations}")
    private String configLocation;

    @Autowired
    private DataSource dataSource;

    // 提供SqlSeesion
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        try {
            SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
            sessionFactoryBean.setDataSource(dataSource);

            // 读取类型别名配置
            sessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);

            //读取mapper的扫描配置
            Resource[] resources = new PathMatchingResourcePatternResolver()
                    .getResources(mapperLocations);
            sessionFactoryBean.setMapperLocations(resources);

            Resource r = new DefaultResourceLoader().getResource(configLocation);

            //读取全局配置
            sessionFactoryBean.setConfigLocation(r);

            //解决spring boot集成mybatis时，无法读取类短名的bug
            sessionFactoryBean.setVfs(SpringBootVFS.class);

            return sessionFactoryBean.getObject();
        } catch (IOException e) {
            logger.warn("mybatis resolver mapper*xml is error");
            logger.warn(e);
        } catch (Exception e) {
            logger.warn("mybatis sqlSessionFactoryBean create error");
        }
        return null;
    }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

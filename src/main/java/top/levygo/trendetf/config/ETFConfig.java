package top.levygo.trendetf.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @description：配置类
 * @author：LevyXie
 * @create：2022-03-08 11:37
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "top.levygo.trendetf.mapper")
public class ETFConfig {

}

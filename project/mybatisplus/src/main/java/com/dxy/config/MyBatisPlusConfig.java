package com.dxy.config;


import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.dxy.mapper")
@Configuration
@EnableTransactionManagement
public class MyBatisPlusConfig {

    //注册乐观锁
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    //分页拦截器
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();

    }
    //逻辑删除组件
     @Bean
     public ISqlInjector sqlInjector(){
         return new LogicSqlInjector();
        }
      @Bean
     @Profile({"dev","test"})
    public PerformanceInterceptor performanceInterceptor(){
          PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
          performanceInterceptor.setMaxTime(100) ;//ms  设置SQL执行过时间，超过了就不执行。
          performanceInterceptor.setFormat(true);  //打印日志

          return performanceInterceptor;
      }



}

class  c{
    public static void main(String[] args) {
        A b = new B();
        System.out.println(b.a);
    }
}
 class A{
    int a = 2;
}

 class B extends A{
    int a = 5;
}
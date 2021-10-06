package com.qf.shopresource;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

@SpringBootApplication(scanBasePackages = "com.qf")
@EnableEurekaClient
//FastDFS的两个注解
@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class ShopResourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopResourceApplication.class, args);
    }

}

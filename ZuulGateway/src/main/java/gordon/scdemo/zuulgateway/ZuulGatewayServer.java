package gordon.scdemo.zuulgateway;

import gordon.scdemo.zuulgateway.zuul.prefilter.GatewayCommonPreFilter;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableZuulProxy
@SpringBootApplication

@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackageClasses = {ZuulGatewayServer.class}, annotationClass = Mapper.class)
public class ZuulGatewayServer {

    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayServer.class, args);
    }

}
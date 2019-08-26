package gordon.scdemo.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class ZuulGatewayServer {

    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayServer.class, args);
    }
}
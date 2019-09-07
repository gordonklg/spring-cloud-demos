package gordon.scdemo.zuulgateway.zuul;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ZuulGatewayWebMvcConfigurer implements WebMvcConfigurer {

    @Bean
    public ServletRegistrationBean servletRegistrationBean(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean<DispatcherServlet> dsrb = new ServletRegistrationBean<>(dispatcherServlet);
        dsrb.addUrlMappings("/apig/*");
        dsrb.addUrlMappings("/favicon.ico");
        return dsrb;
    }
}

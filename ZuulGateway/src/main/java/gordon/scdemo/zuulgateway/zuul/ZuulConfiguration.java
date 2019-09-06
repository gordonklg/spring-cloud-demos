package gordon.scdemo.zuulgateway.zuul;

import gordon.scdemo.zuulgateway.zuul.locator.GatewayRouteLocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ZuulConfiguration {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    protected ZuulProperties zuulProperties;

    @Autowired
    protected ServerProperties server;

    @Autowired(required = false)
    private Registration registration;

    @Autowired
    private ServiceRouteMapper serviceRouteMapper;

//    @Bean
//    public ServiceRouteMapper serviceRouteMapper() {
//        return new LowerCaseServiceRouteMapper();
//    }

    @Bean
    public DiscoveryClientRouteLocator discoveryRouteLocator() {
        return new GatewayRouteLocator("/", this.discoveryClient, this.zuulProperties, serviceRouteMapper, this.registration);
    }

}

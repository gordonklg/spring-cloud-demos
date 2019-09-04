package gordon.scdemo.zuulgateway.zuul.locator;


import com.netflix.zuul.context.RequestContext;
import gordon.scdemo.zuulgateway.filter.model.ServiceInfo;
import gordon.scdemo.zuulgateway.zuul.prefilter.GatewayCommonPreFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;

import java.util.LinkedHashMap;

@Slf4j
public class GatewayRouteLocator extends DiscoveryClientRouteLocator {

    public GatewayRouteLocator(String servletPath, DiscoveryClient discovery, ZuulProperties properties, ServiceRouteMapper serviceRouteMapper, ServiceInstance localServiceInstance) {
        super(servletPath, discovery, properties, serviceRouteMapper, localServiceInstance);
    }

    @Override
    protected LinkedHashMap<String, ZuulProperties.ZuulRoute> locateRoutes() {
        return super.locateRoutes();
    }

    @Override
    public Route getMatchingRoute(String path) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!! in GatewayRouteLocator");
        RequestContext ctx = RequestContext.getCurrentContext();
        final ServiceInfo serviceInfo = (ServiceInfo) ctx.get(GatewayCommonPreFilter.ROUTE_API_INFO);
        String systemType = "1";
        if (systemType.equals("1")) {
            final String serviceId = StringUtils.lowerCase(serviceInfo.getServiceId());
            final String fullPath = "/" + serviceId + path;
            Route route = super.getMatchingRoute(fullPath);
            if (route == null) {
                log.warn("can not found serviceId:[{}] on eureka,apiId:[{}]", serviceId, serviceInfo.getId());
//                throw GatewayException.SERVER_NOT_FOUND;
            }
            return route;
        } else {
            log.info("forward to backendurl:[{}] and path:[{}]", serviceInfo.getBackendUrl(), path);
            return null;
//            return new Route(serviceInfo.getId(), path, serviceInfo.getBackendUrl(), StringUtils.EMPTY,
//                    false,
//                    null);
        }
    }

}

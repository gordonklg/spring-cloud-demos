package gordon.scdemo.zuulgateway.zuul.locator;


import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import gordon.scdemo.zuulgateway.apig.model.ServiceInfo;
import gordon.scdemo.zuulgateway.apig.service.MetadataService;
import gordon.scdemo.zuulgateway.zuul.prefilter.GatewayCommonPreFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class GatewayRouteLocator extends DiscoveryClientRouteLocator {

    @Autowired
    private MetadataService metadataService;

    public GatewayRouteLocator(String servletPath, DiscoveryClient discovery, ZuulProperties properties, ServiceRouteMapper serviceRouteMapper, ServiceInstance localServiceInstance) {
        super(servletPath, discovery, properties, serviceRouteMapper, localServiceInstance);
    }

//    @Override
//    protected LinkedHashMap<String, ZuulRoute> locateRoutes() {
//        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//        LinkedHashMap<String, ZuulRoute> routesMap = new LinkedHashMap<>();
//        // never use zuul config
////        routesMap.putAll(super.locateRoutes());
//        List services = metadataService.getAllServices();
//        Map<String, ServiceInfo> simpleUrlServices = (Map) services.get(0);
//        simpleUrlServices.values().iterator();
//
//        return routesMap;
//    }

//    @Override
//    public List<Route> getRoutes() {
//        List<Route> values = new ArrayList<>();
//        List services = metadataService.getAllServices();
//        Map<String, ServiceInfo> simpleUrlServices = (Map) services.get(0);
//        Set<String> set = new HashSet<>();
//        for (ServiceInfo serviceInfo : simpleUrlServices.values()) {
//            UriComponents uri = UriComponentsBuilder.fromUriString(serviceInfo.getReqUrl()).build();
//            String path = uri.getPath();
//            if(!path.contains("/")) {
//                throw new RuntimeException("Wrong Path of service id " + serviceInfo.getServiceId());
//            }
//            int secondSlash = path.indexOf("/", 1);
//            if(secondSlash > 0 ) {
//                path = path.substring(0, secondSlash);
//            }
//
//            path += "/**";
//
//            if(!set.contains(path)) {
//                set.add(path);
//                values.add(new Route(String.valueOf(serviceInfo.getId()), path, StringUtils.EMPTY, StringUtils.EMPTY, false, null));
//            }
//        }
//        return values;
//    }

    @Override
    public Route getMatchingRoute(String path) {
        RequestContext ctx = RequestContext.getCurrentContext();
        final ServiceInfo serviceInfo = (ServiceInfo) ctx.get(GatewayCommonPreFilter.ROUTE_API_INFO);
        if (serviceInfo.getBackendSystemType().equals("1")) {
            log.info("forward to serviceid:[{}] and path:[{}]", serviceInfo.getServiceId(), path);
            final String serviceId = StringUtils.lowerCase(serviceInfo.getServiceId());
            String backendUrl = serviceInfo.getBackendUrl();
            if (StringUtils.isEmpty(backendUrl)) {
                UriComponents uri = UriComponentsBuilder.fromUriString(serviceInfo.getReqUrl()).build();
                backendUrl = uri.getPath();
            } else {
                UriComponents uri = UriComponentsBuilder.fromUriString(backendUrl).build();
                backendUrl = uri.getPath();
            }
            Route route = new Route(String.valueOf(serviceInfo.getId()), backendUrl, serviceId, StringUtils.EMPTY, false, null);
            log.info(route.toString());
            return route;
        } else {
            log.info("forward to backendurl:[{}] and path:[{}]", serviceInfo.getBackendUrl(), path);
            return new Route(String.valueOf(serviceInfo.getId()), StringUtils.EMPTY, serviceInfo.getBackendUrl(), StringUtils.EMPTY, false, null);
        }
    }

}

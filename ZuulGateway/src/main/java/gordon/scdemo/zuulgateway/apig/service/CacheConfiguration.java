package gordon.scdemo.zuulgateway.apig.service;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gordon.scdemo.zuulgateway.apig.model.ServiceInfo;
import gordon.scdemo.zuulgateway.apig.cache.CacheConstant;
import gordon.scdemo.zuulgateway.mgt.common.util.beancopy.BeanMapper;
import gordon.scdemo.zuulgateway.mgt.entity.ApiService;
import gordon.scdemo.zuulgateway.mgt.service.ServiceService;
import gordon.scdemo.zuulgateway.zuul.RequestInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class CacheConfiguration {

    @Autowired
    private ServiceService service;


    @Bean("defaultCache")
    public LoadingCache<String, Object> apiCache() {
        LoadingCache<String, Object> cache = Caffeine.newBuilder()
                .refreshAfterWrite(5, TimeUnit.SECONDS)
                .maximumSize(10_000)
                .build(key -> getCacheValueFromDB(key));
        return cache;
    }

    private Object getCacheValueFromDB(String key) {

        if (key.equals(CacheConstant.ALL_SERVICES)) {
            List result = new ArrayList(2);
            Map<String, ServiceInfo> simpleUrlServices = Maps.newHashMap();
            List<ServiceInfo> pathVariableUrlServices = Lists.newArrayList();
            result.add(simpleUrlServices);
            result.add(pathVariableUrlServices);

            List<ApiService> services = service.list();
            for (ApiService service : services) {
                ServiceInfo serviceInfo = BeanMapper.map(service, ServiceInfo.class);
                if (service.getReqUrl().contains("{")) {
                    pathVariableUrlServices.add(serviceInfo);
                } else {
                    simpleUrlServices.put(getApiKey(serviceInfo), serviceInfo);
                }
            }
            return result;
        }

//        if (key.startsWith(CacheConstant.APPLICATION_API_PREFIX)) {
//            String clientId = key.substring(CacheConstant.APPLICATION_API_PREFIX.length());
//            return service.findByApp(clientId);
//        }

//        if (key.startsWith(CacheConstant.RATELIIT)) {
//            String keys = key.substring(CacheConstant.RATELIIT.length());
//            String apiId = keys.substring(0, keys.indexOf(CacheConstant.SPLIT));
//            String clientId = keys.substring(keys.indexOf(CacheConstant.SPLIT) + 1);
//            return apiService.getApiLimitPolicy(apiId, clientId);
//        }

//        if (key.startsWith(CacheConstant.IP_WHITE_LIST)) {
//            // FIXME
//            IpRestrictionBundle bundle = new IpRestrictionBundle();
//            return bundle;
//        }
//
//        if (key.equals(CacheConstant.ALL_AM_CLIENT)) {
//            return service.getAmClientList();
//        }
//
//
//        if (key.equals(CacheConstant.SERVICE_MAPPING_REGEX)) {
//            return service.findServiceByRegex();
//        }

        return null;
    }

    private String getApiKey(ServiceInfo info) {
        UriComponents uri = UriComponentsBuilder.fromUriString(info.getReqUrl()).build();
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.upperCase(info.getReqMethod()))
                .append(StringUtils.lowerCase(uri.getScheme()))
                .append(uri.getHost())
                .append(getPort(uri.getPort(), uri.getScheme()))
                .append(StringUtils.stripEnd(uri.getPath(), "/"));
        return sb.toString();
    }

    private int getPort(int port, String schema) {
        if (port > 0) {
            return port;
        }
        return RequestInfo.defaultSchemaPort.get(StringUtils.lowerCase(schema));
    }

}

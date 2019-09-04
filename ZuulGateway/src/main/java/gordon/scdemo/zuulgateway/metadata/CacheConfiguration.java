package gordon.scdemo.zuulgateway.metadata;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import gordon.scdemo.zuulgateway.mgt.service.ServiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
            return service.list();
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


}

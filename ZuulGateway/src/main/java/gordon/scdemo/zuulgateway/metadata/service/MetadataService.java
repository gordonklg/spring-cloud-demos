package gordon.scdemo.zuulgateway.metadata.service;

import com.github.benmanes.caffeine.cache.LoadingCache;
import gordon.scdemo.zuulgateway.metadata.CacheConstant;
import gordon.scdemo.zuulgateway.mgt.entity.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MetadataService {

    @Autowired
    private LoadingCache<String, Object> cache;

//    public List<com.sgm.b2c.msg.data.model.Service> getAllRegexServices(){
//        return (List<com.sgm.b2c.msg.data.model.Service>) cache.get(CacheConstant.SERVICE_MAPPING_REGEX);
//    }
//
    public List<String> getAllServices(final String clientId) {
        return (List<String>) cache.get(CacheConstant.APP_SERV_PREFIX + clientId);
    }
//
//
//    public ApiLimitPolicy getApiLimitPolicy(String apiId, String clientId) {
//        StringBuilder keys = new StringBuilder(CacheConstant.RATELIIT).append(apiId).append(CacheConstant.SPLIT).append(clientId);
//        return (ApiLimitPolicy) cache.get(keys.toString());
//    }
//
//    public IpRestrictionBundle getIpWhiteList() {
//        return (IpRestrictionBundle) cache.get(CacheConstant.IP_WHITE_LIST);
//    }
//
//    public Set<String> getAllAmClient() {
//        return (Set<String>) cache.get(CacheConstant.ALL_AM_CLIENT);
//    }
//
    public Map<String, ApiService> getAllServices() {
        return (Map<String, ApiService>) cache.get(CacheConstant.ALL_SERVICES);
    }
}

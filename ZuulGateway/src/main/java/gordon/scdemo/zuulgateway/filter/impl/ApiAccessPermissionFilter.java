package gordon.scdemo.zuulgateway.filter.impl;

import gordon.scdemo.zuulgateway.exception.GatewayException;
import gordon.scdemo.zuulgateway.filter.CoreFilter;
import gordon.scdemo.zuulgateway.filter.CoreFilterContext;
import gordon.scdemo.zuulgateway.metadata.service.MetadataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class ApiAccessPermissionFilter implements CoreFilter {

    @Autowired
    private MetadataService metadataService;

    @Override
    public void doFilter(final CoreFilterContext context) throws GatewayException {
//        ServiceInfo serviceInfo=context.getServiceInfo();
//        log.info("traceId:[{}]，need permission check ? [{}]",context.getTraceId(),serviceInfo.getNeedAuto());
//        if(NeedAuto.FALSE ==serviceInfo.getNeedAuto()){
//            return;
//        }
//        ClientInfo clientInfo = context.getClientInfo();
//        List<String> data = cacheService.getAllServices(clientInfo.getClientId());
//        boolean isAllow = data.contains(serviceInfo.getId());
//        if (!isAllow) {
//            log.warn("traceId:[{}] ---- clientId:[{}] not allow access serviceId:[{}]", context.getTraceId(), clientInfo.getClientId(), serviceInfo.getId());
//            throw GatewayException.UNAUTHORIZED;
//        }
    }
}

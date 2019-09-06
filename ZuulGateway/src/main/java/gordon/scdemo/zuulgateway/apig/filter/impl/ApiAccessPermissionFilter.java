package gordon.scdemo.zuulgateway.apig.filter.impl;

import gordon.scdemo.zuulgateway.apig.exception.GatewayException;
import gordon.scdemo.zuulgateway.apig.filter.CoreFilter;
import gordon.scdemo.zuulgateway.apig.filter.CoreFilterContext;
import gordon.scdemo.zuulgateway.apig.service.MetadataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ApiAccessPermissionFilter implements CoreFilter {

    @Autowired
    private MetadataService metadataService;

    @Override
    public void doFilter(final CoreFilterContext context) throws GatewayException {
        System.out.println("3232323232323232");
        System.out.println(context);
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

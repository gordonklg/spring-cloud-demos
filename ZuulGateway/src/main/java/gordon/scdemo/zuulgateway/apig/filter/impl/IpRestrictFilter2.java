package gordon.scdemo.zuulgateway.apig.filter.impl;

import gordon.scdemo.zuulgateway.apig.exception.GatewayException;
import gordon.scdemo.zuulgateway.apig.filter.CoreFilter;
import gordon.scdemo.zuulgateway.apig.filter.CoreFilterContext;
import gordon.scdemo.zuulgateway.apig.service.MetadataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Fake
 */
@Slf4j
public class IpRestrictFilter2 extends IpRestrictFilter {

    @Autowired
    private MetadataService metadataService;

    @Override
    public void doFilter(final CoreFilterContext context) throws GatewayException {
        log.info("in IpRestrictFilter2");
    }
}

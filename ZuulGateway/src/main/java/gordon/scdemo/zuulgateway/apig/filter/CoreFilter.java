package gordon.scdemo.zuulgateway.apig.filter;

import gordon.scdemo.zuulgateway.apig.exception.GatewayException;

public interface CoreFilter {

    void doFilter(CoreFilterContext context) throws GatewayException;
}

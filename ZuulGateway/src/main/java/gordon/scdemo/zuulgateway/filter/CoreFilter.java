package gordon.scdemo.zuulgateway.filter;

import gordon.scdemo.zuulgateway.exception.GatewayException;

public interface CoreFilter {

    void doFilter(CoreFilterContext context) throws GatewayException;
}

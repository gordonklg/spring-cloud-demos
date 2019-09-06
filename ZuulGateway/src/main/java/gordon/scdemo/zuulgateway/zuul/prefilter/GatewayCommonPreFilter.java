package gordon.scdemo.zuulgateway.zuul.prefilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import gordon.scdemo.zuulgateway.apig.filter.CoreFilterContext;
import gordon.scdemo.zuulgateway.apig.filter.FilterChain;
import gordon.scdemo.zuulgateway.zuul.RequestInfo;
import gordon.scdemo.zuulgateway.apig.model.ServiceInfo;
import gordon.scdemo.zuulgateway.apig.service.MetadataService;
import gordon.scdemo.zuulgateway.apig.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class GatewayCommonPreFilter extends ZuulFilter {

    private static final String[] PERHAPS_CLIENT_ID = new String[]{"client_id", "clientId"};
    private static final String[] PERHAPS_ACCESS_TOKEN = new String[]{"access_token"};
    private static final String[] PERHAPS_IDPUSERID = new String[]{"idpUserId", "idp_user_id"};

    /**
     * 在 PRE_DECORATION_FILTER_ORDER 之前执行
     */
    private static final int ORDER = FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
    public static final String ZUUL_TRACE_KEY = "x-zuul-trace-id";
    public static final String ZUUL_CLEINT_ID = "x-zuul-client-id";
    public static final String ROUTE_API_INFO = "route_api_info";
    public static final String REQUEST_START_TIME = "request_start_time";

    @Autowired
    private FilterChain filterChain;

    @Autowired
    private MetadataService metadataService;

    @Override
    public int filterOrder() {
        return ORDER;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        System.out.println("!!!!!!!!!!!!!!!!in my apig");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        RequestInfo requestInfo = new RequestInfo(request);
        String clientId = getClientId(request);
        String remoteAddr = request.getRemoteAddr();
        ServiceInfo serviceInfo = this.getInfo(requestInfo.getApiKey());
        CoreFilterContext innerContext = new CoreFilterContext().setClientId(clientId).setServiceInfo(serviceInfo).setRemoteAddr(remoteAddr);
        filterChain.doFilter(innerContext);

//        ctx.put(REQUEST_START_TIME,System.currentTimeMillis());
//        ctx.addZuulRequestHeader(ZUUL_TRACE_KEY, traceId);
//        ctx.addZuulResponseHeader(ZUUL_TRACE_KEY, traceId);
        ctx.put(ROUTE_API_INFO, serviceInfo);
        return null;
    }

    /**
     * 根据请求URL获取对应的API服务接口
     *
     * FIXME：当前版本只考虑简单URL，无PathVariable
     */
    private ServiceInfo getInfo(final String apiKey) {
        List services = metadataService.getAllServices();
        Map<String, ServiceInfo> simpleUrlServices = (Map) services.get(0);
        ServiceInfo serviceInfo = simpleUrlServices.get(apiKey);
        if (serviceInfo != null) {
            return serviceInfo;
        } else {
            throw new RuntimeException("no such service");
        }
    }

    /**
     * 返回对应参数的封装
     ** @param request
     * @return
     */
    public String getClientId(HttpServletRequest request) {
        final String clientId = HttpUtils.getPerhapsHeader(request, PERHAPS_CLIENT_ID);
        return clientId;
    }
}



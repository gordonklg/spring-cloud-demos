package gordon.scdemo.zuulgateway.zuul.prefilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import gordon.scdemo.zuulgateway.filter.CoreFilterContext;
import gordon.scdemo.zuulgateway.filter.FilterChain;
import gordon.scdemo.zuulgateway.filter.model.RequestInfo;
import gordon.scdemo.zuulgateway.filter.model.ServiceInfo;
import gordon.scdemo.zuulgateway.metadata.service.MetadataService;
import gordon.scdemo.zuulgateway.mgt.common.util.beancopy.BeanMapper;
import gordon.scdemo.zuulgateway.mgt.entity.ApiService;
import gordon.scdemo.zuulgateway.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
//@Component
public class GatewayCommonPreFilter extends ZuulFilter {

    private static final String[] PERHAPS_CLIENT_ID = new String[]{"client_id", "clientId"};
    private static final String[] PERHAPS_ACCESS_TOKEN = new String[]{"access_token"};
    private static final String[] PERHAPS_IDPUSERID = new String[]{"idpUserId", "idp_user_id"};

    /**
     *     数字越大，优先级越低
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
        System.out.println("!!!!!!!!!!!!!!!!in my filter");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        RequestInfo requestInfo = new RequestInfo(request);
        String clientId = getClientId(request);
        String remoteAddr = request.getRemoteAddr();
        ServiceInfo serviceInfo = this.getInfo(requestInfo.getApiKey());
        CoreFilterContext innerContext = new CoreFilterContext().setClientId(clientId).setServiceInfo(serviceInfo).setRemoteAddr(remoteAddr);
        filterChain.doFilter(innerContext);
        return null;
    }

    /**
     * 根据请求路径获取API接口信息
     */
    private ServiceInfo getInfo(final String apiKey) {
        Map<String, ApiService> services = metadataService.getAllServices();
        ApiService service = services.get(apiKey);
        if (service != null) {
            ServiceInfo serviceInfo = BeanMapper.map(service, ServiceInfo.class);
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



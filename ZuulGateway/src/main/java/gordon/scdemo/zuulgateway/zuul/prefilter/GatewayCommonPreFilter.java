package gordon.scdemo.zuulgateway.zuul.prefilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import gordon.scdemo.zuulgateway.apig.filter.CoreFilterContext;
import gordon.scdemo.zuulgateway.apig.filter.FilterChain;
import gordon.scdemo.zuulgateway.apig.model.ServiceInfo;
import gordon.scdemo.zuulgateway.apig.service.MetadataService;
import gordon.scdemo.zuulgateway.apig.utils.HttpUtils;
import gordon.scdemo.zuulgateway.mgt.common.util.beancopy.BeanMapper;
import gordon.scdemo.zuulgateway.zuul.RequestInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

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

    private AntPathMatcher pathMatcher = new AntPathMatcher();

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
        ServiceInfo serviceInfo = this.getInfo(requestInfo);
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
     */
    private ServiceInfo getInfo(RequestInfo requestInfo) {
        List services = metadataService.getAllServices();
        Map<String, ServiceInfo> simpleUrlServices = (Map) services.get(0);
        ServiceInfo serviceInfo = simpleUrlServices.get(requestInfo.getApiKey());
        if (serviceInfo != null) {
            ServiceInfo resultService = BeanMapper.map(serviceInfo, ServiceInfo.class);
            String resolvedBackendUrl = resultService.getBackendUrl();
            if(StringUtils.isEmpty(resolvedBackendUrl)) {
                // 只有后端系统是SC类型时，backendUrl才可能是空。因此 resolvedBackendUrl 只取 path。
                resolvedBackendUrl = requestInfo.getUrl().getPath();
            }
            // TODO: 未来会做其它类型的变量赋值操作
            resultService.setResolvedBackendUrl(resolvedBackendUrl);
            return resultService;
        }

        List<ServiceInfo> pathVariableUrlServices = (List) services.get(1);
        for (ServiceInfo pathVarServiceInfo : pathVariableUrlServices) {
            if (pathMatcher.match(pathVarServiceInfo.getReqUrl(), requestInfo.getUrlString())) {
                ServiceInfo resultService = BeanMapper.map(pathVarServiceInfo, ServiceInfo.class);
                Map<String, String> pathVariables = pathMatcher.extractUriTemplateVariables(pathVarServiceInfo.getReqUrl(), requestInfo.getUrlString());
                String resolvedBackendUrl = resultService.getBackendUrl();
                if (StringUtils.isEmpty(resolvedBackendUrl)) {
                    // 只有后端系统是SC类型时，backendUrl才可能是空。因此 resolvedBackendUrl 只取 path。
                    resolvedBackendUrl = requestInfo.getUrl().getPath();
                } else {
                    for (String key : pathVariables.keySet()) {
                        resolvedBackendUrl = resolvedBackendUrl.replace("[" + key + "]", pathVariables.get(key));
                    }
                }
                resultService.setResolvedBackendUrl(resolvedBackendUrl);
                return resultService;
            }
        }

        throw new RuntimeException("no such service");
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

    public static void main(String[] args) {
        AntPathMatcher matcher = new AntPathMatcher();
        System.out.println(matcher.match("http://www.demos.com:80/aa/user/{uid}/car/{vin}/info", "http://www.demos.com:80/aa/user/car/car/v8/info"));
        System.out.println(matcher.extractUriTemplateVariables("http://www.demos.com:80/aa/user/{uid}/car/{vin}/info", "http://www.demos.com:80/aa/user/car/car/v8/info"));


    }
}



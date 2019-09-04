package gordon.scdemo.zuulgateway.filter.model;


import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import gordon.scdemo.zuulgateway.utils.ApiCacheUtil;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Getter
public class RequestInfo {
    private static final Splitter splitter = Splitter.on(':').limit(2);
    private String scheme;
    private String host;
    private Integer port;
    private String method;
    private String path;

    public RequestInfo(final HttpServletRequest request) {
        final String host = request.getHeader(HttpHeaders.HOST);
        List<String> data = Lists.newArrayList(splitter.split(host));
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .host(data.get(0))
                .port(data.size() > 1 ? data.get(1) : null)
                .scheme(request.getScheme())
                .path(request.getRequestURI())
                .build().normalize();

        this.host = uriComponents.getHost();
        this.port = uriComponents.getPort() < 0 ? null : uriComponents.getPort();
        this.scheme = StringUtils.upperCase(uriComponents.getScheme());
        this.path = StringUtils.stripEnd(uriComponents.getPath(), "/");
        this.method = StringUtils.upperCase(request.getMethod());
    }

    public String getApiKey() {
        return ApiCacheUtil.getCacheKey(this);
    }
}

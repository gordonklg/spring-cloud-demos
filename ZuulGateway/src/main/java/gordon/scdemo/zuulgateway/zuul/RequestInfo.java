package gordon.scdemo.zuulgateway.zuul;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Getter
public class RequestInfo {
    private static final Splitter splitter = Splitter.on(':').limit(2);
    public static Map<String, Integer> defaultSchemaPort = Maps.newConcurrentMap();
    private String scheme;
    private String host;
    private Integer port;
    private String method;
    private String path;

    static {
        defaultSchemaPort.put("http", 80);
        defaultSchemaPort.put("https", 443);
    }

    public RequestInfo(final HttpServletRequest request) {
        final String host = request.getHeader(HttpHeaders.HOST);
        List<String> tokens = Lists.newArrayList(splitter.split(host));
        UriComponents uriComponents = UriComponentsBuilder.newInstance() // fromOriginalHeader
                .host(tokens.get(0))
                .port(tokens.size() > 1 ? tokens.get(1) : null)
                .scheme(request.getScheme())
                .path(request.getRequestURI())
                .build().normalize();

        this.host = uriComponents.getHost();
        this.port = uriComponents.getPort();
        this.scheme = StringUtils.lowerCase(uriComponents.getScheme());
        this.path = StringUtils.stripEnd(uriComponents.getPath(), "/");
        this.method = StringUtils.upperCase(request.getMethod());

        if (this.port == -1) {
            this.port = defaultSchemaPort.get(this.scheme);
        }
    }

    public String getApiKey() {
        StringBuilder sb = new StringBuilder();
        sb.append(getMethod())
                .append(getScheme())
                .append(getHost())
                .append(getPort())
                .append(getPath());
        return sb.toString();
    }

}

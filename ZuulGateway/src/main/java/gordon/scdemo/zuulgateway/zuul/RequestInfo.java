package gordon.scdemo.zuulgateway.zuul;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gordon.scdemo.zuulgateway.apig.exception.GatewayException;
import lombok.Getter;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Getter
public class RequestInfo {
    private static final Splitter splitter = Splitter.on(':').limit(2);
    public static final Map<String, Integer> DEFAULT_SCHEMA_PORT = Maps.newConcurrentMap();
    // 将默认端口（-1）转化为具体端口号。
    private URL url;
    private String method;
    private String urlString;

    static {
        DEFAULT_SCHEMA_PORT.put("http", 80);
        DEFAULT_SCHEMA_PORT.put("https", 443);
    }

    public RequestInfo(final HttpServletRequest request) {
        final String host = request.getHeader(HttpHeaders.HOST);
        List<String> tokens = Lists.newArrayList(splitter.split(host));
        int port;
        if (tokens.size() > 1) {
            port = Integer.parseInt(tokens.get(1));
        } else {
            port = DEFAULT_SCHEMA_PORT.get(request.getScheme().toLowerCase());
        }
        try {
            this.url = new URL(request.getScheme(), tokens.get(0), port, request.getRequestURI());
        } catch (MalformedURLException e) {
            throw new GatewayException();
        }

        this.method = request.getMethod();
        this.urlString = url.toExternalForm();
    }

    public String getApiKey() {
        return method + " " + urlString;
    }

}

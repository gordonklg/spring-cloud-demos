package gordon.scdemo.zuulgateway.utils;


import gordon.scdemo.zuulgateway.filter.model.RequestInfo;
import gordon.scdemo.zuulgateway.metadata.CacheConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public abstract class ApiCacheUtil {

    private static final int MAX_PORT = 65535;

    public static String getCacheKey(final RequestInfo requestInfo) {
        String uri=requestInfo.getPort() == null ? nullPort(requestInfo) : withPort(requestInfo);
        return requestInfo.getMethod() + CacheConstant.SPLITTER + uri;
    }


//    public static String getCacheKey(final Service service) {
//        StringBuffer buffer = new StringBuffer();
//        buffer.append(service.getReqMethod()).append(CacheConstant.SPLITTER).append(service.getReqAddr())
//                .append(service.getReqUrl());
//        return buffer.toString();
//    }
//
//
    private static String nullPort(final RequestInfo requestInfo) {
        return UriComponentsBuilder.newInstance()
                .scheme(StringUtils.lowerCase(requestInfo.getScheme()))
                .host(requestInfo.getHost())
                .path(requestInfo.getPath())
                .build().normalize().toString();
    }

    private static String withPort(final RequestInfo requestInfo) {
        return UriComponentsBuilder.newInstance()
                .scheme(StringUtils.lowerCase(requestInfo.getScheme()))
                .host(requestInfo.getHost())
                .port(getPort(requestInfo.getPort()))
                .path(requestInfo.getPath())
                .build().normalize().toString();
    }

    private static int getPort(Integer port) {
        return Math.min(port, MAX_PORT);
    }
}
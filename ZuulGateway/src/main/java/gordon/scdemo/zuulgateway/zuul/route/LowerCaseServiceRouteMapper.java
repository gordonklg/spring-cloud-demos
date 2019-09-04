package gordon.scdemo.zuulgateway.zuul.route;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;

@Slf4j
public class LowerCaseServiceRouteMapper implements ServiceRouteMapper {

    @Override
    public String apply(String serviceId) {
        System.out.println("!!!!!!!!!!!!!!!!! in LowerCaseServiceRouteMapper");
        return StringUtils.lowerCase(serviceId);
    }

}

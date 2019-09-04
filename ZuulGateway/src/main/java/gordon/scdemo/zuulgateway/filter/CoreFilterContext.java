package gordon.scdemo.zuulgateway.filter;

import gordon.scdemo.zuulgateway.filter.model.ServiceInfo;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CoreFilterContext {

    private String traceId;

    private ServiceInfo serviceInfo;

    private String remoteAddr;

    private String clientId;




}

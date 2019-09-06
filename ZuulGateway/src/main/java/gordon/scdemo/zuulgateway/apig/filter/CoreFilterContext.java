package gordon.scdemo.zuulgateway.apig.filter;

import gordon.scdemo.zuulgateway.apig.model.ServiceInfo;
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

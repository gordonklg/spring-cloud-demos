package gordon.scdemo.zuulgateway.apig.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.net.URL;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ServiceInfo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 所属系统
     */
    private String system;

    /**
     * 请求URL
     */
    private String reqUrl;

    // 请求URL，将默认端口（-1）转化为具体端口号。（新增属性）
    private URL reqUrlObj;

    /**
     * 请求方法
     */
    private String reqMethod;

    /**
     * 后端系统类型
     */
    private String backendSystemType;

    /**
     * 后端服务URL
     * 可能是 path；可能是 full url；可能是空或null
     */
    private String backendUrl;

    // 解析后的后端服务URL。（新增属性）
    // 如果后端服务URL字段非空，则将变量全替换为真实值。否则替换为 http req 的 path。
    // 该字段在缓存的元数据中无值！将http req匹配到service时必须new一个新的ServiceInfo实例并为之赋值。
    private String resolvedBackendUrl;

    /**
     * 后端服务方法
     */
    private String backendMethod;

    /**
     * 服务ID
     */
    private String serviceId;

    /**
     * 默认超时时间（毫秒）
     */
    private Integer timeout;

    public boolean hasPathVariable() {
        return reqUrl.contains("[") || reqUrl.contains("{");
    }

}

package gordon.scdemo.zuulgateway.mgt.service.model;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 接口服务。
 * </p>
 *
 * @author Mybatis Plus Generator
 * @since 2019-08-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ServiceModel implements Serializable {

    private static final long serialVersionUID = 1L;

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
     */
    private String backendUrl;

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

}

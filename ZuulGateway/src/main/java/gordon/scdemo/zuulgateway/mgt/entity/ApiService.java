package gordon.scdemo.zuulgateway.mgt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("B_SERVICE")
public class ApiService implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 所属系统
     */
    @TableField("SYSTEM")
    private String system;

    /**
     * 请求URL
     */
    @TableField("REQ_URL")
    private String reqUrl;

    /**
     * 请求方法
     */
    @TableField("REQ_METHOD")
    private String reqMethod;

    /**
     * 后端系统类型
     */
    @TableField("BACKEND_SYSTEM_TYPE")
    private String backendSystemType;

    /**
     * 后端服务URL
     */
    @TableField("BACKEND_URL")
    private String backendUrl;

    /**
     * 后端服务方法
     */
    @TableField("BACKEND_METHOD")
    private String backendMethod;

    /**
     * 服务ID
     */
    @TableField("SERVICE_ID")
    private String serviceId;

    /**
     * 默认超时时间（毫秒）
     */
    @TableField("TIMEOUT")
    private Integer timeout;

}

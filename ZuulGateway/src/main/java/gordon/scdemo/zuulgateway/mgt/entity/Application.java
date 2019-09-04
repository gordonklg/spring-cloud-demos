package gordon.scdemo.zuulgateway.mgt.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 接入方应用程序，网关外部系统
 * </p>
 *
 * @author Mybatis Plus Generator
 * @since 2019-08-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("B_APPLICATION")
public class Application implements Serializable {

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
     * Client ID
     */
    @TableField("CLIENT_ID")
    private String clientId;

    /**
     * Client Secret
     */
    @TableField("CLIENT_SECRET")
    private String clientSecret;

}

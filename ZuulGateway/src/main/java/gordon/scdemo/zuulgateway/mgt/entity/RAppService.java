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
 * APP和SERVICE对应关系
 * </p>
 *
 * @author Mybatis Plus Generator
 * @since 2019-08-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("R_APP_SERVICE")
public class RAppService implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * APP ID
     */
    @TableField("APP_ID")
    private Long appId;

    /**
     * SERVICE_ID
     */
    @TableField("SERVICE_ID")
    private Long serviceId;



}

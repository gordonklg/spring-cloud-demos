package gordon.scdemo.zuulgateway.mgt.service.model;

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
public class RAppServiceModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * APP ID
     */
    private Long appId;

    /**
     * SERVICE_ID
     */
    private Long serviceId;

}

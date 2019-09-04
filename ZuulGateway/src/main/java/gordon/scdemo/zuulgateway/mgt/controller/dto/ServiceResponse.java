package gordon.scdemo.zuulgateway.mgt.controller.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class ServiceResponse {

    private String name;

    private String system;

    private String reqUrl;

    private String reqMethod;

    private String backendSystemType;

    private String backendUrl;

    private String backendMethod;

    private String serviceId;

    private Integer timeout;
}

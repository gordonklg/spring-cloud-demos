package gordon.scdemo.zuulgateway.mgt.controller;

import gordon.scdemo.zuulgateway.mgt.common.util.beancopy.BeanMapper;
import gordon.scdemo.zuulgateway.mgt.controller.dto.ServiceResponse;
import gordon.scdemo.zuulgateway.mgt.entity.ApiService;
import gordon.scdemo.zuulgateway.mgt.service.ServiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/zuulgateway/api/service")
@Slf4j
public class ServiceController {

    @Autowired
    private ServiceService service;

    @GetMapping()
    private List<ServiceResponse> listAllServices() {
        List<ApiService> result = service.list();
        return BeanMapper.mapList(result, ServiceResponse.class);
    }

}

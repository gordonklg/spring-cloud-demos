package gordon.scdemo.zuulgateway.mgt.controller;

import gordon.scdemo.zuulgateway.mgt.controller.dto.ServiceResponse;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class TestServiceController {

    private TestRestTemplate template = new TestRestTemplate();

    @Test
    public void testRequest() throws Exception {
        ResponseEntity resp = template.getForEntity(
                "http://localhost:8000/zuulgateway/api/service", List.class);
        System.out.println(resp.getBody());
    }
}

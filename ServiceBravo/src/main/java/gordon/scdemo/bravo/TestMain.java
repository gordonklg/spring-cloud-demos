package gordon.scdemo.bravo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestMain {

    public static void main(String[] args) {
        RestTemplate template = new RestTemplate();
        ExecutorService es = Executors.newFixedThreadPool(25);
        for (int i = 0; i < 25; i++) {
            es.submit(() -> {
                ResponseEntity resp = template.getForEntity(
                        "http://localhost:8181/b/a/error?second=2", String.class);
                System.out.println(resp.getBody());
            });
        }
        es.shutdown();
    }
}


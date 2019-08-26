package gordon.scdemo.boot.alpha.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class BootAlphaAsyncService {

    private Map<String, String> contentMap = new ConcurrentHashMap<>();

    private Map<String, DeferredResult<String>> taskMap = new ConcurrentHashMap<>();

    private ExecutorService es = Executors.newFixedThreadPool(10);

    public String syncRequest(String id) {
        String result = "";
        try {
            log.info("long-term work started");
            TimeUnit.SECONDS.sleep(5);
            log.info("long-term work finished");
            result = "content_of_" + id;
            contentMap.put(id, result);
        } catch (InterruptedException e) {
            log.warn("interrupted");
        }
        return result;
    }

    public String asyncRequest(String id) {
        String result = "accept_to_get_content_of_" + id;
        es.submit(() -> {
            syncRequest(id);
        });
        return result;
    }

    public String asyncData(String id) {
        if (contentMap.containsKey(id)) {
            return contentMap.get(id);
        } else {
            return "not_ready_content_" + id;
        }
    }

    /////=======================CountDownLatch Section===============================/////
    public String sync_async_cdl(String id) {
        CountDownLatch cdl = new CountDownLatch(1);
        es.submit(() -> {
            try {
                syncRequest(id);
            } finally {
                cdl.countDown();
            }
        });
        try {
            cdl.await();
        } catch (InterruptedException e) {
            log.warn("interrupted");
        }
        return asyncData(id);
    }
    /////=======================End of CountDownLatch Section===============================/////

    /////=======================DeferredResult Section===============================/////
    public void sync_async_dr(DeferredResult<String> deferredResult, String id) {
        deferredResult.onTimeout(() -> {
            deferredResult.setResult("timeout_of_" + id);
        });

        deferredResult.onCompletion(() -> {
        });

        es.submit(() -> {
            deferredResult.setResult(syncRequest(id));
        });
    }
    /////=======================End of DeferredResult Section===============================/////
}

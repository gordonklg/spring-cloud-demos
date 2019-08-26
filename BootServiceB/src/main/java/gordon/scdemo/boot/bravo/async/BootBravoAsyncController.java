package gordon.scdemo.boot.bravo.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.UUID;
import java.util.concurrent.Callable;

@RestController
@RequestMapping(path = "/boot/b")
@Slf4j
public class BootBravoAsyncController {

    @Autowired
    private BootAlphaAsyncClient client;

    @GetMapping(path = "/sync_async_cdl")
    public String sync_async_countdownlatch(@RequestParam(defaultValue = "default") String id) {
        log.info("/sync_async_cdl, id = {}", id);
        return client.sync_async_countdownlatch(id);
    }

    @GetMapping(path = "/sync_async_callable")
    public String sync_async_callable(@RequestParam(defaultValue = "default") String id) {
        log.info("/sync_async_callable, id = {}", id);
        return client.sync_async_callable(id);
    }

    @GetMapping(path = "/sync_async_dr")
    public String sync_async_deferredresult(@RequestParam(defaultValue = "default") String id) {
        log.info("/sync_async_dr, id = {}", id);
        return client.sync_async_deferredresult(id);
    }

    @GetMapping(path = "/sync/request")
    public String syncRequest(@RequestParam(defaultValue = "default") String id) {
        log.info("/sync/request, id = {}", id);
        return client.syncRequest(id);
    }

    @GetMapping(path = "/async/request")
    public String asyncRequest(@RequestParam(defaultValue = "default") String id) {
        log.info("/async/request, id = {}", id);
        return client.asyncRequest(id);
    }

    @GetMapping(path = "/async/data")
    public String asyncData(@RequestParam(defaultValue = "default") String id) {
        log.info("/async/data, id = {}", id);
        return client.asyncData(id);
    }
}
package gordon.scdemo.boot.alpha.async;

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
@RequestMapping(path = "/boot/a")
@Slf4j
public class BootAlphaAsyncController {

    @Autowired
    private BootAlphaAsyncService service;

    @GetMapping(path = "/sync_async_cdl")
    public String sync_async_countdownlatch(@RequestParam(defaultValue = "default") String id) {
        log.info("/sync_async_cdl, id = {}", id);
        return service.sync_async_cdl(id);
    }

    @GetMapping(path = "/sync_async_callable")
    public Callable<String> sync_async_callable(@RequestParam(defaultValue = "default") String id) {
        log.info("/sync_async_callable, id = {}", id);
        Callable<String> result = (() -> {
            return service.syncRequest(id);
        });
        return result;
    }

    @GetMapping(path = "/sync_async_dr")
    public DeferredResult<String> sync_async_deferredresult(@RequestParam(defaultValue = "default") String id) {
        log.info("/sync_async_dr, id = {}", id);
        DeferredResult<String> deferredResult = new DeferredResult<>();
        service.sync_async_dr(deferredResult, id);
        return deferredResult;
    }

    @GetMapping(path = "/sync/request")
    public String syncRequest(@RequestParam(defaultValue = "default") String id) {
        log.info("/sync/request, id = {}", id);
        return service.syncRequest(id);
    }

    @GetMapping(path = "/async/request")
    public String asyncRequest(@RequestParam(defaultValue = "default") String id) {
        log.info("/async/request, id = {}", id);
        return service.asyncRequest(id);
    }

    @GetMapping(path = "/async/data")
    public String asyncData(@RequestParam(defaultValue = "default") String id) {
        log.info("/async/data, id = {}", id);
        return service.asyncData(id);
    }
}
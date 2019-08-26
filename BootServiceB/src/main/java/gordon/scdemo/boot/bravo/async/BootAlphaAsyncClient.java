package gordon.scdemo.boot.bravo.async;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "whatever", url = "http://localhost:8080/", path = "/boot/a")
public interface BootAlphaAsyncClient {

    @GetMapping(path = "/sync_async_cdl")
    public String sync_async_countdownlatch(@RequestParam("id") String id);

    @GetMapping(path = "/sync_async_callable")
    public String sync_async_callable(@RequestParam("id") String id);

    @GetMapping(path = "/sync_async_dr")
    public String sync_async_deferredresult(@RequestParam("id") String id);

    @GetMapping(path = "/sync/request")
    public String syncRequest(@RequestParam("id") String id);

    @GetMapping(path = "/async/request")
    public String asyncRequest(@RequestParam("id") String id);

    @GetMapping(path = "/async/data")
    public String asyncData(@RequestParam("id") String id);
}
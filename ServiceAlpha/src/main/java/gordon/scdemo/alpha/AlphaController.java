package gordon.scdemo.alpha;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/a")
public class AlphaController {

    private final Logger logger = LoggerFactory.getLogger(AlphaController.class);

    @RequestMapping(method = RequestMethod.GET)
    @HystrixCommand()
    public String get(@RequestParam(defaultValue = "default") String name, HttpServletRequest request) {
//        System.out.println(request);
//        System.out.println(request.getHeader("corId"));
        String result = "a-" + name;
        logger.info("/a, name:" + name + ", result:" + result);
        return result;
    }

    @HystrixCommand(fallbackMethod = "sleepFallback", threadPoolKey = "sleepy",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
                    @HystrixProperty(name = "execution.isolation.thread.interruptOnTimeout", value = "false")

            })
    @RequestMapping(path = "/sleep", method = RequestMethod.GET)
    public String sleep(@RequestParam(defaultValue = "60") Integer second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Slept " + second + " seconds.";
    }

    public String sleepFallback(Integer second) {
        return "Plan to sleep " + second + " seconds, but return at once.";
    }

    @HystrixCommand(fallbackMethod = "errorFallback")
    @RequestMapping(path = "/error", method = RequestMethod.GET)
    public String error(@RequestParam(defaultValue = "60") Integer second, HttpServletResponse response) {
        logger.info("In error.");
        if(second == 0) {
            logger.info("In error but no error.");
            return "In error but no error.";
        }
        throw new RuntimeException("ERROR");
    }

    public String errorFallback(Integer second, HttpServletResponse response) {
        logger.info("In error fallback.");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "In error fallback";
    }

    @HystrixCommand()
    @RequestMapping(path = "/error2", method = RequestMethod.GET)
    public String errorNoFallback(@RequestParam(defaultValue = "60") Integer second, HttpServletResponse response) {
        logger.info("In error no fallback.");
        if(second == 0) {
            logger.info("In error no fallback but no error.");
            return "In error no fallback but no error.";
        }
        throw new RuntimeException("ERROR NO FALLBACK");
    }
}
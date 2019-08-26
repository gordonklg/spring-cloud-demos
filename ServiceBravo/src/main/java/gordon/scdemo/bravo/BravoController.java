package gordon.scdemo.bravo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/b")
public class BravoController {

    private final Logger logger = LoggerFactory.getLogger(BravoController.class);

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(method = RequestMethod.GET)
    @HystrixCommand()
    public String get(@RequestParam(defaultValue = "default") String name) {
        String result = "b-" + name;
        logger.info("/b, name:" + name + ", result:" + result);
        return result;
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    @HystrixCommand()
    public String getAll(@RequestParam(defaultValue = "default") String name) {
        String alpha = restTemplate.getForEntity("http://alpha/a?name=" + name, String.class).getBody();
        String result = "b-" + name + ":" + alpha;
        logger.info("/b/all, name:" + name + ", result:" + result);
        return result;
    }

    @HystrixCommand(fallbackMethod = "errorFallback")
    @RequestMapping(path = "/error", method = RequestMethod.GET)
    public String error(@RequestParam(defaultValue = "60") Integer second, HttpServletResponse response) {
        logger.info("In error.");
        if (second == 0) {
            logger.info("In error but no error.");
            return "In error but no error.";
        }
        throw new RuntimeException("ERROR");
    }

    @HystrixCommand(fallbackMethod = "errorFallbackFallback")
    public String errorFallback(Integer second, HttpServletResponse response) {
        logger.info("In error fallback.");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        throw new RuntimeException("ERROR FALLBACK");
    }

    public String errorFallbackFallback(Integer second, HttpServletResponse response) {
        logger.info("In error fallback fallback.");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "In error fallback fallback";
    }

    @RequestMapping(path = "/a/error", method = RequestMethod.GET)
    @HystrixCommand()
    public String callAlphaError(@RequestParam(defaultValue = "60") Integer second) {
        String resp = restTemplate.getForEntity("http://alpha/a/error?second=" + second, String.class).getBody();
        String result = "From Bravo call Alpha's error, response: " + resp;
        logger.info("/b/a/error, second:" + second + ", result:" + result);
        return result;
    }

}
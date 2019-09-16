package gordon.scdemo.alpha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/aa")
public class AlphaController2 {

    private final Logger logger = LoggerFactory.getLogger(AlphaController2.class);

    @RequestMapping(path = "/user/{uid}/car/{vin}/info", method = RequestMethod.GET)
    public String getCarInfo(@PathVariable String uid, @PathVariable String vin) {
        return uid + "'s car " + vin;
    }

    @RequestMapping(path = "/user/car/{uid}/{vin}/info", method = RequestMethod.GET)
    public String getCarInfo2(@PathVariable String uid, @PathVariable String vin) {
        return uid + "'s car " + vin;
    }

    @RequestMapping(path = "/user/membercard/{memberCardNo}", method = RequestMethod.GET)
    public String getMemberInfo(@RequestHeader String idpUserId, @PathVariable String memberCardNo, @RequestParam String cardLevel) {
        return idpUserId + "'s " + cardLevel + " member card " + memberCardNo;
    }

}
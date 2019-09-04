package gordon.scdemo.zuulgateway.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class HttpUtils {

    public static String getPerhapsHeader(final HttpServletRequest request, final String[] perhapsKey) {
        return Arrays.stream(perhapsKey).map(key -> request.getHeader(key)).filter(value -> value != null).findFirst().orElse(null);
    }

}

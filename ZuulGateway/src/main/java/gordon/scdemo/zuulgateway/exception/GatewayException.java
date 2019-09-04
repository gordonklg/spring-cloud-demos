package gordon.scdemo.zuulgateway.exception;


import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;

public final class GatewayException extends RuntimeException {

//    public static final GatewayException NOT_FOUND = new GatewayException(new TextExceptionAttr(ExceptionCodesDef.NOT_FOUND_MSG), ExceptionCodesDef.NOT_FOUND,ExceptionCodesDef.NOT_FOUND_MSG);
//    public static final GatewayException SERVER_NOT_FOUND = new GatewayException(new TextExceptionAttr(ExceptionCodesDef.SERVER_NOT_FOUND_MSG), ExceptionCodesDef.SERVER_NOT_FOUND,ExceptionCodesDef.SERVER_NOT_FOUND_MSG);
//    public static final GatewayException UNAUTHORIZED = new GatewayException(new TextExceptionAttr(ExceptionCodesDef.UNAUTHORIZED_MSG), ExceptionCodesDef.UNAUTHORIZED,ExceptionCodesDef.UNAUTHORIZED_MSG);
//    public static final GatewayException TOO_MANY_REQUESTS = new GatewayException(new TextExceptionAttr(ExceptionCodesDef.TOO_MANY_REQUESTS_MSG), ExceptionCodesDef.TOO_MANY_REQUESTS,ExceptionCodesDef.TOO_MANY_REQUESTS_MSG);
//
//    public GatewayException(GatewayExceptionAttr attr, HttpStatus httpStatus) {
//        super(new ZuulException(attr, httpStatus.value(),httpStatus.getReasonPhrase()));
//    }
//
//    public GatewayException(GatewayExceptionAttr attr, int value, String reason) {
//        super(new ZuulException(attr, value,reason));
//    }

}

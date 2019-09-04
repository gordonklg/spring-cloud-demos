package gordon.scdemo.zuulgateway.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author jli2
 * @date 3/6/2019 1:11 PM
 **/
@Data
@AllArgsConstructor
public class ResponseStatus {

    private int errorCode;
    private String errorMessage;

    public static ResponseStatus gateWayException(int errorCode,String errorMessage){
        return new ResponseStatus(errorCode,errorMessage);
    }
}

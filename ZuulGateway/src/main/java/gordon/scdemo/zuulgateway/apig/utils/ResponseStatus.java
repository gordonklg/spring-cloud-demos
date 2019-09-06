package gordon.scdemo.zuulgateway.apig.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseStatus {

    private int errorCode;
    private String errorMessage;

    public static ResponseStatus gateWayException(int errorCode,String errorMessage){
        return new ResponseStatus(errorCode,errorMessage);
    }
}

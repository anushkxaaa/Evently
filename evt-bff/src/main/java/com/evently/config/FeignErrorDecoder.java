package com.evently.config;

import com.evently.exception.OpenServiceNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();
    @Override
    public Exception decode(String methodKey, Response response){
        if (response.status() == 404) {
            return new OpenServiceNotFoundException("Not Found in Open Service");
        }
        return defaultDecoder.decode(methodKey, response);
    }
}

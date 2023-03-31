package kt.reactive.mywebflux.exception.global;

import kt.reactive.mywebflux.exception.CustomAPIException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.HashMap;
import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorMap = new HashMap<>();
        Throwable error = super.getError(request);
        if (error instanceof RuntimeException) {
            CustomAPIException customAPIException = (CustomAPIException) error;
            errorMap.put("message", customAPIException.getMessage());
            errorMap.put("status", customAPIException.getHttpStatus());
            errorMap.put("endpoint url", request.path());
        }
        return errorMap;
    }
}
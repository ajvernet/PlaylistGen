package org.ssa.ironyard.interceptor;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger log = LogManager.getLogger(LoggingRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        ClientHttpResponse response = execution.execute(request, body);

        log.debug("request: {}",request.getHeaders());
        return response;
    }

    private void log(HttpRequest request, byte[] body, ClientHttpResponse response) throws IOException {
        //do logging
    }


}

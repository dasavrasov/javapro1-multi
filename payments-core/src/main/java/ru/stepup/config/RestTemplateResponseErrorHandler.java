package ru.stepup.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import ru.stepup.dto.IntegrationErrorDto;
import ru.stepup.dto.IntegrationException;

import java.io.IOException;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        boolean isClientError = response.getStatusCode().is4xxClientError();
        boolean isServerError = response.getStatusCode().is5xxServerError();
        return isClientError || isServerError;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()||response.getStatusCode().is5xxServerError()){
            ObjectMapper mapper = new ObjectMapper();
            IntegrationErrorDto integrationErrorDto=mapper.readValue(response.getBody(), IntegrationErrorDto.class);
            throw new IntegrationException(integrationErrorDto);
        }
    }
}

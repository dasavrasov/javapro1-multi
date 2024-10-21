package ru.stepup.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.stepup.dto.IntegrationErrorDto;
import ru.stepup.dto.IntegrationException;
import ru.stepup.dto.PaymentRequestDto;
import ru.stepup.dto.ProductDto;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {
    private final RestTemplate paymentClient;

    public ProductService(@Qualifier("product-client") RestTemplate paymentClient) {
        this.paymentClient = paymentClient;
    }

    public List<ProductDto> getProducts(Long userId) {
        ProductDto[] productsArray = new ProductDto[0];
        try {
            productsArray = paymentClient.getForObject("/user/" + userId, ProductDto[].class);
        } catch (RestClientException e) {
            throw new IntegrationException(new IntegrationErrorDto("Ошибка получения продуктов", e.getMessage()));
        }
        return Arrays.asList(productsArray);
    }

    public ResponseEntity<?> execute(PaymentRequestDto paymentRequest){
        String url = "/user?userId=" + paymentRequest.userId() + "&account=" + paymentRequest.account();
        ResponseEntity<ProductDto> response = paymentClient.getForEntity(url, ProductDto.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            ProductDto product = response.getBody();
            if (product.balance().compareTo(paymentRequest.amount()) < 0) {
                throw new RestClientException("Недостаточно средств на счете для оплаты");
            } else {
                return ResponseEntity.ok("Платеж успешно проведен");
            }
        } else {
            throw new IntegrationException(new IntegrationErrorDto(HttpStatus.NOT_FOUND.name(),"Нет продукта с такими данными"));
        }

    }
}
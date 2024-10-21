package ru.stepup.dto;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.stepup.dto.ProductType;

@Converter(autoApply = true)
public class ProductTypeConverter implements AttributeConverter<ProductType, String> {

    @Override
    public String convertToDatabaseColumn(ProductType productType) {
        if (productType == null) {
            return null;
        }
        return productType.getCode();
    }

    @Override
    public ProductType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
        return ProductType.getByCode(code);
    }
}

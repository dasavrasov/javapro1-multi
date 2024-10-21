package ru.stepup.service;

import org.springframework.stereotype.Service;
import ru.stepup.dto.User;
import ru.stepup.repository.ProductDao;
import ru.stepup.dto.Product;

import java.util.List;

@Service
public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao userDao) {
        this.productDao = userDao;
    }

    public Product findById(Long id) {
        return productDao.findById(id).orElse(null);
    }
//    public List<Product> findByUser(User user) {
//        return productDao.findByUser(user);
//    }

    public Product findByUserIdAndAccount(Long userId, String account) {
        return productDao.findByUserIdAndAccount(userId, account);
    }

    public List<Product> findByUserId(Long userId) {
        return productDao.findByUserId(userId);
    }
}
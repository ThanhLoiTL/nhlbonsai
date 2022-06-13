package com.nhlshop.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nhlshop.dto.ResponseObject;
import com.nhlshop.entities.CartDetailEntity;
import com.nhlshop.entities.CartEntity;
import com.nhlshop.entities.ProductEntity;
import com.nhlshop.entities.UserEntity;
import com.nhlshop.service.ICartDetailService;
import com.nhlshop.service.ICartService;
import com.nhlshop.service.IProductService;
import com.nhlshop.service.ISecurityService;
import com.nhlshop.service.IUserService;

@RestController
@RequestMapping("/cart")
public class Cart {
    @Autowired
    private ICartService cartService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICartDetailService cartDetailService;

    @Autowired
    private IProductService productService;

    @Autowired
    private ISecurityService securityService;

    @GetMapping
    public ResponseEntity<ResponseObject> getCart() {
        UserEntity user = userService.findByEmail(securityService.findLoggedInUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("FAILED", "Not login", ""));
        }
        CartEntity cart = cartService.findByUser(user);
        if (cart == null) {
            cart = cartService.createCart(cart, user);
        }
        List<CartDetailEntity> cartDetails = cartDetailService.findCartDetailByCart(cart);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Get cart successfully", cartDetails));
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addToCart(@RequestParam("product_id") Long productId) {
        // check product id
        if (!productService.findById(productId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("FAILED", "Product with id " + productId + " not exist", ""));
        }
        ProductEntity product = productService.findById(productId).get();
        if (product.getQuantityStock() < 1) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("FAILED", "Product with id " + productId + "out of stock", ""));
        }

        // check login
        UserEntity user = userService.findByEmail(securityService.findLoggedInUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("FAILED", "Not login", ""));
        }

        // get cart
        CartEntity cart = cartService.findByUser(user);
        if (cart == null) {
            cart = cartService.createCart(cart, user);
        }

        boolean isSucceed = false;

        // add one product to cart if product existed in cart
        List<CartDetailEntity> cartDetails = cartDetailService.findCartDetailByCart(cart);
        for (CartDetailEntity detail : cartDetails) {
            if (detail.getProduct().getId() == productId) {
                detail.setQuantity(detail.getQuantity() + 1);
                cartDetailService.saveOrUpdate(detail);
                isSucceed = true;
            }
        }

        // add one product to cart if product not exist in cart
        if (!isSucceed) {
            CartDetailEntity cartDetail = new CartDetailEntity();
            cartDetail.setCart(cart);
            cartDetail.setProduct(product);
            cartDetail.setQuantity(1);
            cartDetailService.saveOrUpdate(cartDetail);
            isSucceed = true;
        }

        // calculate total money in cart
        if (isSucceed) {
            cartService.calculateTotalMoney(cart);
        }

        return isSucceed ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Add to cart successfully", ""))
                : ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseObject("FAILED", "Cannot IMPLEMENTED", ""));
    }

    @PutMapping
    private ResponseEntity<ResponseObject> updateCart(@RequestParam("product_id") Long productId,
            @RequestParam("quantity") int quantity) {
        if (!productService.findById(productId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("FAILED", "Product with id " + productId + " not exist", ""));
        }
        UserEntity user = userService.findByEmail(securityService.findLoggedInUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("FAILED", "Not login", ""));
        }
        CartEntity cart = cartService.findByUser(user);
        if (cart == null) {
            cart = cartService.createCart(cart, user);
        }

        boolean isSucceed = false;

        // update quantity
        List<CartDetailEntity> cartDetails = cartDetailService.findCartDetailByCart(cart);
        for (CartDetailEntity detail : cartDetails) {
            if (detail.getProduct().getId() == productId) {
                if (detail.getProduct().getQuantityStock() - quantity >= 0) {
                    detail.setQuantity(quantity);
                    cartDetailService.saveOrUpdate(detail);
                    isSucceed = true;
                }
            }
        }

        if (isSucceed) {
            cartService.calculateTotalMoney(cart);
        }

        return isSucceed ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Update cart successfully", ""))
                : ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseObject("FAILED", "Product with id " + productId + "out of stock", ""));
    }

    @DeleteMapping
    public ResponseEntity<ResponseObject> deleteCartDetail(@RequestParam("product_id") Long productId) {
        if (!productService.findById(productId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("FAILED", "Product with id " + productId + " not exist", ""));
        }
        UserEntity user = userService.findByEmail(securityService.findLoggedInUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("FAILED", "Not login", ""));
        }
        CartEntity cart = cartService.findByUser(user);
        if (cart == null) {
            cart = cartService.createCart(cart, user);
        }

        boolean isSucceed = false;

        // delete cart detail
        List<CartDetailEntity> cartDetails = cartDetailService.findCartDetailByCart(cart);
        for (CartDetailEntity detail : cartDetails) {
            if (detail.getProduct().getId() == productId) {
                cartDetailService.deleteById(detail.getId());
                isSucceed = true;
            }
        }

        if (isSucceed) {
            cartService.calculateTotalMoney(cart);
        }

        return isSucceed ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Delete product from cart successfully", ""))
                : ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseObject("FAILED", "Cannot IMPLEMENTED", ""));
    }
}

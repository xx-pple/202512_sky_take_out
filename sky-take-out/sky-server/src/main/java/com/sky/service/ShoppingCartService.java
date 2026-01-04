package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    /**
     * 添加购物⻋
     * @param shoppingCartDTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 查看购物⻋
     * @return
     */
    List<ShoppingCart> showShoppingCart();
    /**
     * 清空购物⻋商品
     */
    void cleanShoppingCart();
    /**
     * 删除购物⻋中⼀个商品
     * @param shoppingCartDTO
     */
    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
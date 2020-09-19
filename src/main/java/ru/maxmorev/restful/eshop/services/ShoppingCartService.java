package ru.maxmorev.restful.eshop.services;


import ru.maxmorev.restful.eshop.rest.request.RemoveFromCartRequest;
import ru.maxmorev.restful.eshop.rest.request.ShoppingCartSetRequest;
import ru.maxmorev.restful.eshop.rest.response.ShoppingCartDto;

public interface ShoppingCartService {

    ShoppingCartDto createEmptyShoppingCart();
    ShoppingCartDto findShoppingCartById(Long id);
    ShoppingCartDto removeBranchFromShoppingCart(RemoveFromCartRequest removeFromCartRequest);
    ShoppingCartDto addBranchToShoppingCart(ShoppingCartSetRequest requestShoppingCartSet);
    //ShoppingCart mergeFromTo(ShoppingCart from, ShoppingCart to);
    //ShoppingCart checkAvailabilityByBranches(ShoppingCart sc);
    ShoppingCartDto mergeCartFromTo(Long shoppingCartCookie, Long shoppingCartCustomerId);

}

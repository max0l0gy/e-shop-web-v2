package ru.maxmorev.restful.eshop.rest.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.maxmorev.restful.eshop.rest.Constants;
import ru.maxmorev.restful.eshop.rest.request.RemoveFromCartRequest;
import ru.maxmorev.restful.eshop.rest.request.ShoppingCartSetRequest;
import ru.maxmorev.restful.eshop.rest.response.ShoppingCartDto;
import ru.maxmorev.restful.eshop.services.ShoppingCartService;

import javax.validation.Valid;
import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShoppingCartController {

    public static final String SHOPPING_CART = "shoppingCart/";
    private final ShoppingCartService shoppingCartService;

    @RequestMapping(path = Constants.REST_PUBLIC_URI + "shoppingCart/id/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ShoppingCartDto getShoppingCart(@PathVariable(name = "id", required = true) Long id, Locale locale) throws Exception {
        return shoppingCartService.findShoppingCartById(id);
    }

    @RequestMapping(path = Constants.REST_PUBLIC_URI + SHOPPING_CART, method = RequestMethod.POST)
    @ResponseBody
    public ShoppingCartDto addToShoppingCartSet(@RequestBody @Valid ShoppingCartSetRequest requestShoppingCartSet, Locale locale) {
        log.info("POST:> RequestShoppingCartSet :> {}", requestShoppingCartSet);
        return shoppingCartService.addBranchToShoppingCart(requestShoppingCartSet);
    }

    @RequestMapping(path = Constants.REST_PUBLIC_URI + SHOPPING_CART, method = RequestMethod.DELETE)
    @ResponseBody
    public ShoppingCartDto removeFromShoppingCartSet(@RequestBody @Valid RemoveFromCartRequest removeFromCartRequest, Locale locale) {
        return shoppingCartService.removeBranchFromShoppingCart(removeFromCartRequest);
    }


}

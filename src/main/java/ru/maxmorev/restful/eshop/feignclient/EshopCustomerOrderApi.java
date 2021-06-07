package ru.maxmorev.restful.eshop.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.maxmorev.restful.eshop.domain.CustomerOrder;
import ru.maxmorev.restful.eshop.domain.OrderGrid;
import ru.maxmorev.restful.eshop.domain.ShoppingCart;
import ru.maxmorev.restful.eshop.feignclient.domain.yoomoney.RestResponse;
import ru.maxmorev.restful.eshop.rest.request.CreateOrderRequest;
import ru.maxmorev.restful.eshop.rest.request.OrderIdRequest;
import ru.maxmorev.restful.eshop.rest.request.OrderPaymentConfirmation;
import ru.maxmorev.restful.eshop.rest.request.PaymentInitialRequest;
import ru.maxmorev.restful.eshop.rest.request.RemoveFromCartRequest;
import ru.maxmorev.restful.eshop.rest.request.ShoppingCartSetRequest;
import ru.maxmorev.restful.eshop.rest.response.Message;
import ru.maxmorev.restful.eshop.rest.response.OrderGridDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@FeignClient(name = "eshop-customer-order-api", url = "${external.customerOrderApi.url}")
public interface EshopCustomerOrderApi {

    @RequestMapping(path = "/order/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    CustomerOrder createOrder(@RequestBody CreateOrderRequest createOrderRequest);

    @RequestMapping(path = "/order/expired/{id}", method = RequestMethod.DELETE)
    Message removeExpiredOrder(@PathVariable(name = "id") Long id);

    @RequestMapping(path = "/order/confirmation/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    CustomerOrder confirmOrder(@RequestBody OrderPaymentConfirmation orderPaymentConfirmation);

    @RequestMapping(path = "/order/{id}/customer/{customerId}", method = RequestMethod.GET)
    CustomerOrder findCustomerOrder(@PathVariable(name = "customerId") Long customerId,
                                    @PathVariable(name = "id") Long orderId);

    @RequestMapping(path = "/order/list/customer/{customerId}", method = RequestMethod.GET)
    List<CustomerOrder> customerOrderList(@PathVariable(name = "customerId", required = true) Long customerId);

    @RequestMapping(path = "/order/list/customer/{customerId}/status/{status}", method = RequestMethod.GET)
    List<CustomerOrder> customerOrderListByStatus(@PathVariable(name = "customerId") Long customerId,
                                                  @PathVariable(name = "status") String statusName);

    @RequestMapping(path = "/order/by/customer/cancellation/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    Message customerOrderCancel(@RequestBody OrderIdRequest order);

    @RequestMapping(path = "/order/{id}/{status}", method = RequestMethod.PUT)
    CustomerOrder setOrderStatus(
            @PathVariable(name = "id", required = true) Long id,
            @PathVariable(name = "status", required = true) String status);


    @RequestMapping(path = "/order/list/all", method = RequestMethod.GET)
    OrderGrid adminAllCustomerOrderList(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "rows", required = false) Integer rows,
            @RequestParam(value = "sort", required = false) String sortBy,
            @RequestParam(value = "order", required = false) String order);

    @RequestMapping(path = "/shoppingCart/new/", method = RequestMethod.POST)
    ShoppingCart createEmptySoppingCart();

    @GetMapping(path = "/shoppingCart/id/{id}")
    ShoppingCart getShoppingCart(@PathVariable(name = "id") Long id);

    @PostMapping(path = "/shoppingCart/")
    ShoppingCart addToShoppingCartSet(@RequestBody @Valid ShoppingCartSetRequest requestShoppingCartSet);

    @DeleteMapping(path = "/shoppingCart/")
    ShoppingCart removeFromShoppingCartSet(@RequestBody RemoveFromCartRequest removeFromCartRequest);

    @PutMapping(path = "/shoppingCart/{id}/clear")
    ShoppingCart cleanShoppingCart(@PathVariable(name = "id") Long id);

    @PostMapping(path = "/shoppingCart/merge/from/{fromId}/to/{toId}")
    ShoppingCart mergeCartFromTo(@PathVariable(name = "fromId") Long from,
                                 @PathVariable(name = "toId") Long to);

    @GetMapping(path = "/order/list/expired")
    List<CustomerOrder> findExpiredOrders();

    @PutMapping(path = "/order/payment/initial")
    RestResponse<CustomerOrder> paymentInitial(@RequestBody PaymentInitialRequest paymentRequest);

}
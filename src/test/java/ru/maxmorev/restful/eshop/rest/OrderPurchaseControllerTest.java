package ru.maxmorev.restful.eshop.rest;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.maxmorev.restful.eshop.TestUtils.readFileToString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
@RunWith(SpringRunner.class)
public class OrderPurchaseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void orderConfirmedSuccessfully() {
        mockMvc.perform(
                post("/rest/api/customer/order/confirm/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readFileToString("requests/confirm-payment.json"))
                        .with(user("some-customer@titsonfire.store")
                                .password("customer")
                                .authorities((GrantedAuthority) () -> "CUSTOMER"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.message", is("Success")))
        ;
    }

    @Test
    @SneakyThrows
    public void adminAllCustomerOrderList() {
        mockMvc.perform(
                get("/rest/api/private/order/list/")
                        .with(user("some-customer@titsonfire.store")
                                .password("customer")
                                .authorities((GrantedAuthority) () -> "ADMIN"))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.currentPage", is(1)))
                .andExpect(jsonPath("$.orderData").isArray())
                .andExpect(jsonPath("$.orderData[?(@.id == 25)].dateOfCreation", is(Collections.singletonList("2019-08-23T15:46:25.918+0000"))))

        ;
    }

    @Test
    @SneakyThrows
    public void customerOrderAwaitingForPaymentCancel() {
        mockMvc.perform(
                put("/rest/api/customer/order/cancellation/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFileToString("requests/order-awaiting-for-payment-cancel.json"))
                        .with(user("some-customer@titsonfire.store")
                                .password("customer")
                                .authorities((GrantedAuthority) () -> "CUSTOMER"))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Success")))
        ;
    }

    @Test
    @SneakyThrows
    public void customerOrderPaymentAppruvedCancel() {
        mockMvc.perform(
                put("/rest/api/customer/order/cancellation/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFileToString("requests/order-payment-approved-cancel.json"))
                        .with(user("some-customer@titsonfire.store")
                                .password("customer")
                                .authorities((GrantedAuthority) () -> "CUSTOMER"))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Success")))
        ;
    }
}

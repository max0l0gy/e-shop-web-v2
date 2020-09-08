package ru.maxmorev.restful.eshop.startup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.maxmorev.restful.eshop.config.ManagerConfig;
import ru.maxmorev.restful.eshop.domain.Customer;
import ru.maxmorev.restful.eshop.feignclient.EshopCustomerApi;
import ru.maxmorev.restful.eshop.services.NotificationService;
import ru.maxmorev.restful.eshop.util.ServiceExseptionSuppressor;

import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultManager {
    private final ManagerConfig managerConfig;
    private final EshopCustomerApi eshopCustomerApi;
    private final NotificationService notificationService;

    public Optional<Customer> createDefaultManager() {
        log.info("::: createDefaultManager");
        Optional<Customer> co = ServiceExseptionSuppressor.suppress(() -> eshopCustomerApi.findByEmail(managerConfig.getEmail()));
        if (co.isEmpty()) {
            log.info("::: createDefaultManager ::: create default manager");
            Customer c = Customer.builder()
                    .email(managerConfig.getEmail())
                    .password(managerConfig.getPassword())
                    .fullName(managerConfig.getFullName())
                    .address(managerConfig.getAddress())
                    .country(managerConfig.getCountry())
                    .postcode(managerConfig.getPostcode())
                    .city(managerConfig.getCity())
                    .build();
            Customer created = eshopCustomerApi.createAdmin(c);
            //send verification email
            log.info("::: createDefaultManager ::: send verifyCode to default manager");
            notificationService.emailVerification(
                    created.getEmail(),
                    created.getFullName(),
                    created.getVerifyCode());
            return Optional.of(created);
        }
        return Optional.empty();
    }
}

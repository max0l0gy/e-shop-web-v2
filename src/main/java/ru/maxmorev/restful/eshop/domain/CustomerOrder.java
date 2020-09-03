package ru.maxmorev.restful.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import ru.maxmorev.restful.eshop.annotation.CustomerOrderStatus;
import ru.maxmorev.restful.eshop.annotation.PaymentProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerOrder implements Comparable<CustomerOrder> {

    protected Long id;
    protected int version;
    private Date dateOfCreation;
    private CustomerOrderStatus status;
    private PaymentProvider paymentProvider;
    private String paymentID;
    private Long customerId;
    private List<Purchase> purchases = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerOrder)) return false;
        CustomerOrder that = (CustomerOrder) o;
        return Objects.equals(getId(), that.getId()) && getVersion() == that.getVersion() &&
                getDateOfCreation().equals(that.getDateOfCreation()) &&
                getStatus() == that.getStatus() &&
                Objects.equals(getPaymentProvider(), that.getPaymentProvider()) &&
                Objects.equals(getPaymentID(), that.getPaymentID()) &&
                Objects.equals(getCustomerId(), that.getCustomerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVersion(), getDateOfCreation(), getStatus(), getPaymentProvider(), getPaymentID(), getCustomerId());
    }

    @Override
    public int compareTo(CustomerOrder customerOrder) {
        return Long.compare(getId(), customerOrder.getId());
    }
}

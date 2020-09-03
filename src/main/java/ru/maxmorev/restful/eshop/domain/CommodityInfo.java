package ru.maxmorev.restful.eshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommodityInfo {
    protected Long id;
    protected int version;
    protected String name;
    protected String shortDescription;
    protected String overview;
    protected Date dateOfCreation;
    protected CommodityType type;
    protected List<CommodityImage> images = new ArrayList<>();

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof CommodityInfo)) return false;
        CommodityInfo that = (CommodityInfo) object;
        return Objects.equals(getId(), that.getId())
                && getVersion() == that.getVersion() &&
                getName().equals(that.getName()) &&
                getShortDescription().equals(that.getShortDescription()) &&
                getOverview().equals(that.getOverview()) &&
                getDateOfCreation().equals(that.getDateOfCreation()) &&
                getType().equals(that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getShortDescription(), getOverview(), getType());
    }
}

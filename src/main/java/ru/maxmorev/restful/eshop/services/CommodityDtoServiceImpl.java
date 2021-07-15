package ru.maxmorev.restful.eshop.services;

import com.sun.tools.javadoc.Main;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.maxmorev.restful.eshop.config.MainPageConfig;
import ru.maxmorev.restful.eshop.feignclient.EshopCommodityApi;
import ru.maxmorev.restful.eshop.rest.response.CommodityDto;
import ru.maxmorev.restful.eshop.rest.response.CommodityTypeDto;
import ru.maxmorev.restful.eshop.util.ServiceExseptionSuppressor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CommodityDtoServiceImpl implements CommodityDtoService {

    private final EshopCommodityApi eshopCommodityApi;
    private final MainPageConfig mainPageConfig;

    @Override
    public List<CommodityDto> findWithBranchesAmountGt0() {
        return eshopCommodityApi.findWithBranchesAmountGt0();
    }

    @Override
    public Optional<CommodityDto> findCommodityById(Long id) {
        return ServiceExseptionSuppressor.suppress(() -> eshopCommodityApi.findCommodityById(id));
    }

    @Override
    public List<CommodityTypeDto> findAllTypes() {
        return eshopCommodityApi.findAllTypes();
    }

    @Override
    public List<CommodityDto> findWithBranchesAmountGt0AndType(String typeName) {
        return eshopCommodityApi.findWithBranchesAmountGt0AndType(typeName);
    }

    @Override
    public List<CommodityDto> findWithBranchesAmountEq0AndType(String typeName) {
        return eshopCommodityApi.findWithBranchesAmountEq0AndType(typeName);
    }

    @Override
    public Optional<CommodityTypeDto> findTypeByName(String name) {
        return ServiceExseptionSuppressor.suppress(() -> eshopCommodityApi.findTypeByName(name));
    }

    @Override
    public List<CommodityDto> findNewCommoditiesForMainPage() {
        List<CommodityDto> commodities = new ArrayList<>();
        findAllTypes().forEach(type -> commodities.addAll(findLastFourNewItems(type)));
        return commodities;
    }

    private List<CommodityDto> findLastFourNewItems(CommodityTypeDto type) {
        return findWithBranchesAmountGt0AndType(type.getName())
                .stream()
                .sorted(Collections.reverseOrder())
                .limit(mainPageConfig.newItemsAmount)
                .collect(Collectors.toList());
    }
}

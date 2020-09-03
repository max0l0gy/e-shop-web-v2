package ru.maxmorev.restful.eshop.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.maxmorev.restful.eshop.feignclient.EshopCommodityApi;
import ru.maxmorev.restful.eshop.rest.response.CommodityDto;
import ru.maxmorev.restful.eshop.rest.response.CommodityTypeDto;
import ru.maxmorev.restful.eshop.util.ServiceExseptionSuppressor;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CommodityDtoServiceImpl implements CommodityDtoService {

    private final EshopCommodityApi eshopCommodityApi;

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
    public Optional<CommodityTypeDto> findTypeByName(String name) {
        return ServiceExseptionSuppressor.suppress(() -> eshopCommodityApi.findTypeByName(name));
    }

}

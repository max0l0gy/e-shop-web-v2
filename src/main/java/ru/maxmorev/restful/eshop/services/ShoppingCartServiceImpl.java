package ru.maxmorev.restful.eshop.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.maxmorev.restful.eshop.domain.ShoppingCart;
import ru.maxmorev.restful.eshop.domain.ShoppingCartSet;
import ru.maxmorev.restful.eshop.feignclient.EshopCommodityApi;
import ru.maxmorev.restful.eshop.feignclient.EshopCustomerOrderApi;
import ru.maxmorev.restful.eshop.rest.request.RemoveFromCartRequest;
import ru.maxmorev.restful.eshop.rest.request.ShoppingCartSetRequest;
import ru.maxmorev.restful.eshop.rest.request.CommodityBranchDto;
import ru.maxmorev.restful.eshop.rest.response.CommodityDto;
import ru.maxmorev.restful.eshop.rest.request.CommodityInfoDto;
import ru.maxmorev.restful.eshop.rest.response.CommodityTypeDto;
import ru.maxmorev.restful.eshop.rest.response.ShoppingCartDto;
import ru.maxmorev.restful.eshop.rest.response.ShoppingCartSetDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service("shoppingCartService")
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final EshopCustomerOrderApi eshopCustomerOrderApi;
    private final EshopCommodityApi eshopCommodityApi;

    @Override
    public ShoppingCartDto createEmptyShoppingCart() {
        return convert(eshopCustomerOrderApi.createEmptySoppingCart());
    }

    private CommodityBranchDto convert(CommodityBranchDto cb) {
        return CommodityBranchDto.builder()
                .id(cb.getId())
                .amount(cb.getAmount())
                .currency(cb.getCurrency())
                .price(cb.getPrice())
                .attributes(cb.getAttributes())
                .build();
    }

    private CommodityInfoDto convert(CommodityDto commodity) {
        return CommodityInfoDto
                .builder()
                .id(commodity.getId())
                .dateOfCreation(commodity.getDateOfCreation())
                .name(commodity.getName())
                .overview(commodity.getOverview())
                .shortDescription(commodity.getShortDescription())
                .images(commodity.getImages())
                .type(CommodityTypeDto
                        .builder()
                        .description(commodity.getType().getDescription())
                        .id(commodity.getType().getId())
                        .name(commodity.getType().getName())
                        .build())
                .build();
    }

    ShoppingCartDto convert(ShoppingCart sc) {
        List<ShoppingCartSetDto> items = new ArrayList<>();
        sc.getShoppingSet().forEach(set -> {
            CommodityBranchDto cb = eshopCommodityApi.getCommodityBranch(set.getId().getBranchId());
            CommodityDto commodity = eshopCommodityApi.findCommodityById(cb.getCommodityId());
            items.add(ShoppingCartSetDto.builder()
                    .amount(set.getAmount())
                    .branch(convert(cb))
                    .commodityInfo(convert(commodity))
                    .build());
        });
        return ShoppingCartDto.builder()
                .id(sc.getId())
                .shoppingSet(items)
                .build();
    }

    @Override
    public ShoppingCartDto findShoppingCartById(Long id) {
        return convert(eshopCustomerOrderApi.getShoppingCart(id));
    }


    int getCurrentAmountItemsInShoppingCart(ShoppingCart sc, Long branchId){
        return sc.getShoppingSet().stream()
                .filter(scs -> scs.getId().getBranchId().equals(branchId))
                .findFirst()
                .map(ShoppingCartSet::getAmount)
                .orElse(0);
    }

    @Override
    public ShoppingCartDto removeBranchFromShoppingCart(RemoveFromCartRequest removeFromCartRequest) {
        CommodityBranchDto cb = eshopCommodityApi.getCommodityBranch(removeFromCartRequest.getBranchId());
        ShoppingCart sc = eshopCustomerOrderApi.getShoppingCart(removeFromCartRequest.getShoppingCartId());
        int currentAmountOfItemsInScByBranch = getCurrentAmountItemsInShoppingCart(sc, cb.getId());
        int possibleAmountOfItems = currentAmountOfItemsInScByBranch - removeFromCartRequest.getAmount();
        int availableAmount = cb.getAmount();
        if (availableAmount >= possibleAmountOfItems)
            return convert(eshopCustomerOrderApi.removeFromShoppingCartSet(removeFromCartRequest));
        else
            return convert(sc);
    }

    @Override
    public ShoppingCartDto addBranchToShoppingCart(ShoppingCartSetRequest requestShoppingCartSet) {
        CommodityBranchDto cb = eshopCommodityApi.getCommodityBranch(requestShoppingCartSet.getBranchId());
        ShoppingCart sc = eshopCustomerOrderApi.getShoppingCart(requestShoppingCartSet.getShoppingCartId());
        int currentAmountOfItemsInScByBranch = getCurrentAmountItemsInShoppingCart(sc, cb.getId());
        int possibleAmountOfItems = currentAmountOfItemsInScByBranch + requestShoppingCartSet.getPurchaseInfo().getAmount();
        int availableAmount = cb.getAmount();
        if (availableAmount >= possibleAmountOfItems)
            return convert(eshopCustomerOrderApi.addToShoppingCartSet(requestShoppingCartSet));
        else
            return convert(sc);
    }

    @Override
    public ShoppingCartDto mergeCartFromTo(Long from, Long to) {
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        if (from.equals(to)) {
            throw new IllegalArgumentException("Shopping carts a same");
        }
        log.info("-----------mergeCartFromTo {},{}", from, to);
        return convert(eshopCustomerOrderApi.mergeCartFromTo(from, to));
    }

}

package ru.maxmorev.restful.eshop.rest.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.maxmorev.restful.eshop.feignclient.EshopCommodityApi;
import ru.maxmorev.restful.eshop.rest.Constants;
import ru.maxmorev.restful.eshop.rest.request.RequestCommodity;
import ru.maxmorev.restful.eshop.rest.request.CommodityBranchDto;
import ru.maxmorev.restful.eshop.rest.response.CommodityDto;
import ru.maxmorev.restful.eshop.rest.response.CommodityGridDto;
import ru.maxmorev.restful.eshop.rest.request.CommodityInfoDto;
import ru.maxmorev.restful.eshop.rest.response.Message;

import javax.validation.Valid;
import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommodityController {

    private final EshopCommodityApi commodityService;
    private final MessageSource messageSource;

    @PostMapping(path = Constants.REST_PRIVATE_URI + "commodity/")
    @ResponseBody
    public Message createCommodityFromRequset(@RequestBody @Valid RequestCommodity requestCommodity, Locale locale) {
        log.info("POST -> createCommodityFromRequset");
        return commodityService.createCommodityFromRequset(requestCommodity);
    }

    @PutMapping(path = Constants.REST_PRIVATE_URI + "commodity")
    @ResponseBody
    public Message updateCommodity(@RequestBody @Valid CommodityInfoDto cmInfo) {
        log.info("PUT -> updateCommodity");
        return commodityService.updateCommodity(cmInfo);
    }

    @PutMapping(path = Constants.REST_PRIVATE_URI + "branch")
    @ResponseBody
    public Message updateBranch(@RequestBody @Valid CommodityBranchDto branchDto) {
        log.info("PUT -> updateBranch");
        return commodityService.updateBranch(branchDto);
    }

    @GetMapping(path = Constants.REST_PUBLIC_URI + "commodity/id/{id}/any/branches")
    @ResponseBody
    public CommodityDto getCommodityAnyBranches(@PathVariable(name = "id", required = true) Long id) {
        return commodityService.getCommodityAnyBranches(id);
    }

    @SneakyThrows
    @GetMapping(path = Constants.REST_PUBLIC_URI + "commodities/")
    @ResponseBody
    public CommodityGridDto listCommodity(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "rows", required = false) Integer rows,
            @RequestParam(value = "sort", required = false) String sortBy,
            @RequestParam(value = "order", required = false) String order
    ) {
        return commodityService.getCommodityGrid(page, rows, sortBy, order);
    }

    @GetMapping(path = Constants.REST_PUBLIC_URI + "commodityBranch/{id}")
    @ResponseBody
    public CommodityBranchDto getCommodityBranch(@PathVariable(name = "id", required = true) Long branchId) {
        return commodityService.getCommodityBranch(branchId);
    }

}

package ru.maxmorev.restful.eshop.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.maxmorev.restful.eshop.domain.CommodityAttribute;
import ru.maxmorev.restful.eshop.domain.CommodityType;
import ru.maxmorev.restful.eshop.rest.request.RequestAttributeValue;
import ru.maxmorev.restful.eshop.rest.request.RequestCommodity;
import ru.maxmorev.restful.eshop.rest.request.CommodityBranchDto;
import ru.maxmorev.restful.eshop.rest.response.CommodityDto;
import ru.maxmorev.restful.eshop.rest.response.CommodityGridDto;
import ru.maxmorev.restful.eshop.rest.request.CommodityInfoDto;
import ru.maxmorev.restful.eshop.rest.response.CommodityTypeDto;
import ru.maxmorev.restful.eshop.rest.response.Message;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "eshop-commodity-api", url = "${external.commodityApi.url}")
public interface EshopCommodityApi {

    @RequestMapping(path = "/api/v1/branches/amount/gt/0", method = RequestMethod.GET)
    List<CommodityDto> findWithBranchesAmountGt0();

    @RequestMapping(path = "/api/v1/branches/amount/gt/0/type/name/{typeName}", method = RequestMethod.GET)
    List<CommodityDto> findWithBranchesAmountGt0AndType(@PathVariable(name = "typeName") String typeName);

    @GetMapping(path = "/api/v1/branches/amount/eq/0/type/name/{typeName}")
    List<CommodityDto> findWithBranchesAmountEq0AndType(@PathVariable(name = "typeName") String typeName);

    @RequestMapping(path = "/api/v1/commodity/{id}", method = RequestMethod.GET)
    CommodityDto findCommodityById(@PathVariable(name = "id") Long id);

    @RequestMapping(path = "/api/v1/type/list", method = RequestMethod.GET)
    List<CommodityTypeDto> findAllTypes();

    @RequestMapping(path = "/api/v1/type/name/{name}", method = RequestMethod.GET)
    CommodityTypeDto findTypeByName(@PathVariable(name = "name") String name);

    @RequestMapping(path = "/attribute/", method = RequestMethod.POST)
    Message createAttribute(@RequestBody @Valid RequestAttributeValue property);

    @RequestMapping(path = "/attributes/{typeId}", method = RequestMethod.GET)
    List<CommodityAttribute> getAttributes(@PathVariable(name = "typeId") Long typeId);

    @RequestMapping(path = "/attribute/value/dataTypes/", method = RequestMethod.GET)
    List<String> getAvailebleAttributeDataTypes();

    @RequestMapping(path = "/attribute/value/{id}", method = RequestMethod.DELETE)
    Message deleteAttributeValue(@PathVariable(name = "id") Long valueId);

    @RequestMapping(path = "/types", method = RequestMethod.GET)
    List<CommodityType> getCommodityTypes();

    @RequestMapping(path = "/type", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    Message createCommodityType(@RequestBody CommodityType type);

    @RequestMapping(path = "/type", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    CommodityType updateCommodityType(@RequestBody CommodityType type);


    @RequestMapping(path = "/type/{id}", method = RequestMethod.DELETE)
    Message deleteCommodityType(@PathVariable(name = "id", required = true) Long id);

    @RequestMapping(path = "/type/{id}", method = RequestMethod.GET)
    CommodityType getCommodityType(@PathVariable(name = "id", required = true) Long id);


    @RequestMapping(path = "/api/manager/commodity/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    Message createCommodityFromRequset(@RequestBody RequestCommodity requestCommodity);

    @RequestMapping(path = "/api/manager/commodity", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    Message updateCommodity(@RequestBody CommodityInfoDto cmInfo);

    @RequestMapping(path = "/api/manager/commodity/id/{id}/any/branches", method = RequestMethod.GET)
    CommodityDto getCommodityAnyBranches(@PathVariable(name = "id", required = true) Long id);

    @RequestMapping(path = "/api/manager/commodity/list", method = RequestMethod.GET)
    CommodityGridDto getCommodityGrid(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "rows", required = false) Integer rows,
            @RequestParam(value = "sort", required = false) String sortBy,
            @RequestParam(value = "order", required = false) String order
    );

    @RequestMapping(path = "/api/manager/branch/{id}", method = RequestMethod.GET)
    CommodityBranchDto getCommodityBranch(@PathVariable(name = "id", required = true) Long branchId);

    @RequestMapping(path = "/api/manager/branch/{id}/amount/inc/{amount}", method = RequestMethod.PUT)
    CommodityBranchDto addAmountToBranch(@PathVariable(name = "id", required = true) long branchId,
                                      @PathVariable(name = "amount", required = true) int amount);


    @RequestMapping(path = "/api/manager/branch", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    Message updateBranch(@RequestBody CommodityBranchDto branchDto);
}

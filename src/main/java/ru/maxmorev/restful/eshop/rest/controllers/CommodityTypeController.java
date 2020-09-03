package ru.maxmorev.restful.eshop.rest.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.maxmorev.restful.eshop.domain.CommodityType;
import ru.maxmorev.restful.eshop.feignclient.EshopCommodityApi;
import ru.maxmorev.restful.eshop.rest.Constants;
import ru.maxmorev.restful.eshop.rest.response.Message;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommodityTypeController {

    private final EshopCommodityApi commodityService;
    private final MessageSource messageSource;

    @RequestMapping(path = Constants.REST_PUBLIC_URI+"types/", method = RequestMethod.GET)
    @ResponseBody
    public List<CommodityType> getCommodityTypes() throws Exception{
        return commodityService.getCommodityTypes();
    }

    @RequestMapping(path = Constants.REST_PRIVATE_URI + "type/", method = RequestMethod.POST)
    @ResponseBody
    public Message createCommodityType(@RequestBody @Valid CommodityType type, Locale locale){
        log.info("type : {} ", type);
        return commodityService.createCommodityType(type);
    }

    @RequestMapping(path = Constants.REST_PRIVATE_URI + "type/", method = RequestMethod.PUT)
    @ResponseBody
    public CommodityType updateCommodityType(@RequestBody @Valid CommodityType type, Locale locale ){
        log.info("updateCommodityType {}", type);
        return commodityService.updateCommodityType(type);
    }


    @RequestMapping(path = Constants.REST_PRIVATE_URI + "type/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Message deleteCommodityType(@PathVariable(name = "id", required = true) Long id, Locale locale){
        commodityService.deleteCommodityType(id);
        return new Message(Message.SUCCES, messageSource.getMessage("message_success", new Object[]{}, locale));
    }

    @RequestMapping(path = Constants.REST_PUBLIC_URI+"type/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommodityType getCommodityType(@PathVariable(name = "id", required = true) Long id, Locale locale){
        return commodityService.getCommodityType(id);
    }

}

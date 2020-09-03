package ru.maxmorev.restful.eshop.rest.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.maxmorev.restful.eshop.domain.CommodityAttribute;
import ru.maxmorev.restful.eshop.feignclient.EshopCommodityApi;
import ru.maxmorev.restful.eshop.rest.Constants;
import ru.maxmorev.restful.eshop.rest.request.RequestAttributeValue;
import ru.maxmorev.restful.eshop.rest.response.Message;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@AllArgsConstructor
public class CommodityAttributeController {

    public static final String ATTRIBUTE_DATA_TYPES = "/attribute/value/dataTypes/";

    private final EshopCommodityApi commodityService;
    private final MessageSource messageSource;

    @RequestMapping(path = Constants.REST_PRIVATE_URI + "attribute/", method = RequestMethod.POST)
    @ResponseBody
    public Message createAttribute(@RequestBody @Valid RequestAttributeValue property, Locale locale) {
        //to prevent duplicated properties
        commodityService.createAttribute(property);
        return new Message(Message.SUCCES, messageSource.getMessage("message_success", new Object[]{}, locale));
    }

    @RequestMapping(path = Constants.REST_PUBLIC_URI + "attributes/{typeId}", method = RequestMethod.GET)
    @ResponseBody
    public List<CommodityAttribute> getAttributes(@PathVariable(name = "typeId", required = true) Long typeId) {
        return commodityService.getAttributes(typeId);
    }

    @RequestMapping(path = Constants.REST_PUBLIC_URI + ATTRIBUTE_DATA_TYPES, method = RequestMethod.GET)
    @ResponseBody
    public List<String> getAvailebleAttributeDataTypes() {
        return commodityService.getAvailebleAttributeDataTypes();
    }

    @RequestMapping(path = Constants.REST_PRIVATE_URI + "attributeValue/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Message deleteAttributeValue(@PathVariable(name = "id", required = true) Long valueId, Locale locale) {
        commodityService.deleteAttributeValue(valueId);
        return new Message(Message.SUCCES, messageSource.getMessage("message_success", new Object[]{}, locale));
    }


}

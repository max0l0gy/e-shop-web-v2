
package ru.maxmorev.restful.eshop.mapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.maxmorev.restful.eshop.feignclient.domain.ecb.CurrencyRateContainer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Objects;

@Slf4j
@Component
public class EcbCurrencyRatesMapper {

    @SneakyThrows
    public CurrencyRateContainer parseXml(String xml) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputStream xmlISteam = new ByteArrayInputStream(xml.getBytes());
        Document doc = db.parse(xmlISteam);
        doc.getDocumentElement().normalize();
        NodeList nodes = doc.getElementsByTagName("Cube");
        CurrencyRateContainer currencyRateContainer = new CurrencyRateContainer();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (!Objects.isNull(node.getAttributes().getNamedItem("currency"))) {
                currencyRateContainer.addRate(new CurrencyRateContainer.CurrencyRate()
                        .setCurrency(node.getAttributes().getNamedItem("currency").getNodeValue())
                        .setRate(BigDecimal.valueOf(Double.parseDouble(node.getAttributes().getNamedItem("rate").getNodeValue())))
                );
            }
            if (!Objects.isNull(node.getAttributes().getNamedItem("time"))) {
                currencyRateContainer.setTime(node.getAttributes().getNamedItem("time").getNodeValue());
            }
        }
        log.info("gesmes:subject: {}", doc.getElementsByTagName("gesmes:subject").item(0).getTextContent());
        log.info("gesmes:name: {}", doc.getElementsByTagName("gesmes:name").item(0).getTextContent());
        currencyRateContainer.setSubject(doc.getElementsByTagName("gesmes:subject").item(0).getTextContent());
        currencyRateContainer.setSource(doc.getElementsByTagName("gesmes:name").item(0).getTextContent());
        return currencyRateContainer;
    }
}

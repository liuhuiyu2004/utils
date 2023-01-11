package com.liuhuiyu.web.service;

import org.apache.axis.message.MessageElement;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-12-21 15:56
 */
public class WebServiceUtil {
    public static Stream<Map<String, Object>> dataSetXmlToStream(MessageElement[] any) {
        if (any == null || any.length <= 1 || any[1].getChildren() == null) {
            return new ArrayList<Map<String, Object>>(0).stream();
        }
        SAXBuilder sb = new SAXBuilder();
        return any[1].getChildren().stream().flatMap(child -> {
            String str = child.toString();
            InputStream in = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
            Document doc;
            try {
                doc = sb.build(in);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            Element root = doc.getRootElement(); // 得到根元素
            return root.getChildren().stream().map((elementRowObj) -> {
                Element elementRow = (Element) elementRowObj;
                Map<String, Object> tmp = new HashMap<>();
                elementRow.getChildren().forEach(elementObj -> {
                    Element element = (Element) elementObj;
                    tmp.put(element.getName(), element.getText());
                });
                return tmp;
            });
        });
    }
}

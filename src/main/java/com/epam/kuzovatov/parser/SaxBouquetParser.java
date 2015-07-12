package com.epam.kuzovatov.parser;

import com.epam.kuzovatov.entity.Bouquet;
import com.epam.kuzovatov.entity.Flower;
import com.epam.kuzovatov.util.ParserConfigurator;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Function;

public class SaxBouquetParser implements ComponentParser {
    private static Logger log = Logger.getLogger(SaxBouquetParser.class);
    private Bouquet bouquet;

    private static class BouquetHandler extends DefaultHandler {
        private StringBuilder sb = new StringBuilder();
        private static Logger log = Logger.getLogger(BouquetHandler.class);
        private List<Flower> bunch;
        private Stack<String> elementStack;
        private Stack<Object> objectStack;
        private Map<String,Method> fieldToMethodMap;
        private Map<String, Map<Method,Function>> methodFunctionAdapterMap;
        private ParserConfigurator properties = new ParserConfigurator();
        private static final String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        private Bouquet bouquet;

        public BouquetHandler(){
            this.elementStack = new Stack<>();
            this.objectStack  = new Stack<>();
        }

        public static String getLanguage() {
            return language;
        }

        @Override
        public void startDocument(){
            log.info("Xml document parse started!");
            bunch = new ArrayList<>();
            bouquet = new Bouquet();
            Flower flower = new Flower();
            methodFunctionAdapterMap=properties.getMethodFunctionAdapterMap(properties.getFields(flower.getClass()), properties.getMethods(flower.getClass()));
            fieldToMethodMap = properties.getFieldToMethodMap(properties.getFields(flower.getClass()), properties.getMethods(Flower.class));
        }

        @Override
        public void startElement (String uri, String localName, String qName, Attributes attributes)throws SAXException{
            this.elementStack.push(qName);
            if("flower".equals(qName)) {
                Flower flower = new Flower();
                this.objectStack.push(flower);
                this.bunch.add(flower);
                this.bouquet.add(flower);
            }
        }

        @Override
        public void characters(char ch[], int start, int length) throws SAXException {
            String tagContent = new String(ch, start, length).trim();
            if (currentElementParent().equals("flower"))
                sb.append(tagContent);
        }

        @Override
        public void endElement(String uri, String localName, String qName)throws SAXException{
            for (Map.Entry entry: fieldToMethodMap.entrySet()) {
                String key = entry.getKey().toString();
                if(key.equals(currentElement())&& currentElementParent().equals("flower")) {
                    Flower flower = (Flower) this.objectStack.peek();
                    Method method = fieldToMethodMap.get(currentElement());
                    try {
                        method.invoke(flower, methodFunctionAdapterMap.get(currentElement()).get(method).apply(sb.toString()));
                    } catch (IllegalAccessException|InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
            sb.setLength(0);
            this.elementStack.pop();
        }

        private String currentElement() {
            return this.elementStack.peek();
        }

        private String currentElementParent() {
            if(this.elementStack.size() < 2) return null;
            return this.elementStack.get(this.elementStack.size() - 2);
        }

        @Override
        public void endDocument()throws SAXException{
            log.info("Xml document parse finished!");
        }

        public List<Flower> getBunch() {
            return bunch;
        }
    }

    @Override
    public Bouquet parse(InputStream inputStream) {
        URL xsdUrl = getClass().getClassLoader().getResource("Bouquet.xsd");
        SchemaFactory schemaFactory = SchemaFactory.newInstance(BouquetHandler.getLanguage());
        try {
            Schema schema = schemaFactory.newSchema(xsdUrl);
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            spf.setSchema(schema);
            SAXParser parser = null;
            BouquetHandler handler   = new BouquetHandler();
            try {
                parser = spf.newSAXParser();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            parser.parse(inputStream, handler);
            for(Flower flower : handler.getBunch()){
                log.info(flower.toString());
            }
            log.info(inputStream + " is valid.");
        } catch (SAXException | IOException e) {
            log.error(inputStream + " validation failed!", e);
        }
        return bouquet;
    }

    @Override
    public Bouquet parse(String filePath) {
        URL xsdUrl = getClass().getClassLoader().getResource("Bouquet.xsd");
        URL xmlUrl = getClass().getClassLoader().getResource("Bouquet.xml");
        InputStream xmlIn = BouquetHandler.class.getClassLoader().getResourceAsStream(filePath);
        SchemaFactory schemaFactory = SchemaFactory.newInstance(BouquetHandler.getLanguage());
        try {
            Schema schema = schemaFactory.newSchema(xsdUrl);
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            spf.setSchema(schema);
            SAXParser parser = null;
            BouquetHandler handler   = new BouquetHandler();
            try {
                parser = spf.newSAXParser();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            parser.parse(xmlIn, handler);
            for(Flower flower : handler.getBunch()){
                log.info(flower.toString());
            }
            log.info(xmlUrl + " file is valid");
        } catch (SAXException | IOException e) {
            log.error(xmlUrl + " validation failed!", e);
        }
        return bouquet;
    }

    public Bouquet getBouquet() {
        return bouquet;
    }
}

package com.epam.kuzovatov.parser;

import com.epam.kuzovatov.entity.Bouquet;
import com.epam.kuzovatov.entity.Flower;
import com.epam.kuzovatov.util.BouquetMethodMapCompleter;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BouquetSaxParser implements XmlParser {
    private static Logger log = Logger.getLogger(BouquetSaxParser.class);
    private Bouquet bouquet;

    private static class BouquetHandler extends DefaultHandler {
        private StringBuilder sb = new StringBuilder();
        private static Logger log = Logger.getLogger(BouquetHandler.class);
        private List<Flower> bunch;
        private Stack<String> elementStack;
        private Stack<Object> objectStack;
        private Map<String,Method> fieldToMethodMap = new HashMap<>();
        private BouquetMethodMapCompleter properties = new BouquetMethodMapCompleter();
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
            fieldToMethodMap = properties.getFieldToMethodMap(properties.getFields(flower.getClass()),properties.getMethods());
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
                if("cutDate".equals(currentElement()) && "flower".equals(currentElementParent())){
                    Flower flower   = (Flower) this.objectStack.peek();
                    DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                    try {
                        flower.setCutDate(format.parse(sb.toString()));
                    } catch (ParseException e) {
                        log.error("Error occurred at date parse: "+e);
                    }
                }
                else if(key.equals(currentElement())&& currentElementParent().equals("flower")) {
                    Flower flower = (Flower) this.objectStack.peek();
                    Method method = fieldToMethodMap.get(currentElement());
                    try {
                        method.invoke(flower, sb.toString());
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

        public Bouquet getBouquet() {
            return bouquet;
        }

        public List<Flower> getBunch() {
            return bunch;
        }
    }

    @Override
    public Bouquet parse(InputStream inputStream) {
        URL xsdUrl = BouquetHandler.class.getClassLoader().getResource("Bouquet.xsd");
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
                System.out.println(flower.toString());
            }
            log.info(inputStream + " file is valid");
        } catch (SAXException | IOException e) {
            log.error(inputStream + " validation failed!", e);
        }
        return bouquet;
    }

    @Override
    public Bouquet parse(String filePath) {
        URL xsdUrl = BouquetHandler.class.getClassLoader().getResource("Bouquet.xsd");
        URL xmlUrl = BouquetHandler.class.getClassLoader().getResource("Bouquet.xml");
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
                System.out.println(flower.toString());
            }
            log.info(xmlUrl + " file is valid");
        } catch (SAXException | IOException e) {
            log.error(xmlUrl + " validation failed!", e);
        }
        return bouquet;
    }
}

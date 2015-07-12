package com.epam.kuzovatov.parser;

import com.epam.kuzovatov.entity.Bouquet;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;

public class StaxBouquetParser implements ComponentParser {

    @Override
    public Bouquet parse(InputStream inputStream) {
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader =
                    factory.createXMLEventReader(inputStream);

            while(eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();
                switch(event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String startElementName = startElement.getName().getLocalPart();
                        System.out.println(startElementName);
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        System.out.println(characters.getData());
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        String endElementName = endElement.getName().getLocalPart();
                        System.out.println(endElementName);


//                        if (qName.equalsIgnoreCase("student")) {
//                            System.out.println("Start Element : student");
//                            Iterator<Attribute> attributes = startElement.getAttributes();
//                            String rollNo = attributes.next().getValue();
//                            System.out.println("Roll No : " + rollNo);
//                        } else if (qName.equalsIgnoreCase("firstname")) {
//                            bFirstName = true;
//                        } else if (qName.equalsIgnoreCase("lastname")) {
//                            bLastName = true;
//                        } else if (qName.equalsIgnoreCase("nickname")) {
//                            bNickName = true;
//                        }
//                        else if (qName.equalsIgnoreCase("marks")) {
//                            bMarks = true;
//                        }
//                        break;
//                    case XMLStreamConstants.CHARACTERS:
//                        Characters characters = event.asCharacters();
//                        if(bFirstName){
//                            System.out.println("First Name: "
//                                    + characters.getData());
//                            bFirstName = false;
//                        }
//                        if(bLastName){
//                            System.out.println("Last Name: "
//                                    + characters.getData());
//                            bLastName = false;
//                        }
//                        if(bNickName){
//                            System.out.println("Nick Name: "
//                                    + characters.getData());
//                            bNickName = false;
//                        }
//                        if(bMarks){
//                            System.out.println("Marks: "
//                                    + characters.getData());
//                            bMarks = false;
//                        }
//                        break;
//                    case  XMLStreamConstants.END_ELEMENT:
//                        EndElement endElement = event.asEndElement();
//                        if(endElement.getName().getLocalPart().equalsIgnoreCase("student")){
//                            System.out.println("End Element : student");
//                            System.out.println();
//                        }
//                        break;

                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Bouquet parse(String filePath) {
        return null;
    }
}

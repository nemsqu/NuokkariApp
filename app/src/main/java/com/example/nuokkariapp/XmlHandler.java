package com.example.nuokkariapp;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlHandler {


    public static final String xmlFilePath = "data/data/com.example.nuokkariapp/events.xml";
    private static XmlHandler xmlHandler = new XmlHandler();

    private XmlHandler(){

    }

    public static XmlHandler getInstance(){
        return xmlHandler;
    }

    public void writeToFile(ArrayList<Event> futureEventsList, ArrayList<Event> pastEventsList) {

        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            // root element
            Element root = document.createElement("Events");
            document.appendChild(root);
            // future events element
            Element futureEvents = document.createElement("FutureEvents");
            root.appendChild(futureEvents);
            for(Event e : futureEventsList) {
                Element event = document.createElement("Event");
                futureEvents.appendChild(event);
                // set an attribute to event element
                Attr attr = document.createAttribute("id");
                attr.setValue(Integer.toString(e.getID()));
                event.setAttributeNode(attr);
                // name element
                Element name = document.createElement("name");
                name.appendChild(document.createTextNode(e.getName()));
                event.appendChild(name);
                // location element
                Element location = document.createElement("location");
                location.appendChild(document.createTextNode(e.getLocation()));
                event.appendChild(location);
                // date element
                Element date = document.createElement("date");
                date.appendChild(document.createTextNode(e.getDate()));
                event.appendChild(date);
                // start time element
                Element startTime = document.createElement("startTime");
                startTime.appendChild(document.createTextNode(e.getStart()));
                event.appendChild(startTime);
                // end time element
                Element endTime = document.createElement("endTime");
                endTime.appendChild(document.createTextNode(e.getEnd()));
                event.appendChild(endTime);
                // age limit element
                Element ageLimit = document.createElement("ageLimit");
                ageLimit.appendChild(document.createTextNode(e.getAge()));
                event.appendChild(ageLimit);
                //visitor limit element
                Element visitorLimit = document.createElement("ageLimit");
                visitorLimit.appendChild(document.createTextNode(Integer.toString(e.getVisitorLimit())));
                event.appendChild(visitorLimit);
                // description elements
                Element description = document.createElement("description");
                description.appendChild(document.createTextNode(e.getDescription()));
                event.appendChild(description);
            }
            // past events element
            Element pastEvents = document.createElement("PastEvents");
            root.appendChild(pastEvents);
            for(Event e : pastEventsList) {
                Element event = document.createElement("Event");
                pastEvents.appendChild(event);
                // set an attribute to event element
                Attr attr = document.createAttribute("id");
                attr.setValue(Integer.toString(e.getID()));
                event.setAttributeNode(attr);
                // name element
                Element name = document.createElement("name");
                name.appendChild(document.createTextNode(e.getName()));
                event.appendChild(name);
                // location element
                Element location = document.createElement("location");
                location.appendChild(document.createTextNode(e.getLocation()));
                event.appendChild(location);
                // date element
                Element date = document.createElement("date");
                date.appendChild(document.createTextNode(e.getDate()));
                event.appendChild(date);
                // start time element
                Element startTime = document.createElement("startTime");
                startTime.appendChild(document.createTextNode(e.getStart()));
                event.appendChild(startTime);
                // end time element
                Element endTime = document.createElement("endTime");
                endTime.appendChild(document.createTextNode(e.getEnd()));
                event.appendChild(endTime);
                // age limit element
                Element ageLimit = document.createElement("ageLimit");
                ageLimit.appendChild(document.createTextNode(e.getAge()));
                event.appendChild(ageLimit);
                //visitor limit element
                Element visitorLimit = document.createElement("ageLimit");
                visitorLimit.appendChild(document.createTextNode(Integer.toString(e.getVisitorLimit())));
                event.appendChild(visitorLimit);
                // description elements
                Element description = document.createElement("description");
                description.appendChild(document.createTextNode(e.getDescription()));
                event.appendChild(description);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}


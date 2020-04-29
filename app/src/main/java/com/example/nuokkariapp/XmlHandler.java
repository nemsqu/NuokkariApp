package com.example.nuokkariapp;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlHandler {


    public static final String xmlFilePath = "/data/data/com.example.nuokkariapp/files/events.xml";

    public XmlHandler(ArrayList<Event> list) {

        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            // root element
            Element root = document.createElement("Events");
            document.appendChild(root);
            // employee element
            int i = 1;
            for(Event e : list) {
                Element event = document.createElement("Event");
                root.appendChild(event);
                // set an attribute to event element
                Attr attr = document.createAttribute("id");
                attr.setValue(Integer.toString(i));
                event.setAttributeNode(attr);
                //you can also use staff.setAttribute("id", "1") for this
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
                Element age = document.createElement("age");
                age.appendChild(document.createTextNode(e.getAge()));
                event.appendChild(age);
                // description elements
                Element description = document.createElement("description");
                description.appendChild(document.createTextNode(e.getDescription()));
                event.appendChild(description);
                i++;
            }
            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            // If you use
            // StreamResult result = new StreamResult(System.out);
            // the output will be pushed to the standard output ...
            // You can use that for debugging

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}


package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;

public class XMLReader extends Handler {

    @Override
    public boolean checkType(String filePath) {
        return filePath.endsWith(".xml");

    }

    @Override
    public HashMap<String, Reactor> loadReactors(String filePath) {
        HashMap<String, Reactor> reactors = new HashMap<>();
        File file = new File(filePath);

        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList reactorList = doc.getElementsByTagName("root").item(0).getChildNodes();
            for (int i = 0; i < reactorList.getLength(); i++) {
                Node reactorNode = reactorList.item(i);
                if (reactorNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element reactorElement = (Element) reactorNode;
                    double burnup = Double.parseDouble(reactorElement.getElementsByTagName("burnup").item(0).getTextContent());
                    String classe = reactorElement.getElementsByTagName("class").item(0).getTextContent();
                    double electricalCapacity = Double.parseDouble(reactorElement.getElementsByTagName("electrical_capacity").item(0).getTextContent());
                    double firstLoad = Double.parseDouble(reactorElement.getElementsByTagName("first_load").item(0).getTextContent());
                    double kpd = Double.parseDouble(reactorElement.getElementsByTagName("kpd").item(0).getTextContent());
                    double lifetime = Double.parseDouble(reactorElement.getElementsByTagName("life_time").item(0).getTextContent());
                    double terminalCapacity = Double.parseDouble(reactorElement.getElementsByTagName("termal_capacity").item(0).getTextContent());
                    Reactor reactor = new Reactor(burnup, classe, electricalCapacity, firstLoad, kpd, lifetime, terminalCapacity, "xml");
                    reactors.put(reactor.reactorClass, reactor);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ошибка считывания: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }


        return reactors;
    }
}

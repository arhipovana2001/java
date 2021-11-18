import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DOMxmlReader {

    public List<Valute> readFile(String filepath) {

        File xmlFile = new File(filepath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        List<Valute> valList = new ArrayList<Valute>();
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("Valute");

            for (int i = 0; i < nodeList.getLength(); i++) {
                valList.add(getValute(nodeList.item(i)));
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return valList;
    }

    public static List<String> readValutesFile(String filepath) {

        File xmlFile = new File(filepath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        List<String> valList = new ArrayList<String>();
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("Item");

            for (int i = 0; i < nodeList.getLength(); i++) {

                valList.add(getValutes(nodeList.item(i)));
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return valList;

    }

    private static String getValutes(Node node) {
        String val = "";
        String val1 = "";
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            val = getTagValue("ISO_Char_Code", element);
            val1 = getTagValue("ISO_Num_Code", element);
        }
        return val;
    }

    private static Valute getValute(Node node) {
        Valute val = new Valute();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            val.setNumCode(getTagValue("NumCode", element));
            val.setCharCode(getTagValue("CharCode", element));
            val.setNominal(Integer.parseInt(getTagValue("Nominal", element)));
            val.setName(getTagValue("Name", element));
            val.setValue(Double.parseDouble(getTagValue("Value", element).replace(",", ".")));
        }

        return val;
    }

    private static String getTagValue(String tag, Element element) throws NullPointerException {

        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        if (node != null){
            return node.getNodeValue();
        }
        return "";

    }

}

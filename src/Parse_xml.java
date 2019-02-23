/*
    Этот файл по факту не нужен. Это вариант парсинга xml человеческим способом, а не через Scanner.
*/
import Objects.Gryadka;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;

public class Parse_xml {
    public static ArrayDeque<Gryadka> gryadkas = new ArrayDeque<>();

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        XMLHandler handler = new XMLHandler();
        parser.parse(new File("src/input.xml"), handler);

        for (Gryadka gryadka : gryadkas)
            System.out.println(String.format("Грядка %s, количество плодов: %s", gryadka.getName(), gryadka.getCount()));
    }

    private static class XMLHandler extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("gryadka")) {
                String type = attributes.getValue("name");
                int count = Integer.parseInt(attributes.getValue("count"));
                gryadkas.addFirst(new Gryadka(count, type));
            }
        }
    }
}

package hiberspring.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class XmlParserImpl implements XmlParser {
    @Override
    public <O> O parseXml(Class<O> objectClass, String filePath) throws JAXBException, FileNotFoundException {
        JAXBContext jaxbContext = JAXBContext.newInstance(objectClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return (O) unmarshaller.unmarshal(new FileReader(filePath));
    }
}

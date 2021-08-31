package exam.util;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface XmlParser {

    <E> E fromFile(String path, Class<E> eClass) throws JAXBException, FileNotFoundException;

}

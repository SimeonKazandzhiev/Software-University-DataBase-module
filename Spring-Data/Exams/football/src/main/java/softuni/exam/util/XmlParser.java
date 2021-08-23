package softuni.exam.util;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface XmlParser {

    <E> E  fromFile(String filePath,Class<E> entity) throws JAXBException, FileNotFoundException;

}

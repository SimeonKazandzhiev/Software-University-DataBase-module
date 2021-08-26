package softuni.exam.instagraphlite.util;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface XmlParser {

    <T> T fromFile(String path, Class<T> tClass) throws JAXBException, FileNotFoundException;

}

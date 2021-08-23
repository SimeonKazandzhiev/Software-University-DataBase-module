package softuni.exam.domain.dtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PictureSeedDto {

    @XmlElement
    @Expose
    private String url;

    public PictureSeedDto() {
    }

    @NotBlank
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

package empapp;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@FacesConverter("localDateTimeConverter")
public class LocalDateTimeConverter implements Converter<LocalDateTime> {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        try {
            return LocalDateTime.parse(s, FORMATTER);
        }
        catch (Exception ex) {
            throw new ConverterException(new FacesMessage("Can not convert time"));
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, LocalDateTime localDateTime) {
        return FORMATTER.format(localDateTime);
    }
}

package cz.muni.fi.pv243.tps.action.converters;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */
// TODO: i18n support, right now this converter is pretty dumb

@FacesConverter("dateTime")
public class DateTimeConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return DateTime.parse(value, formatter);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (!(value instanceof DateTime)){
            throw new IllegalArgumentException("Expected an instance of org.joda.time.DateTIme " +
                    "but received " + value);
        }
        DateTime dateTime = (DateTime) value;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        return  dateTime.toDateTime(DateTimeZone.forID("Europe/Prague"))
                .toString(formatter);
    }
}

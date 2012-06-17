package cz.muni.fi.pv243.tps.security.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.jboss.seam.security.annotations.SecurityBindingType;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@SecurityBindingType
@Target({TYPE, METHOD, PARAMETER, FIELD})
@Retention(RUNTIME)
public @interface IsSupervisorOf {
}

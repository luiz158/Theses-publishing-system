package cz.muni.fi.pv243.tps.viewconfig;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface CurrentPage {
}

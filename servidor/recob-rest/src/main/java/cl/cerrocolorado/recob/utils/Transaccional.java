package cl.cerrocolorado.recob.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import org.springframework.transaction.annotation.Transactional;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Transactional(rollbackFor = { MensajeError.class, Exception.class })
public @interface Transaccional {

}

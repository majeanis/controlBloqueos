package cl.cerrocolorado.recob.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class FechaUtils
{
    /**
     * Objeto LocalDateTime inicializado con valor que representa que no hay Fecha
     */
    public static final LocalDate     FECHA_VACIA      = LocalDate.of(1900, 1, 1);

    /**
     * Objeto LocalDateTime inicializado con valor que representa que no hay Fecha y Hora
     */
    public static final LocalDateTime FECHA_HORA_VACIA = toLocalDateTime(FECHA_VACIA);

    /**
     * Permite obtener un objeto LocalDateTime inicializado con una Fecha y Hora  dada.
     * 
     * @param anyo
     * @param mes
     * @param dia
     * @param hora
     * @param minutos
     * @param segundos
     * 
     * @return Objeto LocalDateTime inicializado
     */
    public static LocalDateTime getFechaHora(int anyo, int mes, int dia, int hora, int minutos, int segundos)
    {
        return LocalDateTime.of(anyo, mes, dia, hora, minutos, segundos, 0);
    }

    /**
     * Permite obtener un objeto LocalDate inicializado con una Fecha dada.
     * 
     * @param anyo
     * @param mes
     * @param dia
     * 
     * @return Objeto LocalDate inicializado
     */
    public static LocalDate getFecha(int anyo, int mes, int dia)
    {
        return LocalDate.of(anyo, mes, dia);
    }

    /**
     * Permite obtener un objeto LocalDateTime inicializado con la Fecha y Hora
     * actual del sistema
     * 
     * @return Objeto LocalDateTime inicializado
     */
    public static LocalDateTime getFechaHoraActual()
    {
        return LocalDateTime.now();
    }

    /**
     * Permite obtener un objeto LocaDate inicializado con la Fecha actual del sistema
     * 
     * @return Objeto LocalDate inicializado
     */
    public static LocalDate getFechaActual()
    {
        return LocalDate.now();
    }

    /**
     * Convierte un objeto LocalDate en LocalDateTime
     * 
     * @param fecha
     * 
     * @return Objeto LocalDateTime convertido
     */
    public static LocalDateTime toLocalDateTime(LocalDate fecha)
    {
        if (fecha == null)
        {
            return null;
        }
        return LocalDateTime.of(fecha, LocalTime.of(0, 0, 0, 0));
    }

    /**
     * Calcula la diferencia de Días entre dos objetos LocalDateTime
     * 
     * @param fechaMenor
     * @param fechaMayor
     * 
     * @return Cantidad de días entre ambas fechas
     */
    public static long diffDias(LocalDateTime fechaMenor, LocalDateTime fechaMayor)
    {
        return ChronoUnit.DAYS.between(fechaMenor, fechaMayor);
    }

    /**
     * Calcula la diferencia de Días entre dos objetos LocalDate
     * 
     * @param fechaMenor
     * @param fechaMayor
     * 
     * @return Cantidad de días entre ambas fechas
     */
    public static long diffDias(LocalDate fechaMenor, LocalDate fechaMayor)
    {
        return ChronoUnit.DAYS.between(fechaMenor, fechaMayor);
    }

    /**
     * Determina si la fecha dada está vacía o no, ya sea por que está nula o su
     * valor corresponde al definido como FECHA_HORA_VACIA
     * 
     * @param fecha
     *            Fecha que será evaluada
     * 
     * @return true o false
     */
    public static boolean isEmpty(LocalDateTime fecha)
    {
        return (fecha == null || fecha.compareTo(FECHA_HORA_VACIA) == 0);
    }

    /**
     * Determina si la fecha dada está vacía o no, ya sea por que está nula o su
     * valor corresponde al definido como FECHA_VACIA
     * 
     * @param fecha
     *            Fecha que será evaluada
     * 
     * @return true o false
     */
    public static boolean isEmpty(LocalDate fecha)
    {
        return (fecha == null || fecha.compareTo(FECHA_VACIA) == 0);
    }
    
    /**
     * Transforma un tipo LocalDateTime en java.util.Date
     * 
     * @param fecha     Fecha que será convertida
     * 
     * @return          Objeto Date inicializado
     */
    public static Date toDate(LocalDateTime fecha)
    {
        if (fecha == null)
        {
            return null;
        }
        Instant instant = fecha.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * Transforma un tipo LocalDate en java.util.Date
     * 
     * @param fecha     Fecha que será convertida
     * 
     * @return          Objeto Date inicializado
     */
    public static Date toDate(LocalDate fecha)
    {
        if (fecha == null)
        {
            return null;
        }
        LocalDateTime conTime = fecha.atStartOfDay();
        return toDate(conTime);
    }
    
    /**
     * Transforma un tipo Date a LocalDateTime
     * 
     * @param fecha     Fecha que será convertida
     * @return          Objeto LocalDateTime inicializado
     */
    public static LocalDateTime toLocalDateTime(Date fecha)
    {
        if (fecha == null)
        {
            return null;
        }
        return LocalDateTime.ofInstant(fecha.toInstant(), ZoneId.systemDefault());
    }
}

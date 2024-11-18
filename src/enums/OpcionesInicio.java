package enums;

// este enumerado se usa para pasar por parametro a la clase 'ClienteTruco' para saber que vista iniciar
// y para saber si es una partida reanudada o no, ya que no tenemos otra opcion de avisarle ya que en
// la clase 'ClienteTruco' recien se crea el Controlador

import java.io.Serializable;

public enum OpcionesInicio implements Serializable {
    VISTA_CONSOLA_REANUDAR,
    VISTA_CONSOLA_NO_REANUDAR,
    VISTA_GRAFICA_REANUDAR,
    VISTA_GRAFICA_NO_REANUDAR,
}

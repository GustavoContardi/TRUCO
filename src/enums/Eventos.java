package enums;

// este enumerado se usa para pasar por parametro al metodo 'notificarObservadores()' para saber que mensaje enviar
// y para saber desde el controlador que hay que actualizar

import java.io.Serializable;

public enum Eventos implements Serializable {
    NADA,
    CANTO_TRUCO,
    CANTO_RETRUCO,
    CANTO_VALE_CUATRO,
    CANTO_ENVIDO,
    CANTO_ENVIDO_DOBLE,
    CANTO_REAL_ENVIDO,
    CANTO_FALTA_ENVIDO,
    CANTO_QUERIDO,
    CANTO_NO_QUERIDO,
    TANTO_QUERIDO,
    MENSAJEJ1,
    MENSAJEJ2,
    CARTA_TIRADAJ1,
    CARTA_TIRADAJ2,
    FIN_MANO,
    FIN_PARTIDA,
    INICIO_PARTIDA,
    LISTA_JUGADORES_DISPONIBLES, // los que no están elegidos
    LISTA_JUGADORES_TOTALES, // para el historial
    PUNTAJES,
    NUEVA_RONDA,
    ABANDONO_PARTIDA,
    RESTABLECER_PARTIDA,
    RESTABLECIO_UN_JUGADOR,
}


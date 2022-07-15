package com.unlam.feat.util

import com.unlam.feat.BuildConfig

object Constants {
    const val SPLASH_SCREEN_DURATION = 300L
    const val MIN_USERNAME_LENGTH = 1
    const val MIN_PASSWORD_LENGTH = 1
    const val FEAT_URL_BASE = BuildConfig.HOST_DIR
    const val CHANNEL_ID = "HEADS_UP_NOTIFICATIONS"

    object StateEvent {
        const val PENDING = "PENDIENTE APLICACION"
        const val CONFIRMED = "CONFIRMADO"
        const val FINALIZED = "FINALIZADO"
    }

    object Sports {
        const val TENNIS_SINGLE = "Tenis Single"
        const val TENNIS_DOUBLES = "Tenis Doubles"
        const val SOCCER_5 = "Futbol 5"
        const val SOCCER_6 = "Futbol 6"
        const val SOCCER_7 = "Futbol 7"
        const val SOCCER_9 = "Futbol 9"
        const val SOCCER_11 = "Futbol 11"
        const val BASKETBALL = "Basquet"
        const val PADDLE_SINGLE = "Padel Single"
        const val PADDLE_DOUBLE = "Padel Doubles"
        const val RECREATIONAL_ACTIVITY = "Actividad Recreativa"

    }

    object ImageSport {
        const val FUTBOL =
            "https://img.freepik.com/vector-gratis/coleccion-futbolistas-planos_23-2149002218.jpg"
        const val BASQUET =
            "https://img.freepik.com/vector-gratis/adolescentes-jugando-baloncesto-calle-bola-nino-amigo-ilustracion-vectorial-plana-juego-deportivo-concepto-actividad-verano_74855-10164.jpg"
        const val TENIS =
            "https://png.pngtree.com/illustration/20190226/ourlarge/pngtree-national-fitness-playing-tennis-double-tennis-tennis-court-image_6423.jpg"
        const val PADEL = "https://www.empadelados.com/wp-content/uploads/tipos_jugadores_2.jpg"
    }
}

object Messages {
    const val UNKNOW_ERROR = "Ocurrió un error, por favor contáctese con el administrador."
    const val SUCCESS_CREATED = "Creado con exito."
}

object StateEvent {
    const val SUGGESTED = "SUGERIDO"
    const val CREATED = "EVENTO CREADO"
    const val CONFIRMED = "EVENTO CONFIRMADO"
    const val FINALIZED = "EVENTO TERMINADO"
    const val INVITED = "INVITADO"
    const val PLAYER_CONFIRMED = "CONFIRMADO"
    const val PLAYER_PENDING_APPLY = "APLICADO"
    const val PLAYER_POSTULATED = "POSTULADO"
}

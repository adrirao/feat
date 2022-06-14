package com.unlam.feat.util

import com.unlam.feat.BuildConfig

object Constants {
    const val SPLASH_SCREEN_DURATION = 300L
    const val MIN_USERNAME_LENGTH = 1
    const val MIN_PASSWORD_LENGTH = 1
    const val FEAT_URL_BASE = BuildConfig.HOST_DIR

    object Sports {
        const val TENNIS = "Tennis"
        const val SOCCER = "Futbol 5"
        const val SOCCER_6 = "Futbol 6"
        const val BASKETBALL = "Basketball"
        const val PADDLE = "Paddle"
    }

    object ImageSport{
        const val FUTBOL = "https://img.freepik.com/vector-gratis/coleccion-futbolistas-planos_23-2149002218.jpg"
        const val BASQUET = "https://img.freepik.com/vector-gratis/adolescentes-jugando-baloncesto-calle-bola-nino-amigo-ilustracion-vectorial-plana-juego-deportivo-concepto-actividad-verano_74855-10164.jpg"
        const val TENIS = "https://png.pngtree.com/illustration/20190226/ourlarge/pngtree-national-fitness-playing-tennis-double-tennis-tennis-court-image_6423.jpg"
        const val PADEL = "https://www.empadelados.com/wp-content/uploads/tipos_jugadores_2.jpg"
    }
}

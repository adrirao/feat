package com.unlam.feat.ui.view.config_profile

import android.graphics.Bitmap
import android.net.Uri
import com.unlam.feat.ui.util.TypeClick
import com.unlam.feat.ui.util.TypeValueChange
import com.unlam.feat.ui.view.profile.ProfileEvent
import java.time.LocalTime


sealed class ConfigProfileEvents {
    object SingOutUser : ConfigProfileEvents()
    data class UploadImage(val image: Bitmap) : ConfigProfileEvents()
    data class onClick(val typeClick: TypeClick) : ConfigProfileEvents()
    data class onValueChange(
        val typeValueChange: TypeValueChange,
        val value: String,
        val valueOpt: String? = null,
        val valueBooleanOpt: Boolean? = null,
        val valueLocalTimeOpt: LocalTime? = null,
    ) :
        ConfigProfileEvents()
}
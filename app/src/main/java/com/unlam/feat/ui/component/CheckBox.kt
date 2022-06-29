package com.unlam.feat.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unlam.feat.R
import com.unlam.feat.ui.theme.*
import java.time.LocalTime


@Composable
fun FeatCheckbox (
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CheckboxColors = CheckboxDefaults.colors(
        checkedColor = GreenLight,
        checkmarkColor = PurpleDark ,
        uncheckedColor = GreenLight ,
    )
) {

    Row(
        modifier = modifier.height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = colors,
        )
        FeatText(text = label , fontSize = 15.sp, color = PurpleLight)
    }
}


@Composable
fun FeatAvailabilityCheckBoxPickerTime(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String = "Day",
    starTime: LocalTime?,
    endTime: LocalTime?,
    onValueChangeStartTime: (LocalTime?) -> Unit,
    onValueChangeEndTime: (LocalTime?) -> Unit
) {


    FeatCheckbox(
        modifier = Modifier.fillMaxWidth(),
        checked = checked,
        onCheckedChange = { onCheckedChange(it) },
        label = label
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically

    ) {
        if (checked) {

            FeatOutlinedTimePicker(
                modifier = Modifier.width(50.dp),
                time = starTime,
                onValueChange = { onValueChangeStartTime(it) },
            )

            FeatOutlinedTimePicker(
                modifier = Modifier.width(50.dp),
                time = endTime,
                onValueChange = { onValueChangeEndTime(it) },
            )

        }else{
            onValueChangeStartTime(null)
            onValueChangeEndTime(null)

        }
    }


}
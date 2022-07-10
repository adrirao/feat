package com.unlam.feat.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
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
fun FeatCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String = "",
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CheckboxColors = CheckboxDefaults.colors(
        checkedColor = GreenLight,
        checkmarkColor = PurpleDark,
        uncheckedColor = GreenLight,
    ),
    error: String = ""
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
        FeatText(modifier= Modifier.padding(end = 5.dp),text = label, fontSize = 15.sp, color = PurpleLight)
        FeatText(text = error, fontSize = 15.sp, color = MaterialTheme.colors.error)

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
    onValueChangeEndTime: (LocalTime?) -> Unit,
    titlePickerStart: String = "Default",
    titlePickerEnd: String = "Default",
    error: String = ""
) {


    FeatCheckbox(
        modifier = Modifier.fillMaxWidth(),
        checked = checked,
        onCheckedChange = { onCheckedChange(it) },
        label = label,
        error = error
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround

    ) {
        if (checked) {

            FeatOutlinedTimePicker(
                modifier = Modifier.width(120.dp),
                time = starTime,
                onValueChange = { onValueChangeStartTime(it) },
                label = stringResource(R.string.start_time),
                titlePicker = titlePickerStart,
                error = error,
                isErrorVisible = false
            )

            FeatOutlinedTimePicker(
                modifier = Modifier.width(120.dp),
                time = endTime,
                onValueChange = { onValueChangeEndTime(it) },
                label = stringResource(R.string.end_time),
                titlePicker = titlePickerEnd,
                error = error,
                isErrorVisible = false
            )

        } else {
            onValueChangeStartTime(null)
            onValueChangeEndTime(null)

        }
    }


}
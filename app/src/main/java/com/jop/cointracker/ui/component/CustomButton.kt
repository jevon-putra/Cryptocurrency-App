package com.jop.cointracker.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jop.cointracker.ui.theme.CoinTrackerTheme
import com.jop.cointracker.ui.theme.PADDING_12
import com.jop.cointracker.ui.theme.PADDING_EXTRA
import com.jop.cointracker.ui.theme.PADDING_HALF
import com.jop.cointracker.ui.theme.PADDING_MAIN

@Composable
fun CustomButtonPrimary(modifier: Modifier = Modifier, text: String, action: () -> Unit){
    Button(
        modifier = modifier.height(48.dp),
        onClick = { action() },
        shape = RoundedCornerShape(PADDING_EXTRA),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            modifier = Modifier.padding(horizontal = PADDING_12),
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun CustomButtonOutlinePrimary(modifier: Modifier = Modifier, text: String, action: () -> Unit){
    Button(
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(PADDING_EXTRA),
        onClick = { action() },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            modifier = Modifier.padding(horizontal = PADDING_12),
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewCustomButtonPrimary(){
    CoinTrackerTheme {
        CustomButtonPrimary(text = "Test") {

        }
    }
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewCustomButtonOutlinePrimary(){
    CoinTrackerTheme {
        CustomButtonOutlinePrimary(text = "Test") {

        }
    }
}


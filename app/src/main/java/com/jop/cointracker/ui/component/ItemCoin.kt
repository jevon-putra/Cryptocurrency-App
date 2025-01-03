package com.jop.cointracker.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jop.cointracker.R
import com.jop.cointracker.data.model.Coin
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ItemCoin(coin: Coin, onItemClick: (Coin) -> Unit){
    val format = NumberFormat.getCurrencyInstance(Locale("en", "US"))

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemClick(coin) }
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier.size(32.dp),
            model = "https://cryptologos.cc/logos/${coin.name.lowercase()}-${coin.symbol.lowercase()}-logo.png",
            contentDescription = null,
            placeholder = painterResource(R.drawable.img_placeholder),
            error = painterResource(R.drawable.img_placeholder),
        )

        Text(
            modifier = Modifier.padding(horizontal = 16.dp).weight(1f),
            text = "${coin.name} (${coin.symbol})",
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = format.format(coin.priceUsd),
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            minLines = 1
        )
    }
}
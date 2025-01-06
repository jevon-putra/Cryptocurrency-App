package com.jop.cointracker.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.jop.cointracker.R
import com.jop.cointracker.data.model.Coin
import com.jop.cointracker.util.shimmerBackground
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ItemCoin(coin: Coin, onItemClick: (Coin) -> Unit){
    val format = NumberFormat.getCurrencyInstance(Locale("en", "US"))

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemClick(coin) }
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage(
            modifier = Modifier.size(32.dp),
            contentDescription = coin.name,
            model = coin.iconUrl,
            placeholder = painterResource(R.drawable.img_placeholder),
            error = painterResource(R.drawable.img_placeholder),
        )

        Text(
            modifier = Modifier.weight(2f),
            text = "${coin.name} (${coin.symbol})",
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis
        )

        if(coin.sparkline.isNotEmpty()){
            LineChart(
                modifier = Modifier.height(24.dp).weight(1f),
                list = coin.sparkline.mapNotNull { it }
            )
        }

        Text(
            modifier = Modifier.weight(1f),
            text = format.format(coin.price),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.End,
            minLines = 1
        )
    }
}

@Composable
fun ItemCoinLoading(){
    val coin = Coin(name = "Bitcoin", symbol = "BTC", price = 1000.0)
    val format = NumberFormat.getCurrencyInstance(Locale("en", "US"))

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(32.dp))
                .background(color = Color.Gray)
                .shimmerBackground()
                .size(32.dp),
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .shimmerBackground(),
            text = "${coin.name} (${coin.symbol})",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Gray,
                background = Color.Gray
            ),
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.shimmerBackground(),
            text = format.format(coin.price),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Gray,
                background = Color.Gray
            ),
            overflow = TextOverflow.Ellipsis,
            minLines = 1
        )
    }
}
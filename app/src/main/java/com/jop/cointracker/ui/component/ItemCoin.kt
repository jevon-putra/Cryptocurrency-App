package com.jop.cointracker.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.jop.cointracker.ui.theme.PADDING_EXTRA
import com.jop.cointracker.ui.theme.PADDING_HALF
import com.jop.cointracker.ui.theme.PADDING_MAIN
import com.jop.cointracker.util.shimmerBackground
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ItemCoin(coin: Coin, onItemClick: (Coin) -> Unit){
    val format = NumberFormat.getCurrencyInstance(Locale("en", "US"))
    val df = DecimalFormat("0.##")

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemClick(coin) }
        .padding(PADDING_MAIN),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(PADDING_HALF)
    ) {
        AsyncImage(
            modifier = Modifier.clip(RoundedCornerShape(PADDING_EXTRA)).size(48.dp),
            contentDescription = coin.name,
            model = coin.iconUrl,
            placeholder = painterResource(R.drawable.img_placeholder),
            error = painterResource(R.drawable.img_placeholder),
        )
        Column(
            modifier = Modifier.padding(start = PADDING_HALF).weight(2f)
        ) {
            Text(
                text = coin.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
            )
            Text(
                text = coin.symbol,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.outline
                ),

            )
        }

        if(coin.sparkline.isNotEmpty()){
            LineChart(
                modifier = Modifier
                    .height(24.dp)
                    .weight(1f),
                list = coin.sparkline.mapNotNull { it }
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                text = format.format(coin.price),
                style = MaterialTheme.typography.bodyMedium.copy(),
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End,
                minLines = 1
            )
            Text(
                text = "${if(coin.percentageIncrease() > 0) "+" else ""}${df.format(coin.percentageIncrease())}%",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = if(coin.percentageIncrease() >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun ItemCoinLoading(){
    val coin = Coin(name = "Bitcoin", symbol = "BTC", price = 1000.0)
    val format = NumberFormat.getCurrencyInstance(Locale("en", "US"))

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(PADDING_MAIN),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(PADDING_HALF)
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(PADDING_EXTRA))
                .background(color = Color.Gray)
                .shimmerBackground()
                .size(48.dp),
        )
        Column(
            modifier = Modifier.padding(start = PADDING_HALF).weight(2f)
        ) {
            Text(
                modifier = Modifier.shimmerBackground(),
                text = coin.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray,
                    background = Color.Gray
                ),
            )
            Text(
                modifier = Modifier.shimmerBackground(),
                text = coin.symbol,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Gray,
                    background = Color.Gray
                )
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier.padding(start = PADDING_HALF).weight(2f),
            horizontalAlignment = Alignment.End,
        ) {
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
            Text(
                modifier = Modifier.shimmerBackground(),
                text = "+0%",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Gray,
                    background = Color.Gray
                )
            )
        }
    }
}
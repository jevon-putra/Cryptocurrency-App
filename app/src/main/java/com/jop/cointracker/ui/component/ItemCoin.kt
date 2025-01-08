package com.jop.cointracker.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.jop.cointracker.util.formatCurrencyNumber
import com.jop.cointracker.util.shimmerBackground
import java.text.DecimalFormat

@Composable
fun ItemCoin(coin: Coin = Coin(name = "Bitcoin", symbol = "BTC", price = 10000.0), isLoading: Boolean = false, onItemClick: (Coin) -> Unit){
    val df = DecimalFormat("0.##")

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemClick(coin) }
        .padding(PADDING_MAIN),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(PADDING_HALF)
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(PADDING_EXTRA))
                .shimmerBackground(isLoading)
                .size(48.dp),
            contentDescription = coin.name,
            model = coin.iconUrl,
            placeholder = painterResource(R.drawable.img_placeholder),
            error = painterResource(R.drawable.img_placeholder),
        )
        Column(
            modifier = Modifier.padding(start = PADDING_HALF).weight(2f)
        ) {
            Text(
                modifier = Modifier.shimmerBackground(isLoading),
                text = coin.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
            )
            Text(
                modifier = Modifier.shimmerBackground(isLoading),
                text = coin.symbol,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.outline
                ),

            )
        }

        if(coin.sparkline.isNotEmpty()){
            LineChart(
                modifier = Modifier
                    .shimmerBackground(isLoading)
                    .height(24.dp)
                    .weight(1f),
                list = coin.sparkline.mapNotNull { it }
            )
        }

        Column(
            modifier = Modifier.weight(1.2f),
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                modifier = Modifier.shimmerBackground(isLoading),
                text = coin.price.formatCurrencyNumber(),
                style = MaterialTheme.typography.bodyMedium.copy(),
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End,
                minLines = 1
            )
            Text(
                modifier = Modifier.shimmerBackground(isLoading),
                text = "${if(coin.percentageIncrease() > 0) "+" else ""}${df.format(coin.percentageIncrease())}%",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = if(coin.percentageIncrease() >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}
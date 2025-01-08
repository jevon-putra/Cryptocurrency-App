package com.jop.cointracker.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jop.cointracker.R
import com.jop.cointracker.data.model.CoinDetail
import com.jop.cointracker.ui.theme.CoinTrackerTheme
import com.jop.cointracker.ui.theme.PADDING_HALF
import com.jop.cointracker.ui.theme.PADDING_MAIN

@Composable
fun ItemSocialCoin(socialLink: CoinDetail.Link){
    val uriHandler = LocalUriHandler.current

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { uriHandler.openUri(socialLink.url) }
        .padding(PADDING_MAIN),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(PADDING_HALF)
    ) {
        Image(
            modifier = Modifier.width(24.dp),
            painter = painterResource(
                id = if(socialLink.type == "twitter") R.drawable.ic_twitter
                else if(socialLink.type == "telegram") R.drawable.ic_telegram
                else if(socialLink.type == "cmc") R.drawable.ic_cmc
                else if(socialLink.type == "reddit") R.drawable.ic_reddit
                else if(socialLink.type == "github") R.drawable.ic_github
                else if(socialLink.type == "facebook") R.drawable.ic_facebook
                else if(socialLink.type == "youtube") R.drawable.ic_youtube
                else if(socialLink.type.contains("bitcoin")) R.drawable.ic_bitcoin
                else R.drawable.ic_website
            ),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSurface),
            contentDescription = "",
        )

        Text(
            modifier = Modifier.padding(start = PADDING_HALF),
            text = socialLink.type.capitalize(),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )

        Text(
            modifier = Modifier.weight(1f),
            text = socialLink.name,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.outline
            ),
            textAlign = TextAlign.End
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewItemSocialCoin(){
    CoinTrackerTheme {
        ItemSocialCoin(socialLink = CoinDetail.Link(
            name = "www.ethereum.org",
            type = "bitcoin",
            url = "https://www.ethereum.org")
        )
    }
}
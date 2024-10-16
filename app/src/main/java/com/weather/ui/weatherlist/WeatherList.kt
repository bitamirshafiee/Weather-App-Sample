package com.weather.ui.weatherlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weather.R
import com.weather.data.model.WeatherResponse
import com.weather.data.model.getDefaultWeatherResponse

@Composable
fun WeatherList(viewModel: WeatherListViewModel) {

    val currentWeatherList by viewModel.weatherResult.collectAsStateWithLifecycle()
    LazyColumn {
        items(items = currentWeatherList) { currentWeather: WeatherResponse ->
            WeatherItem(currentWeather = currentWeather)
        }
    }

}

@Composable
fun WeatherItem(modifier: Modifier = Modifier, currentWeather: WeatherResponse) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            modifier = Modifier
                .size(80.dp),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = currentWeather.weather.get(0).condition,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )
        Column(modifier = Modifier.weight(1f)) {
            Temperatures()
        }
    }
}

@Composable
private fun Temperatures() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        text = "18",
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center
    )
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        text = "18",
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center
    )
}

@Composable
@Preview
fun WeatherItemPreview() {
    WeatherItem(currentWeather = getDefaultWeatherResponse())
}


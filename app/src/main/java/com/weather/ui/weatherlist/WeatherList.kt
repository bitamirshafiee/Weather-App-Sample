package com.weather.ui.weatherlist

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.weather.R
import com.weather.data.model.WeatherData
import com.weather.data.model.getDefaultWeatherData

@Composable
fun WeatherList(viewModel: WeatherListViewModel) {

    val weatherDataList by viewModel.weatherResult.collectAsStateWithLifecycle()
    LazyColumn {
        items(items = weatherDataList) { weatherData: WeatherData ->
            WeatherItem(weatherData = weatherData)
        }
    }

}

@Composable
fun WeatherItem(modifier: Modifier = Modifier, weatherData: WeatherData) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = weatherData.iconUrl,
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = weatherData.cityName,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )
        Column(modifier = Modifier.weight(1f)) {
            Temperatures(weatherData)
        }
    }
}

@Composable
private fun Temperatures(weatherData: WeatherData) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        text = stringResource(
            id = R.string.str_current_temperature, weatherData.currentTemperature
        ),
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center
    )
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        text = stringResource(
            id = R.string.str_feels_like,
            weatherData.feelsLikeTemperature
        ),
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center
    )
}

@Composable
@Preview
fun WeatherItemPreview() {
    WeatherItem(weatherData = getDefaultWeatherData())
}


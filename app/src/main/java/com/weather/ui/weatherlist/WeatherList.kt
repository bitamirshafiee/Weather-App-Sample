package com.weather.ui.weatherlist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.weather.R
import com.weather.data.model.WeatherData
import com.weather.data.model.getDefaultWeatherData
import com.weather.ui.utils.CircularProgress
import com.weather.ui.utils.ErrorDialog

@Composable
fun WeatherList(viewModel: WeatherListViewModel) {

    val errorDialog by viewModel.isShowErrorDialog.collectAsState()
    val isProgressVisible by viewModel.isInProgress.collectAsState(initial = true)
    val weatherDataList by viewModel.weatherResult.collectAsState()

    Box {
        if (errorDialog.first) {
            ErrorDialog(errorDialog.second) {
                viewModel.resetErrorDialog()
                //further things to do could be :
                // after error dialog closed we can show a proper message to the user about the
                // error and possible things they can do
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                //in case of ui test and check if the first page or first Nav route is visible
                .testTag("currentWeatherPage")
        ) {

            Box(modifier = Modifier) {
                if (isProgressVisible)
                    CircularProgress()
                else WeatherList(weatherDataList)
            }
        }
    }

}

@Composable
fun WeatherList(weatherDataList: List<WeatherData>) {
    LazyColumn {
        items(items = weatherDataList) { weatherData: WeatherData ->
            WeatherItem(weatherData = weatherData)
        }
    }
}

@Composable
fun WeatherItem(modifier: Modifier = Modifier, weatherData: WeatherData) {

    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier.fillMaxWidth().padding(4.dp)
    ) {

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
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = weatherData.cityName,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                Temperatures(weatherData)
            }
        }
    }
}

@Composable
private fun Temperatures(weatherData: WeatherData) {
    Row {
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
                .weight(1f),
            text = stringResource(
                id = R.string.str_current_temperature, weatherData.currentTemperature
            ),
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
                .weight(1f),
            text = stringResource(
                id = R.string.str_feels_like,
                weatherData.feelsLikeTemperature
            ),
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun WeatherItemPreview() {
    WeatherItem(weatherData = getDefaultWeatherData())
}


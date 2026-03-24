package com.example.tp_country

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp_country.ui.theme.Tp_countryTheme


data class Country(
    val name: String,
    val capital: String,
    val code: String,
    val flagRes: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tp_countryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CountryList(
                        countries = getCountries(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


fun getCountries(): List<Country> {
    return listOf(
        // Note: Remplacez ic_launcher_foreground par vos propres ressources (ex: R.drawable.ic_flag_france)
        Country("Rdc", "Kinshasa", "CD", R.drawable.ic_flag_drc),
        Country("Canada", "Ottawa", "CA", R.drawable.ic_flag_canada),
        Country("Japon", "Tokyo", "JP", R.drawable.ic_flag_japan),
        Country("Allemagne", "Berlin", "DE", R.drawable.ic_flag_deutsh),
        Country("France", "Paris", "FR", R.drawable.ic_flag_france),
        Country("Espagne", "Madrid", "", R.drawable.ic_flag_spain),
        Country("Congo", "Brazzaville", "CG", R.drawable.ic_flag_brazza),
        Country("Sénégal", "Dakar", "SN", R.drawable.ic_flag_senegal)
    )
}


@Composable
fun CountryItem(country: Country, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = country.flagRes),
            contentDescription = "Drapeau de ${country.name}",
            modifier = Modifier
                .size(60.dp)
                .padding(4.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        

        Column {
            Text(
                text = country.name,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Capitale : ${country.capital}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Code : ${country.code}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}


@Composable
fun CountryList(countries: List<Country>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(countries) { country ->
            CountryItem(country = country)
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCountryList() {
    Tp_countryTheme {
        Surface {
            CountryList(countries = getCountries())
        }
    }
}

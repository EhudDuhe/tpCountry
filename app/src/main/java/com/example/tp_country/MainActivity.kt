package com.example.tp_country

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp_country.ui.theme.Tp_countryTheme

data class Country(
    val name: String,
    val capital: String,
    val code: String,
    val flagRes: Int,
    val description: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tp_countryTheme {
                CountryApp()
            }
        }
    }
}

@Composable
fun CountryApp() {
    var searchQuery by remember { mutableStateOf("") }
    val allCountries = getCountries()
    
    val filteredCountries = remember(searchQuery) {
        if (searchQuery.isEmpty()) {
            allCountries
        } else {
            allCountries.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                it.capital.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
            ) {
                // Header with Logo and App Name
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Logo Placeholder (using an Icon for now)
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Logo",
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Text(
                        text = "M'bokas",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Rechercher un pays...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    )
                )
            }
        }
    ) { innerPadding ->
        CountryList(
            countries = filteredCountries,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

fun getCountries(): List<Country> {
    return listOf(
        Country("RDC", "Kinshasa", "CD", R.drawable.ic_flag_drc, "La République Démocratique du Congo est le plus grand pays d'Afrique subsaharienne, connu pour sa forêt tropicale et ses ressources minérales."),
        Country("Canada", "Ottawa", "CA", R.drawable.ic_flag_canada, "Le Canada est le deuxième plus grand pays au monde, célèbre pour ses paysages naturels, ses montagnes et son sirop d'érable."),
        Country("Japon", "Tokyo", "JP", R.drawable.ic_flag_japan, "Le Japon est un archipel volcanique combinant traditions ancestrales et technologies de pointe."),
        Country("Allemagne", "Berlin", "DE", R.drawable.ic_flag_deutsh, "L'Allemagne est une puissance économique européenne, riche en histoire, en culture et en ingénierie."),
        Country("France", "Paris", "FR", R.drawable.ic_flag_france, "La France est célèbre pour sa gastronomie, sa mode, son art et ses monuments historiques comme la Tour Eiffel."),
        Country("Espagne", "Madrid", "ES", R.drawable.ic_flag_spain, "L'Espagne est connue pour ses plages ensoleillées, sa musique flamenco et sa délicieuse cuisine comme la paella."),
        Country("Congo", "Brazzaville", "CG", R.drawable.ic_flag_brazza, "La République du Congo est située en Afrique centrale, traversée par le fleuve Congo et riche en biodiversité."),
        Country("Sénégal", "Dakar", "SN", R.drawable.ic_flag_senegal, "Le Sénégal est réputé pour son hospitalité (la Teranga), sa musique et ses sites historiques comme l'île de Gorée.")
    )
}

@Composable
fun CountryItem(
    country: Country,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = country.flagRes),
                    contentDescription = "Drapeau de ${country.name}",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1.0f)) {
                    Text(
                        text = country.name,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "${country.capital} / ${country.code}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Voir moins" else "Voir plus"
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .fillMaxWidth()
                ) {
                    HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))
                    Text(
                        text = country.description,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CountryList(
    countries: List<Country>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(countries) { country ->
            CountryItem(country = country)
            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCountryList() {
    Tp_countryTheme {
        CountryApp()
    }
}

package com.holyplaces.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HolyPlacesApp()
        }
    }
}

data class HolyPlace(
    val name: String,
    val tradition: String,
    val distanceKm: Int,
    val crowdLevel: String,
    val bestVisitTime: String,
)

data class AppFeature(
    val title: String,
    val description: String,
)

data class NavSection(
    val label: String,
    val icon: ImageVector,
)

private val holyPlaces = listOf(
    HolyPlace("Golden Temple", "Sikh", 3, "Medium", "06:00 - 08:00"),
    HolyPlace("Jagannath Temple", "Hindu", 7, "High", "05:30 - 07:00"),
    HolyPlace("Jama Masjid", "Muslim", 5, "Low", "16:00 - 18:00"),
    HolyPlace("Basilica of Bom Jesus", "Christian", 12, "Low", "09:00 - 10:30"),
)

private val mvpFeatures = listOf(
    AppFeature("Holy Place Discovery", "Nearby temples, mosques, churches, and shrines with filters."),
    AppFeature("Accurate Timings", "Prayer/ritual schedules, opening hours, and festival alerts."),
    AppFeature("Smart Trip Planner", "One-day and multi-day itineraries with route and transit insights."),
    AppFeature("Live Crowd Indicator", "Crowd status and best time recommendations from user signals."),
    AppFeature("Offline Mode", "Downloaded place guides and maps for poor network zones."),
)

private val growthFeatures = listOf(
    AppFeature("Daily Spiritual Feed", "Personalized reminders, prayers, and meaningful quotes."),
    AppFeature("Virtual Darshan", "Live streams for elderly users and remote devotees."),
    AppFeature("Community Groups", "Family/group trip coordination and trusted reviews."),
    AppFeature("Verified Donations", "Secure offerings with receipts and transparent impact updates."),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HolyPlacesApp() {
    val sections = listOf(
        NavSection("Discover", Icons.Default.Map),
        NavSection("Planner", Icons.Default.TravelExplore),
        NavSection("Events", Icons.Default.CalendarMonth),
        NavSection("Community", Icons.Default.People),
        NavSection("Alerts", Icons.Default.Notifications),
    )
    var selectedSection by remember { mutableStateOf(sections.first()) }

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Holy Places", fontWeight = FontWeight.Bold)
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    sections.forEach { section ->
                        NavigationBarItem(
                            selected = selectedSection == section,
                            onClick = { selectedSection = section },
                            label = { Text(section.label) },
                            icon = { Icon(section.icon, contentDescription = section.label) }
                        )
                    }
                }
            }
        ) { paddingValues ->
            when (selectedSection.label) {
                "Discover" -> DiscoverScreen(paddingValues)
                "Planner" -> FeatureScreen(
                    paddingValues = paddingValues,
                    title = "Trip planner ready for 90-day launch",
                    subtitle = "Build sacred journeys with timing, routes, and less-crowded slots.",
                    features = mvpFeatures,
                )

                "Events" -> FeatureScreen(
                    paddingValues = paddingValues,
                    title = "Festival and ritual calendar",
                    subtitle = "Track arti, prayer, mass, and annual events.",
                    features = listOf(
                        AppFeature("Event reminders", "Get notified before rituals and festival ceremonies."),
                        AppFeature("Dress & entry rules", "Check place-wise customs so visitors are prepared."),
                        AppFeature("Language support", "Consume guidance in regional languages."),
                    )
                )

                "Community" -> FeatureScreen(
                    paddingValues = paddingValues,
                    title = "Retention and growth features",
                    subtitle = "Build trust and encourage family participation.",
                    features = growthFeatures,
                )

                else -> FeatureScreen(
                    paddingValues = paddingValues,
                    title = "Safety and trust alerts",
                    subtitle = "Support pilgrims with verified information.",
                    features = listOf(
                        AppFeature("Official profiles", "Verified holy place pages and announcements."),
                        AppFeature("Emergency support", "Local emergency contacts and women-safe routes."),
                        AppFeature("Fraud warnings", "Only official donation and booking links are promoted."),
                    )
                )
            }
        }
    }
}

@Composable
fun DiscoverScreen(paddingValues: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Nearby Holy Places",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
            )
        }

        items(holyPlaces) { place ->
            HolyPlaceCard(place)
        }
    }
}

@Composable
fun HolyPlaceCard(place: HolyPlace) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(place.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Tradition: ${place.tradition}")
                Text("Distance: ${place.distanceKm} km")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Crowd: ${place.crowdLevel}")
                Text("Best time: ${place.bestVisitTime}")
            }
        }
    }
}

@Composable
fun FeatureScreen(
    paddingValues: PaddingValues,
    title: String,
    subtitle: String,
    features: List<AppFeature>,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
        }
        item {
            Text(subtitle, style = MaterialTheme.typography.bodyLarge)
        }

        items(features) { feature ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(feature.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(feature.description, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

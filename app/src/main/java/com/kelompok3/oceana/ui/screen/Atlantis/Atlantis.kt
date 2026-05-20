package com.kelompok3.oceana.ui.screen.Atlantis

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.runtime.collectAsState
import com.kelompok3.oceana.navigation.OceanaRoute
import com.kelompok3.oceana.ui.screen.auth.AuthViewModel

// ─── Color Palette (Konsisten dengan HomeScreen & MarineLife) ───────────────
private val OceanDeep     = Color(0xFF023E8A)
private val OceanMid      = Color(0xFF0077B6)
private val OceanLight    = Color(0xFF00B4D8)
private val OceanSurface  = Color(0xFFCAF0F8)
private val SandWhite     = Color(0xFFF8F9FA)
private val CoralAccent   = Color(0xFFE76F51)
private val TextPrimary   = Color(0xFF0D1B2A)
private val TextSecondary = Color(0xFF4A5568)

// ─── Data Model ──────────────────────────────────────────────────────────────
data class Destination(
    val id: Int,
    val name: String,
    val location: String,
    val rating: String,
    val description: String,
    val emoji: String,
    val category: String
)

val atlantisDestinations = listOf(
    Destination(
        id = 1,
        name = "Raja Ampat",
        location = "Papua Barat",
        rating = "4.9",
        description = "Surga terakhir di bumi dengan keanekaragaman hayati laut tertinggi di dunia.",
        emoji = "🏝️",
        category = "Terpopuler"
    ),
    Destination(
        id = 2,
        name = "Labuan Bajo",
        location = "Nusa Tenggara Timur",
        rating = "4.8",
        description = "Gerbang menuju Taman Nasional Komodo dan keindahan bawah laut Pink Beach.",
        emoji = "⛵",
        category = "Favorit"
    ),
    Destination(
        id = 3,
        name = "Bunaken",
        location = "Sulawesi Utara",
        rating = "4.7",
        description = "Taman laut legendaris dengan dinding karang raksasa yang mempesona.",
        emoji = "🐠",
        category = "Konservasi"
    ),
    Destination(
        id = 4,
        name = "Wakatobi",
        location = "Sulawesi Tenggara",
        rating = "4.7",
        description = "Destinasi selam kelas dunia dengan kejernihan air yang luar biasa.",
        emoji = "🤿",
        category = "Tersembunyi"
    )
)

// ─── Main Screen ─────────────────────────────────────────────────────────────
@Composable
fun AtlantisScreen(
    navController: androidx.navigation.NavController,
    authViewModel: AuthViewModel
) {
    val authState by authViewModel.authState.collectAsState()
    val username = authState.loggedInUser?.username ?: "User"

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(100)
        visible = true
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = SandWhite
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // ── Top Bar ──
            AnimatedVisibility(visible = visible, enter = fadeIn()) {
                AtlantisTopBar(
                    username = username,
                    onHomeClick = {
                        navController.navigate(OceanaRoute.HOME) {
                            popUpTo(OceanaRoute.HOME) { inclusive = false }
                        }
                    },
                    onProfileClick = {
                        navController.navigate(OceanaRoute.PROFILE)
                    },
                    onLogoutClick = {
                        authViewModel.logout()
                        navController.navigate(OceanaRoute.LOGIN) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            // ── Hero Section ──
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { -40 })
            ) {
                AtlantisHero()
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Content Section ──
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn() + slideInVertically(initialOffsetY = { 60 })
                ) {
                    Text(
                        text = "Destinasi Impian",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    atlantisDestinations.forEachIndexed { index, destination ->
                        AnimatedVisibility(
                            visible = visible,
                            enter = fadeIn() + slideInVertically(initialOffsetY = { 80 + (index * 20) })
                        ) {
                            val isDetailEnabled = destination.id == 1
                            DestinationCard(
                                destination = destination,
                                enabled = isDetailEnabled,
                                onClick = {
                                    if (isDetailEnabled) {
                                        navController.navigate(OceanaRoute.ATLANTIS_DETAIL)
                                    }
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun AtlantisTopBar(
    username: String,
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.clickable { menuExpanded = true }
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(OceanDeep, OceanLight),
                                start = Offset(0f, 0f),
                                end = Offset(36f, 36f)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Waves,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    "OCEANA",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = OceanDeep,
                    letterSpacing = 1.sp
                )
            }

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
                modifier = Modifier
                    .background(Color.White)
                    .width(200.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Column {
                        Text(text = "Masuk sebagai", fontSize = 11.sp, color = TextSecondary)
                        Text(text = username, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                    }
                }
                Divider(color = OceanSurface, thickness = 1.dp)
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Icon(Icons.Filled.Home, contentDescription = null, tint = OceanDeep, modifier = Modifier.size(18.dp))
                            Text("Beranda", fontSize = 14.sp, color = TextPrimary)
                        }
                    },
                    onClick = {
                        menuExpanded = false
                        onHomeClick()
                    }
                )
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Icon(Icons.Filled.Person, contentDescription = null, tint = OceanMid, modifier = Modifier.size(18.dp))
                            Text("Lihat Profil", fontSize = 14.sp, color = TextPrimary)
                        }
                    },
                    onClick = {
                        menuExpanded = false
                        onProfileClick()
                    }
                )
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Icon(Icons.Filled.Logout, contentDescription = null, tint = CoralAccent, modifier = Modifier.size(18.dp))
                            Text("Keluar", fontSize = 14.sp, color = CoralAccent)
                        }
                    },
                    onClick = {
                        menuExpanded = false
                        onLogoutClick()
                    }
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            IconButton(onClick = {}) {
                Icon(Icons.Outlined.Search, contentDescription = "Cari", tint = TextPrimary)
            }
            IconButton(onClick = {}) {
                Box {
                    Icon(Icons.Outlined.Notifications, contentDescription = "Notifikasi", tint = TextPrimary)
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(CoralAccent).align(Alignment.TopEnd))
                }
            }
        }
    }
}

@Composable
fun AtlantisHero() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(
                Brush.linearGradient(
                    colors = listOf(OceanDeep, OceanMid),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
            .drawBehind {
                drawCircle(
                    color = Color.White.copy(alpha = 0.05f),
                    radius = 120.dp.toPx(),
                    center = Offset(size.width * 0.9f, size.height * 0.2f)
                )
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color.White.copy(alpha = 0.2f)
            ) {
                Text(
                    "Wisata Bahari",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    fontSize = 11.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "Jelajahi Atlantis\nIndonesia",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                lineHeight = 32.sp
            )
            Text(
                "Temukan keindahan yang belum terjamah.",
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.85f)
            )
        }
    }
}

@Composable
fun DestinationCard(
    destination: Destination,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            disabledContainerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            disabledElevation = 2.dp
        ),
        onClick = onClick,
        enabled = enabled
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(OceanSurface),
                contentAlignment = Alignment.Center
            ) {
                Text(destination.emoji, fontSize = 40.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        destination.category,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = OceanMid,
                        letterSpacing = 0.5.sp
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFB703), modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(destination.rating, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    }
                }
                Text(
                    destination.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.LocationOn, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(destination.location, fontSize = 12.sp, color = TextSecondary)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    destination.description,
                    fontSize = 11.sp,
                    color = TextSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 16.sp
                )
            }
        }
    }
}
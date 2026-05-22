package com.kelompok3.oceana.ui.screen.home

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
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import kotlinx.coroutines.delay
import androidx.compose.runtime.collectAsState
import com.kelompok3.oceana.navigation.OceanaRoute

// ─── Data Models ────────────────────────────────────────────────────────────

data class FeatureCard(
    val title: String,
    val subtitle: String,
    val description: String,
    val emoji: String,
    val gradientStart: Color,
    val gradientEnd: Color,
    val route: String
)

val features = listOf(
    FeatureCard(
        title = "Atlantis",
        subtitle = "Jelajahi Dunia Bawah Laut",
        description = "Temukan keajaiban ekosistem laut Indonesia — dari terumbu karang hingga palung terdalam.",
        emoji = "🏛️",
        gradientStart = Color(0xFF023E8A),
        gradientEnd = Color(0xFF00B4D8),
        route = OceanaRoute.ATLANTIS
    ),
    FeatureCard(
        title = "Marine Life",
        subtitle = "Kenali Biota Laut",
        description = "Eksplorasi ribuan spesies laut Indonesia, habitat aslinya, dan status konservasinya.",
        emoji = "🐠",
        gradientStart = Color(0xFF1B4332),
        gradientEnd = Color(0xFF52B788),
        route = OceanaRoute.MARINE_LIFE
    ),
)

// ─── Color Palette ───────────────────────────────────────────────────────────

val OceanDeep = Color(0xFF023E8A)
val OceanMid = Color(0xFF0077B6)
val OceanLight = Color(0xFF00B4D8)
val OceanSurface = Color(0xFFCAF0F8)
val SandWhite = Color(0xFFF8F9FA)
val CoralAccent = Color(0xFFE76F51)
val TextPrimary = Color(0xFF0D1B2A)
val TextSecondary = Color(0xFF4A5568)

// ─── Main Home Screen ─────────────────────────────────────────────────────────

@Composable
fun HomeScreen(
    navController: androidx.navigation.NavController,
    authViewModel: com.kelompok3.oceana.ui.screen.auth.AuthViewModel
) {
    val authState by authViewModel.authState.collectAsState()
    val username = authState.username ?: ""
    val coins = 250 

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
            AnimatedVisibility(visible = visible, enter = fadeIn()) {
                OceanaTopBar(
                    username = username,
                    coins = coins,
                    onHomeClick = { /* Already on Home */ },
                    onAtlantisClick = { 
                        navController.navigate(OceanaRoute.ATLANTIS) {
                            launchSingleTop = true
                        }
                    },
                    onMarineLifeClick = { 
                        navController.navigate(OceanaRoute.MARINE_LIFE) {
                            launchSingleTop = true
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

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { -40 })
            ) {
                HeroBanner()
            }

            Spacer(modifier = Modifier.height(28.dp))

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { 60 })
            ) {
                FeaturesSection(navController = navController)
            }

            Spacer(modifier = Modifier.height(32.dp))

            AnimatedVisibility(visible = visible, enter = fadeIn()) {
                FooterBanner()
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

// ─── Top Bar ─────────────────────────────────────────────────────────────────

@Composable
fun OceanaTopBar(
    username: String,
    coins: Int,
    onHomeClick: () -> Unit,
    onAtlantisClick: () -> Unit,
    onMarineLifeClick: () -> Unit,
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
        // Logo & Brand
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

                HorizontalDivider(color = OceanSurface, thickness = 1.dp)

                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Icon(Icons.Filled.Home, null, tint = OceanMid, modifier = Modifier.size(18.dp))
                            Text("Dashboard", fontSize = 14.sp, color = TextPrimary)
                        }
                    },
                    onClick = { menuExpanded = false; onHomeClick() }
                )

                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Icon(Icons.Filled.Person, null, tint = OceanMid, modifier = Modifier.size(18.dp))
                            Text("Lihat Profil", fontSize = 14.sp, color = TextPrimary)
                        }
                    },
                    onClick = { menuExpanded = false; onProfileClick() }
                )

                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Icon(Icons.Filled.Logout, null, tint = CoralAccent, modifier = Modifier.size(18.dp))
                            Text("Keluar", fontSize = 14.sp, color = CoralAccent)
                        }
                    },
                    onClick = { menuExpanded = false; onLogoutClick() }
                )
            }
        }

        // Right side: Coins + Icons
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                modifier = Modifier.size(0.dp),
                shape = RoundedCornerShape(20.dp),
                color = Color.Transparent
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("🪙", fontSize = 14.sp)
                    Text(text = "$coins Ocs", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(onClick = {}) {
                    Icon(Icons.Outlined.Search, contentDescription = "Cari", tint = TextPrimary)
                }
                IconButton(onClick = {}) {
                    Box {
                        Icon(Icons.Outlined.Notifications, contentDescription = "Notifikasi", tint = TextPrimary)
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(CoralAccent)
                                .align(Alignment.TopEnd)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NavLink(label: String, onClick: () -> Unit) {
    Text(
        text = label,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextPrimary,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 4.dp, vertical = 4.dp)
    )
}

// ─── Hero Banner ─────────────────────────────────────────────────────────────

@Composable
fun HeroBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(
                Brush.linearGradient(
                    colors = listOf(OceanDeep, OceanMid, OceanLight),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
            .drawBehind {
                drawCircle(
                    color = Color.White.copy(alpha = 0.05f),
                    radius = 180.dp.toPx(),
                    center = Offset(size.width * 0.85f, size.height * 0.2f)
                )
            }
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color.White.copy(alpha = 0.2f)
            ) {
                Text(
                    "Hari Laut Sedunia — 8 Juni",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    fontSize = 11.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Jaga Laut,\nJaga Masa Depan",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                lineHeight = 32.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(24.dp),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Text(
                    "Jelajahi Sekarang",
                    color = OceanDeep,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    Icons.Filled.ArrowForward,
                    contentDescription = null,
                    tint = OceanDeep,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

// ─── Features Section ────────────────────────────────────────────────────────

@Composable
fun FeaturesSection(navController: androidx.navigation.NavController) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        SectionHeader(title = "Fitur Unggulan", actionLabel = null)

        Spacer(modifier = Modifier.height(14.dp))

        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            features.forEach { feature ->
                FeatureCardItem(
                    feature = feature,
                    onClick = {
                        navController.navigate(feature.route) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FeatureCardItem(feature: FeatureCard, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(feature.gradientStart, feature.gradientEnd)
                )
            )
            .clickable(onClick = onClick)
            .drawBehind {
                drawCircle(
                    color = Color.White.copy(alpha = 0.06f),
                    radius = 100.dp.toPx(),
                    center = Offset(size.width * 0.85f, size.height * 0.3f)
                )
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    feature.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    letterSpacing = 0.5.sp
                )
                Text(
                    feature.subtitle,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.85f),
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    feature.description,
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    lineHeight = 15.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(feature.emoji, fontSize = 42.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White.copy(alpha = 0.2f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text("Buka", fontSize = 10.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
                        Icon(Icons.Filled.ArrowForward, contentDescription = null, tint = Color.White, modifier = Modifier.size(10.dp))
                    }
                }
            }
        }
    }
}

// ─── Footer Banner ────────────────────────────────────────────────────────────

@Composable
fun FooterBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF1B4332), Color(0xFF52B788))
                )
            )
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Dukung Konservasi",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Setiap kontribusimu berarti bagi kelestarian laut kita.",
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.85f),
                    lineHeight = 15.sp
                )
            }
            Text("🌿", fontSize = 48.sp, modifier = Modifier.padding(start = 12.dp))
        }
    }
}

// ─── Reusable Section Header ──────────────────────────────────────────────────

@Composable
fun SectionHeader(title: String, actionLabel: String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        if (actionLabel != null) {
            TextButton(onClick = {}) {
                Text(actionLabel, color = OceanMid, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = OceanMid, modifier = Modifier.size(18.dp))
            }
        }
    }
}

package com.kelompok3.oceana.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import kotlinx.coroutines.delay

// ─── Data Models ────────────────────────────────────────────────────────────

data class Article(
    val id: Int,
    val category: String,
    val title: String,
    val description: String,
    val readTime: String,
    val gradientStart: Color,
    val gradientEnd: Color
)

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
        route = "atlantis"
    ),
    FeatureCard(
        title = "Marine Life",
        subtitle = "Kenali Biota Laut",
        description = "Eksplorasi ribuan spesies laut Indonesia, habitat aslinya, dan status konservasinya.",
        emoji = "🐠",
        gradientStart = Color(0xFF1B4332),
        gradientEnd = Color(0xFF52B788),
        route = "marine_life"
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
val CardBg = Color(0xFFFFFFFF)

// ─── Main Home Screen ─────────────────────────────────────────────────────────

@Composable
fun HomeScreen() {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(100)
        visible = true
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = SandWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AnimatedVisibility(visible = visible, enter = fadeIn()) {
                OseanaTopBar()
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
                FeaturesSection()
            }

            Spacer(modifier = Modifier.height(28.dp))

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { 80 })
            ) {

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
fun OseanaTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
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
            Column {
                Text(
                    "OCEANA",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = OceanDeep,
                    letterSpacing = 1.sp
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
                drawCircle(
                    color = Color.White.copy(alpha = 0.07f),
                    radius = 120.dp.toPx(),
                    center = Offset(size.width * 0.75f, size.height * 0.7f)
                )
                drawCircle(
                    color = Color.White.copy(alpha = 0.04f),
                    radius = 80.dp.toPx(),
                    center = Offset(size.width * 0.1f, size.height * 0.8f)
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

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Lestarikan kekayaan laut\nIndonesia bersama kami.",
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.85f),
                lineHeight = 19.sp
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

        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            listOf("🐋", "🐠", "🌿").forEach { emoji ->
                Surface(
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.15f),
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(emoji, fontSize = 22.sp)
                    }
                }
            }
        }
    }
}

// ─── Features Section ────────────────────────────────────────────────────────

@Composable
fun FeaturesSection() {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        SectionHeader(title = "Fitur Unggulan", actionLabel = null)

        Spacer(modifier = Modifier.height(14.dp))

        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            features.forEach { feature ->
                FeatureCardItem(feature = feature)
            }
        }
    }
}

@Composable
fun FeatureCardItem(feature: FeatureCard) {
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
            .clickable { /* TODO: navigate ke feature.route */ }
            .drawBehind {
                drawCircle(
                    color = Color.White.copy(alpha = 0.06f),
                    radius = 100.dp.toPx(),
                    center = Offset(size.width * 0.85f, size.height * 0.3f)
                )
                drawCircle(
                    color = Color.White.copy(alpha = 0.04f),
                    radius = 60.dp.toPx(),
                    center = Offset(size.width * 0.75f, size.height * 0.9f)
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
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    modifier = Modifier.clickable {}
                ) {
                    Text(
                        "Donasi Sekarang  →",
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B4332)
                    )
                }
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

// ─── Preview ──────────────────────────────────────────────────────────────────

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}
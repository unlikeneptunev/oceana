package com.kelompok3.oceana.ui.screen.MarineLife

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.delay
import com.kelompok3.oceana.navigation.OceanaRoute
import com.kelompok3.oceana.ui.screen.auth.AuthViewModel

// ─── Color Palette (matches HomeScreen) ──────────────────────────────────────
private val OceanDeep     = Color(0xFF023E8A)
private val OceanMid      = Color(0xFF0077B6)
private val OceanLight    = Color(0xFF00B4D8)
private val OceanSurface  = Color(0xFFCAF0F8)
private val TealCard      = Color(0xFF3DCFC7)
private val TealCardDark  = Color(0xFF2BAFA8)
private val CoralAccent   = Color(0xFFE76F51)
private val TextPrimary   = Color(0xFF0D1B2A)
private val TextSecondary = Color(0xFF4A5568)

// ─── Data model ──────────────────────────────────────────────────────────────
data class MarineChapter(
    val id: Int,
    val title: String,
    val isUnlocked: Boolean,
    val emoji: String = ""
)

val marineChapters = listOf(
    MarineChapter(id = 1, title = "The Reef", isUnlocked = true,  emoji = "🐠"),
    MarineChapter(id = 2, title = "Terkunci", isUnlocked = false),
    MarineChapter(id = 3, title = "Terkunci", isUnlocked = false),
    MarineChapter(id = 4, title = "Terkunci", isUnlocked = false),
)

// ─── Main Screen ─────────────────────────────────────────────────────────────
@Composable
fun MarineLifeScreen(
    navController: androidx.navigation.NavController,
    authViewModel: AuthViewModel
) {
    val authState by authViewModel.authState.collectAsState()
    val username = authState.username ?: ""

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(100)
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF4AC8E8), Color(0xFF1A7EC8), Color(0xFF0D5DAD))
                )
            )
    ) {
        DecorativeBackground()

        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // ── Top Navigation Bar ──
            AnimatedVisibility(visible = visible, enter = fadeIn()) {
                MarineLifeTopBar(
                    username       = username,
                    onHomeClick    = {
                        navController.navigate(OceanaRoute.HOME) {
                            popUpTo(OceanaRoute.HOME) { inclusive = false }
                        }
                    },
                    onProfileClick = { navController.navigate(OceanaRoute.PROFILE) },
                    onLogoutClick  = {
                        authViewModel.logout()
                        navController.navigate(OceanaRoute.LOGIN) { popUpTo(0) { inclusive = true } }
                    }
                )
            }

            // ── Content ────────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter   = fadeIn() + slideInVertically(initialOffsetY = { -40 })
                ) {
                    Text(
                        text       = "Marine Life",
                        fontSize   = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                AnimatedVisibility(
                    visible = visible,
                    enter   = fadeIn() + slideInVertically(initialOffsetY = { 60 })
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        marineChapters.chunked(2).forEach { rowChapters ->
                            Row(
                                modifier              = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                rowChapters.forEach { chapter ->
                                    ChapterCard(
                                        chapter  = chapter,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                if (rowChapters.size == 1) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }

            Spacer(modifier = Modifier.height(100.dp))
        }

        SeaBottomDecor(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}

// ─── Top Bar (Matches OceanaTopBar in HomeScreen.kt) ─────────────────────────
@Composable
fun MarineLifeTopBar(
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
        verticalAlignment     = Alignment.CenterVertically
    ) {
        // ── Logo + Dropdown ───────────────────────────────────────────────
        Box {
            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier              = Modifier.clickable { menuExpanded = true }
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(OceanDeep, OceanLight),
                                start  = Offset(0f, 0f),
                                end    = Offset(36f, 36f)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector        = Icons.Filled.Waves,
                        contentDescription = null,
                        tint               = Color.White,
                        modifier           = Modifier.size(20.dp)
                    )
                }
                Text(
                    "OCEANA",
                    fontSize     = 18.sp,
                    fontWeight   = FontWeight.Bold,
                    color        = OceanDeep,
                    letterSpacing = 1.sp
                )
            }

            // ── Dropdown Menu ─────────────────────────────────────────────
            DropdownMenu(
                expanded         = menuExpanded,
                onDismissRequest = { menuExpanded = false },
                modifier         = Modifier
                    .background(Color.White)
                    .width(200.dp)
            ) {
                // Header: logged-in user info
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Column {
                        Text(
                            text     = "Masuk sebagai",
                            fontSize = 11.sp,
                            color    = TextSecondary
                        )
                        Text(
                            text       = username,
                            fontSize   = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color      = TextPrimary
                        )
                    }
                }

                Divider(color = OceanSurface, thickness = 1.dp)

                // ── Dashboard / Beranda ───────────────────────────────────
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment     = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector        = Icons.Filled.Home,
                                contentDescription = null,
                                tint               = OceanDeep,
                                modifier           = Modifier.size(18.dp)
                            )
                            Text(
                                "Dashboard",
                                fontSize = 14.sp,
                                color    = TextPrimary
                            )
                        }
                    },
                    onClick = {
                        menuExpanded = false
                        onHomeClick()
                    }
                )

                Divider(color = OceanSurface, thickness = 1.dp)

                // ── Lihat Profil ──────────────────────────────────────────
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment     = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector        = Icons.Filled.Person,
                                contentDescription = null,
                                tint               = OceanMid,
                                modifier           = Modifier.size(18.dp)
                            )
                            Text(
                                "Lihat Profil",
                                fontSize = 14.sp,
                                color    = TextPrimary
                            )
                        }
                    },
                    onClick = {
                        menuExpanded = false
                        onProfileClick()
                    }
                )

                // ── Keluar ────────────────────────────────────────────────
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment     = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector        = Icons.Filled.Logout,
                                contentDescription = null,
                                tint               = CoralAccent,
                                modifier           = Modifier.size(18.dp)
                            )
                            Text(
                                "Keluar",
                                fontSize = 14.sp,
                                color    = CoralAccent
                            )
                        }
                    },
                    onClick = {
                        menuExpanded = false
                        onLogoutClick()
                    }
                )
            }
        }

        // ── Right side: Search + Notifications ───────────────────────────
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

// ─── Chapter Card ─────────────────────────────────────────────────────────────
@Composable
fun ChapterCard(chapter: MarineChapter, modifier: Modifier = Modifier) {
    val cardColor = if (chapter.isUnlocked) TealCard else TealCardDark.copy(alpha = 0.85f)

    Box(
        modifier = modifier
            .aspectRatio(0.85f)
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        cardColor.copy(alpha = 0.95f),
                        cardColor.copy(alpha = 0.75f)
                    )
                )
            )
            .border(
                width = 2.dp,
                color = Color.White.copy(alpha = 0.35f),
                shape = RoundedCornerShape(24.dp)
            )
            .clickable(enabled = chapter.isUnlocked) {}
    ) {
        Column(
            modifier            = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                if (!chapter.isUnlocked) {
                    Icon(
                        imageVector        = Icons.Filled.Lock,
                        contentDescription = "Terkunci",
                        tint               = Color.White.copy(alpha = 0.5f),
                        modifier           = Modifier
                            .size(18.dp)
                            .align(Alignment.TopEnd)
                    )
                }
            }

            Box(
                modifier         = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (chapter.isUnlocked) {
                    Box(
                        modifier         = Modifier
                            .size(110.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("🪸", fontSize = 40.sp)
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text("⭐", fontSize = 20.sp)
                                Text("🌿", fontSize = 20.sp)
                            }
                        }
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier         = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("?", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier         = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.15f))
                                .align(Alignment.End),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("?", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.8f))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text       = chapter.title,
                fontSize   = 15.sp,
                fontWeight = FontWeight.Bold,
                color      = Color.White,
                textAlign  = TextAlign.Center
            )
        }
    }
}

// ─── Decorative Background ────────────────────────────────────────────────────
@Composable
private fun DecorativeBackground() {
    Box(modifier = Modifier
        .fillMaxSize()
        .drawBehind {
            drawCircle(
                color  = Color.White.copy(alpha = 0.06f),
                radius = 160.dp.toPx(),
                center = Offset(size.width * 0.9f, size.height * 0.1f)
            )
            drawCircle(
                color  = Color.White.copy(alpha = 0.04f),
                radius = 120.dp.toPx(),
                center = Offset(size.width * 0.1f, size.height * 0.75f)
            )
            drawCircle(
                color  = Color.White.copy(alpha = 0.05f),
                radius = 60.dp.toPx(),
                center = Offset(size.width * 0.08f, size.height * 0.35f)
            )
        }
    )
}

// ─── Sea Bottom Decorations ───────────────────────────────────────────────────
@Composable
private fun SeaBottomDecor(modifier: Modifier = Modifier) {
    Box(modifier = modifier.height(90.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.Bottom
        ) {
            Text("🪸", fontSize = 36.sp)
            Text("🌿", fontSize = 28.sp)
            Text("🐚", fontSize = 22.sp)
            Text("🌿", fontSize = 32.sp)
            Text("🪸", fontSize = 30.sp)
        }
    }
}
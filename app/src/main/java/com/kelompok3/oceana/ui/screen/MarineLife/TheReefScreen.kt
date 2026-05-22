package com.kelompok3.oceana.ui.screen.MarineLife

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kelompok3.oceana.navigation.OceanaRoute
import kotlinx.coroutines.delay

// ─── Color Palette ────────────────────────────────────────────────────────────
private val ReefGradientTop    = Color(0xFF4AC8E8)
private val ReefGradientMid    = Color(0xFF1A7EC8)
private val ReefGradientBottom = Color(0xFF0D5DAD)
private val TealCard           = Color(0xFF3DCFC7)
private val CoralPink          = Color(0xFFE8447A)
private val CoralAccent        = Color(0xFFE76F51)
private val CardBg             = Color(0x26FFFFFF)
private val TextPrimary        = Color(0xFF0D1B2A)
private val TextWhite          = Color.White
private val TextWhiteSoft      = Color.White.copy(alpha = 0.85f)

// ─── Data Models ──────────────────────────────────────────────────────────────
data class ReefZone(
    val title: String,
    val depth: String,
    val description: String,
    val emoji: String,
    val temperature: String
)

data class ReefCreature(
    val name: String,
    val emoji: String,
    val type: String
)

val reefZones = listOf(
    ReefZone(
        title       = "Fitoplankton",
        depth       = "Permukaan laut",
        description = "Organisme mikroskopis yang melakukan fotosintesis dan menjadi dasar rantai makanan laut.",
        emoji       = "🌿",
        temperature = "Cahaya tinggi"
    ),
    ReefZone(
        title       = "Terumbu Karang",
        depth       = "Perairan dangkal",
        description = "Ekosistem karang tumbuh baik karena mendapat cukup sinar matahari dan menjadi rumah banyak ikan kecil.",
        emoji       = "🪸",
        temperature = "Hangat"
    ),
    ReefZone(
        title       = "Ikan Tropis",
        depth       = "Sekitar karang",
        description = "Ikan berwarna cerah banyak hidup di area terang untuk mencari makan dan berlindung di antara karang.",
        emoji       = "🐠",
        temperature = "20-30 C"
    ),
    ReefZone(
        title       = "Lamun dan Rumput Laut",
        depth       = "Dasar dangkal",
        description = "Tumbuhan laut memanfaatkan cahaya matahari untuk tumbuh dan menyediakan tempat berlindung bagi biota kecil.",
        emoji       = "🌱",
        temperature = "Fotosintesis"
    ),
    ReefZone(
        title       = "Penyu dan Lumba-lumba",
        depth       = "Dekat permukaan",
        description = "Hewan besar seperti penyu dan lumba-lumba sering berada di zona ini untuk mencari makan dan bernapas.",
        emoji       = "🐢",
        temperature = "Aktif"
    )
)

val reefCreatures = listOf(
    ReefCreature("Ikan Badut",    "🐠", "Ikan Tropis"),
    ReefCreature("Lumba-lumba",   "🐬", "Mamalia Laut"),
    ReefCreature("Penyu Hijau",   "🐢", "Reptil Laut"),
    ReefCreature("Bintang Laut",  "⭐", "Echinodermata"),
    ReefCreature("Ubur-ubur",     "🪼", "Cnidaria"),
    ReefCreature("Kuda Laut",     "🐴", "Ikan Unik"),
    ReefCreature("Gurita",        "🐙", "Moluska"),
    ReefCreature("Hiu Karang",    "🦈", "Ikan Predator"),
)

// ─── Main Screen ──────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TheReefScreen(navController: NavController) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(100)
        visible = true
    }

    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(ReefGradientTop, ReefGradientMid, ReefGradientBottom)
                )
            )
    ) {
        LazyColumn(
            state             = listState,
            modifier          = Modifier.fillMaxSize(),
            contentPadding    = PaddingValues(bottom = 40.dp)
        ) {
            // ── Top Bar ──────────────────────────────────────────────────
            item {
                AnimatedVisibility(
                    visible = visible,
                    enter   = fadeIn(tween(400))
                ) {
                    ReefTopBar(onBack = { navController.popBackStack() })
                }
            }

            // ── Hero Section ─────────────────────────────────────────────
            item {
                AnimatedVisibility(
                    visible = visible,
                    enter   = fadeIn(tween(500)) + slideInVertically(tween(500)) { -60 }
                ) {
                    ReefHeroSection()
                }
            }

            // ── Zona Kedalaman Section Header ─────────────────────────────
            item {
                AnimatedVisibility(
                    visible = visible,
                    enter   = fadeIn(tween(650))
                ) {
                    SectionHeader(title = "☀️ Isi Sunlight Zone")
                }
            }

            // ── Zone Cards (lazy) ─────────────────────────────────────────
            items(reefZones) { zone ->
                AnimatedVisibility(
                    visible = visible,
                    enter   = fadeIn(tween(700)) + slideInVertically(tween(700)) { 80 }
                ) {
                    ReefZoneCard(zone = zone)
                }
            }

            // ── Creatures Section Header ──────────────────────────────────
            item {
                AnimatedVisibility(
                    visible = visible,
                    enter   = fadeIn(tween(750))
                ) {
                    SectionHeader(title = "🐟 Penghuni Terumbu Karang")
                }
            }

            // ── Creatures Horizontal Scroll (LazyRow inside LazyColumn) ──
            item {
                AnimatedVisibility(
                    visible = visible,
                    enter   = fadeIn(tween(800)) + slideInVertically(tween(800)) { 60 }
                ) {
                    LazyRow(
                        contentPadding    = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier          = Modifier.padding(bottom = 8.dp)
                    ) {
                        items(reefCreatures) { creature ->
                            CreatureChip(creature = creature)
                        }
                    }
                }
            }

            // ── Fun Facts Section ─────────────────────────────────────────
            item {
                AnimatedVisibility(
                    visible = visible,
                    enter   = fadeIn(tween(850))
                ) {
                    SectionHeader(title = "💡 Fakta Menarik")
                }
            }

            items(funFacts) { fact ->
                AnimatedVisibility(
                    visible = visible,
                    enter   = fadeIn(tween(900)) + slideInVertically(tween(900)) { 60 }
                ) {
                    FunFactCard(fact = fact)
                }
            }

            // ── Mulai Button ─────────────────────────────────────────────
            item {
                AnimatedVisibility(
                    visible = visible,
                    enter   = fadeIn(tween(950)) + slideInVertically(tween(950)) { 60 }
                ) {
                    Box(
                        modifier         = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick  = { navController.navigate(OceanaRoute.REEF_QUIZ) },
                            colors   = ButtonDefaults.buttonColors(containerColor = CoralPink),
                            shape    = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .width(180.dp)
                                .height(52.dp)
                        ) {
                            Icon(
                                imageVector        = Icons.Filled.PlayArrow,
                                contentDescription = null,
                                tint               = Color.White,
                                modifier           = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text       = "Mulai",
                                fontSize   = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color      = Color.White
                            )
                        }
                    }
                }
            }

            // ── Bottom padding ────────────────────────────────────────────
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

// ─── Top Bar ──────────────────────────────────────────────────────────────────
@Composable
private fun ReefTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp)
            .background(Color.White.copy(alpha = 0.95f))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector        = Icons.Filled.ArrowBack,
                contentDescription = "Kembali",
                tint               = TextPrimary
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text       = "The Reef",
            fontSize   = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color      = TextPrimary
        )
    }
}

// ─── Hero Section ─────────────────────────────────────────────────────────────
@Composable
private fun ReefHeroSection() {
    Column(
        modifier            = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Decorative fish row
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("🐡", fontSize = 28.sp)
            Text("🐠", fontSize = 36.sp)
            Text("🐡", fontSize = 22.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text       = "THE REEF",
            fontSize   = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color      = Color.White,
            letterSpacing = 3.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text      = "🐟 🐟 🐟",
            fontSize  = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Chapter label
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(TealCard.copy(alpha = 0.5f))
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(
                text       = "Sunlight Zone",
                fontSize   = 18.sp,
                fontWeight = FontWeight.Bold,
                color      = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Description card
        Card(
            shape  = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardBg),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text      = "Sunlight Zone adalah salah satu zona kedalaman laut yang paling atas, zona ini dari permukaan laut hingga 213 meter. Sebab paling dangkal, maka pada zona ini suhu airnya masih terbilang hangat karena masih disinari cahaya matahari. Pada zona ini, sebagian besar hewan laut seperti photoplankton hidup dan membangun ekosistem di dalamnya.",
                fontSize  = 14.sp,
                color     = TextWhiteSoft,
                textAlign = TextAlign.Center,
                modifier  = Modifier.padding(16.dp),
                lineHeight = 22.sp
            )
        }
    }
}

// ─── Section Header ────────────────────────────────────────────────────────────
@Composable
private fun SectionHeader(title: String) {
    Text(
        text       = title,
        fontSize   = 18.sp,
        fontWeight = FontWeight.Bold,
        color      = Color.White,
        modifier   = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
    )
}

// ─── Zone Card ────────────────────────────────────────────────────────────────
@Composable
private fun ReefZoneCard(zone: ReefZone) {
    Card(
        shape    = RoundedCornerShape(16.dp),
        colors   = CardDefaults.cardColors(containerColor = CardBg),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp)
    ) {
        Row(
            modifier          = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier         = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(zone.emoji, fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text       = zone.title,
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextWhite
                )
                Text(
                    text     = zone.depth,
                    fontSize = 12.sp,
                    color    = TealCard
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text      = zone.description,
                    fontSize  = 12.sp,
                    color     = TextWhiteSoft,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🌡️", fontSize = 16.sp)
                Text(
                    text     = zone.temperature,
                    fontSize = 11.sp,
                    color    = TextWhiteSoft
                )
            }
        }
    }
}

// ─── Creature Chip ────────────────────────────────────────────────────────────
@Composable
private fun CreatureChip(creature: ReefCreature) {
    Column(
        modifier            = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(CardBg)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(creature.emoji, fontSize = 32.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text       = creature.name,
            fontSize   = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color      = TextWhite
        )
        Text(
            text     = creature.type,
            fontSize = 10.sp,
            color    = TealCard
        )
    }
}

// ─── Fun Facts ────────────────────────────────────────────────────────────────
private val funFacts = listOf(
    "🪸 Terumbu karang hanya menempati <1% dasar laut, namun menjadi rumah bagi lebih dari 25% spesies laut.",
    "🌊 Sunlight Zone menerima cukup cahaya untuk mendukung fotosintesis, itulah mengapa kehidupan sangat melimpah di sini.",
    "🐠 Ikan badut (Nemo) hidup dalam simbiosis mutualisme dengan anemon laut di zona ini.",
    "☀️ Suhu air di Sunlight Zone bervariasi dari 20°C hingga 30°C tergantung musim dan lokasi."
)

@Composable
private fun FunFactCard(fact: String) {
    Card(
        shape    = RoundedCornerShape(14.dp),
        colors   = CardDefaults.cardColors(containerColor = CardBg),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp)
    ) {
        Text(
            text      = fact,
            fontSize  = 13.sp,
            color     = TextWhiteSoft,
            lineHeight = 20.sp,
            modifier  = Modifier.padding(14.dp)
        )
    }
}
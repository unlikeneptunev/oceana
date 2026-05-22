package com.kelompok3.oceana.ui.screen.MarineLife

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

// ─── Color Palette (sama dengan TheReefScreen) ────────────────────────────────
private val QuizGradientTop    = Color(0xFF4AC8E8)
private val QuizGradientMid    = Color(0xFF1A7EC8)
private val QuizGradientBottom = Color(0xFF0D5DAD)
private val QuizCardBg         = Color(0xFF2196C8).copy(alpha = 0.85f)
private val QuizCardSelected   = Color(0xFF1A7EC8)
private val QuizCardBorder     = Color(0xFF5BC8E8)
private val CoralPinkBtn       = Color(0xFFE8447A)
private val BubbleColor        = Color.White.copy(alpha = 0.25f)

// ─── Data Model ───────────────────────────────────────────────────────────────
data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int
)

val reefQuizQuestions = listOf(
    QuizQuestion(
        question     = "Berapakah rentang kedalaman dari Sunlight Zone?",
        options      = listOf(
            "Rentang kedalaman dari Sunlight Zone yakni mulai dari permukaan laut hingga 213 meter.",
            "Rentang kedalaman dari Sunlight Zone yakni mulai dari permukaan laut hingga 120 meter."
        ),
        correctIndex = 0
    ),
    QuizQuestion(
        question     = "Apa yang membuat Sunlight Zone berbeda dari zona laut lainnya?",
        options      = listOf(
            "Sunlight Zone adalah zona terdalam di lautan dan tidak mendapat cahaya matahari.",
            "Sunlight Zone mendapat cukup cahaya matahari sehingga suhunya hangat dan mendukung fotosintesis."
        ),
        correctIndex = 1
    ),
    QuizQuestion(
        question     = "Organisme apa yang menjadi dasar rantai makanan di Sunlight Zone?",
        options      = listOf(
            "Fitoplankton yang melakukan fotosintesis menjadi dasar rantai makanan laut.",
            "Hiu karang yang merupakan predator puncak di zona ini."
        ),
        correctIndex = 0
    ),
    QuizQuestion(
        question     = "Berapa persen dasar laut yang ditempati oleh terumbu karang?",
        options      = listOf(
            "Terumbu karang menempati lebih dari 50% dasar laut di seluruh dunia.",
            "Terumbu karang hanya menempati kurang dari 1% dasar laut namun menjadi rumah 25% spesies laut."
        ),
        correctIndex = 1
    ),
    QuizQuestion(
        question     = "Berapa suhu air di Sunlight Zone?",
        options      = listOf(
            "Suhu air di Sunlight Zone bervariasi dari 20°C hingga 30°C tergantung musim dan lokasi.",
            "Suhu air di Sunlight Zone selalu berada di bawah 10°C sepanjang tahun."
        ),
        correctIndex = 0
    )
)

// ─── Main Quiz Screen ─────────────────────────────────────────────────────────
@Composable
fun QuizScreen(navController: NavController) {
    var currentIndex  by remember { mutableStateOf(0) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var isAnswered    by remember { mutableStateOf(false) }
    var score         by remember { mutableStateOf(0) }
    var showResult    by remember { mutableStateOf(false) }
    var visible       by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        visible = true
    }

    // Reset animasi saat soal berganti
    LaunchedEffect(currentIndex) {
        visible = false
        delay(150)
        visible = true
        selectedIndex = null
        isAnswered    = false
    }

    val question = reefQuizQuestions[currentIndex]

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(QuizGradientTop, QuizGradientMid, QuizGradientBottom)
                )
            )
    ) {
        // Dekorasi bubble kiri atas
        BubbleDecoration()

        if (showResult) {
            QuizResultSection(
                score      = score,
                total      = reefQuizQuestions.size,
                onRestart  = {
                    currentIndex  = 0
                    score         = 0
                    showResult    = false
                    selectedIndex = null
                    isAnswered    = false
                },
                onBack     = { navController.popBackStack() }
            )
        } else {
            Column(
                modifier            = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(48.dp))

                // Progress indicator
                AnimatedVisibility(
                    visible = visible,
                    enter   = fadeIn(tween(300))
                ) {
                    QuizProgressBar(
                        current = currentIndex + 1,
                        total   = reefQuizQuestions.size
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Question text
                AnimatedVisibility(
                    visible = visible,
                    enter   = fadeIn(tween(400)) + slideInVertically(tween(400)) { -40 }
                ) {
                    Text(
                        text       = question.question,
                        fontSize   = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Color.White,
                        textAlign  = TextAlign.Center,
                        modifier   = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Answer options
                question.options.forEachIndexed { idx, option ->
                    AnimatedVisibility(
                        visible = visible,
                        enter   = fadeIn(tween(500 + idx * 100)) +
                                slideInVertically(tween(500 + idx * 100)) { 60 }
                    ) {
                        QuizOptionCard(
                            text        = option,
                            isSelected  = selectedIndex == idx,
                            isAnswered  = isAnswered,
                            isCorrect   = idx == question.correctIndex,
                            onClick     = {
                                if (!isAnswered) {
                                    selectedIndex = idx
                                    isAnswered    = true
                                    if (idx == question.correctIndex) score++
                                }
                            },
                            modifier    = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Next / Selesai button
                AnimatedVisibility(
                    visible = visible && isAnswered,
                    enter   = fadeIn(tween(300)) + slideInVertically(tween(300)) { 40 }
                ) {
                    val isLast = currentIndex == reefQuizQuestions.lastIndex
                    Button(
                        onClick  = {
                            if (isLast) {
                                showResult = true
                            } else {
                                currentIndex++
                            }
                        },
                        colors   = ButtonDefaults.buttonColors(containerColor = CoralPinkBtn),
                        shape    = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(52.dp)
                    ) {
                        Text(
                            text       = if (isLast) "Lihat Hasil" else "Selanjutnya",
                            fontSize   = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color      = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))
            }
        }
    }
}

// ─── Progress Bar ─────────────────────────────────────────────────────────────
@Composable
private fun QuizProgressBar(current: Int, total: Int) {
    Column(
        modifier            = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text      = "Soal $current / $total",
            fontSize  = 14.sp,
            color     = Color.White.copy(alpha = 0.85f),
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.White.copy(alpha = 0.25f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(current.toFloat() / total)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50))
                    .background(CoralPinkBtn)
            )
        }
    }
}

// ─── Option Card ──────────────────────────────────────────────────────────────
@Composable
private fun QuizOptionCard(
    text       : String,
    isSelected : Boolean,
    isAnswered : Boolean,
    isCorrect  : Boolean,
    onClick    : () -> Unit,
    modifier   : Modifier = Modifier
) {
    val bgColor = when {
        isAnswered && isSelected && isCorrect  -> Color(0xFF2ECC71).copy(alpha = 0.3f)
        isAnswered && isSelected && !isCorrect -> Color(0xFFE74C3C).copy(alpha = 0.3f)
        isAnswered && !isSelected && isCorrect -> Color(0xFF2ECC71).copy(alpha = 0.15f)
        isSelected                             -> QuizCardSelected.copy(alpha = 0.6f)
        else                                   -> QuizCardBg
    }

    val borderColor = when {
        isAnswered && isSelected && isCorrect  -> Color(0xFF2ECC71)
        isAnswered && isSelected && !isCorrect -> Color(0xFFE74C3C)
        isAnswered && !isSelected && isCorrect -> Color(0xFF2ECC71).copy(alpha = 0.7f)
        isSelected                             -> QuizCardBorder
        else                                   -> Color.White.copy(alpha = 0.3f)
    }

    // Bubble dekoratif di pojok kanan atas kartu
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .border(2.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable(enabled = !isAnswered) { onClick() }
    ) {
        // Bubble dekoratif dalam kartu
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.12f))
        )

        Text(
            text       = text,
            fontSize   = 14.sp,
            color      = Color.White,
            textAlign  = TextAlign.Center,
            lineHeight = 22.sp,
            modifier   = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 28.dp)
        )
    }
}

// ─── Result Screen ────────────────────────────────────────────────────────────
@Composable
private fun QuizResultSection(
    score    : Int,
    total    : Int,
    onRestart: () -> Unit,
    onBack   : () -> Unit
) {
    Column(
        modifier            = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val emoji = when {
            score == total      -> "🏆"
            score >= total / 2  -> "🎉"
            else                -> "📚"
        }
        Text(emoji, fontSize = 72.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text       = "Hasil Kuis",
            fontSize   = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color      = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text      = "Kamu menjawab benar $score dari $total soal",
            fontSize  = 16.sp,
            color     = Color.White.copy(alpha = 0.85f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Score card
        Card(
            shape  = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier            = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text       = "$score / $total",
                    fontSize   = 48.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color      = Color.White
                )
                Text(
                    text     = when {
                        score == total     -> "Sempurna! Kamu ahli kelautan! 🌊"
                        score >= total / 2 -> "Bagus! Terus belajar ya!"
                        else               -> "Jangan menyerah, coba lagi!"
                    },
                    fontSize  = 14.sp,
                    color     = Color.White.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick  = onRestart,
            colors   = ButtonDefaults.buttonColors(containerColor = CoralPinkBtn),
            shape    = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("Coba Lagi", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick  = onBack,
            shape    = RoundedCornerShape(12.dp),
            colors   = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("Kembali ke The Reef", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
    }
}

// ─── Bubble Decoration ────────────────────────────────────────────────────────
@Composable
private fun BubbleDecoration() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Bubble kiri atas
        Box(
            modifier = Modifier
                .padding(start = 16.dp, top = 80.dp)
                .size(18.dp)
                .clip(CircleShape)
                .background(BubbleColor)
        )
        Box(
            modifier = Modifier
                .padding(start = 40.dp, top = 110.dp)
                .size(10.dp)
                .clip(CircleShape)
                .background(BubbleColor)
        )
        // Bubble kanan atas
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 24.dp, top = 90.dp)
                .size(22.dp)
                .clip(CircleShape)
                .background(BubbleColor)
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 55.dp, top = 120.dp)
                .size(12.dp)
                .clip(CircleShape)
                .background(BubbleColor)
        )
    }
}
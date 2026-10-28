package com.yara.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val sender: String,
    val content: String,
    val isUser: Boolean,
    val timestamp: String = "Now"
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YaraAITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    YaraMainApp()
                }
            }
        }
    }
}

@Composable
fun YaraAITheme(content: @Composable () -> Unit) {
    val darkColors = darkColorScheme(
        primary = Color(0xFF00E5FF),
        secondary = Color(0xFF7C4DFF),
        background = Color(0xFF0A0A12),
        surface = Color(0xFF14142B),
        onSurface = Color.White
    )
    MaterialTheme(
        colorScheme = darkColors,
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YaraMainApp() {
    var selectedTab by remember { mutableStateOf(0) }
    var inputText by remember { mutableStateOf("") }
    
    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                sender = "Yara AI v13",
                content = "Welcome HASHIM (@hashim_m4x)! Yara AI v13 Ultimate is fully online with Multi-API Cluster, Offline Brain & Custom Emotion Engine.",
                isUser = false
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        listOf(Color(0xFF00E5FF), Color(0xFF7C4DFF))
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Y13", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Yara AI v13 Ultimate", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                            Text("Owner: HASHIM (@hashim_m4x) • Online", fontSize = 11.sp, color = Color(0xFF00E5FF))
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF10101E))
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF10101E)) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Chat, contentDescription = "Chat") },
                    label = { Text("Chat AI") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Analytics, contentDescription = "Analytics") },
                    label = { Text("Analytics") }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.Psychology, contentDescription = "Cluster") },
                    label = { Text("API Cluster") }
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF0A0A12))
        ) {
            when (selectedTab) {
                0 -> ChatScreen(
                    messages = messages,
                    inputText = inputText,
                    onInputChanged = { inputText = it },
                    onSend = {
                        if (inputText.isNotBlank()) {
                            val userMsg = inputText
                            messages.add(ChatMessage(sender = "HASHIM", content = userMsg, isUser = true))
                            inputText = ""
                            
                            // Neural Response simulation
                            val aiResponse = when {
                                userMsg.contains("hello", ignoreCase = true) || userMsg.contains("hi", ignoreCase = true) ->
                                    "Hello Boss HASHIM! Ready to execute any code, data analysis, or automated workflow."
                                userMsg.contains("status", ignoreCase = true) ->
                                    "All systems operational! Multi-API Cluster: Active | Offline Engine: Ready | Build Engine: Optimal."
                                else -> "Yara AI v13 processed: '$userMsg'. Executing high-speed neural processing pipeline."
                            }
                            messages.add(ChatMessage(sender = "Yara AI v13", content = aiResponse, isUser = false))
                        }
                    }
                )
                1 -> FeatureCardScreen("Data Analytics & Insights Engine", "Real-time performance tracking and predictive AI model dashboard.")
                2 -> FeatureCardScreen("Multi-API Cluster & Offline Brain", "Active Cluster: Gemini, GPT-4, Llama 3 & Local Neural Fallback.")
                3 -> FeatureCardScreen("Yara Emotion & Identity Engine", "Configured for Owner: HASHIM (@hashim_m4x). Status: Deep Synced.")
            }
        }
    }
}

@Composable
fun ChatScreen(
    messages: List<ChatMessage>,
    inputText: String,
    onInputChanged: (String) -> Unit,
    onSend: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages, key = { it.id }) { msg ->
                ChatBubble(msg)
            }
        }

        Surface(
            color = Color(0xFF14142B),
            tonalElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = onInputChanged,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Command Yara AI v13...", color = Color.Gray) },
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF00E5FF),
                        unfocusedBorderColor = Color(0xFF333355),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    maxLines = 3
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = onSend,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFF00E5FF), Color(0xFF7C4DFF))
                            )
                        )
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.Black)
                }
            }
        }
    }
}

@Composable
fun ChatBubble(msg: ChatMessage) {
    val alignment = if (msg.isUser) Alignment.CenterEnd else Alignment.CenterStart
    val bgGradient = if (msg.isUser) {
        Brush.linearGradient(listOf(Color(0xFF7C4DFF), Color(0xFF6200EE)))
    } else {
        Brush.linearGradient(listOf(Color(0xFF1E1E3A), Color(0xFF252545)))
    }

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = alignment) {
        Column(
            horizontalAlignment = if (msg.isUser) Alignment.End else Alignment.Start,
            modifier = Modifier.widthIn(max = 300.dp)
        ) {
            Text(
                text = msg.sender,
                fontSize = 10.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 2.dp)
            )
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (msg.isUser) 16.dp else 2.dp,
                            bottomEnd = if (msg.isUser) 2.dp else 16.dp
                        )
                    )
                    .background(bgGradient)
                    .padding(12.dp)
            ) {
                Text(
                    text = msg.content,
                    color = Color.White,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
fun FeatureCardScreen(title: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFF14142B),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = Color(0xFF00E5FF),
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(title, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Text(description, color = Color.LightGray, fontSize = 14.sp)
            }
        }
    }
}

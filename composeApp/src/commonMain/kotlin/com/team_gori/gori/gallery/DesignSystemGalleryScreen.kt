package com.team_gori.gori.gallery

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.team_gori.gori.designsystem.component.GoriPreviewContent
import com.team_gori.gori.designsystem.theme.CustomTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesignSystemGalleryScreen(
    onNavigateBack: () -> Unit
) {
    CustomTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("디자인 시스템 갤러리") },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로가기")
                        }
                    }
                )
            }
        ) { innerPadding ->
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                item {
                    GoriPreviewContent()
                }
            }
        }
    }
}
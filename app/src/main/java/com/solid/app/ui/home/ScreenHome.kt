package com.solid.app.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.solid.app.widget.month.JcYearMonth
import com.solid.app.widget.year.JcYear
import org.threeten.bp.Year
import org.threeten.bp.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHome() {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            JcYear(
                modifier = Modifier.fillMaxWidth(),
                year = Year.now(),
                verticalDisplay = true
            )

            JcYearMonth(
                modifier = Modifier.fillMaxWidth(),
                yearMonth = YearMonth.now()
            )
        }
    }
}
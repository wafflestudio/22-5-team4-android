package com.example.interpark.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate


data class Performance(
    val id: String,
    val title: String,
    val content: String,
    val detail: String,
    val date: List<LocalDate>,
    val posterUrl: String,
    val backdropUrl: String,
    val category: String
)

@RequiresApi(Build.VERSION_CODES.O)
val listPerformance = listOf(
    Performance(
        id = "1",
        title = "Les Misérables",
        content = "A story of love, redemption, and revolution.",
        detail = "A sweeping musical adaptation of Victor Hugo's classic novel.",
        date = listOf(
            LocalDate.of(2024, 3, 15),
            LocalDate.of(2024, 3, 16),
            LocalDate.of(2024, 3, 17)
        ),
        posterUrl = "https://example.com/posters/les_miserables.jpg",
        backdropUrl = "https://example.com/backdrops/les_miserables.jpg",
        category = "뮤지컬"
    ),
    Performance(
        id = "2",
        title = "The Phantom of the Opera",
        content = "A tragic love story set in a Parisian opera house.",
        detail = "One of the most famous musicals of all time, featuring the music of Andrew Lloyd Webber.",
        date = listOf(
            LocalDate.of(2024, 4, 5),
            LocalDate.of(2024, 4, 6),
            LocalDate.of(2024, 4, 7)
        ),
        posterUrl = "https://example.com/posters/phantom.jpg",
        backdropUrl = "https://example.com/backdrops/phantom.jpg",
        category = "뮤지컬"
    ),
    Performance(
        id = "3",
        title = "Beethoven Symphony No.9",
        content = "Experience the masterpiece live.",
        detail = "A performance of Beethoven's Symphony No. 9, featuring the iconic 'Ode to Joy.'",
        date = listOf(
            LocalDate.of(2024, 5, 10),
            LocalDate.of(2024, 5, 11)
        ),
        posterUrl = "https://example.com/posters/beethoven.jpg",
        backdropUrl = "https://example.com/backdrops/beethoven.jpg",
        category = "뮤지컬"
    ),
    Performance(
        id = "4",
        title = "Swan Lake",
        content = "A timeless ballet about love and betrayal.",
        detail = "Pyotr Tchaikovsky's masterpiece performed by the National Ballet Company.",
        date = listOf(
            LocalDate.of(2024, 6, 1),
            LocalDate.of(2024, 6, 2),
            LocalDate.of(2024, 6, 3)
        ),
        posterUrl = "https://example.com/posters/swan_lake.jpg",
        backdropUrl = "https://example.com/backdrops/swan_lake.jpg",
        category = "뮤지컬"
    )
)

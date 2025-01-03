package com.example.interpark.data

import com.example.interpark.R

private fun getDummyPerformances(): List<Performance> {
    return listOf(
        Performance(
            title = "라파치니의 정원",
            location = "플러스씨어터",
            dateRange = "2025.01.30 - 2025.04.20",
            imageUrl = R.drawable.performance1
        ),
        Performance(
            title = "오지게 재밌는 가시나들",
            location = "국립극장 하늘극장",
            dateRange = "2025.02.11 - 2025.02.27",
            imageUrl = R.drawable.performance2
        ),
        Performance(
            title = "미아 파밀리아",
            location = "링크아트센터드림 드림1관",
            dateRange = "2024.12.19 - 2025.03.23",
            imageUrl = R.drawable.performance3
        ),
        Performance(
            title = "카포네 밀크",
            location = "예스24아트원 1관",
            dateRange = "2024.12.18 - 2025.03.09",
            imageUrl = R.drawable.performance4
        )
    )
}

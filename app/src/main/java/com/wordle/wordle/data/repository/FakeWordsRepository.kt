package com.wordle.wordle.data.repository

import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeWordsRepository @Inject constructor(

): WordsRepository {
    override fun getWordsByLength(length: Int) = flowOf(
listOf(
            "apple", "bread", "chair", "house", "river", "plant", "light", "world",
            "music", "dream", "stone", "cloud", "water", "table", "drink", "fruit",
            "green", "night", "sound", "earth", "story", "sweet", "money", "heart",
            "voice", "peace", "smile", "dance", "grass", "ocean", "sleep", "clock",
            "lemon", "glass",
            "animal", "forest", "summer", "school", "father", "mother", "little", "yellow",
            "garden", "flower", "island", "castle", "desert", "family", "energy", "winter",
            "writer", "player", "orange", "travel", "people", "beauty", "nature", "doctor",
            "planet", "circle", "market", "silver", "farmer", "hunter", "bottle", "engine",
            "ladder",
            "morning", "freedom", "library", "country", "diamond", "holiday", "journey", "rainbow",
            "blanket", "station", "picture", "message", "teacher", "student", "musical", "monster",
            "candle", "courage", "history", "fortune", "horizon", "fashion", "justice", "science",
            "village", "passion", "victory", "silence", "forward", "project", "digital", "balance",
            "respect"
        ).filter { it.length == length }.shuffled().map { it.uppercase() }
    )
}
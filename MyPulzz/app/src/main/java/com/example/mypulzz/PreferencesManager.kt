package com.example.mypulzz

import android.content.Context

object PreferencesManager {
    private const val PREF_NAME = "GamePreferences"
    private const val KEY_PUZZLE_SIZE = "puzzleSize"

    private const val KEY_HIGH_SCORE = "highScore"

    private const val INITIAL_HIGH_SCORE = 800

    fun savePuzzleSize(context: Context, size: Int) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt(KEY_PUZZLE_SIZE, size).apply()
    }

    fun getPuzzleSize(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_PUZZLE_SIZE, 4) // Default size is 4
    }

    fun saveHighScore(context: Context, score: Int) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val currentHighScore = sharedPreferences.getInt(KEY_HIGH_SCORE, INITIAL_HIGH_SCORE)
        if (score < currentHighScore || currentHighScore == INITIAL_HIGH_SCORE) {
            sharedPreferences.edit().putInt(KEY_HIGH_SCORE, score).apply()
        }
    }

    fun getHighScore(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_HIGH_SCORE, INITIAL_HIGH_SCORE)
    }
}

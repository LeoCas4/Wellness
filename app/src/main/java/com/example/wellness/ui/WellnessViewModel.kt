package com.example.wellness.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wellness.data.Habit
import com.example.wellness.data.WellnessDatabase
import com.example.wellness.network.HealthArticle
import com.example.wellness.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WellnessViewModel(application: Application) : AndroidViewModel(application) {

    private val habitDao = WellnessDatabase.getDatabase(application).habitDao()

    val habits: StateFlow<List<Habit>> = habitDao.getAllHabits()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _articles = MutableStateFlow<List<HealthArticle>>(emptyList())
    val articles: StateFlow<List<HealthArticle>> = _articles.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        fetchArticles()
    }

    fun addHabit(name: String) {
        viewModelScope.launch {
            habitDao.insertHabit(Habit(name = name))
        }
    }

    fun toggleHabitComplete(habit: Habit) {
        viewModelScope.launch {
            habitDao.updateHabit(habit.copy(isCompleted = !habit.isCompleted))
        }
    }

    fun fetchArticles() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                kotlinx.coroutines.delay(1000)
                _articles.value = RetrofitClient.api.getHealthArticles().take(10)
            } catch (e: Exception) {
                _errorMessage.value = "Error al conectar. Revisa tu internet."
            } finally {
                _isLoading.value = false
            }
        }
    }
}

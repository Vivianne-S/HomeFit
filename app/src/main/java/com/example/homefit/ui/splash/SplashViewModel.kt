package com.example.homefit.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    // LiveData för att styra synligheten av texterna på Splash-skärmen
    private val _showJourneyText = MutableLiveData(false)
    val showJourneyText: LiveData<Boolean> get() = _showJourneyText

    private val _showWithText = MutableLiveData(false)  // För "with"-texten
    val showWithText: LiveData<Boolean> get() = _showWithText

    private val _showHomeFitText = MutableLiveData(false)
    val showHomeFitText: LiveData<Boolean> get() = _showHomeFitText

    init {
        viewModelScope.launch {
            delay(1500) // Vänta 1.5 sekunder innan första texten visas
            _showJourneyText.value = true
            delay(600)  // Vänta ytterligare 0.6 sekunder innan "with" visas
            _showWithText.value = true
            delay(500)  // Vänta ytterligare 0.5 sekunder innan "HomeFit" visas
            _showHomeFitText.value = true
        }
    }
}

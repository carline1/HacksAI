package com.example.hacksai.ui.fragments.shipCard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.hacksai.api.services.MainService
import com.example.hacksai.di.appComponent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class ShipCardViewModel(application: Application) : AndroidViewModel(application) {

    init {
        application.appComponent.inject(this)
    }
    @Inject
    lateinit var mainService: MainService

    val compositeDisposable = CompositeDisposable()

    fun getImage(query: Map<String, String>) = mainService.getImage(query)
    fun getImageById(id: String) = mainService.getImageById(id)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}
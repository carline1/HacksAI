package com.example.hacksai.ui.fragments.sailors

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.hacksai.api.services.MainService
import com.example.hacksai.di.appComponent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class SailorsViewModel(application: Application): AndroidViewModel(application) {

    init {
        application.appComponent.inject(this)
    }
    @Inject
    lateinit var mainService: MainService

    val compositeDisposable = CompositeDisposable()

    fun getShipList() = mainService.getShips()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}
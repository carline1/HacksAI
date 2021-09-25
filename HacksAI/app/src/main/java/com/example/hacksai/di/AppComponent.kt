package com.example.hacksai.di

import com.example.hacksai.ui.fragments.sailors.SailorsViewModel
import com.example.hacksai.ui.fragments.shipCard.ShipCardViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RemoteModule::class])
@Singleton
interface AppComponent {

    fun inject(viewModel: SailorsViewModel)
    fun inject(viewModel: ShipCardViewModel)

}
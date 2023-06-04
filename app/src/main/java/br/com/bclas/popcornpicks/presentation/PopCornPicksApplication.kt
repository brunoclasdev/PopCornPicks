package br.com.bclas.popcornpicks.presentation

import android.app.Application
import br.com.bclas.popcornpicks.framework.di.loadMovieModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PopCornPicksApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@PopCornPicksApplication)
            modules(listOf())
        }
        loadMovieModules()
    }

}
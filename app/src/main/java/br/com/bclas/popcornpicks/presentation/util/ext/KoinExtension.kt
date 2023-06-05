package br.com.bclas.popcornpicks.presentation.util.ext

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

internal fun Activity.getScope(scopeNamed: String): Scope {
    val scope: Scope by lazy {
        getKoin().getOrCreateScope(
            scopeId = scopeNamed,
            qualifier = named(scopeNamed)
        )
    }
    return scope
}

internal inline fun <reified T : ViewModel> Scope.byViewModel(
    owner: LifecycleOwner
) : Lazy<T> {
    return lazy {
        viewModel<T>(owner).value
    }
}
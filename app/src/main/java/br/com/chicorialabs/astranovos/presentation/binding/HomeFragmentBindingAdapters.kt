package br.com.chicorialabs.astranovos.presentation.binding

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import br.com.chicorialabs.astranovos.R
import br.com.chicorialabs.astranovos.data.SpaceFlightNewsCategory
import com.google.android.material.appbar.MaterialToolbar

@BindingAdapter("toolbarTitle")
fun MaterialToolbar.setToolbarTitleFromCategory(category: LiveData<SpaceFlightNewsCategory>?) {
    category?.let {

        val stringResource = when(it.value) {
            SpaceFlightNewsCategory.ARTICLES -> R.string.latest_news
            SpaceFlightNewsCategory.BLOGS -> R.string.latest_blogs
            SpaceFlightNewsCategory.REPORTS -> R.string.latest_reports
            else -> { 0 }
        }

        this.title = context.getString(stringResource)

    }

}
package com.nicolepaupa.cartoonmansour.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nicolepaupa.cartoonmansour.entity.Video
import com.nicolepaupa.cartoonmansour.repo.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor(
   private val repository: DataRepository
) : ViewModel() {

    fun videoListLiveData(s: String): LiveData<List<Video>> {
        return repository.getAllVideo(s)
    }

}
package com.pagertask.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PagingViewModel @Inject constructor(val pagingRepository: PagingRepository):ViewModel() {

    val list=pagingRepository.getPost().cachedIn(viewModelScope)


}
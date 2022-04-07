package com.thingsflow.internapplication.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thingsflow.internapplication.data.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _issues = MutableLiveData<ArrayList<Item>>()
    val issues: LiveData<ArrayList<Item>> = _issues
    private val _orgName = MutableLiveData<String>()
    val orgName: LiveData<String> = _orgName
    private val _repoName = MutableLiveData<String>()
    val repoName: LiveData<String> = _repoName
    private val _loadingError = MutableLiveData<Boolean>()
    val loadingError: LiveData<Boolean> = _loadingError
    private val _selectedIssue = MutableLiveData<Item.Issue>()
    val selectedIssue = _selectedIssue
    private val _eventStartDetailActivity = MutableLiveData<Event<Unit>>()
    val eventStartDetailActivity: LiveData<Event<Unit>> = _eventStartDetailActivity

    companion object {
        const val POS_BANNER = 4
        const val URL_BANNER = "https://s3.ap-northeast-2.amazonaws.com/hellobot-kr-test/image/main_logo.png"
    }

    private fun loadIssues(orgName: String, repoName: String) {
        // github api에서 issue 목록을 가져옴
        /* Rxjava version
        mainRepository.getIssuesRx(orgName, repoName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d("SUCCESS: Get issue by rxjava", "${it.size}")

                    setLoadedIssues(it, orgName, repoName)
                },
                {
                    Log.e("ERROR: Get issue by rxjava", "${it.message}")
                    _loadingError.value = true
                }
            )
         */

        /* Coroutine flow version */
        viewModelScope.launch {
            try {
                val it = mainRepository.getIssuesFlow(orgName, repoName).single()

                Log.d("SUCCESS: Get issue by coroutine flow", "${it.size}")
                setLoadedIssues(it, orgName, repoName)
            } catch (e: Exception) {
                Log.e("ERROR: Get issue by rxjava", "${e.message}")
                _loadingError.value = true
            }
        }
    }

    private fun setLoadedIssues(it: List<Item.Issue>, orgName: String, repoName: String) {
        val list: MutableList<Item> = it.toMutableList()
        if (list.size >= POS_BANNER) {
            list.add(POS_BANNER, Item.Image(URL_BANNER))
        }
        _issues.value = ArrayList(list)

        setOrgName(orgName)
        setRepoName(repoName)
        _loadingError.value = false
    }

    private fun setOrgName(orgName: String) {
        _orgName.value = orgName
    }

    private fun setRepoName(repoName: String) {
        _repoName.value = repoName
    }

    fun changeTitle(orgName: String, repoName: String) {
        loadIssues(orgName, repoName)
    }

    fun clickIssue(issueIdx: Int) {
        _selectedIssue.value = issues.value?.get(issueIdx) as Item.Issue
        _eventStartDetailActivity.value = Event(Unit)
    }
}
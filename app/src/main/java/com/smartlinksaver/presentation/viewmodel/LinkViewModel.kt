package com.smartlinksaver.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartlinksaver.data.local.entity.Group
import com.smartlinksaver.data.local.entity.LinkItem
import com.smartlinksaver.data.repository.LinkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinkViewModel @Inject constructor(
    private val repository: LinkRepository
) : ViewModel() {

    private val _activeGroupFilter = MutableStateFlow<Long?>(null)
    val activeGroupFilter: StateFlow<Long?> = _activeGroupFilter.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val links: StateFlow<List<LinkItem>> = _activeGroupFilter
        .flatMapLatest { groupId ->
            if (groupId == null) repository.getAllLinks()
            else repository.getLinksByGroup(groupId)
        }
        .stateIn(
            scope        = viewModelScope,
            started      = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val groups: StateFlow<List<Group>> = repository.getAllGroups()
        .stateIn(
            scope        = viewModelScope,
            started      = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _uiState = MutableStateFlow<LinkUiState>(LinkUiState.Idle)
    val uiState: StateFlow<LinkUiState> = _uiState.asStateFlow()

    fun addLink(url: String, userNotes: String? = null, groupId: Long? = null) {
        viewModelScope.launch {
            _uiState.value = LinkUiState.Loading
            runCatching {
                val metadata = repository.fetchWebMetadata(url)
                repository.upsertLink(
                    LinkItem(
                        url            = url,
                        title          = metadata.title,
                        webDescription = metadata.description,
                        imageUrl       = metadata.imageUrl,
                        userNotes      = userNotes,
                        groupId        = groupId
                    )
                )
            }.onSuccess {
                _uiState.value = LinkUiState.Success
            }.onFailure { error ->
                _uiState.value = LinkUiState.Error(error.message ?: "Unknown error")
            }
        }
    }

    fun updateLink(linkItem: LinkItem) {
        viewModelScope.launch { repository.upsertLink(linkItem) }
    }

    fun deleteLink(linkItem: LinkItem) {
        viewModelScope.launch { repository.deleteLink(linkItem) }
    }

    fun addGroup(name: String) {
        viewModelScope.launch { repository.upsertGroup(Group(groupName = name)) }
    }

    fun deleteGroup(group: Group) {
        viewModelScope.launch { repository.deleteGroup(group) }
    }

    fun filterByGroup(groupId: Long?) {
        _activeGroupFilter.value = groupId
    }

    fun resetUiState() {
        _uiState.value = LinkUiState.Idle
    }
}

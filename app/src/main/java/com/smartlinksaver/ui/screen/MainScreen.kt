package com.smartlinksaver.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.CreateNewFolder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smartlinksaver.presentation.viewmodel.LinkUiState
import com.smartlinksaver.presentation.viewmodel.LinkViewModel
import com.smartlinksaver.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: LinkViewModel = hiltViewModel()) {
    val uiState       by viewModel.uiState.collectAsStateWithLifecycle()
    val links         by viewModel.links.collectAsStateWithLifecycle()
    val groups        by viewModel.groups.collectAsStateWithLifecycle()
    val activeGroupId by viewModel.activeGroupFilter.collectAsStateWithLifecycle()

    var isRtl              by rememberSaveable { mutableStateOf(false) }
    var showAddLinkDialog  by remember { mutableStateOf(false) }
    var showAddGroupDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is LinkUiState.Success -> {
                showAddLinkDialog = false
                viewModel.resetUiState()
            }
            is LinkUiState.Error -> {
                snackbarHostState.showSnackbar(state.message)
                viewModel.resetUiState()
            }
            else -> Unit
        }
    }

    CompositionLocalProvider(
        LocalLayoutDirection provides if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text  = "LinkDeck",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    actions = {
                        TextButton(onClick = { isRtl = !isRtl }) {
                            Text(
                                text  = if (isRtl) "EN" else "FA",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        IconButton(onClick = { showAddGroupDialog = true }) {
                            Icon(
                                imageVector        = Icons.Outlined.CreateNewFolder,
                                contentDescription = "Add Group",
                                tint               = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick        = { showAddLinkDialog = true },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor   = MaterialTheme.colorScheme.onPrimary,
                    shape          = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Outlined.Add, contentDescription = "Add Link")
                }
            },
            snackbarHost = {
                SnackbarHost(snackbarHostState) { data ->
                    Snackbar(
                        snackbarData   = data,
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor   = MaterialTheme.colorScheme.onError,
                        shape          = RoundedCornerShape(12.dp)
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (groups.isNotEmpty()) {
                    GroupFilterRow(
                        groups           = groups,
                        activeGroupId    = activeGroupId,
                        onFilterSelected = viewModel::filterByGroup,
                        modifier         = Modifier.padding(vertical = 8.dp)
                    )
                }
                if (links.isEmpty()) {
                    EmptyState(modifier = Modifier.fillMaxSize())
                } else {
                    LazyColumn(
                        modifier              = Modifier.fillMaxSize(),
                        contentPadding        = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement   = Arrangement.spacedBy(12.dp)
                    ) {
                        items(items = links, key = { it.id }) { link ->
                            LinkCard(linkItem = link, onDelete = viewModel::deleteLink)
                        }
                        item { Spacer(Modifier.height(80.dp)) }
                    }
                }
            }
        }

        if (showAddLinkDialog) {
            AddLinkDialog(
                groups    = groups,
                uiState   = uiState,
                onConfirm = { url, notes, groupId -> viewModel.addLink(url, notes, groupId) },
                onDismiss = { if (uiState !is LinkUiState.Loading) showAddLinkDialog = false }
            )
        }
        if (showAddGroupDialog) {
            AddGroupDialog(
                onConfirm = { name -> viewModel.addGroup(name); showAddGroupDialog = false },
                onDismiss = { showAddGroupDialog = false }
            )
        }
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier            = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text  = "No links saved",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text  = "Tap + to save your first link",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
    }
}

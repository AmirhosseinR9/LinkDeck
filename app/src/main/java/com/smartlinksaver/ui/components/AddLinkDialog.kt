package com.smartlinksaver.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartlinksaver.data.local.entity.Group
import com.smartlinksaver.presentation.viewmodel.LinkUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLinkDialog(
    groups    : List<Group>,
    uiState   : LinkUiState,
    onConfirm : (url: String, notes: String?, groupId: Long?) -> Unit,
    onDismiss : () -> Unit
) {
    var url              by remember { mutableStateOf("") }
    var notes            by remember { mutableStateOf("") }
    var selectedGroup    by remember { mutableStateOf<Group?>(null) }
    var dropdownExpanded by remember { mutableStateOf(false) }

    val isLoading = uiState is LinkUiState.Loading

    AlertDialog(
        onDismissRequest = { if (!isLoading) onDismiss() },
        shape            = RoundedCornerShape(20.dp),
        title            = { Text("Save Link", style = MaterialTheme.typography.titleLarge) },
        text             = {
            Column(
                modifier            = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value         = url,
                    onValueChange = { url = it },
                    label         = { Text("URL") },
                    placeholder   = { Text("https://...") },
                    singleLine    = true,
                    enabled       = !isLoading,
                    modifier      = Modifier.fillMaxWidth(),
                    shape         = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value         = notes,
                    onValueChange = { notes = it },
                    label         = { Text("Notes (optional)") },
                    minLines      = 2,
                    maxLines      = 4,
                    enabled       = !isLoading,
                    modifier      = Modifier.fillMaxWidth(),
                    shape         = RoundedCornerShape(12.dp)
                )
                if (groups.isNotEmpty()) {
                    ExposedDropdownMenuBox(
                        expanded         = dropdownExpanded,
                        onExpandedChange = { if (!isLoading) dropdownExpanded = it }
                    ) {
                        OutlinedTextField(
                            value         = selectedGroup?.groupName ?: "No group",
                            onValueChange = {},
                            readOnly      = true,
                            label         = { Text("Group") },
                            trailingIcon  = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded)
                            },
                            enabled  = !isLoading,
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        ExposedDropdownMenu(
                            expanded         = dropdownExpanded,
                            onDismissRequest = { dropdownExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text    = { Text("No group") },
                                onClick = { selectedGroup = null; dropdownExpanded = false }
                            )
                            groups.forEach { group ->
                                DropdownMenuItem(
                                    text    = { Text(group.groupName) },
                                    onClick = { selectedGroup = group; dropdownExpanded = false }
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick  = { onConfirm(url.trim(), notes.trim().ifBlank { null }, selectedGroup?.id) },
                enabled  = url.isNotBlank() && !isLoading,
                shape    = RoundedCornerShape(10.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier    = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color       = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Save")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !isLoading) { Text("Cancel") }
        }
    )
}

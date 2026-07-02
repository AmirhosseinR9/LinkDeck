package com.smartlinksaver.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartlinksaver.data.local.entity.Group

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupFilterRow(
    groups           : List<Group>,
    activeGroupId    : Long?,
    onFilterSelected : (Long?) -> Unit,
    modifier         : Modifier = Modifier
) {
    LazyRow(
        modifier              = modifier.fillMaxWidth(),
        contentPadding        = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = activeGroupId == null,
                onClick  = { onFilterSelected(null) },
                label    = { Text("All") }
            )
        }
        items(items = groups, key = { it.id }) { group ->
            FilterChip(
                selected = activeGroupId == group.id,
                onClick  = { onFilterSelected(group.id) },
                label    = { Text(group.groupName) }
            )
        }
    }
}

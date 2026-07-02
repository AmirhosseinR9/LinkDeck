package com.smartlinksaver.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddGroupDialog(
    onConfirm : (String) -> Unit,
    onDismiss : () -> Unit
) {
    var name by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        shape            = RoundedCornerShape(20.dp),
        title            = { Text("New Group", style = MaterialTheme.typography.titleLarge) },
        text             = {
            OutlinedTextField(
                value         = name,
                onValueChange = { name = it },
                label         = { Text("Group name") },
                singleLine    = true,
                modifier      = Modifier.fillMaxWidth(),
                shape         = RoundedCornerShape(12.dp)
            )
        },
        confirmButton = {
            Button(
                onClick  = { if (name.isNotBlank()) onConfirm(name.trim()) },
                enabled  = name.isNotBlank(),
                shape    = RoundedCornerShape(10.dp)
            ) { Text("Create") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

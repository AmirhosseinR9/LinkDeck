package com.smartlinksaver.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.smartlinksaver.data.local.entity.LinkItem
import com.smartlinksaver.ui.theme.ErrorColor
import com.smartlinksaver.ui.theme.Primary
import com.smartlinksaver.ui.theme.PrimaryDim

private val CardShape  = RoundedCornerShape(16.dp)
private val ImageShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)

@Composable
fun LinkCard(
    linkItem : LinkItem,
    onDelete : (LinkItem) -> Unit,
    modifier : Modifier = Modifier
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val hasNotes = !linkItem.userNotes.isNullOrBlank()

    Card(
        modifier  = modifier.fillMaxWidth(),
        shape     = CardShape,
        colors    = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp, pressedElevation = 6.dp)
    ) {
        Column {

            // ── Feature Image ──────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(ImageShape)
            ) {
                if (!linkItem.imageUrl.isNullOrBlank()) {
                    SubcomposeAsyncImage(
                        model              = linkItem.imageUrl,
                        contentDescription = linkItem.title,
                        contentScale       = ContentScale.Crop,
                        modifier           = Modifier.fillMaxSize()
                    ) {
                        when (painter.state) {
                            is AsyncImagePainter.State.Loading -> ShimmerBox(Modifier.fillMaxSize())
                            is AsyncImagePainter.State.Error   -> ImagePlaceholder()
                            else                               -> SubcomposeAsyncImageContent()
                        }
                    }
                } else {
                    ImagePlaceholder()
                }
            }

            // ── Title & Description ────────────────────────────────────
            Column(
                modifier            = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text     = linkItem.title ?: linkItem.url,
                    style    = MaterialTheme.typography.titleMedium,
                    color    = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                if (!linkItem.webDescription.isNullOrBlank()) {
                    Text(
                        text     = linkItem.webDescription,
                        style    = MaterialTheme.typography.bodySmall,
                        color    = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            HorizontalDivider(
                thickness = 0.5.dp,
                color     = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
            )

            // ── Notes Footer ───────────────────────────────────────────
            Row(
                modifier          = Modifier
                    .fillMaxWidth()
                    .padding(start = 14.dp, end = 6.dp, top = 4.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector        = Icons.Outlined.Notes,
                    contentDescription = null,
                    tint               = Primary.copy(alpha = 0.7f),
                    modifier           = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text     = if (hasNotes) "Notes" else "No notes",
                    style    = MaterialTheme.typography.labelMedium,
                    color    = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
                if (hasNotes) {
                    IconButton(
                        onClick  = { isExpanded = !isExpanded },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector        = if (isExpanded) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                            contentDescription = if (isExpanded) "Collapse" else "Expand",
                            tint               = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier           = Modifier.size(18.dp)
                        )
                    }
                }
                IconButton(
                    onClick  = { onDelete(linkItem) },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.Delete,
                        contentDescription = "Delete link",
                        tint               = ErrorColor.copy(alpha = 0.8f),
                        modifier           = Modifier.size(18.dp)
                    )
                }
            }

            // ── Expanded Notes ─────────────────────────────────────────
            AnimatedVisibility(
                visible = isExpanded && hasNotes,
                enter   = expandVertically(),
                exit    = shrinkVertically()
            ) {
                Text(
                    text     = linkItem.userNotes ?: "",
                    style    = MaterialTheme.typography.bodyMedium,
                    color    = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 14.dp, end = 14.dp, bottom = 12.dp)
                )
            }
        }
    }
}

@Composable
private fun ImagePlaceholder() {
    Box(
        modifier         = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(listOf(Color(0xFF1A1D2B), PrimaryDim))),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector        = Icons.Outlined.Link,
            contentDescription = null,
            tint               = Primary.copy(alpha = 0.35f),
            modifier           = Modifier.size(44.dp)
        )
    }
}

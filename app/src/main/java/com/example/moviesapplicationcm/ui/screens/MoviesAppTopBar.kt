package com.example.moviesapplicationcm.ui.screens


import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviesapplicationcm.R
import com.example.moviesapplicationcm.ui.theme.MoviesApplicationCMTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesTopAppBar(
    openDrawer: () -> Unit = {},
    onFilterAllTasks: () -> Unit = {},
    onFilterActiveTasks: () -> Unit = {},
    onFilterCompletedTasks: () -> Unit = {},
    onClearCompletedTasks: () -> Unit = {},
    onRefresh: () -> Unit = {},
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = { Image(painter = painterResource(id = R.drawable.popcorn), contentDescription = stringResource(
            id = R.string.popcorn_bucket), modifier.size(32.dp),) },
        navigationIcon = {
        },
//        actions = {
//            FilterTasksMenu(onFilterAllTasks, onFilterActiveTasks, onFilterCompletedTasks)
//            MoreTasksMenu(onClearCompletedTasks, onRefresh)
//        },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
    )
}


// OPTIONS MENU GIA SEARCG KTL META

//@Composable
//private fun FilterTasksMenu(
//    onFilterAllTasks: () -> Unit,
//    onFilterActiveTasks: () -> Unit,
//    onFilterCompletedTasks: () -> Unit
//) {
//    TopAppBarDropdownMenu(
//        iconContent = {
//            Icon(
//                painterResource(id = R.drawable.ic_filter_list),
//                stringResource(id = R.string.menu_filter)
//            )
//        }
//    ) { closeMenu ->
//        DropdownMenuItem(onClick = { onFilterAllTasks(); closeMenu() }) {
//            Text(text = stringResource(id = R.string.nav_all))
//        }
//        DropdownMenuItem(onClick = { onFilterActiveTasks(); closeMenu() }) {
//            Text(text = stringResource(id = R.string.nav_active))
//        }
//        DropdownMenuItem(onClick = { onFilterCompletedTasks(); closeMenu() }) {
//            Text(text = stringResource(id = R.string.nav_completed))
//        }
//    }
//}
//
//@Composable
//private fun MoreTasksMenu(
//    onClearCompletedTasks: () -> Unit,
//    onRefresh: () -> Unit
//) {
//    TopAppBarDropdownMenu(
//        iconContent = {
//            Icon(Icons.Filled.MoreVert, stringResource(id = R.string.menu_more))
//        }
//    ) { closeMenu ->
//        DropdownMenuItem(onClick = { onClearCompletedTasks(); closeMenu() }) {
//            Text(text = stringResource(id = R.string.menu_clear))
//        }
//        DropdownMenuItem(onClick = { onRefresh(); closeMenu() }) {
//            Text(text = stringResource(id = R.string.refresh))
//        }
//    }
//}

@Composable
private fun TopAppBarDropdownMenu(
    iconContent: @Composable () -> Unit,
    content: @Composable ColumnScope.(() -> Unit) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
        IconButton(onClick = { expanded = !expanded }) {
            iconContent()
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.wrapContentSize(Alignment.TopEnd)
        ) {
            content { expanded = !expanded }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsTopAppBar(openDrawer: () -> Unit) {
    TopAppBar(
        title = { Text(text = "statistics drawer") },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(Icons.Filled.Menu, "menu open drawer")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailTopAppBar(onBack: () -> Unit, onDelete: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "Task Detail")
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, "menu back")
            }
        },
        actions = {
            IconButton(onClick = onDelete) {
                Icon(Icons.Filled.Delete, "menu delete task")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskTopAppBar(@StringRes title: Int, onBack: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(title)) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, "menu back")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun TasksTopAppBarPreview() {
    MoviesApplicationCMTheme {
        Surface {
            MoviesTopAppBar({}, {}, {}, {}, {}, canNavigateBack = true, navigateUp = {}, )
        }
    }
}

@Preview
@Composable
private fun StatisticsTopAppBarPreview() {
    MoviesApplicationCMTheme {
        Surface {
            StatisticsTopAppBar { }
        }
    }
}

@Preview
@Composable
private fun TaskDetailTopAppBarPreview() {
    MoviesApplicationCMTheme {
        Surface {
            TaskDetailTopAppBar({ }, { })
        }
    }
}

@Preview
@Composable
private fun AddEditTaskTopAppBarPreview() {
    MoviesApplicationCMTheme {
        Surface {
            AddEditTaskTopAppBar(R.string.app_name) { }
        }
    }
}

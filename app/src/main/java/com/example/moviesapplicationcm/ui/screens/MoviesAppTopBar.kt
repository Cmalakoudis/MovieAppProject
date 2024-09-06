package com.example.moviesapplicationcm.ui.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviesapplicationcm.R
import com.example.moviesapplicationcm.data.AppUIState
import com.example.moviesapplicationcm.ui.theme.MoviesApplicationCMTheme


@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MoviesTopAppBar(
        checkDark: (Boolean) -> Unit,
        uiState: AppUIState,
        onProfileClicked: () -> Unit,
        modifier: Modifier = Modifier
    ) {

        CenterAlignedTopAppBar(
            modifier = modifier
                .padding(start = 12.dp)
                .fillMaxWidth(),
            title = { Image(painter = painterResource(id = R.drawable.popcorn), contentDescription = stringResource(
                id = R.string.popcorn_bucket), modifier.size(32.dp),) },
            navigationIcon = {
           IconButton(onClick = onProfileClicked, enabled = uiState.movieAppUiState.isLoggedIn) {
                   Image(painter = painterResource(id = R.drawable.profile_pic_emtpy), contentDescription = stringResource(id = R.string.profile_picture),
                       modifier = Modifier
                           .clip(shape = CircleShape)
                           .border(
                               BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                               CircleShape
                           )
                           .size(32.dp), contentScale = ContentScale.Fit
                   )
               }


            },
            actions = {
                Switch(modifier = modifier
                    .width(32.dp)
                    .height(18.dp)
                    .padding(vertical = 7.dp), checked = uiState.movieAppUiState.darkTheme , onCheckedChange = checkDark,
                    colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.onTertiary,
                                                    checkedTrackColor = MaterialTheme.colorScheme.primary,
                                                    uncheckedBorderColor = MaterialTheme.colorScheme.primary,
                                                    uncheckedThumbColor = MaterialTheme.colorScheme.primary,
                                                    uncheckedTrackColor = MaterialTheme.colorScheme.onPrimary,
                        ))
                Spacer(modifier = Modifier.width(20.dp))
                Icon(painter = painterResource(id = R.drawable.vectormoon), contentDescription = stringResource(id = R.string.moon)
                    , modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSecondary)
                Spacer(modifier = Modifier.width(8.dp))
            },
            colors = TopAppBarDefaults.topAppBarColors( containerColor = Color.Transparent)
        )
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesTopAppBarContent(
    modifier: Modifier = Modifier,
    checkButton: Boolean = true,
) {

    CenterAlignedTopAppBar(
        modifier = modifier
            .padding(start = 12.dp)
            .fillMaxWidth(),
        title = { Image(painter = painterResource(id = R.drawable.popcorn), contentDescription = stringResource(
            id = R.string.popcorn_bucket), modifier.size(32.dp),) },
        navigationIcon = {
            IconButton(onClick = { }, enabled = true) {
                Image(painter = painterResource(id = R.drawable.profile_pic_emtpy), contentDescription = stringResource(id = R.string.profile_picture),
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .border(
                            BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                            CircleShape
                        )
                        .size(32.dp), contentScale = ContentScale.Fit
                )
            }


        },
        actions = {
            Switch(modifier = modifier
                .width(32.dp)
                .height(18.dp)
                .padding(vertical = 7.dp), checked = checkButton, onCheckedChange = { },
                colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.onTertiary,
                    checkedTrackColor = MaterialTheme.colorScheme.primary,
                    uncheckedBorderColor = MaterialTheme.colorScheme.primary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.primary,
                    uncheckedTrackColor = MaterialTheme.colorScheme.onPrimary,
                ))
            Spacer(modifier = Modifier.width(20.dp))
            Icon(painter = painterResource(id = R.drawable.vectormoon), contentDescription = stringResource(id = R.string.moon)
                , modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSecondary)
            Spacer(modifier = Modifier.width(8.dp))
        },
        colors = TopAppBarDefaults.topAppBarColors( containerColor = Color.Transparent)
    )
}


@Preview
@Composable
private fun MoviesTopAppBarPreview() {
    MoviesApplicationCMTheme {
        Surface {
            MoviesTopAppBarContent(checkButton = true)
        }
    }
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


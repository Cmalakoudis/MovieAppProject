package com.example.moviesapplicationcm.ui.screens



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviesapplicationcm.R
import com.example.moviesapplicationcm.ui.theme.MoviesApplicationCMTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesBottomAppBar(
    openDrawer: () -> Unit = {},
    onFilterAllTasks: () -> Unit = {},
    onFilterActiveTasks: () -> Unit = {},
    onFilterCompletedTasks: () -> Unit = {},
    onClearCompletedTasks: () -> Unit = {},
    onRefresh: () -> Unit = {},
    isPopular: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier.fillMaxWidth(),){
        Row(
            modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp), horizontalArrangement = Arrangement.Center){
            Spacer(modifier = Modifier.weight(0.05f))
            TextButton(onClick = { /*TODO*/ },
                modifier = Modifier.weight(1f),
                enabled = isPopular,
                colors = ButtonColors(containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary,
                                    disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                                    disabledContentColor = MaterialTheme.colorScheme.onTertiary)) {
                Icon(painterResource(id = R.drawable.vector), contentDescription = null)
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = stringResource(id = R.string.popular))
            }
            Spacer(modifier = Modifier.weight(0.10f))
            TextButton(onClick = { /*TODO*/ },
                modifier = Modifier.weight(1f),
                enabled = !isPopular,
                colors = ButtonColors(containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                    disabledContentColor = MaterialTheme.colorScheme.onTertiary)) {
                Icon(painterResource(id = R.drawable.heart_full) , contentDescription = null)
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = stringResource(id = R.string.favourites))
            }
            Spacer(modifier = Modifier.weight(0.05f))
        }

    }
}
@Preview
@Composable
private fun MovieBottomAppBarPreview() {
    MoviesApplicationCMTheme {
        Surface {
            MoviesBottomAppBar(isPopular = true, navigateUp = { /*TODO*/ })
        }
    }
}

@Preview
@Composable
private fun MovieBottomAppWholeBarPreview() {
    MoviesApplicationCMTheme {
        Surface {
            LogInScreen(
                topBarTitle = R.string.app_name,
                onNameChange = {},
                onKeyboardDone = {},
                userName = "username"
            )
        }
    }
}
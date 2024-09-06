package com.example.moviesapplicationcm.ui.screens



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviesapplicationcm.R
import com.example.moviesapplicationcm.ui.MovieViewModel
import com.example.moviesapplicationcm.ui.theme.MoviesApplicationCMTheme

@Composable
fun MoviesBottomAppBar(
    modifier: Modifier = Modifier,
    myViewModel: MovieViewModel,
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier.fillMaxWidth(),){
        var viewingPopular by remember{ mutableStateOf(myViewModel.uiState.value.movieAppUiState.viewingPopular)}
        Row(
            modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp), horizontalArrangement = Arrangement.Center){
            Spacer(modifier = Modifier.weight(0.05f))
            TextButton(onClick = {viewingPopular = myViewModel.viewingPopular() },
                modifier = Modifier.weight(1f),
                enabled = !viewingPopular,
                colors = ButtonColors(containerColor = MaterialTheme.colorScheme.tertiary,
                                    contentColor = MaterialTheme.colorScheme.onTertiary,
                                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                                    disabledContentColor = MaterialTheme.colorScheme.onPrimary)) {
                Icon(painterResource(id = R.drawable.vector), contentDescription = null)
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = stringResource(id = R.string.popular))
            }
            Spacer(modifier = Modifier.weight(0.10f))
            TextButton(onClick = {viewingPopular =  myViewModel.viewingFavourites() },
                modifier = Modifier.weight(1f),
                enabled = viewingPopular,
                colors = ButtonColors(containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary)) {
                Icon(painterResource(id = R.drawable.heart_full) , contentDescription = null)
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = stringResource(id = R.string.favourites))
            }
            Spacer(modifier = Modifier.weight(0.05f))
        }

    }
}

@Composable
fun MoviesBottomAppBarContent(
    modifier: Modifier = Modifier,
    viewingPopular: Boolean = true,
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier.fillMaxWidth(),){
        Row(
            modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp), horizontalArrangement = Arrangement.Center){
            Spacer(modifier = Modifier.weight(0.05f))
            TextButton(onClick = {},
                modifier = Modifier.weight(1f),
                enabled = !viewingPopular,
                colors = ButtonColors(containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary)) {
                Icon(painterResource(id = R.drawable.vector), contentDescription = null)
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = stringResource(id = R.string.popular))
            }
            Spacer(modifier = Modifier.weight(0.10f))
            TextButton(onClick = {},
                modifier = Modifier.weight(1f),
                enabled = viewingPopular,
                colors = ButtonColors(containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary)) {
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
            MoviesBottomAppBarContent()
        }
    }
}

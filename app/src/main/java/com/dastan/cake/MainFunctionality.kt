package com.dastan.cake

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dastan.cake.domain.InfoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindField(viewModel: InfoViewModel) {
    val text = viewModel.queryText.collectAsState()
    Row(
        modifier = Modifier.padding(start = 8.dp, end = 12.dp, bottom = 8.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {

        TextField(
            value = text.value,
            onValueChange = {
                viewModel.onQueryUpdate(it)
            },
            leadingIcon = {
                Image(
                    painter = painterResource(R.drawable.findicon),
                    contentDescription = "Description of the image",
                    modifier = Modifier.height(20.dp).width(20.dp)

                )
            },
            modifier = Modifier.background(color = colorScheme.tertiary, shape = RoundedCornerShape(16.dp))
                .weight(1f).height(48.dp),
            placeholder = {
                Text(
                    "поиск",
                    fontSize = 14.sp,
                    style = TextStyle(color = colorScheme.onSecondary),
                    overflow = TextOverflow.Ellipsis
                )
            },
            maxLines = 1,

            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledTextColor = colorScheme.onSurface,
                disabledIndicatorColor = Color.Transparent,
                containerColor = Color.Transparent,
                cursorColor = colorScheme.scrim,
            )
        )



    }
}
package com.dastan.cake.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.dastan.cake.*
import com.dastan.cake.domain.OrderViewModel
import io.ak1.drawbox.DrawBox
import io.ak1.drawbox.rememberDrawController


@Composable
fun CustomScreen(navController: NavController, orderViewModel: OrderViewModel){
    val undoVisibility = remember { mutableStateOf(false) }
    val redoVisibility = remember { mutableStateOf(false) }
    val colorBarVisibility = remember { mutableStateOf(false) }
    val sizeBarVisibility = remember { mutableStateOf(false) }
    val currentColor = remember { mutableStateOf(Color.Red) }
    val bg = MaterialTheme.colors.background
    val currentBgColor = remember { mutableStateOf(bg) }
    val currentSize = remember { mutableStateOf(10) }
    val drawController = rememberDrawController()
    val context = LocalContext.current
    Box {
        Column {
            Icon(
                Icons.Default.Clear,
                contentDescription = null,
                modifier = Modifier.clickable { navController.navigateUp() })
            DrawBox(
                drawController = drawController,
                backgroundColor = currentBgColor.value,
                modifier = Modifier.fillMaxSize().weight(1f, true),
                bitmapCallback = { bitmap, _ ->
                    bitmap?.let {imageBitmap ->
                        orderViewModel.addImageBitmap(imageBitmap, context)
                    }
                    navController.navigateUp()
                }
            ) { undoCount, redoCount ->
                sizeBarVisibility.value = false
                colorBarVisibility.value = false
                undoVisibility.value = undoCount != 0
                redoVisibility.value = redoCount != 0
            }
            ControlsBar(
                drawController = drawController,
                {
                    drawController.saveBitmap()
                },
                {
                    colorBarVisibility.value = when (colorBarVisibility.value) {
                        false -> true
                        else -> false
                    }
                    sizeBarVisibility.value = false
                },
                {
                    colorBarVisibility.value = when (colorBarVisibility.value) {
                        false -> true
                        else -> false
                    }
                    sizeBarVisibility.value = false
                },
                {
                    sizeBarVisibility.value = !sizeBarVisibility.value
                    colorBarVisibility.value = false
                },
                undoVisibility = undoVisibility,
                redoVisibility = redoVisibility,
                colorValue = currentColor,
                bgColorValue = currentBgColor,
                sizeValue = currentSize
            )

            RangVikalp(isVisible = colorBarVisibility.value, showShades = true) {

                currentColor.value = it
                drawController.changeColor(it)

            }
            CustomSeekbar(
                isVisible = sizeBarVisibility.value,
                progress = currentSize.value,
                progressColor = MaterialTheme.colors.primary.toOldColor(),
                thumbColor = currentColor.value.toOldColor()
            ) {
                currentSize.value = it
                drawController.changeStrokeWidth(it.toFloat())
            }
        }
    }

}


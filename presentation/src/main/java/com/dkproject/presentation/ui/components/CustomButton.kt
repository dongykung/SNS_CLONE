package com.dkproject.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dkproject.presentation.R


@Composable
fun CustomButton(
    modifier: Modifier=Modifier,
    text:String,
    enabled:Boolean=true,
    onClick:()->Unit,
) {
    Surface(modifier=modifier.clickable { if(enabled)onClick() }, color = if(enabled)Color.White else Color.Gray,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier=Modifier.padding(10.dp)) {
            Text(text = text)
        }
    }
}
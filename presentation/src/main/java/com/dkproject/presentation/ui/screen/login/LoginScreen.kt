package com.dkproject.presentation.ui.screen.login

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.components.CustomButton
import com.dkproject.presentation.ui.components.InputIdField
import com.dkproject.presentation.ui.components.PasswordField


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    moveToSignUp: () -> Unit,
    moveToHome:()->Unit,
) {
    Log.d("LoginScreen", "LoginScreen")
    val id = viewModel.id
    val password = viewModel.password
    val context= LocalContext.current
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(100.dp))
            //welcome Text
            Text(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp),
                text = stringResource(id = R.string.welcome),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    fontSize = 36.sp
                ),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(100.dp))

            //InputSection
            InputIdSection(
                modifier = Modifier.imePadding(),
                id = id,
                label = stringResource(id = R.string.id),
                idChange = { viewModel.updateId(it) })
            Spacer(modifier = Modifier.height(18.dp))
            //password Section
            PasswordSection(
                modifier = Modifier.imePadding(),
                pw = password,
                label = stringResource(id = R.string.password),
                pwChange = { viewModel.updatePw(it) },
            )
            Spacer(modifier = Modifier.height(20.dp))

            //login Button
            CustomButton(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
                text = stringResource(id = R.string.login), onClick = {
                    viewModel.login(context, moveToHome = {moveToHome()})
                })
            Spacer(modifier = Modifier.weight(1f))
            //moveToSignUp
            Row(modifier = Modifier
                .padding(bottom = 24.dp)
                .clickable { moveToSignUp() }) {
                Text(text = stringResource(id = R.string.noaccount), color = Color.White)
                Text(
                    text = "회원가입",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun InputIdSection(
    modifier: Modifier = Modifier,
    id: String,
    label: String,
    idChange: (String) -> Unit,
) {
    Log.d("InputIdSection", "InputIdSection")
    InputIdField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp),
        value = id,
        valueChange = idChange,
        label = label,
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Email
    )
}

@Composable
fun PasswordSection(
    modifier: Modifier = Modifier,
    pw: String,
    label: String,
    pwChange: (String) -> Unit,
    checkpw: Boolean = false,
    checkpwvalue: String = ""
) {
    Log.d("PasswordSection", "PasswordSection")
    val passwordVisibility = rememberSaveable {
        mutableStateOf(false)
    }
    PasswordField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp),
        value = pw,
        valueChange = pwChange,
        labelId = label,
        imeAction = ImeAction.Done,
        passwordVisibility = passwordVisibility,
        checkpw = checkpw,
        checkpwValue = checkpwvalue
    )
}
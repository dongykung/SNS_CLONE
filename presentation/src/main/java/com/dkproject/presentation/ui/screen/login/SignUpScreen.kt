package com.dkproject.presentation.ui.screen.login

import android.graphics.fonts.FontStyle
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dkproject.presentation.R
import com.dkproject.presentation.ui.components.CustomButton
import com.dkproject.presentation.ui.components.CustomTopAppBar
import com.dkproject.presentation.ui.theme.SNS_CloneTheme
import com.dkproject.presentation.util.rememberImeState
import java.io.InputStream


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    moveToLogin:()->Unit,
) {
    Log.d("SignUpScreen", "SignUpScreen")
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val id = viewModel.id
    val password = viewModel.password
    val passwordconfirm = viewModel.passwordConfirm
    val username = viewModel.userName
    val valid = viewModel.valid
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.scrollTo(scrollState.maxValue)
        }
    }

    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = stringResource(id = R.string.signup),
                onBack = true,
                onBackClick = { onBackClick() },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = stringResource(id = R.string.appName),
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color(21, 239, 201),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 22.dp),
                    text = stringResource(id = R.string.createaccount), color = Color.White,
                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(30.dp))
                InputIdSection(
                    modifier = Modifier.fillMaxWidth(),
                    id = id,
                    label = stringResource(id = R.string.id),
                    idChange = { newId ->
                        viewModel.updateId(newId)
                    }
                )
                Spacer(modifier = Modifier.height(28.dp))
                InputIdSection(
                    id = username,
                    label = stringResource(id = R.string.username),
                    idChange = { newName ->
                        viewModel.updateName(newName)
                    })
                Spacer(modifier = Modifier.height(28.dp))
                PasswordSection(pw = password, label = stringResource(id = R.string.password),
                    pwChange = { newPw ->
                        viewModel.updatePassword(newPw)
                    })
                Spacer(modifier = Modifier.height(28.dp))
                PasswordSection(
                    pw = passwordconfirm,
                    label = stringResource(id = R.string.passwordconfirm),
                    pwChange = { newPw ->
                        viewModel.updatePasswordConfirm(newPw)
                    },
                    checkpw = true,
                    checkpwvalue = password
                )
                Spacer(modifier = Modifier.height(50.dp))
                CustomButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp),
                    text = stringResource(id = R.string.signup), onClick = {
                        viewModel.signUp(context, moveToLogin = {
                            moveToLogin()
                        })
                    },
                    enabled = valid
                )
            }
        }
    }
}



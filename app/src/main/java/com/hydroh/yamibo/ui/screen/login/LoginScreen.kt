package com.hydroh.yamibo.ui.screen.login

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hydroh.yamibo.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun LoginScreen(
    resultNavigator: ResultBackNavigator<Boolean>? = null,
    viewModel: LoginViewModel = LoginViewModel(),
) {
    val uiState = viewModel.uiState
    Surface {
        Column {

            Spacer(
                Modifier
                    .weight(1f)
                    .animateContentSize()
            )

            Column(Modifier.wrapContentHeight(align = Alignment.CenterVertically)) {
                // Logo
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 76.dp),
                    contentDescription = null
                )
                Text(
                    text = stringResource(id = R.string.app_welcome),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                )
            }

            Spacer(
                Modifier
                    .weight(1f)
                    .animateContentSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Username
                OutlinedTextField(
                    value = uiState.username,
                    onValueChange = { viewModel.updateUsername(it) },
                    enabled = uiState.loginState != LoginState.LOADING,
                    label = {
                        Text(
                            text = stringResource(id = R.string.label_username),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(24.dp),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next,
                    ),
                )
                // Password
                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = { viewModel.updatePassword(it) },
                    enabled = uiState.loginState != LoginState.LOADING,
                    label = {
                        Text(
                            text = stringResource(id = R.string.label_password),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(24.dp),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                    // Show/Hide Password
                    trailingIcon = {
                        if (uiState.showPassword) {
                            IconButton(onClick = { viewModel.toggleShowPassword() }) {
                                Icon(
                                    imageVector = Icons.Filled.Visibility,
                                    contentDescription = stringResource(id = R.string.hide_password)
                                )
                            }
                        } else {
                            IconButton(onClick = { viewModel.toggleShowPassword() }) {
                                Icon(
                                    imageVector = Icons.Filled.VisibilityOff,
                                    contentDescription = stringResource(id = R.string.show_password)
                                )
                            }
                        }
                    },
                    visualTransformation = if (uiState.showPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                )
                // Submit
                Button(
                    onClick = {
                        viewModel.submitLogin()
                        if (uiState.loginState == LoginState.SUCCESS) {
                            resultNavigator?.navigateBack(result = true)
                        }
                    },
                    enabled = uiState.loginState != LoginState.LOADING,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp, bottom = 30.dp),
                ) {
                    if (uiState.loginState != LoginState.LOADING) {
                        Text(
                            text = stringResource(id = R.string.text_login),
                            style = MaterialTheme.typography.titleMedium
                        )
                    } else {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
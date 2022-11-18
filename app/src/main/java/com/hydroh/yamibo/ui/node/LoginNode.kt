package com.hydroh.yamibo.ui.node

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.hydroh.yamibo.R
import com.hydroh.yamibo.network.login
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginNode(
    buildContext: BuildContext,
    private val navToHome: () -> Unit
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        Surface {
            Column(Modifier.fillMaxWidth()) {

                Spacer(
                    Modifier
                        .weight(1f)
                        .animateContentSize()
                )

                AnimatedVisibility(visible = true, modifier = Modifier.fillMaxWidth()) {
                    Branding(Modifier.wrapContentHeight(align = Alignment.CenterVertically))
                }

                Spacer(
                    Modifier
                        .weight(1f)
                        .animateContentSize()
                )

                LoginField(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
            }
        }
    }

    @Composable
    fun Branding(modifier: Modifier = Modifier) {
        Column(modifier) {
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
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LoginField(modifier: Modifier = Modifier) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val showPassword = rememberSaveable { mutableStateOf(false) }

        val scope = rememberCoroutineScope()
        var isLoading by remember { mutableStateOf(false) }
        var isLoginFailed by remember { mutableStateOf(false) }

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Username
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                enabled = !isLoading,
                label = {
                    Text(
                        text = stringResource(id = R.string.label_username),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
            )
            // Password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                enabled = !isLoading,
                label = {
                    Text(
                        text = stringResource(id = R.string.label_password),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                // Show/Hide Password
                trailingIcon = {
                    if (showPassword.value) {
                        IconButton(onClick = { showPassword.value = false }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = stringResource(id = R.string.hide_password)
                            )
                        }
                    } else {
                        IconButton(onClick = { showPassword.value = true }) {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = stringResource(id = R.string.show_password)
                            )
                        }
                    }
                },
                visualTransformation = if (showPassword.value) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
            )
            // Submit
            Button(
                onClick = {
                    isLoading = true
                    scope.launch(Dispatchers.IO) {
                        if (login(username, password)) {
                            navToHome()
                        } else {

                        }
                        isLoading = false
                    }
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp, bottom = 30.dp),
            ) {
                if (!isLoading) {
                    Text(
                        text = stringResource(id = R.string.text_login),
                        style = MaterialTheme.typography.titleSmall
                    )
                } else {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

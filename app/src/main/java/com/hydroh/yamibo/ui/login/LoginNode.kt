package com.hydroh.yamibo.ui.login

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.push
import com.hydroh.yamibo.R
import com.hydroh.yamibo.ui.navigation.RootNode

class LoginNode(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>,
    private val viewModel: LoginViewModel = LoginViewModel(),
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        LoginScreen(
            modifier = modifier,
            backStack = backStack,
            viewModel = viewModel,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    backStack: BackStack<RootNode.NavTarget>? = null,
    viewModel: LoginViewModel = LoginViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    Surface {
        Column(modifier) {

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
                    value = uiState.password,
                    onValueChange = { viewModel.updatePassword(it) },
                    enabled = uiState.loginState != LoginState.LOADING,
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
                            backStack?.push(RootNode.NavTarget.Home)
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
                            style = MaterialTheme.typography.titleSmall
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
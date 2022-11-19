package com.hydroh.yamibo

import android.os.Bundle
import androidx.activity.compose.setContent
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeComponentActivity
import com.hydroh.yamibo.data.DataProvider
import com.hydroh.yamibo.ui.navigation.RootNode
import com.hydroh.yamibo.ui.theme.YamiboTheme

class MainActivity : NodeComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataProvider.init(this.applicationContext)

        setContent {
            YamiboTheme {
                NodeHost(integrationPoint = appyxIntegrationPoint) {
                    RootNode(it)
                }
                // A surface container using the 'background' color from the theme
//                YamiboApp()
            }
        }
    }
}

//
//@OptIn(ExperimentalMaterial3Api::class)
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Preview
//@Composable
//fun YamiboApp() {
//    Scaffold (
//        content = {
//            LoginScreen()
//        }
//    )
//}

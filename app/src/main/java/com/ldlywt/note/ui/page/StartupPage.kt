package com.ldlywt.note.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ireward.htmlcompose.HtmlText
import com.ldlywt.note.R
import com.ldlywt.note.component.RYScaffold
import com.ldlywt.note.utils.Constant
import com.ldlywt.note.utils.lunchMain
import com.ldlywt.note.ui.page.router.Screen
import com.ldlywt.note.utils.str


@Composable
fun StartupPage(
    navHostController: NavHostController
) {
    val context = LocalContext.current

    RYScaffold(
        title = null,
        navController = null, bottomBar = null, floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.navigationBarsPadding(),
                onClick = {
                    lunchMain {
                        navHostController.navigate(route = Screen.Main){
                            // 将登录页面设置为起始页，当返回时销毁登录页面
                            popUpTo(route = Screen.Startup) {
                                inclusive = true
                            }
                        }
                    }
                },
                icon = {
                    Icon(
                        Icons.Rounded.CheckCircleOutline, stringResource(R.string.agree)
                    )
                },
                text = { Text(text = stringResource(R.string.agree)) },
            )
        }) {
        LazyColumn(
            modifier = Modifier.navigationBarsPadding(),
        ) {
            item {
                Spacer(modifier = Modifier.height(64.dp))
                Text(
                    text = stringResource(R.string.welcome),
                    modifier = Modifier.padding(horizontal = 24.dp),
                    style = MaterialTheme.typography.displaySmall.copy(
                        baselineShift = BaselineShift.Superscript
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.pic_thinking),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.5f),
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = R.string.tos_tips.str,
                    modifier = Modifier.padding(horizontal = 24.dp),
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Light),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
                TextButton(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    onClick = { Constant.startUserAgreeUrl(context) }) {
                    HtmlText(
                        text = stringResource(R.string.browse_tos_tips_service),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.outline,
                        ),
                    )
                }
                TextButton(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    onClick = { Constant.startPrivacyUrl(context) }) {
                    HtmlText(
                        text = stringResource(R.string.browse_tos_tips_privacy),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.outline,
                        ),
                    )
                }
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }


}
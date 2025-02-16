package com.ldlywt.note.ui.page.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ldlywt.note.R
import com.ldlywt.note.ui.page.router.Screen
import com.ldlywt.note.ui.page.LocalMemosViewModel
import com.ldlywt.note.ui.page.NoteViewModel
import com.moriafly.salt.ui.SaltTheme
import com.moriafly.salt.ui.TitleBar
import com.moriafly.salt.ui.UnstableSaltApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class, UnstableSaltApi::class)
@Composable
fun LocationListPage(navHostController: NavHostController) {
    val noteViewModel: NoteViewModel = LocalMemosViewModel.current
    val locationInfoList by noteViewModel.getAllLocationInfo().collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SaltTheme.colors.background)
            .padding(top = 30.dp)
    ) {
        TitleBar(
            onBack = {
                navHostController.popBackStack()
            },
            text = stringResource(R.string.location_info)
        )

        LazyColumn(modifier = Modifier
            .fillMaxWidth(1f)
            .padding(start = 12.dp, end = 12.dp), content = {
            repeat(locationInfoList.size) { index ->
                item {
                    ElevatedAssistChip(
                        modifier = Modifier
                            .padding(horizontal = 4.dp),
                        onClick = {
                            navHostController.navigate(Screen.LocationDetail(locationInfoList[index]))
                        },
                        label = {
                            Text(locationInfoList[index])
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.LocationOn,
                                contentDescription = "Localized description",
                                Modifier.size(AssistChipDefaults.IconSize)
                            )
                        }
                    )
                }
            }


            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
        })
    }
}
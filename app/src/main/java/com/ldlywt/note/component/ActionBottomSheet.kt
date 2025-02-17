package com.ldlywt.note.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.ldlywt.note.R
import com.ldlywt.note.bean.NoteShowBean
import com.ldlywt.note.ui.page.router.Screen
import com.ldlywt.note.utils.copy
import com.ldlywt.note.utils.str
import com.ldlywt.note.ui.page.LocalMemosViewModel
import com.moriafly.salt.ui.SaltTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionBottomSheet(
    navHostController: NavHostController, noteShowBean: NoteShowBean, show: Boolean, onDismissRequest: () -> Unit
) {

    val viewModel = LocalMemosViewModel.current
    val skipPartiallyExpanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )

    // Sheet content
    if (show) {
        ModalBottomSheet(
            containerColor = SaltTheme.colors.popup,
            onDismissRequest = onDismissRequest, sheetState = bottomSheetState
        ) {
            Row(
                Modifier
                    .fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                LazyColumn {
//                    item {
//                        TextButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
//                            Text(text = R.string.pin.str, style = MaterialTheme.typography.titleMedium)
//                        }
//                    }

                    item {
                        TextButton(onClick = {
                            copy(noteShowBean.note)
                            onDismissRequest()
                        }, modifier = Modifier.fillMaxWidth()) {
                            Text(text = R.string.copy.str, style = SaltTheme.textStyles.paragraph)
                        }
                    }

                    item {
                        TextButton(onClick = {
//                            EditorTextActivity.startActivity(context, noteShowBean)
                            navHostController.navigate(route = Screen.InputDetail(noteShowBean.note.noteId))
                            onDismissRequest()
                        }, modifier = Modifier.fillMaxWidth()) {
                            Text(text = R.string.edit.str, style = SaltTheme.textStyles.paragraph)
                        }
                    }

                    item {
                        TextButton(onClick = {
                            navHostController.navigate(Screen.Share(noteShowBean.note.noteId))
                            onDismissRequest()
                        }, modifier = Modifier.fillMaxWidth()) {
                            Text(text = R.string.share.str, style = SaltTheme.textStyles.paragraph)
                        }
                    }

                    item {
                        TextButton(onClick = {
                            scope.launch {
                                viewModel.deleteNote(noteShowBean.note, noteShowBean.tagList)
                                onDismissRequest()
                            }
                        }, modifier = Modifier.fillMaxWidth()) {
                            Text(text = R.string.delete.str, style = SaltTheme.textStyles.paragraph)
                        }
                    }
                }
            }
        }
    }
}
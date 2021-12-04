package net.xzos.testself.ui.utils

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.io.InputStream

@Composable
fun getFileLauncher(
    callback: (InputStream?) -> Unit
): ManagedActivityResultLauncher<String, Uri?> {
    val context = LocalContext.current
    val pickPictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { callback(context.contentResolver.openInputStream(it)) }
    }

    return pickPictureLauncher
}
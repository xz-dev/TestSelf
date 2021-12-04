package net.xzos.testself.core.manager

import androidx.compose.runtime.Composable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import net.xzos.testself.core.parser.xlsSaveDB
import net.xzos.testself.ui.utils.getFileLauncher

@Composable
fun addPoolLauncher(callback: () -> Unit): () -> Unit {
    val launcher = getFileLauncher(callback = { inputStream ->
        inputStream?.let {
            runBlocking(Dispatchers.Default) { xlsSaveDB(it) }
        }
        callback()
    })
    return { launcher.launch("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") }
}
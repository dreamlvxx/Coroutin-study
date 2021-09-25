import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            //如果在finally中调用挂起函数，当被取消的时候会报错
//            try {
//                meth11()
//            } catch (e: Throwable) {
//                println(e)
//            }
            //如果必须调用，那么利用如下写法可以实现
            withContext(NonCancellable) {
                println("job: I'm running finally")
                delay(1000L)
                println("job: And I've just delayed for 1 sec because I'm non-cancellable")
            }
        }
    }

    delay(1300L) // 延迟一段时间
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // 取消该作业并等待它结束
    println("main: Now I can quit.")
}

suspend fun meth11() {
    delay(1000)
    println("suspend method")
}
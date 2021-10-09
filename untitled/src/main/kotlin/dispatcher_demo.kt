import kotlinx.coroutines.*


/**
 * 当一个协程被其它协程在 CoroutineScope 中启动的时候， 它将通过 CoroutineScope.coroutineContext 来承袭上下文，
 * 并且这个新协程的 Job 将会成为父协程作业的 子 作业。当一个父协程被取消的时候，所有它的子协程也会被递归的取消。
 * coroutineScope启动的协程亦然
 *
 * 然而，当使用 GlobalScope 来启动一个协程时，则新协程的作业没有父作业。 因此它与这个启动的作用域无关且独立运作。
 */
fun main() = runBlocking {
    // 启动一个协程来处理某种传入请求（request）
    val request = launch {
        // 孵化了两个子作业, 其中一个通过 GlobalScope 启动
        GlobalScope.launch {
            println("job1: I run in GlobalScope and execute independently!")
            delay(1000)
            println("job1: I am not affected by cancellation of the request")
        }
        // 另一个则承袭了父协程的上下文
        launch {
            delay(100)
            println("job2: I am a child of the request coroutine")
            delay(1000)
            println("job2: I will not execute this line if my parent request is cancelled")
        }

        coroutineScope {
            delay(100)
            println("job3: I am a coroutineScope child of the request coroutine")
            delay(1000)
            println("job3: I will not execute this line if my parent request is cancelled")
        }
    }
    delay(500)
    request.cancel() // 取消请求（request）的执行
    delay(1000) // 延迟一秒钟来看看发生了什么
    println("main: Who has survived request cancellation?")


    val job2 = launch {
        repeat(3) { i ->
            launch {
                delay((i + 1) * 200L)
                println("corou $i is done")
            }
        }
    }

    job2.join()
    println("all task is done")

}

/**
 *Dispatchers.Unconfined 比较特殊
 */
suspend fun dispatche() = coroutineScope{
    launch { // 运行在父协程的上下文中，即 runBlocking 主协程
        println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Unconfined) { // 不受限的——将工作在主线程中
        println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Default) { // 将会获取默认调度器
        println("Default               : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(newSingleThreadContext("MyOwnThread")) { // 将使它获得一个新的线程
        println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
    }
}

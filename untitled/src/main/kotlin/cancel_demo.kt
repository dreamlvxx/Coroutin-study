import kotlinx.coroutines.*


/**
 * 之所以连续调用这两个方法，是因为 cancel() 函数调用后会马上返回而不是等待协程结束后再返回，所以此时协程不一定是马上就停止了，
 * 为了确保协程执行结束后再执行后续代码，此时就需要调用 join() 方法来阻塞等待。可以通过调用 Job 的扩展函数 cancelAndJoin() 来完成相同操作
 */
fun main() = runBlocking {
    val job = launch {
        repeat(1000) { i ->
            println("job: I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L) // 延迟一段时间
    println("main: I'm tired of waiting!")
    job.cancel() // 取消该作业
    job.join()
    println("main: Now I can quit.")
}
import kotlinx.coroutines.*

//runBlocking会阻塞到内部的协程执行完毕
fun main() = runBlocking { // 开始执行主协程
    launch(start = CoroutineStart.LAZY) { // 在后台启动一个新的协程并继续
        delay(1000L)
        println("World!")
    }
    println("Hello,") // 主协程在这里会立即执行
}
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * runBlocking 与 coroutineScope 可能看起来很类似，因为它们都会等待其协程体以及所有子协程结束。
 * 主要区别在于，runBlocking 方法会【阻塞】当前线程来等待， 而 coroutineScope 只是【挂起】，会释放底层线程用于其他用途。
 * 由于存在这点差异，runBlocking 是常规函数，而 coroutineScope 是挂起函数
 */
fun main() = runBlocking {
    val time = measureTimeMillis {
        launch {  //这个scope 是coroutineScope的scope
            delay(200L)
            println("Task 200 ${Thread.currentThread().name}")
        }

        coroutineScope { // 创建一个协程作用域
            launch {  //这个scope 是coroutineScope的scope
                delay(500L)
                println("Task 500 ${Thread.currentThread().name}")
            }

            delay(100L)
            println("Task 100 ${Thread.currentThread().name}") // 这一行会在内嵌 launch 之前输出
        }

        // 这一行在内嵌 launch 执行完毕后才输出
        println("final ${Thread.currentThread().name}")
    }
    println("time si = $time")
}
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun main(){
    val job = GlobalScope.launch {
        delay(1000)
        println("world1")
    }
    println("hello1")
    job.join() // 等待直到子协程执行结束
}
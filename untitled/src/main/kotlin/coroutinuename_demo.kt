import kotlinx.coroutines.*

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")
fun main() = runBlocking {
    val v1 = async(CoroutineName("name-1")) {
        delay(500)
        log("v1")
        252
    }
    val v2 = async(CoroutineName("name-2")) {
        delay(1000)
        log("bbb")
        6
    }

    println(v1.await() + v2.await())

    /**
     * 设置启动模式  并且命名
     */
    launch(Dispatchers.Default + CoroutineName("name-3")) {
        log("222")
    }
    println("asd")
}
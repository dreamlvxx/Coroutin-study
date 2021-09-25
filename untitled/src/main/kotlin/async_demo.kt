import kotlinx.coroutines.*

fun main() = runBlocking {
    //下面是挂起，依次执行的
//    val res1 = getRes1()
//    val res2 = getRes2()
//    println(res1 + res2)


    //使用async并发
    val res1 = async { getRes1() }
    val res2 = async { getRes2() }
    println(res1.await() + res2.await())
}

suspend fun getRes1(): Int{
    println("start 1")
    delay(5000)
    return 1
}

suspend fun getRes2(): Int{
    println("start 2")
    delay(1000)
    return 2
}


//使用 async 的结构化并发
suspend fun twoSum(): Int = coroutineScope {
    val one = async { getRes1() }
    val two = async { getRes2() }
    one.await() + two.await()
}
suspend fun main() {
    // введите сколько раз должен заполниться грузовик товаром в переменную count для завершения программы.

    val distributionCenter = DistributionCenter()
    val count = 5
    distributionCenter.distCenter(count)
}

fun Int.check(): Boolean {
    return (0..100).random() <= this
}
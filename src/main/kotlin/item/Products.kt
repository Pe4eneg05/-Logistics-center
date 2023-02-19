package item

import kotlin.random.Random
import kotlin.random.nextInt

object Products {
    private val products = listOf(
        Product("Socks", false, ItemTypes.SMALL),
        Product("T-Shirt", false, ItemTypes.SMALL),
        Product("Hoodie", false, ItemTypes.SMALL),

        Product("PC", false, ItemTypes.MEDIUM),
        Product("TV", false, ItemTypes.MEDIUM),
        Product("Monitor", false, ItemTypes.MEDIUM),

        Product("Bad", false, ItemTypes.BIG),
        Product("Cupboard", false, ItemTypes.BIG),
        Product("Fridge", false, ItemTypes.BIG),

        Product("Bread", true, ItemTypes.FOOD),
        Product("Coconut", true, ItemTypes.FOOD),
        Product("Corn", true, ItemTypes.FOOD)
    )

    fun getRandomProduct(maxWeight: Int, isFood: Boolean): Product? =
        if (isFood) {
            val list = products.filter { (it.isFood) && (it.type.size <= maxWeight) }
            if (list.isNotEmpty()) list[Random.nextInt(list.indices)] else null
        } else {
            val list = products.filter { (!it.isFood) && (it.type.size <= maxWeight) }
            if (list.isNotEmpty()) list[Random.nextInt(list.indices)] else null
        }
}
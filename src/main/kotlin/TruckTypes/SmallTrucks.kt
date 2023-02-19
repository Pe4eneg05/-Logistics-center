package TruckTypes

import item.Product

data class SmallTrucks(
    override val loadCapacity: Int = 10,
    override val listProducts: MutableList<Product> = mutableListOf(),
    override var isFull: Boolean = false,
) : Truck(loadCapacity, listProducts, isFull)

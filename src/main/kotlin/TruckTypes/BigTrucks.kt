package TruckTypes

import item.Product

data class BigTrucks(
    override val loadCapacity: Int = 20,
    override val listProducts: MutableList<Product> = mutableListOf(),
    override var isFull: Boolean = false,
) : Truck(loadCapacity, listProducts, isFull)
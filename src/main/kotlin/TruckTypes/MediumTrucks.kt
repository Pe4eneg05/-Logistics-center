package TruckTypes

import item.Product

data class MediumTrucks(
    override val loadCapacity: Int = 15,
    override val listProducts: MutableList<Product> = mutableListOf(),
    override var isFull: Boolean = false,
) : Truck(loadCapacity, listProducts, isFull)
package TruckTypes

import item.Product

abstract class Truck(
    open val loadCapacity: Int,
    open val listProducts: MutableList<Product>,
    open var isFull: Boolean,
)
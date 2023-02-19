import TruckTypes.BigTrucks
import TruckTypes.MediumTrucks
import TruckTypes.SmallTrucks
import TruckTypes.Truck
import item.ItemTypes
import item.Product
import item.Products
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlin.random.Random
import kotlin.random.nextInt

//Шанс того, что грузовик будет загружен пищевым товаром
private const val CHANCE_FOOD = 33

class DistributionCenter() {
    private fun createTrucks(): Truck {
        val listTruck = listOf(SmallTrucks(), MediumTrucks(), BigTrucks())
        val truck = listTruck[Random.nextInt(listTruck.indices)]

        val objectProduct = Products
        val chanceFood = CHANCE_FOOD.check()
        var size = truck.loadCapacity
        while (true) {
            val product = objectProduct.getRandomProduct(size, chanceFood)
            if (product == null) break
            else {
                size -= product.type.size
                truck.listProducts.add(product)
            }
        }
        return truck
    }

    private suspend fun portOfDischarge(channelTruck: Channel<Truck>, productList: MutableList<Product>) {
        val truck = channelTruck.receive()
        println("A truck with a load capacity of ${truck.loadCapacity} arrived")
        truck.listProducts.forEach {
            productList.add(it)
        }
        val list = mutableListOf<String>()
        truck.listProducts.forEach { list.add(it.name) }
        print("Truck is over - ")
        println(list.joinToString(", "))
        println("----------------------------------------------------")
    }

    suspend fun distCenter(count: Int) = coroutineScope {
        var counter = 0
        val channelTruck = Channel<Truck>()
        //Склад
        val productList = mutableListOf<Product>()

        //Создает грузовик с продуктами
        launch {
            while (counter != count) {
                channelTruck.send(createTrucks())
                delay(10000)
            }
        }

        //Порт разгрузки 1
        launch {
            while (counter != count) {
                println("Port of discharge #1 start")
                portOfDischarge(channelTruck, productList)
                delay(60000)
            }
        }

        //Порт разгрузки 2
        launch {
            while (counter != count) {
                println("Port of discharge #2 start")
                portOfDischarge(channelTruck, productList)
                delay(60000)
            }
        }

        //Порт разгрузки 3
        launch {
            while (counter != count) {
                println("Port of discharge #3 start")
                portOfDischarge(channelTruck, productList)
                delay(60000)
            }
        }

        //Порт загрузки
        launch {
            while (counter != count) {
                val listTruck = listOf(SmallTrucks(), MediumTrucks())
                val truck = listTruck[Random.nextInt(listTruck.indices)]
                val listType = listOf(ItemTypes.SMALL, ItemTypes.MEDIUM, ItemTypes.BIG, ItemTypes.FOOD)
                val type = listType[Random.nextInt(listType.indices)]
                val productMaxWeight = type.size
                var productsInTruck = 0


                delay(500)
                println("The truck arrived at the port of loading!")
                println("----------------------------------------------------")

                when (type) {
                    ItemTypes.SMALL -> {
                        while (truck.loadCapacity > productsInTruck) {
                            for (i in productList.indices.reversed()) {
                                if (truck.loadCapacity < productsInTruck || truck.loadCapacity == productsInTruck || truck.loadCapacity < productsInTruck + productMaxWeight) break
                                if (productList[i].type == ItemTypes.SMALL) {
                                    truck.listProducts.add(productList[i])
                                    productList.removeAt(i)
                                    productsInTruck += productMaxWeight
                                }
                            }
                        }
                    }
                    ItemTypes.MEDIUM -> {
                        while (truck.loadCapacity > productsInTruck + productMaxWeight) {
                            for (i in productList.indices.reversed()) {
                                if (truck.loadCapacity < productsInTruck || truck.loadCapacity == productsInTruck || truck.loadCapacity < productsInTruck + productMaxWeight) break
                                if (productList[i].type == ItemTypes.MEDIUM) {
                                    truck.listProducts.add(productList[i])
                                    productList.removeAt(i)
                                    productsInTruck += productMaxWeight
                                }
                            }
                        }
                    }
                    ItemTypes.BIG -> {
                        while (truck.loadCapacity > productsInTruck) {
                            for (i in productList.indices.reversed()) {
                                if (truck.loadCapacity < productsInTruck || truck.loadCapacity == productsInTruck || truck.loadCapacity < productsInTruck + productMaxWeight) break
                                if (productList[i].type == ItemTypes.BIG) {
                                    truck.listProducts.add(productList[i])
                                    productList.removeAt(i)
                                    productsInTruck += productMaxWeight
                                }
                            }
                        }
                    }
                    ItemTypes.FOOD -> {
                        while (truck.loadCapacity > productsInTruck + productMaxWeight) {
                            for (i in productList.indices.reversed()) {
                                if (truck.loadCapacity < productsInTruck || truck.loadCapacity == productsInTruck || truck.loadCapacity < productsInTruck + productMaxWeight) break
                                if (productList[i].type == ItemTypes.FOOD) {
                                    truck.listProducts.add(productList[i])
                                    productList.removeAt(i)
                                    productsInTruck += productMaxWeight
                                }
                            }
                        }
                    }
                }
                counter++
                println("Truck capacity: ${truck.loadCapacity}")
                print("Loaded products: ")
                val list = mutableListOf<String>()
                truck.listProducts.forEach { list.add(it.name) }
                println(list.joinToString(", "))
                println("The truck is full!")
                println("The truck is gone!")
                println("----------------------------------------------------")
                delay(60000)
            }
        }

    }
}
import java.util.*

fun main(args: Array<String>) {
    val pharmaceuticalStockTracker = PharmaceuticalStockTracker()

    // Create Medication Containers to add to the Stock //
    //--------------------------------------------------------------------------------------------------
    val tablet1ExpirationDate =  Date(2022, 1, 1)
    val tablet1 = TabletMedicationContainer("tablet1", tablet1ExpirationDate,
        10, 2.1, "IU")
    val tablet2ExpirationDate = Date(2022, 2, 1)
    val tablet2 = TabletMedicationContainer("tablet2", tablet2ExpirationDate,
        12, 2.4, "IU")
    var tablets : Set<TabletMedicationContainer> = ArrayList<TabletMedicationContainer>().toSet()
    tablets = tablets.plus(tablet1).plus(tablet2)

    val liquid1ExpirationDate = Date(2021, 1, 1)
    val liquid1 = LiquidMedicationContainer("liquid1", liquid1ExpirationDate,
        20.4, 5, "M")
    val liquid2ExpirationDate = Date(2021, 2, 1)
    val liquid2 = LiquidMedicationContainer("liquid2", liquid1ExpirationDate,
        26.4, 2, "M")
    var liquids : Set<LiquidMedicationContainer> = ArrayList<LiquidMedicationContainer>().toSet()
    liquids = liquids.plus(liquid1).plus(liquid2)
    //------------------------------------------------------------------------------------------------------

    // Test working operations (no errors) //
    //------------------------------------------------------------------------------------------------------
    val addedTablets = pharmaceuticalStockTracker.add("55555-333-22", tablets)
    val addedLiquids = pharmaceuticalStockTracker.add("44444-000-22", liquids)

    val inStockTablets = pharmaceuticalStockTracker.currentStockOf("55555-333-22")
    val inStockLiquids = pharmaceuticalStockTracker.currentStockOf("44444-000-22")

    if (inStockTablets.first == StockingMessage.succes) {
        println("\n55555-333-22 in stock: ")
        for (i in inStockTablets.second!!) {
            println("Name:" + i.name + ", Expiration Date: " + i.expirationDate)
        }
    }
    if (inStockLiquids.first == StockingMessage.succes) {
        println("\n44444-000-22 in stock: ")
        for (i in inStockLiquids.second!!) {
            println("Name: " + i.name + ", Expiration Date: " + i.expirationDate)
        }
    }
    if (pharmaceuticalStockTracker.sold(1, "55555-333-22").first == StockingMessage.succes) {
        println("\n55555-333-22 after sold 1: ")
        for (i in pharmaceuticalStockTracker.currentStockOf("55555-333-22").second!!) {
                println("Name:" + i.name + ", Expiration Date: " + i.expirationDate)
        }
    }
    //------------------------------------------------------------------------------------------------------

    // Test invalid operation (Errors) //
    //------------------------------------------------------------------------------------------------------
    println("\nExamples of Errors")
    println("- Wrong format:")
    val error1 = pharmaceuticalStockTracker.currentStockOf("555-333-333")
    println(error1)
    println("\n- Doesn't exist")
    val error2 = pharmaceuticalStockTracker.currentStockOf("55555-333-11")
    println(error2)
    println("\n- Not enough in stock to sell")
    val error3 = pharmaceuticalStockTracker.sold(3, "55555-333-22")
    println(error3)
    //------------------------------------------------------------------------------------------------------
}



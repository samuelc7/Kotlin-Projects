import java.util.Date

class TabletMedicationContainer(aName: String, anExpirationDate:Date, aPillCount:Int, aPotency:Double, thePotencyUnits:String)
                : MedicationContainer(aName, anExpirationDate) {
    val pillCount = aPillCount
    val potency = aPotency
    val potencyUnits = thePotencyUnits
}
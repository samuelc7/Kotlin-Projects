import java.util.Date

class LiquidMedicationContainer(aName:String, anExpirationDate:Date, aVolume:Double, aConcentration:Int, theConcentrationUnits:String)
    : MedicationContainer(aName, anExpirationDate) {
    val volume = aVolume
    val concentration = aConcentration
    val concentrationUnits = theConcentrationUnits
}
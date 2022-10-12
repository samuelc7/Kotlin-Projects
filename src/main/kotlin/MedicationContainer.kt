import java.util.*

open class MedicationContainer(aName:String, anExpirationDate:Date){
    val id = UUID.randomUUID().toString()
    val name = aName
    val expirationDate = anExpirationDate
}
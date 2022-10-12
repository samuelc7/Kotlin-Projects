class PharmaceuticalStockTracker {
    var inStockMedications = mutableMapOf<String, Set<MedicationContainer>>()

    /**
     * This method will add the given containers to the inStockMedications dictionary
     *
     * Params:
     *  - expectedNdcPackageCode:Sting - The NDC code of the package in the format 00000-000-00
     *  - containersToAdd:Set<MedicationContainer> - A set of MedicationContainers to add
     * Returns:
     *  - (Boolean, StockingMessage)
     *      - (True, success) if the operation was successful
     *      - (False, {Failure_Message}) if the operation was unsuccessful
     */
    fun add(expectedNdcPackageCode:String, containersToAdd:Set<MedicationContainer>) : Pair<Boolean, StockingMessage> {
        // Check to make sure the NDC code is in correct format
        if (! checkFormat(expectedNdcPackageCode))
            return Pair(false, StockingMessage.invalidNDCFormat)
        // If NDC code already in inStockMedications -> add container to it
        if (inStockMedications.containsKey(expectedNdcPackageCode))
            inStockMedications[expectedNdcPackageCode]?.plus(containersToAdd)
        // Otherwise, add new key with the value of containers
        else {
            val sorted = containersToAdd.sortedBy { it.expirationDate }
            inStockMedications[expectedNdcPackageCode] = sorted.toSet()
        }

        return Pair(true, StockingMessage.succes)
    }

    /**
     * This method will 'sell' the amount of Medication Containers requested
     * It will pull {count} number of containers from the inStockMedications dictionary
     *
     * Params:
     *  - count:Int - The number of Medication Containers that will be pulled
     *  - of:String - The NDC code of the Medication Containers. Should be in the
     *          format of 00000-000-00
     * Returns:
     *  (StockingMessage, Set<MedicationContainer>)
     *      - (success, [MedicationContainer]) if the operation was successful
     *      - ({Failure_Message}, null) if the operation was unsuccessful
     */
    fun sold(count:Int, of:String) : Pair<StockingMessage, Set<MedicationContainer>?> {
        // Check to see if the NDC code is in the correct format
        if (! checkFormat(of))
            return Pair(StockingMessage.invalidNDCFormat, null)
        // Check if the inStockMedications has the requested medication containers
        if (! inStockMedications.containsKey(of))
            return Pair(StockingMessage.notInInventory, null)
        // Check to if there is enough materials to sell the requested amount
        if (inStockMedications[of]!!.size < count)
            return Pair(StockingMessage.notEnoughInInventory, null)
        // If all checks passed, get, remove, and return materials
        val containers = inStockMedications[of]!!.take(count)
        inStockMedications[of] = inStockMedications[of]!!.minus(containers.toSet())
        return Pair(StockingMessage.succes, containers.toSet())
    }

    /**
     * This method will get the available medication containers in the inStockMedications dictionary
     *
     * Params:
     *  - ndcPackageCode:String - The NDC of the medication containers that will be pulled.
     *      Should be in the format of 00000-000-00
     * Returns:
     *  (StockingMessage, Set<MedicationContainer>)
     *      - (success, [MedicationContainer]) if the operation was successful
     *      - ({Failure_Message}, null) if the operation was unsuccessful
     */
    fun currentStockOf(ndcPackageCode:String) : Pair<StockingMessage, Set<MedicationContainer>?> {
        // Check to see if the NDC code is in the correct format
        if (! checkFormat(ndcPackageCode))
            return Pair(StockingMessage.invalidNDCFormat, null)
        // Check to see if there ary any of the requested Medication containers
        if (! inStockMedications.containsKey(ndcPackageCode))
            return Pair(StockingMessage.notInInventory, null)
        // Get the Medication Containers from the inStockMedications dictionary
        return Pair(StockingMessage.succes, inStockMedications[ndcPackageCode]!!.sortedBy { it.expirationDate }.toSet())
    }

    /**
     * This will check the format of the given input. Checks to see if the input String
     * is in the format of 00000-000-00
     *
     * Params:
     *  - toCheck:String - The string that will be checked
     *  Returns:
     *      Boolean
     *      - true if the given input is in the format of 00000-000-00
     *      - false if the given input is not in the format of 00000-000-00
     */
    private fun checkFormat(toCheck:String) : Boolean {
        val split = toCheck.split("-")
        if (split.size != 3 || split[0].count() != 5 || split[1].count() != 3 || split[2].count() != 2)
            return false
        return true
    }

}
import com.usercentrics.sdk.v2.settings.data.UsercentricsService
import kotlin.math.roundToInt

data class UCService(
    val name: String,
    val dataTypes: Set<UCDataType>
) {
    constructor(usercentricsService: UsercentricsService) : this(
        name = usercentricsService.dataProcessor ?: "The Unknown",
        dataTypes = usercentricsService.dataCollectedList.map { UCDataType.fromString(it) }.toSet()
    )

    val cost: Int
        get() {
            var totalCosts = dataTypes.sumOf { it.cost }.toDouble()

            if (isBankingSnoopy) {
                totalCosts += totalCosts * 0.1
            }

            if (isWhyDoYouCare) {
                totalCosts += totalCosts * 0.27
            }

            if (isTheGoodCitizen) {
                totalCosts -= totalCosts * 0.1
            }

            return totalCosts.roundToInt()
        }

    private val isBankingSnoopy: Boolean
        get() = dataTypes.containsAll(
            listOf(
                UCDataType.PurchaseActivity,
                UCDataType.BankDetails,
                UCDataType.CreditAndDebitCardNumber
            )
        )

    private val isWhyDoYouCare: Boolean
        get() = dataTypes.containsAll(
            listOf(
                UCDataType.SearchTerms,
                UCDataType.GeographicLocation,
                UCDataType.IPAddress
            )
        )

    private val isTheGoodCitizen: Boolean
        get() = dataTypes.size <= 4
}

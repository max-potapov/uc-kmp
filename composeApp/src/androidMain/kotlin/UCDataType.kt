enum class UCDataType(val value: String, val cost: Int) {
    ConfigurationOfAppSettings("Configuration of app settings", 1),
    IPAddress("IP address", 2),
    UserBehaviour("User behaviour", 2),
    UserAgent("User agent", 3),
    AppCrashes("App crashes", -2),
    BrowserInformation("Browser information", 3),
    CreditAndDebitCardNumber("Credit and debit card number", 4),
    FirstName("First name", 6),
    GeographicLocation("Geographic location", 7),
    DateAndTimeOfVisit("Date and time of visit", 1),
    AdvertisingIdentifier("Advertising identifier", 2),
    BankDetails("Bank details", 5),
    PurchaseActivity("Purchase activity", 6),
    InternetServiceProvider("Internet service provider", 4),
    JavaScriptSupport("JavaScript support", -1),

    // FIXME: define cost in briefing table
    SearchTerms("Search terms", 0),

    // TODO: assert on any new unhandled type OR ask to improve SDK with providing strong types out of the box
    Undefined("undefined", 0);

    companion object {
        fun fromString(value: String): UCDataType {
            return entries.find { it.value == value } ?: Undefined
        }
    }
}

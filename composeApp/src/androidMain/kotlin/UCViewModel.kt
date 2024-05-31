import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.usercentrics.sdk.BannerSettings
import com.usercentrics.sdk.SecondLayerStyleSettings
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsBanner
import com.usercentrics.sdk.UsercentricsConsentUserResponse
import com.usercentrics.sdk.UsercentricsServiceConsent
import java.lang.ref.WeakReference

class UCViewModel(context: Context) : ViewModel() {
    val score = MutableLiveData("0")
    val isReady = MutableLiveData(false)
    private var banner: UsercentricsBanner? = null
    private val contextRef: WeakReference<Context> = WeakReference(context)
    private val context: Context?
        get() = contextRef.get()

    init {
        Usercentrics.isReady(onSuccess = { status ->
            if (!status.shouldCollectConsent) {
                applyConsent(status.consents)
            }
            isReady.value = true
        }, onFailure = { error ->
            isReady.value = false
            // TODO: Handle error
            error.printStackTrace()
        })
    }

    fun update() {
        val settings = BannerSettings(
            secondLayerStyleSettings = SecondLayerStyleSettings(
                showCloseButton = true,
            )
        )

        banner = context?.let { context ->
            UsercentricsBanner(context, settings).also { banner ->
                banner.showSecondLayer(
                    callback = ::handleUserResponse
                )
            }
        }
    }

    private fun handleUserResponse(userResponse: UsercentricsConsentUserResponse?) {
        userResponse ?: return
        applyConsent(userResponse.consents)
    }

    private fun applyConsent(consents: List<UsercentricsServiceConsent>) {
        // TODO: apply consent
        val enabledTemplateIDs = consents.filter { it.status }.map { it.templateId }
        calculateScore(enabledTemplateIDs)
    }

    private fun calculateScore(enabledTemplateIDs: List<String>) {
        val data = Usercentrics.instance.getCMPData()
        val enabledServices = data.services.filter { enabledTemplateIDs.contains(it.templateId) }
        val services: List<UCService> = enabledServices.map { UCService(it) }
        services.sumOf { it.cost }.toString().also { score.value = it }
        services.forEach { service ->
            println("${service.name} = ${service.cost}")
        }
    }
}

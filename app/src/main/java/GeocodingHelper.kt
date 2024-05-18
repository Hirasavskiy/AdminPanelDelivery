import com.google.android.gms.maps.model.LatLng
import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import com.google.maps.model.GeocodingResult

class GeocodingHelper(apiKey: String) {
    private val context: GeoApiContext = GeoApiContext.Builder()
        .apiKey(apiKey)
        .build()

    fun getCoordinates(address: String): LatLng? {
        return try {
            val results: Array<GeocodingResult> = GeocodingApi.geocode(context, address).await()
            val location = results.first().geometry.location
            LatLng(location.lat, location.lng)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

package com.example.forecastapp.data.dataBase.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime


const val WEATHER_LOCATION_ID = 0

@Entity(tableName = "Weather_location")
data class Location(
    val name: String,
    val country: String,
    val region: String,
    val lat: String,
    val lon: String,
    @SerializedName("timezone_id")
    val timezoneId: String,
    val localtime: String,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Int,
    @SerializedName("utc_offset")
    val utcOffset: String
)
{
    // because we only need to have one instance of the weather and we replace the old
    // value with the new
    @PrimaryKey(autoGenerate = false)
    var id: Int = WEATHER_LOCATION_ID


    // to adjust the timezones between where it was requested and where is taken from
    val zonedDateTime: ZonedDateTime
        get() {
            val instant = Instant.ofEpochSecond(localtimeEpoch.toLong())
            val zoneId = ZoneId.of(timezoneId)
            return ZonedDateTime.ofInstant(instant, zoneId)
        }
}
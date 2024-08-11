package com.sam.testapplication

import android.annotation.SuppressLint
import android.car.Car
import android.car.FuelType
import android.car.VehicleAreaType
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.sam.testapplication.databinding.ActivityCarPropertyBinding

class CarPropertyActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCarPropertyBinding
    private val carProperty by lazy { Car.createCar(this).getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager }

    private val permissionRequest = registerForActivityResult(ActivityResultContracts.RequestPermission()){isGranted ->
        if (isGranted){
            val fuelTypes = getCarPropertyValue<Array<Int>>(VehiclePropertyIds.DOOR_LOCK,VehicleAreaType.VEHICLE_AREA_TYPE_DOOR)
            binding.tvDoor.text = fuelTypes.toString()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeSpeed()
        binding.tvFuelType.text = "Fuel type: ${getFuelType()}"



        permissionRequest.launch("android.car.permission.CONTROL_CAR_DOORS")
    }


    private fun observeSpeed() {
        carProperty.registerCallback(carPropertyCallback, VehiclePropertyIds.PERF_VEHICLE_SPEED, CarPropertyManager.SENSOR_RATE_ONCHANGE)
        carProperty.registerCallback(carPropertyCallback, VehiclePropertyIds.EV_BATTERY_LEVEL, CarPropertyManager.SENSOR_RATE_ONCHANGE)
        carProperty.registerCallback(carPropertyCallback, VehiclePropertyIds.FUEL_LEVEL, CarPropertyManager.SENSOR_RATE_ONCHANGE)
        carProperty.registerCallback(carPropertyCallback, VehiclePropertyIds.DOOR_LOCK, CarPropertyManager.SENSOR_RATE_ONCHANGE)
    }

    private val carPropertyCallback = object : CarPropertyManager.CarPropertyEventCallback {
        @SuppressLint("SetTextI18n")
        override fun onChangeEvent(carPropertvalue: CarPropertyValue<*>?) {
            when(carPropertvalue?.propertyId){
                VehiclePropertyIds.PERF_VEHICLE_SPEED -> {
                    // convert speed from sensor (m/s) to (km/h)
                    val speedKmH = (carPropertvalue.value as Float) * 3600 / 1000
                    binding.tvSpeed.text = "$speedKmH km"
                }
                VehiclePropertyIds.EV_BATTERY_LEVEL -> {
                    val batteryPercent = ((carPropertvalue.value as Float) * 100) / getBatteryCapacity()
                    binding.tvBattery.text = "Battery percent: ${batteryPercent.toInt()}% - capacity ${getBatteryCapacity()}"
                }
                VehiclePropertyIds.FUEL_LEVEL -> {
                    val batteryPercent = ((carPropertvalue.value as Float) * 100) / getFuelCapacity()
                    binding.tvFuel.text = "Fuel level: ${batteryPercent.toInt()}% - capacity ${getFuelCapacity()} Ml"
                }
            }
        }

        override fun onErrorEvent(propertyId: Int, zone: Int) = Unit
    }

    private fun getFuelType() : String {
        var fuel = ""
        val fuelTypes = getCarPropertyValue<Array<Int>>(VehiclePropertyIds.INFO_FUEL_TYPE,VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL)
        fuelTypes.forEach {
            fuel = "$fuel ${FuelType.values()[it].name}"
        }
        return fuel
    }

    fun getFuelCapacity() : Float{
        return getCarPropertyValue(VehiclePropertyIds.INFO_FUEL_CAPACITY, VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL)
    }

    fun getBatteryCapacity() : Float{
        return getCarPropertyValue(VehiclePropertyIds.INFO_EV_BATTERY_CAPACITY, VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL)
    }

    private inline fun <reified T> getCarPropertyValue(carPropertyId : Int, carAreaType: Int) : T{
        return carProperty.getProperty<T>(carPropertyId,carAreaType).value
    }

    enum class FuelType {
        UNKNOWN,UNLEADED,LEADED,DIESEL_1,DIESEL_2,BIODIESEL,E85,LPG,CNG,LNG,ELECTRIC,
        HYDROGEN,OTHER
    }

}
package com.example.thermometre_mobile2

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var manager: SensorManager
    private lateinit var tempSensor: Sensor
    private lateinit var thermo : Thermometre

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        creation d'objet , tu peux creer objets vides
        thermo = Thermometre(this,20f,"C")
//        thermo = Thermometre(this,20f,"K")
//        thermo = Thermometre(this,20f,"F")

//        appeler le view que tu vas a utiliser
        setContentView(thermo)

        manager= getSystemService(Context.SENSOR_SERVICE) as SensorManager
        tempSensor= manager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) as Sensor
        manager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent) {
//        create variable and take value from event (object)
        val temperatureCelcius = event.values[0]
//        donner nouvelle temperature a l'objet
        thermo.setTemperature(temperatureCelcius)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

}
package com.example.thermometre_mobile2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class Thermometre : View {
    private var TempEnDegC: Float = 0f
    private var Unit: String = ""

    // constructor default
    constructor(context: Context) : super(context)

    // constructor temperature
    constructor(context: Context, temp: Float) : super(context) {
        this.TempEnDegC = temp
    }

    // constructor temperature et unite
    constructor(context: Context, temp: Float, unit: String) : super(context) {
        this.TempEnDegC = temp
        this.Unit = unit
    }

    // setter temperature
    fun setTemperature(temp: Float) {
        this.TempEnDegC = temp
        invalidate()
    }

    // setter unite
    fun setUnit(unit: String) {
        this.Unit = unit
        invalidate()
    }

    // function changer a fahrenheit
    fun celsiusToFahrenheit(celsius: Float): Float {
        return (celsius * 9 / 5) + 32
    }

    // function changer a kelvin
    fun celsiusToKelvin(celsius: Float): Float {
        return celsius + 273.15f
    }

    // function changer couleur
    private fun getColorForTemperature(currentTemp: Float, minTemp: Float, maxTemp: Float): Int {
        var temp = currentTemp
        // Limiter la température à la plage definie
        if (temp < minTemp) {
            temp = minTemp
        } else if (temp > maxTemp) {
            temp = maxTemp
        }
        val proportion = (temp - minTemp) / (maxTemp - minTemp)
        val red = (255 * proportion).toInt()
        val blue = (255 * (1 - proportion)).toInt()
        return Color.rgb(red, 0, blue)
    }

    // function dessiner depend de l'unite
    fun drawThermometer(canvas: Canvas, minTemp: Float, maxTemp: Float, unit: String) {
        val currentTemp = when (unit) {
            "F" -> celsiusToFahrenheit(TempEnDegC)
            "K" -> celsiusToKelvin(TempEnDegC)
            else -> TempEnDegC  // Celsius par default
        }

        val color = getColorForTemperature(currentTemp, minTemp, maxTemp)
        val paint = Paint().apply {
            this.color = color
            strokeWidth = 8.0f
        }

        val radius = 35f
        val topMargin = 30f
        val bottomMargin = 30f
        val availableHeight = height - topMargin - bottomMargin
        val proportion = (currentTemp - minTemp) / (maxTemp - minTemp)
        val temperatureHeight = proportion * availableHeight
        val lineHeight = height - bottomMargin - temperatureHeight

        canvas.drawCircle(width / 2f, height - bottomMargin, radius, paint)
        canvas.drawLine(width / 2f, height - bottomMargin - radius, width / 2f, lineHeight, paint)
        drawScale(canvas, minTemp, maxTemp, paint)
    }

    // function dessiner lignes horizontel
    fun drawScale(canvas: Canvas, minTemp: Float, maxTemp: Float, paint: Paint) {
        val stepCount = ((maxTemp - minTemp) / 10).toInt()
        val stepHeight = (height - 60f) / stepCount
        val textPaint = Paint().apply {
            color = Color.BLACK
            strokeWidth = 5.0f
            textSize = 40f
        }

//        boucle "for" pour dessiner petites lignes d'echelle
        for (i in 0..stepCount) {
            val tempStep = minTemp + (i * 10)
            val yPos = height - 30f - stepHeight * i

            canvas.drawLine(width / 2f - 15, yPos, width / 2f + 15, yPos, textPaint)
            canvas.drawText(tempStep.toString(), width / 2f - 100, yPos + textPaint.textSize / 3, textPaint)
        }
    }

//    function pour dessiner depend de quelle type de temperature
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        when (Unit) {
            "C" -> drawThermometer(canvas, 0f, 100f, "C")
            "F" -> drawThermometer(canvas, 32f, 212f, "F")
            "K" -> drawThermometer(canvas, 273.15f, 373.15f, "K")
        }
    }
}

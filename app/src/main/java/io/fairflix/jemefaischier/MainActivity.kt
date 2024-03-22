package io.fairflix.jemefaischier

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import androidx.appcompat.app.AppCompatActivity
import io.fairflix.jemefaischier.databinding.MainActivityBinding
import io.fairflix.jemefaischier.viewmodels.activities.MainActivityViewModel
import io.fairflix.jemefaischier.views.activities.FavoritesActivity
import io.fairflix.jemefaischier.views.activities.MapActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var gravity: Sensor? = null

    private lateinit var binding : MainActivityBinding
    private lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchBtn.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.favoriteBtn.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)


        viewModel = MainActivityViewModel(application)

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event != null){

            fun Float.format(digits: Int) = "%.${digits}f".format(this)

            binding.gravity.text = buildString {
                append(event.values[0].format(2))
                append(" ")
                append(event.values[1].format(2))
                append(" ")
                append(event.values[2].format(2))
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onResume() {
        // Register a listener for the sensor.
        super.onResume()
        sensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause()
        sensorManager.unregisterListener(this)
    }


}

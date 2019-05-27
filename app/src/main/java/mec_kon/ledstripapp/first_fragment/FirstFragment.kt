package mec_kon.ledstripapp.first_fragment

import android.app.Fragment
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.slider.LightnessSlider
import mec_kon.ledstripapp.R
import mec_kon.ledstripapp.network.Network
import mec_kon.ledstripapp.storage.Storage


class FirstFragment : Fragment() {
    var isOn = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.first_fragment, container, false)

        val network = Network()
        val storage = Storage(activity)
        val settings = storage.settings


        val lightnessSlider = view.findViewById<LightnessSlider>(R.id.lightness_slider)
        val colorPicker = view.findViewById<ColorPickerView>(R.id.color_picker)

        colorPicker.setLightnessSlider(lightnessSlider)
        colorPicker.setInitialColor(0xFFFFFFFF.toInt(), true)

        val turnOnOffButton = view.findViewById<Button>(R.id.turn_on_off)

        colorPicker.addOnColorChangedListener {
            val color = colorPicker.selectedColor
            val red = Color.red(color)
            val green = Color.green(color)
            val blue = Color.blue(color)
            val jsonString = network.createJsonString(red, green, blue)
            network.postJsonString(jsonString, settings.address, settings.port)

            isOn = true
            turnOnOffButton.text = "turn off"
        }


        turnOnOffButton.setOnClickListener {
            val jsonString:String
            if(!isOn){
                jsonString = network.createJsonString(255, 255, 255)
                isOn = true
                turnOnOffButton.text = "turn off"
            }
            else {
                jsonString = network.createJsonString(0, 0, 0)
                isOn = false
                turnOnOffButton.text = "turn on"
            }
            network.postJsonString(jsonString, settings.address, settings.port)
        }

        // Inflate the layout for this fragment
        return view
    }

}
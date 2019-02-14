package mec_kon.ledstripapp.first_fragment

import android.app.Fragment
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.slider.LightnessSlider
import mec_kon.ledstripapp.R
import mec_kon.ledstripapp.network.Network
import mec_kon.ledstripapp.storage.Storage


class FirstFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.first_fragment, container, false)

        val network = Network()
        val storage = Storage(activity)
        val settings = storage.settings


        val lightnessSlider = view.findViewById<LightnessSlider>(R.id.lightness_slider)
        val colorPicker = view.findViewById<ColorPickerView>(R.id.color_picker)

        colorPicker.setLightnessSlider(lightnessSlider)
        colorPicker.setInitialColor(0xFFFFFFFF.toInt(), true)

        colorPicker.addOnColorChangedListener {
            val color = colorPicker.selectedColor
            val red = Color.red(color)
            val green = Color.green(color)
            val blue = Color.blue(color)

            val jsonString = network.createJsonString(red, green, blue)
            network.postJsonString(jsonString, settings.address, settings.port)
        }

        val setColorButton = view.findViewById<FloatingActionButton>(R.id.set_color_button)
        setColorButton.setOnClickListener {

            val jsonString = network.createJsonString(0, 0, 0)
            network.postJsonString(jsonString, settings.address, settings.port)
        }

        // Inflate the layout for this fragment
        return view
    }

}
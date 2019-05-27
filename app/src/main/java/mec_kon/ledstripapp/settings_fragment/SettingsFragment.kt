package mec_kon.ledstripapp.settings_fragment

import android.app.Fragment
import android.app.FragmentManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import mec_kon.ledstripapp.R
import mec_kon.ledstripapp.storage.Settings
import mec_kon.ledstripapp.storage.Storage

class SettingsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.settings_fragment, container, false)

        val storage = Storage(activity)

        val saveButton = view.findViewById<Button>(R.id.set_address)
        val addressField = view.findViewById<EditText>(R.id.address)
        val portField = view.findViewById<EditText>(R.id.port)

        addressField.hint = "address: " + storage.settings.address
        portField.hint = "port: " + storage.settings.port

        saveButton.setOnClickListener {
            val address = if (addressField.text.toString().isEmpty()) {
                storage.settings.address
            } else {
                addressField.text.toString()
            }

            val portAsString = portField.text.toString()
            val port: Int = if (portAsString.isEmpty()) storage.settings.port else portAsString.toInt()

            val settings = Settings(address, port)
            storage.settings = settings

            val manager = fragmentManager
            manager.popBackStack()

        }

        return view
    }
}
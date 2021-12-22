package statesdemoapp.view

import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.maruthimourya.statesdemoapp.R
import com.maruthimourya.statesdemoapp.databinding.FragmentStateDetailBinding
import java.util.*

class StateDetailFragment : Fragment() {

    private lateinit var stateDetailFragmentBinding: FragmentStateDetailBinding

    private val args: StateDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        stateDetailFragmentBinding =
            FragmentStateDetailBinding.inflate(layoutInflater, container, false)
        return stateDetailFragmentBinding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action =
                    StateDetailFragmentDirections.actionStateDetailFragmentToStatesListFragment2()
                Navigation.findNavController(view).navigate(action)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        val stateName = args.state
        val stateLatitude = args.latitude
        val stateLongitude = args.longitude
        stateDetailFragmentBinding.apply {
            stateNameText.text = getString(R.string.state) + "   " + stateName
            latitude.text = getString(R.string.latitude) + "   " + stateLatitude
            longitude.text = getString(R.string.longitude) + "   " + stateLongitude

            // GeoCoder
            val geocoder = Geocoder(context, Locale.getDefault())
            val generateAddress =
                geocoder.getFromLocation(stateLatitude.toDouble(), stateLongitude.toDouble(), 1)
            val address = generateAddress[0].getAddressLine(0)

            addressTextview.text = address

            logout.setOnClickListener {
                val action =
                    StateDetailFragmentDirections.actionStateDetailFragmentToLoginFragment()
                Navigation.findNavController(view).navigate(action)
            }

            register.setOnClickListener {
                val action =
                    StateDetailFragmentDirections.actionStateDetailFragmentToRegisterUserFragment()
                Navigation.findNavController(view).navigate(action)

            }
        }

    }


}
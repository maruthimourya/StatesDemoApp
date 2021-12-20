package statesdemoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.maruthimourya.statesdemoapp.databinding.FragmentLoginSuccessBinding

class LoginSuccessFragment : Fragment() {

    private lateinit var loginSuccessFragmentBinding: FragmentLoginSuccessBinding
    private val args: LoginSuccessFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        loginSuccessFragmentBinding =
            FragmentLoginSuccessBinding.inflate(layoutInflater, container, false)
        return loginSuccessFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Do Nothing
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        val userId = args.userId
        val userEmail = args.userEmail

        loginSuccessFragmentBinding.apply {
            firebaseUidText.text = userId
            firebaseEmailText.text = userEmail

            usStatesList.setOnClickListener {
                val action =
                    LoginSuccessFragmentDirections.actionLoginSuccessFragmentToStatesListFragment()
                Navigation.findNavController(view).navigate(action)
            }

            logout.setOnClickListener {
                val action =
                    LoginSuccessFragmentDirections.actionLoginSuccessFragmentToLoginFragment()
                Navigation.findNavController(view).navigate(action)
            }
        }

    }

}
package statesdemoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.maruthimourya.statesdemoapp.databinding.FragmentTechnicalErrorBinding

class TechnicalErrorFragment : Fragment() {

    private lateinit var technicalErrorFragmentBinding: FragmentTechnicalErrorBinding
    private val args: TechnicalErrorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        technicalErrorFragmentBinding =
            FragmentTechnicalErrorBinding.inflate(layoutInflater, container, false)
        return technicalErrorFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Do Nothing
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        technicalErrorFragmentBinding.apply {
            val errorMessage = args.errorMessage
            title.text = errorMessage
            logout.setOnClickListener {
                val action =
                    TechnicalErrorFragmentDirections.actionTechnicalErrorfragmentToLoginFragment()
                Navigation.findNavController(view).navigate(action)
            }
        }

    }

}
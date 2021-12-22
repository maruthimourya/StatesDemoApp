package statesdemoapp.view

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.maruthimourya.statesdemoapp.R
import com.maruthimourya.statesdemoapp.databinding.FragmentRegisterUserBinding

class RegisterUserFragment : Fragment() {

    private lateinit var registerUserFragmentBinding: FragmentRegisterUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        registerUserFragmentBinding =
            FragmentRegisterUserBinding.inflate(layoutInflater, container, false)
        return registerUserFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action =
                    RegisterUserFragmentDirections.actionRegisterUserFragmentToLoginFragment()
                Navigation.findNavController(view).navigate(action)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        registerUserFragmentBinding.apply {

            registerScreenLoginButton.setOnClickListener {
                val action =
                    RegisterUserFragmentDirections.actionRegisterUserFragmentToLoginFragment()
                Navigation.findNavController(view).navigate(action)
            }

            registerEmailEditText.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrEmpty() && !Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                    registerEmailLayout.error = getString(R.string.valid_email)
                } else {
                    registerEmailLayout.error = null
                }
            }

            registerPasswordEditText.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrEmpty() && text.length > 9) {
                    registerPasswordLayout.error = getString(R.string.valid_password)
                } else {
                    registerPasswordLayout.error = null
                }
            }

            registerButton.setOnClickListener {
                when {
                    TextUtils.isEmpty(registerEmailEditText.text.toString().trim { it <= ' ' }) -> {
                        registerEmailLayout.error = getString(R.string.enter_email)
                    }

                    TextUtils.isEmpty(
                        registerPasswordEditText.text.toString().trim { it <= ' ' }) -> {
                        registerPasswordLayout.error = getString(R.string.enter_password)
                    }
                    else -> {
                        val email = registerEmailEditText.text.toString().trim { it <= ' ' }
                        val password = registerPasswordEditText.text.toString().trim { it <= ' ' }

                        // FireBase
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                // Successful Registration
                                if (task.isSuccessful) {
                                    val action =
                                        RegisterUserFragmentDirections.actionRegisterUserFragmentToLoginFragment()
                                    Navigation.findNavController(view).navigate(action)
                                    Toast.makeText(
                                        context,
                                        "Successfully Registered",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()

                                } else {
                                    Toast.makeText(
                                        context,
                                        task.exception?.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                }
            }
        }

    }

}
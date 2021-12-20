package statesdemoapp.view

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.maruthimourya.statesdemoapp.R
import com.maruthimourya.statesdemoapp.databinding.FragmentLoginUserBinding

class LoginUserFragment : Fragment() {

    private lateinit var loginUserFragmentBinding: FragmentLoginUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        loginUserFragmentBinding =
            FragmentLoginUserBinding.inflate(layoutInflater, container, false)
        return loginUserFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        loginUserFragmentBinding.apply {

            loginRegisterButton.setOnClickListener {
                val action = LoginUserFragmentDirections.actionLoginFragmentToRegisterUserFragment()
                Navigation.findNavController(view).navigate(action)
            }

            loginEmailEditText.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrEmpty() && !Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                    loginEmailLayout.error = getString(R.string.valid_email)
                } else {
                    loginEmailLayout.error = null
                }
            }

            loginPasswordEditText.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrEmpty() && text.length > 9) {
                    loginPasswordLayout.error = getString(R.string.valid_password)
                } else {
                    loginPasswordLayout.error = null
                }
            }

            loginButton.setOnClickListener {
                when {
                    TextUtils.isEmpty(loginEmailEditText.text.toString().trim { it <= ' ' }) -> {
                        loginEmailLayout.error = getString(R.string.enter_email)
                    }

                    TextUtils.isEmpty(
                        loginPasswordEditText.text.toString().trim { it <= ' ' }) -> {
                        loginPasswordLayout.error = getString(R.string.enter_password)
                    }
                    else -> {
                        val email = loginEmailEditText.text.toString().trim { it <= ' ' }
                        val password = loginPasswordEditText.text.toString().trim { it <= ' ' }

                        // FireBase
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                // Successful Registration
                                if (task.isSuccessful) {
                                    val firebaseUser = task.result!!.user
                                    val userId = firebaseUser!!.uid
                                    val userEmail = firebaseUser.email.toString()
                                    val action =
                                        LoginUserFragmentDirections.actionLoginFragmentToLoginSuccessFragment(
                                            userId,
                                            userEmail
                                        )
                                    Navigation.findNavController(view).navigate(action)
                                } else {
                                    val errorMessage = task.exception?.message.toString()
                                    val action =
                                        LoginUserFragmentDirections.actionLoginFragmentToTechnicalErrorfragment(
                                            errorMessage
                                        )
                                    Navigation.findNavController(view).navigate(action)
                                }
                            }
                    }
                }
            }
        }
    }

}
package statesdemoapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.maruthimourya.statesdemoapp.R
import com.maruthimourya.statesdemoapp.databinding.FragmentStatesListBinding
import statesdemoapp.network.States
import statesdemoapp.network.StatesListService
import statesdemoapp.network.RetrofitBuilder
import statesdemoapp.network.StateAdapter
import retrofit2.Call
import retrofit2.Response

class StatesListFragment : Fragment() {

    private lateinit var statesListFragmentBinding: FragmentStatesListBinding
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: StateAdapter
    private lateinit var service: StatesListService
    private lateinit var dialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        statesListFragmentBinding =
            FragmentStatesListBinding.inflate(layoutInflater, container, false)
        return statesListFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(context)
        service = RetrofitBuilder.statesApiService
        statesListFragmentBinding.statesView.layoutManager = layoutManager
        getAllStates()

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

    }

    private fun getAllStates() {
        val errorMessage =
            getString(R.string.something_went_wrong_please_check_your_network_connection)
        dialog = LoadingDialog(this.requireActivity())
        dialog.startLoading()
        service.getStates().enqueue(object : retrofit2.Callback<MutableList<States>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<MutableList<States>>,
                response: Response<MutableList<States>>
            ) {
                if (response.isSuccessful) {
                    adapter = StateAdapter(response.body() as MutableList<States>)
                    adapter.notifyDataSetChanged()
                    statesListFragmentBinding.statesView.adapter = adapter
                    dialog.dismiss()
                } else {
                    val action =
                        StatesListFragmentDirections.actionStatesListFragmentToTechnicalErrorfragment(
                            errorMessage
                        )
                    Navigation.findNavController(statesListFragmentBinding.root).navigate(action)
                    dialog.dismiss()
                }

            }

            override fun onFailure(call: Call<MutableList<States>>, t: Throwable) {
                val action =
                    StatesListFragmentDirections.actionStatesListFragmentToTechnicalErrorfragment(
                        errorMessage
                    )
                Navigation.findNavController(statesListFragmentBinding.root).navigate(action)
                dialog.dismiss()
            }
        })
    }

}
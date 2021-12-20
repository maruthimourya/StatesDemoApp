package statesdemoapp.network

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.maruthimourya.statesdemoapp.databinding.StateViewBinding
import statesdemoapp.view.StatesListFragmentDirections

class StateAdapter(private val stateList: MutableList<States>) :
    RecyclerView.Adapter<StateHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StateViewBinding.inflate(inflater, parent, false)
        this.context = parent.context
        return StateHolder(binding)
    }

    override fun onBindViewHolder(holder: StateHolder, position: Int) {
        val stateName = stateList[position].state
        val stateLatitude = stateList[position].latitude.toString()
        val stateLongitude = stateList[position].longitude.toString()
        holder.stateName.text = stateName
        holder.stateLat.text = stateLatitude
        holder.stateLong.text = stateLongitude
        holder.nextBtn.setOnClickListener {
            val action = StatesListFragmentDirections.actionStatesListFragmentToStateDetailFragment(
                stateName,
                stateLatitude,
                stateLongitude
            )
            Navigation.findNavController(holder.itemView).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return stateList.size
    }

}

class StateHolder(binding: StateViewBinding) : RecyclerView.ViewHolder(binding.root) {
    var stateName = binding.stateName
    var stateLat = binding.stateLat
    var stateLong = binding.stateLong
    var nextBtn = binding.nextButton
}
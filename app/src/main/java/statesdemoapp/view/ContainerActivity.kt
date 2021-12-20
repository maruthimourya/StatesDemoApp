package statesdemoapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maruthimourya.statesdemoapp.databinding.ActivityContainerBinding

class ContainerActivity : AppCompatActivity() {

    private lateinit var containerActivityBinding: ActivityContainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        containerActivityBinding = ActivityContainerBinding.inflate(layoutInflater)
        setContentView(containerActivityBinding.root)
    }

}
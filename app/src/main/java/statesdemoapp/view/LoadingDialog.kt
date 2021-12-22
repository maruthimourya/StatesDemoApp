package statesdemoapp.view

import android.app.Activity
import android.app.AlertDialog
import com.maruthimourya.statesdemoapp.R

class LoadingDialog(private val mActivity: Activity) {

    private lateinit var dialog: AlertDialog

    fun startLoading() {
        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_item, null)
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }

}
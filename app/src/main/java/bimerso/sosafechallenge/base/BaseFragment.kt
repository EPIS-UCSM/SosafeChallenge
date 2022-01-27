package bimerso.sosafechallenge.base

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.b3ek.rovident.base.MasterDialog

open class BaseFragment : Fragment() {
    public lateinit  var parentActivity: Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentActivity = this.requireActivity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    protected fun showMessage(title: String?, message: String?) {
        MasterDialog(requireContext(), title!!, message!!, "Aceptar", false,) {
            //ok
        }.show()
    }
    protected fun showErrorMessage(title: String?, message: String?) {
        MasterDialog(requireContext(), title!!, message!!, "Aceptar", false,) {
            //ok
        }.show()
    }

    protected fun showErrorMessageFragment(title: String?, message: String?, fragment: Fragment) {
        MasterDialog(requireContext(), title!!, message!!, "Aceptar",false,) {

        }.show()
    }

}

package com.manish.shaadi.binding

import androidx.databinding.DataBindingComponent
import androidx.fragment.app.Fragment
import com.manish.shaadi.binding.viewBinding.BindingAdapters
import com.manish.shaadi.binding.viewBinding.InverseSpinnerBindings
import com.manish.shaadi.binding.viewBinding.SpinnerBindings


/**
 *  @author Manish patel on 28/09/19
 * A Data Binding Component implementation for fragments.
 */
class FragmentDataBindingComponent(var fragment: Fragment) : DataBindingComponent {
    override fun getSpinnerBindings(): SpinnerBindings {
        return SpinnerBindings()
    }

    override fun getInverseSpinnerBindings(): InverseSpinnerBindings {
        return InverseSpinnerBindings()
    }

    override fun getBindingAdapters(): BindingAdapters {
        return BindingAdapters()
    }

    override fun getFragmentBindingAdapters(): FragmentBindingAdapters {
        return FragmentBindingAdapters(fragment)
    }
}

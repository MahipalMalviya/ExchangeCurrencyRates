package com.mahipal.exchangecurrencyrates.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.mahipal.exchangecurrencyrates.viewModel.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<T: ViewModel,V: ViewBinding> : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected lateinit var viewModel: T
    protected lateinit var binding: V

    protected abstract fun getViewModelClass(): Class<out ViewModel>
    protected abstract fun getViewBinding(layoutInflater: LayoutInflater): V

    abstract fun initializeViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,viewModelFactory)[getViewModelClass()] as T
        initializeViews()
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
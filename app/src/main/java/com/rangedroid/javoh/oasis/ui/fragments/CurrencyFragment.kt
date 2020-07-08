package com.rangedroid.javoh.oasis.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.db.entity.currency.CurrencyModel
import com.rangedroid.javoh.oasis.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.currency_fragment.*
import kotlinx.android.synthetic.main.snipped_err_connection.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.text.DecimalFormat

class CurrencyFragment : ScopedFragment(R.layout.currency_fragment), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: CurrencyViewModelFactory by instance<CurrencyViewModelFactory>()
    private lateinit var viewModel: CurrencyViewModel

    private lateinit var tvTextUzb: TextView
    private var recyclerView: RecyclerView? = null
    private var recyclerViewAdd: RecyclerView? = null
//    private var ccyAdapter: CurrencyDataAdapter = CurrencyDataAdapter()
//    private var ccyAddAdapter: CurrencyAddAdapter = CurrencyAddAdapter()
    private lateinit var editCcy: EditText
    private lateinit var editSearch: EditText
    private var df: DecimalFormat = DecimalFormat("#,###.##")
    private lateinit var frameAddCcy: FrameLayout
    private lateinit var frameNav: FrameLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrencyViewModel::class.java)
        loadData()
    }

    private fun loadData() = launch{
        viewModel.model().value.await().observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            if (it.isNotEmpty()){
                bindUI(it)
            }else errConnect()
        })
    }

    private fun bindUI(list: List<CurrencyModel>){

    }

    private fun errConnect(){
        relative_err_connect.visibility = View.VISIBLE
        frame_ccy_nav.visibility = View.GONE
        btn_retry.setOnClickListener {
            relative_err_connect.visibility = View.GONE
            spin_kit_ccy.visibility = View.VISIBLE
            Handler().postDelayed(Runnable {
                if (viewModel.mUnitProvider.isOnline()) {
                    relative_err_connect.visibility = View.GONE
                    loadData()
                }else{
                    spin_kit_ccy.visibility = View.GONE
                    relative_err_connect.visibility = View.VISIBLE
                }
            }, 1000)
        }
    }

}
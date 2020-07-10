package com.rangedroid.javoh.oasis.ui.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ybq.android.spinkit.SpinKitView
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.db.entity.currency.CurrencyModel
import com.rangedroid.javoh.oasis.ui.adapters.CurrencyAddAdapter
import com.rangedroid.javoh.oasis.ui.adapters.CurrencyDataAdapter
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
    private var ccyAdapter: CurrencyDataAdapter? = null
    private var ccyAddAdapter: CurrencyAddAdapter? = null
    private lateinit var editCcy: EditText
    private lateinit var editSearch: EditText
    private var df: DecimalFormat = DecimalFormat("#,###.##")
    private lateinit var frameAddCcy: FrameLayout
    private lateinit var frameNav: FrameLayout
    private lateinit var spinKit: SpinKitView
    private lateinit var relativeMain: RelativeLayout
    private lateinit var tvBack: TextView
    private lateinit var viewCcyAddMain: View

    companion object{
        var clicItem: Boolean = false
        var ccyRate: Double = 0.0
        var listMainId: ArrayList<String> = ArrayList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvTextUzb = view.findViewById(R.id.tv_ccy_rate_any)
        editCcy = view.findViewById(R.id.edit_ccy_main)
        editSearch = view.findViewById(R.id.edit_search)
        recyclerView = view.findViewById(R.id.recycler_currency)
        recyclerViewAdd = view.findViewById(R.id.recycler_ccy_add)
        frameAddCcy = view.findViewById(R.id.frame_add_ccy)
        frameNav = view.findViewById(R.id.frame_ccy_nav)
        spinKit = view.findViewById(R.id.spin_kit_ccy)
        relativeMain = view.findViewById(R.id.relativ_main_ccy)
        tvBack = view.findViewById(R.id.tv_back)
        viewCcyAddMain = view.findViewById(R.id.view_ccy_add_main)

        listMainId.clear()
        listMainId.add("USD")
        listMainId.add("EUR")
        listMainId.add("RUB")
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
                if (it.size > 73)
                    bindUI(it, viewModel.listFlags.value!!)
            }
        })
    }

    private fun bindUI(list: List<CurrencyModel>, listFlags: HashMap<String, Int>){
        ccyAdapter = CurrencyDataAdapter(listCurrencyModel = list, listFlags = listFlags, language = viewModel.mUnitProvider.isLocale(), editCcy = editCcy, relativeMain = relativeMain)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = ccyAdapter
        spinKit.visibility = View.GONE
        spinKit.clearAnimation()
        frameNav.visibility = View.VISIBLE

        ccyAddAdapter = CurrencyAddAdapter(listCcyModel = list, listFlags = listFlags, language = viewModel.mUnitProvider.isLocale())
        recyclerViewAdd?.layoutManager = LinearLayoutManager(context)
        recyclerViewAdd?.adapter = ccyAddAdapter

        tvBack.setOnClickListener {
            frameAddCcy.visibility = View.GONE
            ccyAdapter!!.notifyDataSetChanged()
            val imm: InputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm.isAcceptingText) {
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            }
        }

        viewCcyAddMain.setOnClickListener {
            frameAddCcy.visibility = View.VISIBLE
        }

        relativeMain.setBackgroundResource(R.color.colorPanelCcy)
        view_main_ccy?.setOnClickListener {
            editCcy.requestFocus()
            val imm: InputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editCcy, InputMethodManager.SHOW_IMPLICIT)
            editCcy.setText("")
            relativ_main_ccy.setBackgroundResource(R.color.colorPanelMainCcy)
            clicItem = false
        }

        editCcy.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (editCcy.text.isNotEmpty()) {
                    ccyAdapter!!.summ = editCcy.text.toString().toDouble()
                    if (clicItem){
                        tvTextUzb.text = df.format(editCcy.text.toString().toDouble() * ccyRate)
                    }else{
                        tvTextUzb.text = df.format(editCcy.text.toString().toDouble())
                    }
                    ccyAdapter!!.notifyDataSetChanged()
                }else{
                    ccyAdapter!!.summ = 0.0
                    tvTextUzb.text = "0"
                    ccyAdapter!!.notifyDataSetChanged()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        editSearch.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                ccyAddAdapter?.searchText(editSearch.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    private fun errConnect(){
        relative_err_connect.visibility = View.VISIBLE
        frame_ccy_nav.visibility = View.GONE
        spinKit.visibility = View.GONE
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
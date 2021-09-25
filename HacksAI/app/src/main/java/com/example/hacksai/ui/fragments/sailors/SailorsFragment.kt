package com.example.hacksai.ui.fragments.sailors

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hacksai.R
import com.example.hacksai.databinding.FragmentSailorsBinding
import com.example.hacksai.databinding.SailorsViewHolderBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SailorsFragment : Fragment(R.layout.fragment_sailors) {

    private val sailorsViewModel by activityViewModels<SailorsViewModel>()
    private var binding: FragmentSailorsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSailorsBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sailorsViewModel.compositeDisposable.add(
            sailorsViewModel.getShipList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("TEST", it.ships[0].toString())
                    val recyclerView = binding?.sailorsRecyclerView
                    recyclerView?.layoutManager = LinearLayoutManager(view.context)
                    recyclerView?.adapter = SailorsAdapter(it.ships)
                },
                {

                })
        )
    }

}
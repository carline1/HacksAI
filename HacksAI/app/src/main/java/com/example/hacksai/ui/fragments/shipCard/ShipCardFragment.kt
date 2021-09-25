package com.example.hacksai.ui.fragments.shipCard

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.hacksai.ui.common.FullScreenStateChanger
import com.example.hacksai.databinding.FragmentShipCardBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import androidx.core.app.NavUtils
import androidx.navigation.fragment.findNavController
import com.example.hacksai.R


class ShipCardFragment : Fragment() {

    private val shipCardViewModel by activityViewModels<ShipCardViewModel>()

    private var binding: FragmentShipCardBinding? = null
    private val args by navArgs<ShipCardFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentShipCardBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shipCardViewModel.compositeDisposable.add(
            shipCardViewModel.getImageById(args.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val imageBytes = Base64.decode(it.img, Base64.DEFAULT)
                    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    binding?.shipCardImage?.setImageBitmap(decodedImage)

                }, {
                    Log.d("TEST", "error image get -> ${it.localizedMessage}")
                })
        )

        val toolbar = binding?.shipCardToolBar
        toolbar?.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        FullScreenStateChanger.fullScreen(activity as AppCompatActivity, true)
    }

    override fun onStop() {
        super.onStop()
        FullScreenStateChanger.fullScreen(activity as AppCompatActivity, false)
    }
}
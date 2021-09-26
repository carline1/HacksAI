package com.example.hacksai.ui.fragments.shipCard

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
import com.example.hacksai.api.models.res.toList
import com.example.hacksai.api.models.res.toPoint
import com.example.hacksai.ui.common.FullScreenStateChanger
import com.example.hacksai.databinding.FragmentShipCardBinding


import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import androidx.navigation.fragment.findNavController
import com.example.hacksai.R


class ShipCardFragment : Fragment() {

    private val shipCardViewModel by activityViewModels<ShipCardViewModel>()

    private var binding: FragmentShipCardBinding? = null
    private val args by navArgs<ShipCardFragmentArgs>()

    private var mapview: MapView? = null

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

//        val line = Polyline(listOf(
//            Point(59.945933, 30.320045),
//            Point(69.945933, 32.320045)
//        ))

//        mapview?.map?.mapObjects?.addPolyline(line)
        if (args.id == "Седов") {
            mapview = binding?.mapview as MapView

            shipCardViewModel.compositeDisposable.add(
                shipCardViewModel.getImage(mapOf("id" to args.id))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        Log.d("TEST", response.toString())

                        //                    val imageBytes = Base64.decode(it.img, Base64.DEFAULT)
                        //                    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        //                    binding?.shipCardImage?.setImageBitmap(decodedImage)
                        val waysList: List<Point> = response.map {
                            it.toPoint()
                        }
                        //                    val line = Polyline(waysList)

                        val listOfCords: List<List<Double>> = response.map {
                            it.toList()
                        }

                        val listOfPolylines = mutableListOf<Polyline>()
                        val tempList = mutableListOf<Point>()
                        var signPoint = waysList[0]
                        for (point in waysList) {
                            if (signPoint.longitude * point.longitude > 0) {
                                tempList.add(point)
                                signPoint = point
                            } else {
                                listOfPolylines.add(Polyline(tempList))
                                signPoint = point
                                tempList.clear()
                                //                            var longitude = 180.0
                                //                            if (signPoint.longitude < point.longitude)
                                //                                longitude = -180.0
                                //                            val zeroPointEnd = Point(
                                //                                (signPoint.latitude + point.latitude) / 2,
                                //                                longitude
                                //                            )
                                //                            tempList.add(zeroPointEnd)
                                //                            listOfPolylines.add(Polyline(tempList))
                                //                            signPoint = point
                                //                            tempList.clear()
                                //                            val zeroPointStart = Point(
                                //                                (signPoint.latitude + point.latitude) / 2,
                                //                                -longitude
                                //                            )
                                //                            tempList.add(zeroPointStart)
                            }
                        }
                        listOfPolylines.add(Polyline(tempList))

                        for (polyline in listOfPolylines) {
                            mapview?.map?.mapObjects?.addPolyline(polyline)
                        }


                        //                    mapview?.map?.Geo
                        //                    (waysList[0])

                        //                    mapview?.map?.mapObjects?.addPlacemark(waysList[0])
                        //                    mapview?.map?.mapObjects?.addPlacemark(waysList[-1])

                        //                    mapview?.map?.mapObjects?.addPolyline(Polyline(listOf(waysList[0], waysList[0])))
                        //                        ?.strokeColor = R.color.light_green

                        //                    val source =
                        //                        BitmapFactory.decodeResource(requireContext().resources,
                        //                            com.example.hacksai.R.drawable.ic_start_placemark)
                        //
                        //                    val bitmap = source.copy(Bitmap.Config.ARGB_8888, true)
                        //
                        //                    mapview?.map?.mapObjects?.addPlacemark(waysList[0],
                        //                        ImageProvider.fromBitmap(bitmap))
                        //                    mapview?.map?.mapObjects?.addPlacemark(waysList[0], ImageProvider.fromResource(
                        //                        context,
                        //                        com.example.hacksai.R.drawable.ic_start_placemark
                        //                    ))

                        //                    mapview?.map?.mapObjects?.addPolyline(Polyline(listOf(waysList[-1], waysList[-1])))
                        //                        ?.strokeColor = R.color.red

                        //                    mapview?.map?.mapObjects?.addColoredPolyline(
                        //                    val minCord = listOfCords.minWithOrNull { p1, p2 ->
                        //                        when {
                        //                            p1[0] > p2[0] && p1[1] > p2[1] -> 1
                        //                            p1[0] == p2[0] && p1[1] == p2[1] -> 0
                        //                            else -> -1
                        //                        }
                        //                    }
                        //                    val maxCord = listOfCords.minWithOrNull { p1, p2 ->
                        //                        when {
                        //                            p1[0] < p2[0] && p1[1] < p2[1] -> 1
                        //                            p1[0] == p2[0] && p1[1] == p2[1] -> 0
                        //                            else -> -1
                        //                        }
                        //                    }
                        val meanFirstLatitude = (listOfCords[0][0] + listOfCords[0][0]) / 2
                        val meanFirstLongitude = (listOfCords[0][1] + listOfCords[0][1]) / 2

                        //                    Log.d("TEST", minCord.toString())
                        //                    Log.d("TEST", maxCord.toString())
                        //                    Log.d("TEST", )
                        mapview?.map?.move(
                            CameraPosition(
                                Point(meanFirstLatitude, meanFirstLongitude),
                                11.0f,
                                0.0f,
                                0.0f
                            ),
                            Animation(Animation.Type.SMOOTH, 0F),
                            null
                        )
                        //
                        //                    mapview?.map?.mapObjects?.addPolyline(line)
                        //                    Log.d("TEST", way.toString())

                    }, {
                        Log.d("TEST", "error image get -> ${it.localizedMessage}")
                    })
            )
        } else {
            binding?.mapview?.visibility = View.GONE

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
        }

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

        mapview?.onStop();
        MapKitFactory.getInstance().onStop();
    }

    override fun onStart() {
        super.onStart()
        mapview?.onStart()
        MapKitFactory.getInstance().onStart()
    }
}
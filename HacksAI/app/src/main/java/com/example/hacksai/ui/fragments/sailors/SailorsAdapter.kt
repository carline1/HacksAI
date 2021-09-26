package com.example.hacksai.ui.fragments.sailors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hacksai.R
import com.example.hacksai.api.models.res.ShipResponse

class SailorsAdapter(private val dataSet: List<ShipResponse>) :
    RecyclerView.Adapter<SailorsAdapter.SailorsViewHolder>() {

    private fun getItem(position: Int): ShipResponse {
        return dataSet[position]
    }

    inner class SailorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tvSheepId)
        val delimiter: View = itemView.findViewById(R.id.sailorsDelimiter)

        fun bind(item: ShipResponse?, position: Int) {

            textView.text = textView.context.resources.getString(
                R.string.ship_id,
                item?.name
            )
            if (position == dataSet.size - 1)
                delimiter.visibility = View.GONE

//            itemView.setOnClickListener {
//                itemView.findNavController().navigate(R.id.action_sailors_to_shipCardFragment)
//            }

            itemView.setOnClickListener {
                it.startAnimation(AlphaAnimation(10f, 0.8f))

                val action = SailorsFragmentDirections.actionSailorsToShipCardFragment(
                    item?.name!!
                )
                it.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SailorsViewHolder {
        return SailorsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.sailors_view_holder, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SailorsViewHolder, position: Int) {
        return holder.bind(getItem(position), position)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}
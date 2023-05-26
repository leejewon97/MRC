package com.example.myrunjwl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myrunjwl.databinding.RowBinding

class ChallengeDataAdapter(val items:ArrayList<ChallengeData>): RecyclerView.Adapter<ChallengeDataAdapter.ViewHolder>() {
    interface OnItemClickListener{
        fun onItemClick(data: ChallengeData, adapterPosition: Int)
    }

    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.textView.setOnClickListener {
                itemClickListener?.onItemClick(items[adapterPosition], adapterPosition)
            }
        }
    }

    fun moveItem(oldPos:Int, newPos:Int){
        val tmp = items[newPos]
        items[newPos] = items[oldPos]
        items[oldPos] = tmp
        notifyItemMoved(oldPos, newPos)
    }
    fun removeItem(pos:Int){
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textView.text = "한 달에 " + items[position].km + "km"
        holder.binding.textView.background = when (items[position].select) {
            0 -> ContextCompat.getDrawable(holder.binding.root.context, R.drawable.back_border)
            else -> ContextCompat.getDrawable(holder.binding.root.context, R.drawable.back_border_select)
        }
    }
}

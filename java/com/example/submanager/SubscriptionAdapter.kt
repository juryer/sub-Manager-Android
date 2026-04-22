package com.example.submanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class SubscriptionAdapter(
    private val items: MutableList<Subscription>,
    private val onItemClick: (Subscription) -> Unit, // 編集用
    private val onDeleteClick: (Int) -> Unit        // 削除用：これを追加
) : RecyclerView.Adapter<SubscriptionAdapter.ViewHolder>() {

    class ViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view) {
        val nameText: android.widget.TextView = view.findViewById(R.id.txtName)
        val priceText: android.widget.TextView = view.findViewById(R.id.txtPrice)
        val btnDelete: android.widget.ImageButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subscription, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.nameText.text = item.name
        // ここは今朝の状態に合わせて monthlyFee か price にしてください
        holder.priceText.text = "¥${String.format("%,d", item.monthlyFee)}"

        // 行タップで編集画面へ
        holder.itemView.setOnClickListener { onItemClick(item) }

        // ゴミ箱ボタンタップで削除ダイアログへ
        holder.btnDelete.setOnClickListener { onDeleteClick(position) }
    }

    override fun getItemCount() = items.size

    fun onItemMove(fromPos: Int, toPos: Int) {
        val item = items.removeAt(fromPos)
        items.add(toPos, item)
        notifyItemMoved(fromPos, toPos)
    }
}
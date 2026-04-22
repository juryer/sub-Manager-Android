package com.example.submanager

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private var subscriptionList = mutableListOf<Subscription>()
    private lateinit var adapter: SubscriptionAdapter
    private lateinit var txtTotalAmount: TextView

    // 画面遷移の結果（新規追加・編集されたデータ）を受け取る窓口
    private val startForResult = registerForActivityResult(androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val sub = data?.getSerializableExtra("RESULT_SUB") as? Subscription
            val isEdit = data?.getBooleanExtra("IS_EDIT", false) ?: false

            if (sub != null) {
                if (isEdit) {
                    // 編集：IDが一致するものを更新
                    val index = subscriptionList.indexOfFirst { it.id == sub.id }
                    if (index != -1) {
                        subscriptionList[index] = sub
                        adapter.notifyItemChanged(index)
                    }
                } else {
                    // 新規追加
                    subscriptionList.add(sub)
                    adapter.notifyItemInserted(subscriptionList.size - 1)
                }

                // データ更新後に「保存」と「合計計算」を実行
                saveData()
                updateTotalAmount()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. 起動時にデータを読み込む
        loadData()

        setContentView(R.layout.activity_main)

        // ビューの紐付け
        txtTotalAmount = findViewById(R.id.txtTotalAmount)
        val btnAdd = findViewById<ImageButton>(R.id.btnAdd)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // 初回の合計金額を表示
        updateTotalAmount()

        adapter = SubscriptionAdapter(subscriptionList,
            onItemClick = { subscription ->
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("EXTRA_SUB", subscription)
                startForResult.launch(intent)
            },
            onDeleteClick = { position ->
                showDeleteConfirmation(position)
            }
        ) // ← ここをしっかり閉じる

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // 3. 並び替え（ドラッグ＆ドロップ）の実装
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPos = viewHolder.adapterPosition
                val toPos = target.adapterPosition
                adapter.onItemMove(fromPos, toPos)

                // 並び順が変わったので保存
                saveData()
                return true
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)

        // 4. 追加ボタンのクリック（新規作成画面へ）
        btnAdd.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startForResult.launch(intent)
        }
    }

    // 合計金額を計算してUIに反映する
    private fun updateTotalAmount() {
        val total = subscriptionList.sumOf { it.monthlyFee }
        // 三桁区切りのフォーマットを適用
        txtTotalAmount.text = "合計: ${String.format("%,d", total)}円"
    }

    // データの保存
    private fun saveData() {
        val sharedPreferences = getSharedPreferences("sub_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(subscriptionList)
        editor.putString("subscription_list", json)
        editor.apply()
    }

    // データの読み込み
    private fun loadData() {
        val sharedPreferences = getSharedPreferences("sub_prefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("subscription_list", null)
        val type = object : TypeToken<MutableList<Subscription>>() {}.type

        if (json != null) {
            subscriptionList = gson.fromJson(json, type)
        } else {
            subscriptionList = mutableListOf()
        }
    }

    // 削除の確認ダイアログを表示する関数
    private fun showDeleteConfirmation(position: Int) {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("削除の確認")
            .setMessage("このサブスクを削除しますか？")
            .setPositiveButton("削除") { _, _ ->
                subscriptionList.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, subscriptionList.size)
                saveData()
                updateTotalAmount()
            }
            .setNegativeButton("キャンセル", null)
            .show()
    }
}
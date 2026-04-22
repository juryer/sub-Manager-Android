package com.example.submanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val editName = findViewById<EditText>(R.id.editName)
        val editFee = findViewById<EditText>(R.id.editFee)
        val editMethod = findViewById<EditText>(R.id.editMethod)
        val btnSave = findViewById<Button>(R.id.btnSave)

        // 編集モードの場合、既存データをセット
        val subscription = intent.getSerializableExtra("EXTRA_SUB") as? Subscription
        subscription?.let {
            editName.setText(it.name)
            editFee.setText(it.monthlyFee.toString())
            editMethod.setText(it.paymentMethod)
        }

        btnSave.setOnClickListener {
            val name = editName.text.toString()
            val fee = editFee.text.toString().toIntOrNull() ?: 0
            val method = editMethod.text.toString()

            val resultSub = subscription?.apply {
                this.name = name
                this.monthlyFee = fee
                this.paymentMethod = method
            } ?: Subscription(name = name, monthlyFee = fee, paymentMethod = method)

            val intent = Intent().apply {
                putExtra("RESULT_SUB", resultSub)
                // 編集か新規かを判定するためにIDも渡す
                putExtra("IS_EDIT", subscription != null)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
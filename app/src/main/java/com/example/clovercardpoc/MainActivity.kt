package com.example.clovercardpoc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.clover.sdk.v1.Intents
import com.clover.sdk.v3.payments.Payment
import com.clover.sdk.v3.payments.api.PaymentRequestIntentBuilder

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "CLOVER_POC"
        private const val TOKENIZE_REQUEST_CODE = 1001
    }

    private lateinit var txtLog: TextView
    private lateinit var btnTokenize: Button
    private lateinit var btnClear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtLog = findViewById(R.id.txtLog)
        btnTokenize = findViewById(R.id.btnTokenize)
        btnClear = findViewById(R.id.btnClear)

        btnTokenize.setOnClickListener {
            appendLog("Start tokenizacji przez payment flow...")
            startTokenize()
        }

        btnClear.setOnClickListener {
            txtLog.text = ""
            appendLog("Log wyczyszczony")
        }

        appendLog("App started")
        appendLog("Jeśli to widzisz na Cloverze, deploy działa poprawnie.")
    }

    private fun startTokenize() {
        val externalPaymentId = "poc-" + System.currentTimeMillis()
        val amount = 1L

        val builder = PaymentRequestIntentBuilder(externalPaymentId, amount)
        builder.tokenizeOptions(
            PaymentRequestIntentBuilder.TokenizeOptions.Instance(false)
        )

        val intent = builder.build(this)
        startActivityForResult(intent, TOKENIZE_REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TOKENIZE_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                val payment = data.getParcelableExtra<Payment>(Intents.EXTRA_PAYMENT)
                val token = data.getStringExtra(Intents.EXTRA_TOKEN)
                val tokenType = data.getStringExtra(Intents.EXTRA_TOKEN_TYPE)

                appendLog("=== SUCCESS ===")
                appendLog("payment: $payment")
                appendLog("token: $token")
                appendLog("tokenType: $tokenType")
            } else {
                val error = data?.getStringExtra(Intents.EXTRA_FAILURE_MESSAGE)
                appendLog("=== ERROR ===")
                appendLog("error: $error")
            }
        }
    }

    private fun appendLog(message: String) {
        Log.d(TAG, message)
        txtLog.append(message + "\n")
    }
}
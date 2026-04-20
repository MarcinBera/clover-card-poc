package com.example.clovercardpoc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.clover.sdk.v1.Intents
import com.clover.sdk.v3.tokens.Card
import com.clover.sdk.v3.payments.api.TokenizeCardRequestIntentBuilder

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
            appendLog("Start czystej tokenizacji karty...")
            try {
                startTokenize()
            } catch (e: Exception) {
                appendLog("EXCEPTION: ${e.javaClass.simpleName}: ${e.message}")
                Log.e(TAG, "startTokenize failed", e)
            }
        }

        btnClear.setOnClickListener {
            txtLog.text = ""
            appendLog("Log wyczyszczony")
        }

        appendLog("App started")
        appendLog("Tryb: tokenizacja karty bez payment flow")
    }

    private fun startTokenize() {
        val intent = TokenizeCardRequestIntentBuilder().build(this)
        startActivityForResult(intent, TOKENIZE_REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode != TOKENIZE_REQUEST_CODE) return

        if (resultCode == RESULT_OK && data != null) {
            val token = data.getStringExtra(Intents.EXTRA_TOKEN)
            val tokenType = data.getStringExtra(Intents.EXTRA_TOKEN_TYPE)
            val card = data.getParcelableExtra<Card>(Intents.EXTRA_CARD)

            appendLog("=== SUCCESS ===")
            appendLog("resultCode: $resultCode")
            appendLog("token: $token")
            appendLog("tokenType: $tokenType")
            appendLog("card: $card")
        } else {
            val error = data?.getStringExtra(Intents.EXTRA_FAILURE_MESSAGE)

            appendLog("=== ERROR ===")
            appendLog("resultCode: $resultCode")
            appendLog("data is null: ${data == null}")
            appendLog("error: $error")
        }
    }

    private fun appendLog(message: String) {
        Log.d(TAG, message)
        txtLog.append(message + "\n")
    }
}
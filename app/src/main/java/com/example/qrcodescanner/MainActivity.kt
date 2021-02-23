package com.example.qrcodescanner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_scan.setOnClickListener {




            val scanner = IntentIntegrator(this)

            //scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            scanner.setBeepEnabled(true)
            scanner.initiateScan()


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK) {
            val barcodeData =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

            if (barcodeData != null) {

                if (barcodeData.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Scanned: " + barcodeData.contents, Toast.LENGTH_LONG)
                        .show()

                    //show product details


                    //or save new data to database
                    val context = applicationContext
                    val filename = "barcodeDatabase"
                    val file = File(context.filesDir, filename)


                    if (file.exists()) {

                        val data = file.readText()

                        if (data == barcodeData.contents)
                        {
                            Toast.makeText(this, "Data exists. " + barcodeData.contents, Toast.LENGTH_LONG)
                                .show()
                        }
                        else
                        {
                            file.writeText(barcodeData.contents)
                            Toast.makeText(this, "Data saved. ", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                    else
                    {
                        file.createNewFile()
                        Toast.makeText(this, "New File Created. ", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }


        }


    }
}
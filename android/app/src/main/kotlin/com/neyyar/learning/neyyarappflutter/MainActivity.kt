package com.neyyar.learning

import android.os.Bundle
import android.widget.Toast
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    private val CHANNEL = "com.neyyar.learning/toast"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call: MethodCall, result: MethodChannel.Result ->
            if (call.method == "showToast") {
                val message = call.argument<String>("message")
                if (message != null) {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    result.success("Toast displayed successfully")
                } else {
                    result.error("ERROR", "Message argument is null", null)
                }
            } else {
                result.notImplemented()
            }
        }
    }
}


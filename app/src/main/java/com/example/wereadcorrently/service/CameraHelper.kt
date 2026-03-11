package com.example.wereadcorrently.service

import android.util.Log
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.ExecutionException

class CameraHelper {
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null

    fun startPreview(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner,
        cameraLens: Int = CameraSelector.LENS_FACING_FRONT
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(previewView.context)
        cameraProviderFuture.addListener({
            try {
                cameraProvider = cameraProviderFuture.get()
                bindPreview(cameraProvider!!, previewView, lifecycleOwner, cameraLens)
            } catch (e: ExecutionException) {
                Log.e("CameraHelper", "Ошибка получения CameraProvider", e)
            } catch (e: InterruptedException) {
                Log.e("CameraHelper", "Прерывание", e)
            }
        }, ContextCompat.getMainExecutor(previewView.context))
    }

    private fun bindPreview(
        cameraProvider: ProcessCameraProvider,
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner,
        cameraLens: Int
    ) {
        cameraProvider.unbindAll()

        val preview = Preview.Builder().build()
        preview.setSurfaceProvider(previewView.surfaceProvider)

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(cameraLens)
            .build()

        try {
            camera = cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview)
            Log.d("CameraHelper", "Камера успешно запущена")
        } catch (e: Exception) {
            Log.e("CameraHelper", "Ошибка привязки камеры", e)
        }
    }

    fun stopCamera() {
        cameraProvider?.unbindAll()
        camera = null
        Log.d("CameraHelper", "Камера остановлена")
    }
}
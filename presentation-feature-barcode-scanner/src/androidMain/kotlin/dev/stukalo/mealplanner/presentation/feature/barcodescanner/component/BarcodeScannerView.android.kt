package dev.stukalo.mealplanner.presentation.feature.barcodescanner.component

import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

@OptIn(ExperimentalGetImage::class)
@Composable
actual fun BarcodeScannerView(modifier: Modifier, onBarcodeScanned: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            val executor = ContextCompat.getMainExecutor(ctx)
            cameraProviderFuture.addListener(
                {
                    val cameraProvider = cameraProviderFuture.get()
                    val preview =
                        Preview.Builder().build().also {
                            it.surfaceProvider = previewView.surfaceProvider
                        }

                    val scanner = BarcodeScanning.getClient()

                    val imageAnalysis =
                        ImageAnalysis
                            .Builder()
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .build()

                    imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor()) { imageProxy ->
                        val mediaImage = imageProxy.image
                        if (mediaImage != null) {
                            val rotation = imageProxy.imageInfo.rotationDegrees
                            val image = InputImage.fromMediaImage(mediaImage, rotation)

                            scanner
                                .process(image)
                                .addOnSuccessListener { barcodes ->
                                    for (barcode in barcodes) {
                                        val boundingBox = barcode.boundingBox ?: continue

                                        // The analyzer receives frames that are typically in landscape (e.g. 640x480).
                                        // rotationDegrees (e.g. 90) tells us how to orient them to portrait.

                                        val imgWidth = imageProxy.width.toFloat()
                                        val imgHeight = imageProxy.height.toFloat()

                                        // Map ML Kit coordinates to UI coordinates [0, 1]
                                        // imgWidth/imgHeight are dimensions of the buffer.
                                        // ML Kit bounding box is in buffer coordinates.

                                        val (normCenterX, normCenterY) = when (rotation) {
                                            90 -> Pair(
                                                1f - (boundingBox.centerY().toFloat() / imgHeight),
                                                boundingBox.centerX().toFloat() / imgWidth
                                            )
                                            270 -> Pair(
                                                boundingBox.centerY().toFloat() / imgHeight,
                                                1f - (boundingBox.centerX().toFloat() / imgWidth)
                                            )
                                            180 -> Pair(
                                                1f - (boundingBox.centerX().toFloat() / imgWidth),
                                                1f - (boundingBox.centerY().toFloat() / imgHeight)
                                            )
                                            else -> Pair(
                                                boundingBox.centerX().toFloat() / imgWidth,
                                                boundingBox.centerY().toFloat() / imgHeight
                                            )
                                        }

                                        // Horizontal range: [0.15, 0.85] for 0.7 fraction
                                        val hStart = (1f - WINDOW_WIDTH_FRACTION) / 2f
                                        val hEnd = 1f - hStart

                                        // Vertical range: we want it centered.
                                        // The UI height is fraction of width, so we need to account for screen aspect ratio.
                                        // Since we don't have screen aspect ratio easily here,
                                        // we'll check if it's in the vertical center [0.3, 0.7] area.
                                        val vStart = 0.3f
                                        val vEnd = 0.7f

                                        if (normCenterX in hStart..hEnd &&
                                            normCenterY in vStart..vEnd
                                        ) {
                                            barcode.rawValue?.let { onBarcodeScanned(it) }
                                        }
                                    }
                                }.addOnCompleteListener {
                                    imageProxy.close()
                                }
                        } else {
                            imageProxy.close()
                        }
                    }

                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                executor
            )
            previewView
        },
        modifier = modifier
    )
}

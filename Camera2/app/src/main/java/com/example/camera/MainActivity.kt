package com.example.camera

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.Surface
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.camera.ui.theme.CameraTheme
import kotlinx.coroutines.suspendCancellableCoroutine
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CameraTheme {

                // Permission handling
                var hasCameraPermission by remember { mutableStateOf(false) }
                val launcher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { granted ->
                    hasCameraPermission = granted
                }

                LaunchedEffect(Unit) {
                    launcher.launch(Manifest.permission.CAMERA)
                }

                if (!hasCameraPermission) {
                    Text("Camera permission is required")
                    return@CameraTheme
                }

                // State holders
                var previewView by remember { mutableStateOf<PreviewView?>(null) }
                var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }

                Box(modifier = Modifier.fillMaxSize()) {

                    // Camera Preview
                    CameraPreview(
                        modifier = Modifier.fillMaxSize(),
                        onPreviewReady = { view ->
                            previewView = view
                        }
                    )

                    val context = LocalContext.current
                    val lifecycleOwner = LocalLifecycleOwner.current

                    // Bind CameraX once previewView is ready
                    LaunchedEffect(previewView) {
                        previewView?.let { view ->

                            // Create provider
                            val provider = ProcessCameraProvider
                                .getInstance(context)
                                .get()

                            // Create preview
                            val preview = Preview.Builder().build().apply {
                                setSurfaceProvider(view.surfaceProvider)
                            }

                            // Selector
                            val selector = CameraSelector.DEFAULT_BACK_CAMERA

                            // Bind ImageCapture here
                            imageCapture = bindWithImageCapture(
                                context = context,
                                provider = provider,
                                owner = lifecycleOwner,
                                preview = preview,
                                selector = selector,
                                previewView = view
                            )
                        }
                    }

                    // Capture button
                    Button(
                        onClick = {
                            imageCapture?.let { ic ->
                                takePhoto(context, ic) { uri ->
                                    Toast.makeText(
                                        context,
                                        "Saved: $uri",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Log.d("Camera", "Saved to: ${uri}")
                                }
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 32.dp)
                    ) {
                        Text("Capture")
                    }
                }
            }
        }
    }
}


@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    onPreviewReady: (PreviewView) -> Unit
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            PreviewView(context).apply {
                scaleType = PreviewView.ScaleType.FILL_CENTER
                post { onPreviewReady(this) }
            }
        }
    )
}

suspend fun bindPreview(
    context: Context,
    owner: LifecycleOwner,
    view: PreviewView
): Pair<Preview, Camera> {

    val provider = suspendCancellableCoroutine<ProcessCameraProvider> { cont ->
        val future = ProcessCameraProvider.getInstance(context)
        future.addListener(
            { cont.resume(future.get()) {} },
            ContextCompat.getMainExecutor(context)
        )
    }

    val preview = Preview.Builder().build().also {
        it.setSurfaceProvider(view.surfaceProvider)
    }

    val selector = CameraSelector.DEFAULT_BACK_CAMERA

    provider.unbindAll()

    val camera = provider.bindToLifecycle(owner, selector, preview)

    return preview to camera
}

fun bindWithImageCapture(
    context: Context,
    provider: ProcessCameraProvider,
    owner: LifecycleOwner,
    preview: Preview,
    selector: CameraSelector,
    previewView: PreviewView
): ImageCapture {
    val ic =
        ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY).build()

    val rotation = previewView.display?.rotation ?: Surface.ROTATION_0
    ic.targetRotation = rotation

    provider.unbindAll()
    provider.bindToLifecycle(owner, selector, preview, ic)
    return ic
}

fun outputOptions(ctx: Context, name: String):
        ImageCapture.OutputFileOptions {
    val v = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH,
            "Pictures/KameraKu")
    }
    val r = ctx.contentResolver; val uri =
        r.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, v)!!

    return ImageCapture.OutputFileOptions.Builder(r, uri, v).build()
}

fun takePhoto(ctx: Context, ic: ImageCapture, onSaved: (Uri) ->
Unit) {
    val opt = outputOptions(ctx, "IMG_" +
            System.currentTimeMillis())


    ic.takePicture(opt, ContextCompat.getMainExecutor(ctx), object:
        ImageCapture.OnImageSavedCallback {
        override fun onImageSaved(res:
                                  ImageCapture.OutputFileResults) { onSaved(res.savedUri!!) }
        override fun onError(e: ImageCaptureException) { /*
tampilkan error */ }
    })
}




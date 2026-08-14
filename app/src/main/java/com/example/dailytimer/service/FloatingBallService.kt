package com.example.dailytimer.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import com.example.dailytimer.ui.theme.*

class FloatingBallService : Service(), LifecycleOwner, SavedStateRegistryOwner {

    private lateinit var windowManager: WindowManager
    private var floatingView: View? = null
    private val lifecycleRegistry = LifecycleRegistry(this)
    private val savedStateRegistryController = SavedStateRegistryController.create(this)
    override val savedStateRegistry: SavedStateRegistry = savedStateRegistryController.savedStateRegistry
    override val lifecycle: Lifecycle = lifecycleRegistry

    companion object {
        fun start(context: Context) {
            context.startService(Intent(context, FloatingBallService::class.java))
        }
        fun stop(context: Context) {
            context.stopService(Intent(context, FloatingBallService::class.java))
        }
    }

    override fun onCreate() {
        super.onCreate()
        savedStateRegistryController.performAttach()
        savedStateRegistryController.performRestore(null)
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        addFloatingView()
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    override fun onDestroy() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        removeFloatingView()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun addFloatingView() {
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 200
            y = 400
        }

        val composeView = ComposeView(this).apply {
            setContent { FloatingBallContent() }
            setViewTreeLifecycleOwner(this@FloatingBallService)
            setViewTreeSavedStateRegistryOwner(this@FloatingBallService)
        }

        val wrapper = FrameLayout(this).apply {
            addView(composeView)
            setOnTouchListener(FloatingTouchListener(params, windowManager))
        }

        floatingView = wrapper
        windowManager.addView(wrapper, params)
    }

    private fun removeFloatingView() {
        floatingView?.let { windowManager.removeView(it) }
        floatingView = null
    }

    private class FloatingTouchListener(
        private val params: WindowManager.LayoutParams,
        private val windowManager: WindowManager
    ) : View.OnTouchListener {
        private var initialX = 0
        private var initialY = 0
        private var touchX = 0f
        private var touchY = 0f

        override fun onTouch(v: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = params.x
                    initialY = params.y
                    touchX = event.rawX
                    touchY = event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    params.x = initialX + (event.rawX - touchX).toInt()
                    params.y = initialY + (event.rawY - touchY).toInt()
                    windowManager.updateViewLayout(v, params)
                }
            }
            return false
        }
    }
}

@Composable
fun FloatingBallContent() {
    Box(modifier = Modifier.size(120.dp), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .shadow(24.dp, CircleShape, spotColor = Red.copy(alpha = 0.4f))
                .background(Red, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "午睡计时",
                    style = Typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, fontSize = 11.sp),
                    color = TextWhite
                )
                Text(
                    text = "02:35",
                    style = Typography.labelMedium.copy(fontWeight = FontWeight.Bold, fontSize = 24.sp),
                    color = TextWhite
                )
            }
        }
        // Green badge
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(24.dp)
                .shadow(4.dp, CircleShape, spotColor = Color.Black.copy(alpha = 0.1f))
                .background(Green, CircleShape)
        ) {
            Box(modifier = Modifier.size(6.dp).background(TextWhite, CircleShape).align(Alignment.Center))
        }
    }
}

package com.pmirkelam.cointracker

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.pmirkelam.cointracker.activities.MainActivity
import com.pmirkelam.cointracker.coindetail.data.CoinDetailRepository
import com.pmirkelam.cointracker.favorites.data.FavoritesRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.lang.Runnable
import javax.inject.Inject

@AndroidEntryPoint
class RefreshService : Service() {

    @Inject
    lateinit var favoritesRepository: FavoritesRepository

    @Inject
    lateinit var coinDetailRepository: CoinDetailRepository

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    private var _handler: Handler? = null
    private val _runnable: Runnable = object : Runnable {
        override fun run() {
            refresh()
            _handler?.postDelayed(this, BACKGROUND_REFRESH_INTERVAL)
        }
    }

    companion object{
        const val NOTIFICATION_TEXT = "Coin tracking service is running"
        const val CONTEXT_TEXT = "Refreshing favorite coin prices"
        const val BACKGROUND_REFRESH_INTERVAL = 6000L
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        setTask()
        startMeForeground()
        return START_STICKY
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    fun refresh() {
        scope.launch {
            val coins = favoritesRepository.getFavorites()
            if (coins.isNotEmpty()) {
                coins.forEach {
                    Log.e(RefreshService::class.simpleName, "Refreshing coin: ${it.name}")
                    refreshCoinDetails(it.id)
                }
            }
        }
    }

    private suspend fun refreshCoinDetails(id: String) {
        coinDetailRepository.refreshCoinDetail(id)
    }

    private fun setTask() {
        _handler?.removeCallbacks(_runnable)
        _handler = Handler(Looper.getMainLooper())
        _handler?.postDelayed(_runnable, BACKGROUND_REFRESH_INTERVAL)
    }


    private fun startMeForeground() {
        val channelID = applicationContext.packageName
        val foregroundText = NOTIFICATION_TEXT
        val contentText = CONTEXT_TEXT

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chan = NotificationChannel(
                channelID,
                foregroundText,
                NotificationManager.IMPORTANCE_LOW
            )
            chan.lightColor = Color.BLUE
            chan.lockscreenVisibility = Notification.VISIBILITY_SECRET
            val manager = (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            manager.createNotificationChannel(chan)
            val notificationBuilder = NotificationCompat.Builder(this, channelID)
            val notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(contentText)
                .setPriority(NotificationManager.IMPORTANCE_LOW)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setChannelId(channelID)
                .build()
            startForeground(1, notification)

        } else {
            val notificationIntent = Intent(this, MainActivity::class.java)
            notificationIntent.action = NOTIFICATION_TEXT
            notificationIntent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK
                    or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            val pendingIntent = PendingIntent.getActivity(
                this, 0, notificationIntent, 0
            )

            val icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)

            val notification = NotificationCompat.Builder(this, channelID)
                .setContentTitle(getString(R.string.app_name))
                .setTicker(foregroundText)
                .setContentText(contentText)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build()

            startForeground(101, notification)
        }

    }
}
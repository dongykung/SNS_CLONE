package com.dkproject.data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SHORT_SERVICE
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.dkproject.data.R
import com.dkproject.data.model.BoardParam
import com.dkproject.data.model.ContentParam
import com.dkproject.data.model.Parcel.BoardParcel
import com.dkproject.data.retrofit.BoardService
import com.dkproject.domain.model.ACTION_POSTED
import com.dkproject.domain.usecase.file.UploadImageUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BoardPostingService() : LifecycleService() {
    companion object {
        const val EXTRA_BOARD = "extra_board"
        const val CHANNEL_ID = "post"
        const val CHANNEL_NAME = "게시글 업로드 채널"
        const val FOREGROUND_NOTIFICATION_ID = 1000
    }

    @Inject
    lateinit var uploadImageUseCase: UploadImageUseCase

    @Inject
    lateinit var boardService: BoardService

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        startForeground()
        intent?.run {
            if (hasExtra(EXTRA_BOARD)) {
                val boardParcel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    getParcelableExtra(EXTRA_BOARD, BoardParcel::class.java)
                } else {
                    getParcelableExtra(EXTRA_BOARD)
                }
                boardParcel?.run {
                    lifecycleScope.launch {
                        postBoard(this@run)
                    }
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "게시글 업로드 중..."
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun startForeground() {

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(androidx.constraintlayout.widget.R.drawable.notification_bg)
            .setContentTitle("게시글 업로드 중...")
            .setContentText("게시글이 업로드 중입니다.")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("test this is test line"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                FOREGROUND_NOTIFICATION_ID,
                notificationBuilder,
                FOREGROUND_SERVICE_TYPE_SHORT_SERVICE
            )
        }else{
            startForeground(
                FOREGROUND_NOTIFICATION_ID,
                notificationBuilder
            )
        }
    }


    private suspend fun postBoard(boardParcel: BoardParcel) {

        val uploadImages = boardParcel.images.mapNotNull {image->
            uploadImageUseCase(image.toImage()).getOrNull()
        }
        val contentParam = ContentParam(text=boardParcel.content,images = uploadImages)
        val boardParam = BoardParam(title=boardParcel.title,content = contentParam.toJson())
        val requestBody = boardParam.toRequestBody()
        boardService.postBoard(requestBody = requestBody)

        sendBroadcast(
            Intent(
                ACTION_POSTED
            ).apply {
                setPackage(packageName)
            }
        )
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(androidx.constraintlayout.widget.R.drawable.notification_bg)
            .setContentTitle("게시글 업로드 완료")
            .setContentText("게시글이 업로드 되었습니다.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(FOREGROUND_NOTIFICATION_ID,notificationBuilder)

        stopForeground(STOP_FOREGROUND_DETACH)
    }

}
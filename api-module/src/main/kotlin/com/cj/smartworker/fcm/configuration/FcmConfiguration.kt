package com.cj.smartworker.fcm.configuration

import com.cj.smartworker.domain.util.logger
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.io.FileNotFoundException
import java.io.IOException
import javax.annotation.PostConstruct

@Configuration
class FcmConfiguration(
    @Value("\${fcm.project-id}")
    private val projectId: String,
) {
    private val logger = logger()
    companion object {
        private const val FILE_BASE_CONFIG_PATH = "firebase/joljack-863ea-firebase-adminsdk-itotf-02cc47993a.json"
        private val SYSTEM_RESOURCE_AS_STREAM = this::class.java.classLoader.getResourceAsStream(FILE_BASE_CONFIG_PATH)
//        private val SYSTEM_RESOURCE_AS_STREAM = ClassLoader.getSystemResourceAsStream(FILE_BASE_CONFIG_PATH)
        private var GOOGLE_CREDENTIALS = GoogleCredentials.fromStream(SYSTEM_RESOURCE_AS_STREAM)
            .createScoped(listOf("https://www.googleapis.com/auth/cloud-platform"))
    }


    @PostConstruct
    fun initialize() {
        try {
            FirebaseOptions.builder()
                .setCredentials(GOOGLE_CREDENTIALS)
                .setProjectId(projectId).build().let {
                    FirebaseApp.initializeApp(it)
                }
        } catch (e: FileNotFoundException) {
            logger.error("Firebase Admin SDK 설정 파일을 찾을 수 없습니다.")
            throw RuntimeException("Firebase Admin SDK 설정 파일을 찾을 수 없습니다.")
        } catch (e: IOException) {
            logger.error("Firebase Admin SDK 설정 파일을 읽을 수 없습니다.")
            throw RuntimeException("Firebase Admin SDK 설정 파일을 읽을 수 없습니다.")
        }
    }
}

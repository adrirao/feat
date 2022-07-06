package com.unlam.feat.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.unlam.feat.util.Constants
import com.unlam.feat.provider.FeatProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ProviderModule {
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl() = Constants.FEAT_URL_BASE.toHttpUrl()

    @Singleton
    @Provides
    fun provideRetrofit(@Named("BaseUrl") baseUrl: HttpUrl): Retrofit {

        val nullOnEmptyConverterFactory = object : Converter.Factory() {
            fun converterFactory() = this
            override fun responseBodyConverter(
                type: Type,
                annotations: Array<out Annotation>,
                retrofit: Retrofit
            ) = Converter<ResponseBody, Any?> {
                if(it.contentLength() != 0L) retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations).convert(it) else null
            }
        }

        return Retrofit.Builder()
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()


    }



    @Provides
    @Singleton
    fun providerFeatProvider(retrofit: Retrofit): FeatProvider =
        retrofit.create(FeatProvider::class.java)

    @Provides
    @Singleton
    fun provideFirebaseAuthInstance() = Firebase.auth

    @Provides
    @Singleton
    fun provideFirebaseMessagingInstance() = FirebaseMessaging.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStorageInstance() = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestoreInstance() = Firebase.firestore
}


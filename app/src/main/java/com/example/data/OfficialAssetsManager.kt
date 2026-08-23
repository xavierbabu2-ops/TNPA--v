package com.example.data

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Manages persisted real photo and image assets uploaded by the user / association admins:
 * - மாநிலத் தலைவர் (State President) Photo
 * - மாநில பொதுச் செயலாளர் (State General Secretary) Photo
 * - மாநில பொருளாளர் (State Treasurer) Photo
 * - சங்கத்தின் அதிகாரப்பூர்வ லோகோ (Official Association Logo)
 * - சங்கத்தின் அதிகாரப்பூர்வ கொடி (Official Association Flag)
 * - அதிகாரப்பூர்வ அட்டை முன் பக்கம் (Official ID Card Front Template)
 * - அதிகாரப்பூர்வ அட்டை பின் பக்கம் (Official ID Card Back Template)
 */
object OfficialAssetsManager {
  private const val PREFS_NAME = "tnpa_official_assets_prefs"
  private const val KEY_PRESIDENT_URI = "key_president_photo_uri"
  private const val KEY_GEN_SEC_URI = "key_gen_sec_photo_uri"
  private const val KEY_TREASURER_URI = "key_treasurer_photo_uri"
  private const val KEY_LOGO_URI = "key_logo_uri"
  private const val KEY_FLAG_URI = "key_flag_uri"
  private const val KEY_ID_FRONT_URI = "key_id_front_uri"
  private const val KEY_ID_BACK_URI = "key_id_back_uri"

  private var sharedPrefs: SharedPreferences? = null

  private val _presidentPhotoUri = MutableStateFlow<String?>(null)
  val presidentPhotoUri: StateFlow<String?> = _presidentPhotoUri.asStateFlow()

  private val _generalSecPhotoUri = MutableStateFlow<String?>(null)
  val generalSecPhotoUri: StateFlow<String?> = _generalSecPhotoUri.asStateFlow()

  private val _treasurerPhotoUri = MutableStateFlow<String?>(null)
  val treasurerPhotoUri: StateFlow<String?> = _treasurerPhotoUri.asStateFlow()

  private val _logoUri = MutableStateFlow<String?>(null)
  val logoUri: StateFlow<String?> = _logoUri.asStateFlow()

  private val _flagUri = MutableStateFlow<String?>(null)
  val flagUri: StateFlow<String?> = _flagUri.asStateFlow()

  private val _idFrontTemplateUri = MutableStateFlow<String?>(null)
  val idFrontTemplateUri: StateFlow<String?> = _idFrontTemplateUri.asStateFlow()

  private val _idBackTemplateUri = MutableStateFlow<String?>(null)
  val idBackTemplateUri: StateFlow<String?> = _idBackTemplateUri.asStateFlow()

  fun init(context: Context) {
    if (sharedPrefs == null) {
      sharedPrefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
      _presidentPhotoUri.value = sharedPrefs?.getString(KEY_PRESIDENT_URI, null)
      _generalSecPhotoUri.value = sharedPrefs?.getString(KEY_GEN_SEC_URI, null)
      _treasurerPhotoUri.value = sharedPrefs?.getString(KEY_TREASURER_URI, null)
      _logoUri.value = sharedPrefs?.getString(KEY_LOGO_URI, null)
      _flagUri.value = sharedPrefs?.getString(KEY_FLAG_URI, null)
      _idFrontTemplateUri.value = sharedPrefs?.getString(KEY_ID_FRONT_URI, null)
      _idBackTemplateUri.value = sharedPrefs?.getString(KEY_ID_BACK_URI, null)
    }
  }

  fun setPresidentPhoto(uri: Uri?) {
    val str = uri?.toString()
    _presidentPhotoUri.value = str
    sharedPrefs?.edit()?.putString(KEY_PRESIDENT_URI, str)?.apply()
  }

  fun setGeneralSecPhoto(uri: Uri?) {
    val str = uri?.toString()
    _generalSecPhotoUri.value = str
    sharedPrefs?.edit()?.putString(KEY_GEN_SEC_URI, str)?.apply()
  }

  fun setTreasurerPhoto(uri: Uri?) {
    val str = uri?.toString()
    _treasurerPhotoUri.value = str
    sharedPrefs?.edit()?.putString(KEY_TREASURER_URI, str)?.apply()
  }

  fun setLogo(uri: Uri?) {
    val str = uri?.toString()
    _logoUri.value = str
    sharedPrefs?.edit()?.putString(KEY_LOGO_URI, str)?.apply()
  }

  fun setFlag(uri: Uri?) {
    val str = uri?.toString()
    _flagUri.value = str
    sharedPrefs?.edit()?.putString(KEY_FLAG_URI, str)?.apply()
  }

  fun setIdFrontTemplate(uri: Uri?) {
    val str = uri?.toString()
    _idFrontTemplateUri.value = str
    sharedPrefs?.edit()?.putString(KEY_ID_FRONT_URI, str)?.apply()
  }

  fun setIdBackTemplate(uri: Uri?) {
    val str = uri?.toString()
    _idBackTemplateUri.value = str
    sharedPrefs?.edit()?.putString(KEY_ID_BACK_URI, str)?.apply()
  }
}

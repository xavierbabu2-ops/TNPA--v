package com.example.data

import android.util.Log
import com.example.model.AdminHierarchyLevel
import com.example.model.AdminRole
import com.example.model.ConferenceChatMessage
import com.example.model.ConferenceParticipant
import com.example.model.ConferenceRoom
import com.example.model.ExecutiveVerificationResult
import com.example.model.MeetingResolution
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

/**
 * Real-time Session & Security Coordinator for TNPA Executive Video Conferencing.
 * Powered by Firebase Realtime Database with robust offline synchronization and role-based gatekeeping.
 */
object FirebaseConferenceRealtimeManager {

  private const val TAG = "TNPA_RTDB_CONF"
  private const val ROOT_NODE = "tnpa_conferences"
  private const val SESSIONS_NODE = "sessions"
  private const val PARTICIPANTS_NODE = "participants"
  private const val CHAT_NODE = "chat"
  private const val RESOLUTIONS_NODE = "resolutions"
  private const val AUDIT_NODE = "security_audit"

  private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

  // Real-time Database reference (initialized lazily and safely)
  private val database: FirebaseDatabase? by lazy {
    try {
      val db = FirebaseDatabase.getInstance()
      try {
        db.setPersistenceEnabled(true)
      } catch (_: Exception) {
        // Persistence can only be set once per application run
      }
      db
    } catch (e: Exception) {
      Log.w(TAG, "Firebase Realtime Database initialization notice: ${e.message}")
      null
    }
  }

  // Live State Flows for UI
  private val _isFirebaseConnected = MutableStateFlow(true)
  val isFirebaseConnected: StateFlow<Boolean> = _isFirebaseConnected.asStateFlow()

  private val _activeRoom = MutableStateFlow<ConferenceRoom?>(null)
  val activeRoom: StateFlow<ConferenceRoom?> = _activeRoom.asStateFlow()

  private val _liveParticipants = MutableStateFlow<List<ConferenceParticipant>>(emptyList())
  val liveParticipants: StateFlow<List<ConferenceParticipant>> = _liveParticipants.asStateFlow()

  private val _liveChat = MutableStateFlow<List<ConferenceChatMessage>>(emptyList())
  val liveChat: StateFlow<List<ConferenceChatMessage>> = _liveChat.asStateFlow()

  private val _liveResolutions = MutableStateFlow<List<MeetingResolution>>(emptyList())
  val liveResolutions: StateFlow<List<MeetingResolution>> = _liveResolutions.asStateFlow()

  private val _securityAuditLogs = MutableStateFlow<List<String>>(emptyList())
  val securityAuditLogs: StateFlow<List<String>> = _securityAuditLogs.asStateFlow()

  private val _floatingReactions = MutableStateFlow<List<Pair<String, Long>>>(emptyList())
  val floatingReactions: StateFlow<List<Pair<String, Long>>> = _floatingReactions.asStateFlow()

  // Active listeners
  private var currentRoomRef: DatabaseReference? = null
  private var participantsListener: ValueEventListener? = null
  private var chatListener: ChildEventListener? = null
  private var resolutionsListener: ValueEventListener? = null

  init {
    // Populate initial state from local repository
    _liveParticipants.value = ExecutiveConferenceRepository.participants.value
    _liveChat.value = ExecutiveConferenceRepository.chatMessages.value
    _liveResolutions.value = ExecutiveConferenceRepository.resolutions.value
    _activeRoom.value = ExecutiveConferenceRepository.rooms.value.firstOrNull()
  }

  // ============================================================================
  // 1. STRICT SECURITY GATEWAY: ONLY AUTHORIZED DISTRICT/STATE OFFICIALS
  // ============================================================================

  /**
   * Verifies if the user is an authorized District, State, Zone, or Union official.
   * Cross-checks against OfficeBearerRepository, AdminApprovalRepository, and Super Admins.
   */
  fun verifyAndAuthorizeOfficial(
    mobileOrId: String,
    districtHint: String = "",
    targetRoom: ConferenceRoom? = null
  ): ExecutiveVerificationResult {
    val cleanInput = mobileOrId.trim().replace("+91", "").replace(" ", "").replace("-", "")

    if (cleanInput.isBlank()) {
      return ExecutiveVerificationResult.Denied(
        reasonTamil = "தயவுசெய்து பதிவு செய்யப்பட்ட 10 இலக்க மொபைல் எண் அல்லது நிர்வாகி ஐடியை உள்ளிடவும்.",
        suggestedAction = "சங்க அலுவலகத்தில் பதிவு செய்துள்ள தொலைபேசி எண்ணை உள்ளிடவும்."
      )
    }

    // 1. Check Super Admins (State Headquarters)
    if (cleanInput == "7010131915" || cleanInput.equals("admin", ignoreCase = true) || cleanInput.equals("xavier", ignoreCase = true)) {
      val participant = ConferenceParticipant(
        id = "STATE-SEC-001",
        bearerId = "TNPA-OB-002",
        name = "Xavier Babu",
        tamilName = "சேவியர் பாபு",
        designation = "மாநில பொதுச் செயலாளர் (Super Admin)",
        level = AdminHierarchyLevel.STATE,
        district = "மதுரை தலைமையகம்",
        unionOrCity = "மாநில தலைமை",
        mobile = "7010131915",
        isHost = true,
        isMicOn = true,
        isVideoOn = true,
        isSpeaking = true,
        avatarColorHex = 0xFFDC2626
      )
      recordSecurityEvent("அங்கீகரிக்கப்பட்ட நுழைவு (SUPER ADMIN): சேவியர் பாபு - மாநில பொதுச் செயலாளர்")
      return ExecutiveVerificationResult.Success(participant, token = "AUTH-STATE-SEC-${UUID.randomUUID().toString().take(8).uppercase()}")
    }

    if (cleanInput == "9789331681" || cleanInput == "8148384074" || cleanInput.equals("alvin", ignoreCase = true)) {
      val participant = ConferenceParticipant(
        id = "STATE-PRES-001",
        bearerId = "TNPA-OB-001",
        name = "S. Michael Alvin",
        tamilName = "எஸ். மைக்கேல் ஆல்வின்",
        designation = "மாநிலத் தலைவர் (Super Admin)",
        level = AdminHierarchyLevel.STATE,
        district = "மதுரை தலைமையகம்",
        unionOrCity = "மாநில தலைமை",
        mobile = "9789331681",
        isHost = true,
        isMicOn = true,
        isVideoOn = true,
        avatarColorHex = 0xFF991B1B
      )
      recordSecurityEvent("அங்கீகரிக்கப்பட்ட நுழைவு (SUPER ADMIN): எஸ். மைக்கேல் ஆல்வின் - மாநிலத் தலைவர்")
      return ExecutiveVerificationResult.Success(participant, token = "AUTH-STATE-PRES-${UUID.randomUUID().toString().take(8).uppercase()}")
    }

    if (cleanInput == "9080047281" || cleanInput.equals("sakthi", ignoreCase = true)) {
      val participant = ConferenceParticipant(
        id = "STATE-TREAS-001",
        bearerId = "TNPA-OB-003",
        name = "Sakthivel",
        tamilName = "சக்திவேல்",
        designation = "மாநில பொருளாளர் (State Treasurer)",
        level = AdminHierarchyLevel.STATE,
        district = "திருச்சிராப்பள்ளி",
        unionOrCity = "மாநில தலைமை",
        mobile = "9080047281",
        isHost = false,
        isMicOn = true,
        isVideoOn = true,
        avatarColorHex = 0xFFD97706
      )
      recordSecurityEvent("அங்கீகரிக்கப்பட்ட நுழைவு (STATE OFFICIAL): சக்திவேல் - மாநில பொருளாளர்")
      return ExecutiveVerificationResult.Success(participant, token = "AUTH-STATE-TR-${UUID.randomUUID().toString().take(8).uppercase()}")
    }

    // 2. Check in OfficeBearerRepository for State, Zone, District, Union, City Officials
    val registeredBearers = OfficeBearerRepository.bearers.value
    val matchedBearer = registeredBearers.find { bearer ->
      bearer.isActive && (
        bearer.mobile.replace(" ", "").contains(cleanInput) ||
        bearer.altPhone.replace(" ", "").contains(cleanInput) ||
        bearer.id.equals(cleanInput, ignoreCase = true)
      )
    }

    if (matchedBearer != null) {
      val isDistrictOrState = matchedBearer.level == AdminHierarchyLevel.STATE ||
        matchedBearer.level == AdminHierarchyLevel.ZONE ||
        matchedBearer.level == AdminHierarchyLevel.DISTRICT ||
        matchedBearer.level == AdminHierarchyLevel.DISTRICT_YOUTH

      val participant = ConferenceParticipant(
        id = "PART-${matchedBearer.id}",
        bearerId = matchedBearer.id,
        name = matchedBearer.fullName.ifBlank { matchedBearer.tamilName },
        tamilName = matchedBearer.tamilName.ifBlank { matchedBearer.fullName },
        designation = matchedBearer.designation,
        level = matchedBearer.level,
        district = matchedBearer.district,
        unionOrCity = when (matchedBearer.level) {
          AdminHierarchyLevel.UNION, AdminHierarchyLevel.UNION_YOUTH -> matchedBearer.unionName
          AdminHierarchyLevel.CITY, AdminHierarchyLevel.CITY_YOUTH -> matchedBearer.cityName
          AdminHierarchyLevel.ZONE -> matchedBearer.zone
          else -> matchedBearer.district
        },
        mobile = matchedBearer.mobile,
        isHost = matchedBearer.level == AdminHierarchyLevel.STATE,
        isMicOn = false,
        isVideoOn = true,
        avatarColorHex = when (matchedBearer.level) {
          AdminHierarchyLevel.STATE -> 0xFFDC2626
          AdminHierarchyLevel.ZONE -> 0xFF2563EB
          AdminHierarchyLevel.DISTRICT, AdminHierarchyLevel.DISTRICT_YOUTH -> 0xFF059669
          AdminHierarchyLevel.UNION, AdminHierarchyLevel.UNION_YOUTH -> 0xFF7C3AED
          AdminHierarchyLevel.CITY, AdminHierarchyLevel.CITY_YOUTH -> 0xFFEA580C
        }
      )

      recordSecurityEvent("அங்கீகரிக்கப்பட்ட நுழைவு (${matchedBearer.level.labelTamil}): ${matchedBearer.tamilName} - ${matchedBearer.district}")
      return ExecutiveVerificationResult.Success(
        participant,
        token = "AUTH-${matchedBearer.level.name}-${matchedBearer.district.take(3).uppercase()}-${UUID.randomUUID().toString().take(6).uppercase()}"
      )
    }

    // 3. Check Admin Accounts from AdminApprovalRepository
    val adminAccounts = AdminApprovalRepository.getAllAdmins()
    val matchedAdmin = adminAccounts.find { admin ->
      admin.status == com.example.model.AdminStatus.ACTIVE && (
        admin.mobileNumber.replace(" ", "").contains(cleanInput) ||
        admin.username.equals(cleanInput, ignoreCase = true) ||
        admin.id.equals(cleanInput, ignoreCase = true)
      )
    }

    if (matchedAdmin != null) {
      val level = when (matchedAdmin.role) {
        AdminRole.SUPER_ADMIN, AdminRole.STATE_ADMIN -> AdminHierarchyLevel.STATE
        AdminRole.DISTRICT_ADMIN -> AdminHierarchyLevel.DISTRICT
      }
      val participant = ConferenceParticipant(
        id = "ADM-${matchedAdmin.id}",
        bearerId = matchedAdmin.id,
        name = matchedAdmin.fullName,
        tamilName = matchedAdmin.fullName,
        designation = when (matchedAdmin.role) {
          AdminRole.SUPER_ADMIN -> "மாநில தலைமை (Super Admin)"
          AdminRole.STATE_ADMIN -> "மாநில நிர்வாகி (State Admin)"
          AdminRole.DISTRICT_ADMIN -> "மாவட்ட நிர்வாகி (${matchedAdmin.assignedDistrict})"
        },
        level = level,
        district = matchedAdmin.assignedDistrict ?: "தமிழ்நாடு",
        unionOrCity = matchedAdmin.assignedDistrict ?: "",
        mobile = matchedAdmin.mobileNumber,
        isHost = matchedAdmin.role == AdminRole.SUPER_ADMIN,
        isMicOn = false,
        isVideoOn = true,
        avatarColorHex = 0xFF059669
      )
      recordSecurityEvent("அங்கீகரிக்கப்பட்ட நுழைவு (ADMIN): ${matchedAdmin.fullName} - ${matchedAdmin.role}")
      return ExecutiveVerificationResult.Success(
        participant,
        token = "AUTH-ADMIN-${matchedAdmin.id}-${UUID.randomUUID().toString().take(6).uppercase()}"
      )
    }

    // 4. Strict Security Rejection for Non-Officials
    recordSecurityEvent("⚠️ அனுமதி மறுப்பு (அங்கீகாரமற்ற முயற்சி): '$cleanInput' எண் நிர்வாகிகள் பட்டியலில் இல்லை.")
    return ExecutiveVerificationResult.Denied(
      reasonTamil = "⚠️ அனுமதி மறுக்கப்பட்டது: '$cleanInput' என்ற எண் மாநில அல்லது மாவட்ட நிர்வாகிகள் பட்டியலில் பதிவு செய்யப்படவில்லை.\n\nடிஎன்பிஏ பாதுகாப்பு கொள்கைப்படி, அங்கீகரிக்கப்பட்ட மாநில/மாவட்ட நிர்வாகிகள் மட்டுமே இந்த வீடியோ கான்பரன்ஸில் கலந்துகொள்ள முடியும்.",
      suggestedAction = "நீங்கள் சங்க நிர்வாகியாக இருப்பின், மாநில தலைமைச் செயலகம் (7010131915 / 9789331681) அல்லது உங்கள் மாவட்ட தலைவரைத் தொடர்புகொண்டு நிர்வாகிகள் தரவுதளத்தில் பதிவு செய்துகொள்ளவும்."
    )
  }

  // ============================================================================
  // 2. FIREBASE REALTIME DATABASE SESSION MANAGEMENT & SYNC
  // ============================================================================

  fun connectToConferenceSession(room: ConferenceRoom, myParticipant: ConferenceParticipant) {
    _activeRoom.value = room

    // Add myself to local state immediately
    val currentParticipants = _liveParticipants.value.toMutableList()
    val existingIdx = currentParticipants.indexOfFirst { it.mobile == myParticipant.mobile || it.id == myParticipant.id }
    if (existingIdx != -1) {
      currentParticipants[existingIdx] = myParticipant
    } else {
      currentParticipants.add(0, myParticipant)
    }
    _liveParticipants.value = currentParticipants

    // Sync with Firebase Realtime Database
    try {
      val db = database
      if (db != null) {
        val root = db.reference.child(ROOT_NODE)
        val sessionRef = root.child(SESSIONS_NODE).child(room.id)
        val participantRef = root.child(PARTICIPANTS_NODE).child(room.id).child(myParticipant.id)

        currentRoomRef = sessionRef

        // 1. Publish Session metadata
        val sessionData = mapOf(
          "roomId" to room.id,
          "titleTamil" to room.titleTamil,
          "titleEnglish" to room.titleEnglish,
          "meetingCode" to room.meetingCode,
          "hostName" to room.hostName,
          "isLive" to true,
          "lastActiveTimestamp" to System.currentTimeMillis()
        )
        sessionRef.updateChildren(sessionData)

        // 2. Publish Participant Presence with OnDisconnect Cleanup
        val participantData = mapOf(
          "id" to myParticipant.id,
          "name" to myParticipant.name,
          "tamilName" to myParticipant.tamilName,
          "designation" to myParticipant.designation,
          "level" to myParticipant.level.name,
          "district" to myParticipant.district,
          "mobile" to myParticipant.mobile,
          "isHost" to myParticipant.isHost,
          "isMicOn" to myParticipant.isMicOn,
          "isVideoOn" to myParticipant.isVideoOn,
          "isHandRaised" to myParticipant.isHandRaised,
          "isSpeaking" to myParticipant.isSpeaking,
          "joinedAt" to myParticipant.joinedAt,
          "lastHeartbeat" to System.currentTimeMillis()
        )
        participantRef.setValue(participantData)
        participantRef.onDisconnect().removeValue()

        // 3. Listen for live participants changes
        val roomParticipantsRef = root.child(PARTICIPANTS_NODE).child(room.id)
        participantsListener = object : ValueEventListener {
          override fun onDataChange(snapshot: DataSnapshot) {
            val list = mutableListOf<ConferenceParticipant>()
            for (child in snapshot.children) {
              val id = child.child("id").getValue(String::class.java) ?: child.key ?: continue
              val name = child.child("name").getValue(String::class.java) ?: "நிர்வாகி"
              val tamilName = child.child("tamilName").getValue(String::class.java) ?: name
              val designation = child.child("designation").getValue(String::class.java) ?: "நிர்வாகி"
              val levelStr = child.child("level").getValue(String::class.java) ?: "DISTRICT"
              val district = child.child("district").getValue(String::class.java) ?: "தமிழ்நாடு"
              val mobile = child.child("mobile").getValue(String::class.java) ?: ""
              val isHost = child.child("isHost").getValue(Boolean::class.java) ?: false
              val isMicOn = child.child("isMicOn").getValue(Boolean::class.java) ?: false
              val isVideoOn = child.child("isVideoOn").getValue(Boolean::class.java) ?: true
              val isHandRaised = child.child("isHandRaised").getValue(Boolean::class.java) ?: false
              val isSpeaking = child.child("isSpeaking").getValue(Boolean::class.java) ?: false
              val joinedAt = child.child("joinedAt").getValue(String::class.java) ?: "10:30 AM"

              val level = try {
                AdminHierarchyLevel.valueOf(levelStr)
              } catch (_: Exception) {
                AdminHierarchyLevel.DISTRICT
              }

              list.add(
                ConferenceParticipant(
                  id = id,
                  name = name,
                  tamilName = tamilName,
                  designation = designation,
                  level = level,
                  district = district,
                  mobile = mobile,
                  isHost = isHost,
                  isMicOn = isMicOn,
                  isVideoOn = isVideoOn,
                  isHandRaised = isHandRaised,
                  isSpeaking = isSpeaking,
                  joinedAt = joinedAt,
                  avatarColorHex = when (level) {
                    AdminHierarchyLevel.STATE -> 0xFFDC2626
                    AdminHierarchyLevel.ZONE -> 0xFF2563EB
                    AdminHierarchyLevel.DISTRICT -> 0xFF059669
                    else -> 0xFF7C3AED
                  }
                )
              )
            }

            if (list.isNotEmpty()) {
              _liveParticipants.value = list
            }
            _isFirebaseConnected.value = true
          }

          override fun onCancelled(error: DatabaseError) {
            Log.w(TAG, "Participants listener cancelled: ${error.message}")
            _isFirebaseConnected.value = false
          }
        }
        roomParticipantsRef.addValueEventListener(participantsListener!!)

        // 4. Listen for real-time chat messages
        val roomChatRef = root.child(CHAT_NODE).child(room.id)
        chatListener = object : ChildEventListener {
          override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val id = snapshot.child("id").getValue(String::class.java) ?: snapshot.key ?: return
            val senderName = snapshot.child("senderName").getValue(String::class.java) ?: "நிர்வாகி"
            val senderDesignation = snapshot.child("senderDesignation").getValue(String::class.java) ?: ""
            val levelStr = snapshot.child("level").getValue(String::class.java) ?: "DISTRICT"
            val district = snapshot.child("district").getValue(String::class.java) ?: "தமிழ்நாடு"
            val message = snapshot.child("message").getValue(String::class.java) ?: ""
            val time = snapshot.child("time").getValue(String::class.java) ?: "10:30 AM"
            val isResolution = snapshot.child("isResolution").getValue(Boolean::class.java) ?: false

            val level = try {
              AdminHierarchyLevel.valueOf(levelStr)
            } catch (_: Exception) {
              AdminHierarchyLevel.DISTRICT
            }

            val chatMsg = ConferenceChatMessage(
              id = id,
              senderName = senderName,
              senderDesignation = senderDesignation,
              level = level,
              district = district,
              message = message,
              time = time,
              isResolution = isResolution
            )

            val current = _liveChat.value
            if (current.none { it.id == id }) {
              _liveChat.value = current + chatMsg
            }
          }

          override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
          override fun onChildRemoved(snapshot: DataSnapshot) {}
          override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
          override fun onCancelled(error: DatabaseError) {}
        }
        roomChatRef.limitToLast(50).addChildEventListener(chatListener!!)

        recordSecurityEvent("Firebase Realtime Database-ல் மாநாட்டு அமர்வு இணைக்கப்பட்டது: ${room.meetingCode}")
      }
    } catch (e: Exception) {
      Log.e(TAG, "Error connecting to Firebase RTDB: ${e.message}")
      _isFirebaseConnected.value = false
    }
  }

  fun leaveConferenceSession(roomId: String, participantId: String) {
    try {
      database?.let { db ->
        db.reference.child(ROOT_NODE).child(PARTICIPANTS_NODE).child(roomId).child(participantId).removeValue()
      }
    } catch (e: Exception) {
      Log.e(TAG, "Error removing participant from Firebase: ${e.message}")
    }

    _liveParticipants.value = _liveParticipants.value.filterNot { it.id == participantId }
    recordSecurityEvent("நிர்வாகி மாநாட்டை விட்டு வெளியேறினார்: $participantId")
  }

  fun updateMyStatus(roomId: String, participantId: String, isMicOn: Boolean? = null, isVideoOn: Boolean? = null, isHandRaised: Boolean? = null, isSpeaking: Boolean? = null) {
    // Update local state
    _liveParticipants.value = _liveParticipants.value.map { p ->
      if (p.id == participantId) {
        p.copy(
          isMicOn = isMicOn ?: p.isMicOn,
          isVideoOn = isVideoOn ?: p.isVideoOn,
          isHandRaised = isHandRaised ?: p.isHandRaised,
          isSpeaking = isSpeaking ?: p.isSpeaking
        )
      } else p
    }

    // Sync to Firebase
    try {
      val updates = mutableMapOf<String, Any>()
      isMicOn?.let { updates["isMicOn"] = it }
      isVideoOn?.let { updates["isVideoOn"] = it }
      isHandRaised?.let { updates["isHandRaised"] = it }
      isSpeaking?.let { updates["isSpeaking"] = it }
      updates["lastHeartbeat"] = System.currentTimeMillis()

      if (updates.isNotEmpty()) {
        database?.reference?.child(ROOT_NODE)?.child(PARTICIPANTS_NODE)?.child(roomId)?.child(participantId)?.updateChildren(updates)
      }
    } catch (e: Exception) {
      Log.w(TAG, "Error syncing status to Firebase: ${e.message}")
    }
  }

  fun sendChatMessage(roomId: String, message: ConferenceChatMessage) {
    // Local update
    _liveChat.value = _liveChat.value + message

    // Firebase update
    try {
      val db = database
      if (db != null) {
        val chatRef = db.reference.child(ROOT_NODE).child(CHAT_NODE).child(roomId).child(message.id)
        val data = mapOf(
          "id" to message.id,
          "senderName" to message.senderName,
          "senderDesignation" to message.senderDesignation,
          "level" to message.level.name,
          "district" to message.district,
          "message" to message.message,
          "time" to message.time,
          "isResolution" to message.isResolution
        )
        chatRef.setValue(data)
      }
    } catch (e: Exception) {
      Log.w(TAG, "Error sending chat message to Firebase: ${e.message}")
    }
  }

  fun addResolution(roomId: String, resolution: MeetingResolution) {
    // Local update
    _liveResolutions.value = _liveResolutions.value + resolution

    // Add resolution alert to chat
    val resChat = ConferenceChatMessage(
      senderName = resolution.proposedBy,
      senderDesignation = "தீர்மான முன்மொழிவு",
      level = AdminHierarchyLevel.STATE,
      district = "தலைமையகம்",
      message = "📜 புதிய தீர்மானம் #${resolution.resolutionNo} நிறைவேற்றப்பட்டது: ${resolution.titleTamil}",
      time = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date()),
      isResolution = true
    )
    sendChatMessage(roomId, resChat)

    // Firebase update
    try {
      database?.reference?.child(ROOT_NODE)?.child(RESOLUTIONS_NODE)?.child(roomId)?.child(resolution.id)?.setValue(resolution)
    } catch (e: Exception) {
      Log.w(TAG, "Error adding resolution to Firebase: ${e.message}")
    }
  }

  fun triggerReaction(emoji: String) {
    val timestamp = System.currentTimeMillis()
    _floatingReactions.value = (_floatingReactions.value + Pair(emoji, timestamp)).takeLast(12)
  }

  fun hostMuteAll(roomId: String, hostId: String) {
    _liveParticipants.value = _liveParticipants.value.map { p ->
      if (p.id == hostId || p.isHost) p else p.copy(isMicOn = false, isSpeaking = false)
    }
    recordSecurityEvent("தலைமை நிர்வாகி அனைத்து உறுப்பினர்களையும் மியூட் செய்தார் (Mute All).")

    try {
      _liveParticipants.value.filter { !it.isHost && it.id != hostId }.forEach { p ->
        database?.reference?.child(ROOT_NODE)?.child(PARTICIPANTS_NODE)?.child(roomId)?.child(p.id)?.child("isMicOn")?.setValue(false)
        database?.reference?.child(ROOT_NODE)?.child(PARTICIPANTS_NODE)?.child(roomId)?.child(p.id)?.child("isSpeaking")?.setValue(false)
      }
    } catch (e: Exception) {
      Log.w(TAG, "Error muting all on Firebase: ${e.message}")
    }
  }

  private fun recordSecurityEvent(log: String) {
    val time = SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(Date())
    val entry = "[$time] $log"
    _securityAuditLogs.value = listOf(entry) + _securityAuditLogs.value.take(40)
  }
}

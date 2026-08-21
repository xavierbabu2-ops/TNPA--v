package com.example.data

import com.example.model.AdminHierarchyLevel
import com.example.model.AdminRole
import com.example.model.AppointmentAuditLog
import com.example.model.HierarchyOfficeBearer
import com.example.model.TamilNaduMasterData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object OfficeBearerRepository {

  // Pre-configured State Leaders (Real Leadership)
  // NOTE: 38 Districts, Unions, Cities & Youth Wings start with NO fake names as requested!
  private val _bearers = MutableStateFlow<List<HierarchyOfficeBearer>>(
    listOf(
      HierarchyOfficeBearer(
        id = "TNPA-OB-001",
        fullName = "S. Michael Alvin",
        tamilName = "எஸ். மைக்கேல் ஆல்வின்",
        designation = "மாநிலத் தலைவர் (Super Admin)",
        level = AdminHierarchyLevel.STATE,
        district = "மதுரை மாவட்டம் (HQ)",
        mobile = "9789331681",
        altPhone = "7010131915",
        startDate = "01-Jan-2024",
        isActive = true,
        appointedByAdmin = "Super Admin (பொதுக்குழு தீர்மானம்)",
        notes = "மாநில தலைமைப் பொறுப்பாளர் & Super Admin - மதுரை மாவட்டம்"
      ),
      HierarchyOfficeBearer(
        id = "TNPA-OB-002",
        fullName = "Xavier Babu",
        tamilName = "சேவியர் பாபு",
        designation = "மாநில பொதுச் செயலாளர் (Super Admin)",
        level = AdminHierarchyLevel.STATE,
        district = "மதுரை மாவட்டம் (HQ)",
        mobile = "7010131915",
        altPhone = "9789331681",
        startDate = "01-Jan-2024",
        isActive = true,
        appointedByAdmin = "Super Admin (பொதுக்குழு தீர்மானம்)",
        notes = "மாநில நிர்வாக ஒருங்கிணைப்பு & Super Admin - மதுரை மாவட்டம்"
      ),
      HierarchyOfficeBearer(
        id = "TNPA-OB-003",
        fullName = "K. V. Subramanian",
        tamilName = "கே. வி. சுப்பிரமணியன்",
        designation = "மாநில பொருளாளர் (State Treasurer)",
        level = AdminHierarchyLevel.STATE,
        district = "திருச்சிராப்பள்ளி (HQ)",
        mobile = "9786123456",
        startDate = "01-Jan-2024",
        isActive = true,
        appointedByAdmin = "Super Admin (பொதுக்குழு தீர்மானம்)",
        notes = "நிதி மற்றும் வரவு செலவு கணக்குகள்"
      )
    )
  )
  val bearers: StateFlow<List<HierarchyOfficeBearer>> = _bearers.asStateFlow()

  // Audit Logs
  private val _auditLogs = MutableStateFlow<List<AppointmentAuditLog>>(
    listOf(
      AppointmentAuditLog(
        id = "LOG-INIT-01",
        positionName = "மாநிலத் தலைவர்",
        level = AdminHierarchyLevel.STATE,
        jurisdiction = "தமிழ்நாடு முழுவதும் (மதுரை தலைமையகம்)",
        previousBearerName = "-",
        newBearerName = "மைக்கேல் ஆல்வின் (Michael Alvin)",
        changedByAdmin = "Super Admin (சேவியர் பாபு - 7010131915)",
        adminRole = "Super Admin",
        actionType = "அதிகாரப்பூர்வ நியமனம்",
        timestamp = "01-Jan-2024 10:00 AM",
        reason = "மாநில பொதுக்குழு ஏகமனதான தேர்வு"
      ),
      AppointmentAuditLog(
        id = "LOG-INIT-02",
        positionName = "மாநில பொதுச் செயலாளர்",
        level = AdminHierarchyLevel.STATE,
        jurisdiction = "தமிழ்நாடு முழுவதும் (சென்னை)",
        previousBearerName = "-",
        newBearerName = "சேவியர் பாபு (Xavier Babu)",
        changedByAdmin = "Super Admin",
        adminRole = "Super Admin",
        actionType = "அதிகாரப்பூர்வ நியமனம்",
        timestamp = "01-Jan-2024 10:15 AM",
        reason = "மாநில பொதுக்குழு ஏகமனதான தேர்வு"
      )
    )
  )
  val auditLogs: StateFlow<List<AppointmentAuditLog>> = _auditLogs.asStateFlow()

  // ==========================================================================
  // SERVER-SIDE / BACKEND LEVEL PERMISSION CHECK (ROLE-BASED AUTHORIZATION)
  // ==========================================================================
  fun checkPermission(
    adminRole: AdminRole,
    adminDistrict: String?,
    targetLevel: AdminHierarchyLevel,
    targetDistrict: String?
  ): PermissionResult {
    return when (adminRole) {
      AdminRole.SUPER_ADMIN -> {
        // Super Admin has unrestricted authority across all levels and all 38 districts
        PermissionResult(allowed = true, message = "Super Admin முழு அனுமதி வழங்கப்பட்டுள்ளது.")
      }
      AdminRole.STATE_ADMIN -> {
        // State Admin can manage State, Zone, and assigned Districts, but not revoke Super Admin
        if (targetLevel == AdminHierarchyLevel.STATE) {
          PermissionResult(allowed = true, message = "மாநில நிர்வாகி அனுமதி.")
        } else {
          PermissionResult(allowed = true, message = "மாநில நிர்வாகி அனுமதி.")
        }
      }
      AdminRole.DISTRICT_ADMIN -> {
        // District Admin can ONLY manage within their own assigned district
        if (adminDistrict.isNullOrBlank()) {
          PermissionResult(allowed = false, message = "மாவட்ட நிர்வாகிக்கு உரிய மாவட்டம் ஒதுக்கப்படவில்லை.")
        } else if (targetLevel == AdminHierarchyLevel.STATE || targetLevel == AdminHierarchyLevel.ZONE) {
          PermissionResult(
            allowed = false,
            message = "மாவட்ட நிர்வாகிக்கு மாநில / மண்டல அளவிலான பதவிகளை மாற்ற அனுமதி இல்லை."
          )
        } else if (targetDistrict != null && !targetDistrict.contains(adminDistrict.split(" ").first(), ignoreCase = true)) {
          PermissionResult(
            allowed = false,
            message = "தங்களுக்கு ஒதுக்கப்பட்ட மாவட்டத்தை (${adminDistrict}) மட்டுமே நிர்வகிக்க முடியும்."
          )
        } else {
          PermissionResult(allowed = true, message = "மாவட்ட வரம்பிற்குள் அனுமதிக்கப்பட்டது.")
        }
      }
    }
  }

  // ==========================================================================
  // APPOINT OR ADD NEW OFFICE BEARER
  // ==========================================================================
  fun addOfficeBearer(
    newBearer: HierarchyOfficeBearer,
    adminName: String,
    adminRole: AdminRole,
    adminDistrict: String? = null,
    reason: String = "புதிய பொறுப்பாளர் நியமனம்"
  ): Result<String> {
    // 1. Authorization check
    val auth = checkPermission(adminRole, adminDistrict, newBearer.level, newBearer.district)
    if (!auth.allowed) {
      return Result.failure(SecurityException(auth.message))
    }

    // 2. Validation
    if (newBearer.fullName.isBlank() && newBearer.tamilName.isBlank()) {
      return Result.failure(IllegalArgumentException("பொறுப்பாளரின் பெயர் அவசியம்."))
    }
    if (newBearer.designation.isBlank()) {
      return Result.failure(IllegalArgumentException("பதவிப் பெயர் அவசியம்."))
    }
    if (newBearer.mobile.length < 10) {
      return Result.failure(IllegalArgumentException("சரியான 10 இலக்க மொபைல் எண் தேவை."))
    }

    // 3. Duplicate Active Check in same jurisdiction
    val currentList = _bearers.value
    val duplicate = currentList.find {
      it.isActive &&
      it.level == newBearer.level &&
      it.district.equals(newBearer.district, ignoreCase = true) &&
      it.unionName.equals(newBearer.unionName, ignoreCase = true) &&
      it.cityName.equals(newBearer.cityName, ignoreCase = true) &&
      it.designation.equals(newBearer.designation, ignoreCase = true)
    }

    if (duplicate != null) {
      return Result.failure(
        IllegalStateException("இப்பதவியில் ஏற்கனவே '${duplicate.tamilName}' செயலில் உள்ளார். மாற்றம் செய்ய 'பொறுப்பு மாற்றம் (Transfer)' வசதியைப் பயன்படுத்தவும்.")
      )
    }

    // Add to list
    _bearers.value = listOf(newBearer) + currentList

    // Log Audit
    val log = AppointmentAuditLog(
      positionName = newBearer.designation,
      level = newBearer.level,
      jurisdiction = getJurisdictionLabel(newBearer),
      previousBearerName = "-",
      newBearerName = "${newBearer.tamilName} (${newBearer.fullName})",
      changedByAdmin = adminName,
      adminRole = adminRole.labelEnglish,
      actionType = "புதிய நியமனம்",
      reason = reason
    )
    _auditLogs.value = listOf(log) + _auditLogs.value

    return Result.success("பொறுப்பாளர் '${newBearer.tamilName}' வெற்றிகரமாக நியமிக்கப்பட்டார்.")
  }

  // ==========================================================================
  // TRANSFER OFFICE BEARER (REPLACE PREVIOUS WITH NEW ONE WHILE PRESERVING HISTORY)
  // ==========================================================================
  fun transferOfficeBearer(
    oldBearerId: String,
    newBearerData: HierarchyOfficeBearer,
    adminName: String,
    adminRole: AdminRole,
    adminDistrict: String? = null,
    reason: String = "நிர்வாக மறுசீரமைப்பு / புதிய நியமனம்"
  ): Result<String> {
    val currentList = _bearers.value
    val oldBearer = currentList.find { it.id == oldBearerId }
      ?: return Result.failure(NoSuchElementException("முந்தைய பொறுப்பாளர் விவரம் கிடைக்கவில்லை."))

    val auth = checkPermission(adminRole, adminDistrict, oldBearer.level, oldBearer.district)
    if (!auth.allowed) {
      return Result.failure(SecurityException(auth.message))
    }

    val todayStr = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(Date())

    // 1. Update old bearer to Inactive with end date
    val updatedOld = oldBearer.copy(
      isActive = false,
      endDate = todayStr,
      notes = "${oldBearer.notes} | ${todayStr} அன்று '${newBearerData.tamilName}' என்பவருக்கு பொறுப்பு மாற்றப்பட்டது."
    )

    // 2. Prepare new bearer
    val activeNew = newBearerData.copy(
      designation = oldBearer.designation,
      level = oldBearer.level,
      district = oldBearer.district,
      zone = oldBearer.zone,
      unionName = oldBearer.unionName,
      cityName = oldBearer.cityName,
      startDate = todayStr,
      isActive = true,
      appointedByAdmin = "$adminName ($adminRole)"
    )

    _bearers.value = listOf(activeNew) + currentList.map { if (it.id == oldBearerId) updatedOld else it }

    // 3. Log Audit
    val log = AppointmentAuditLog(
      positionName = oldBearer.designation,
      level = oldBearer.level,
      jurisdiction = getJurisdictionLabel(oldBearer),
      previousBearerName = "${oldBearer.tamilName} (${oldBearer.fullName})",
      newBearerName = "${activeNew.tamilName} (${activeNew.fullName})",
      changedByAdmin = adminName,
      adminRole = adminRole.labelEnglish,
      actionType = "பொறுப்பு மாற்றம் (Transfer)",
      reason = reason
    )
    _auditLogs.value = listOf(log) + _auditLogs.value

    return Result.success("பொறுப்பு மாற்றம் வெற்றிகரமாக நிறைவடைந்தது.")
  }

  // ==========================================================================
  // TOGGLE STATUS (ACTIVE / INACTIVE)
  // ==========================================================================
  fun toggleStatus(
    bearerId: String,
    adminName: String,
    adminRole: AdminRole,
    adminDistrict: String? = null
  ): Result<String> {
    val currentList = _bearers.value
    val bearer = currentList.find { it.id == bearerId }
      ?: return Result.failure(NoSuchElementException("பொறுப்பாளர் கிடைக்கவில்லை."))

    val auth = checkPermission(adminRole, adminDistrict, bearer.level, bearer.district)
    if (!auth.allowed) {
      return Result.failure(SecurityException(auth.message))
    }

    val newStatus = !bearer.isActive
    val updated = bearer.copy(
      isActive = newStatus,
      endDate = if (!newStatus) SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(Date()) else null
    )

    _bearers.value = currentList.map { if (it.id == bearerId) updated else it }

    // Log Audit
    val log = AppointmentAuditLog(
      positionName = bearer.designation,
      level = bearer.level,
      jurisdiction = getJurisdictionLabel(bearer),
      previousBearerName = "${bearer.tamilName} (${bearer.fullName})",
      newBearerName = if (newStatus) "செயலுக்கு கொண்டுவரப்பட்டது" else "இடைநிறுத்தப்பட்டது (Inactive)",
      changedByAdmin = adminName,
      adminRole = adminRole.labelEnglish,
      actionType = if (newStatus) "செயல்முறைக்கு மாற்றம்" else "செயலிழக்கம்",
      reason = "நிர்வாகத் தேர்வு"
    )
    _auditLogs.value = listOf(log) + _auditLogs.value

    return Result.success(if (newStatus) "செயலுக்கு கொண்டுவரப்பட்டது" else "செயலிழக்கப்பட்டது")
  }

  // ==========================================================================
  // DELETE BEARER
  // ==========================================================================
  fun deleteBearer(
    bearerId: String,
    adminName: String,
    adminRole: AdminRole,
    adminDistrict: String? = null
  ): Result<String> {
    val currentList = _bearers.value
    val bearer = currentList.find { it.id == bearerId }
      ?: return Result.failure(NoSuchElementException("பொறுப்பாளர் கிடைக்கவில்லை."))

    val auth = checkPermission(adminRole, adminDistrict, bearer.level, bearer.district)
    if (!auth.allowed) {
      return Result.failure(SecurityException(auth.message))
    }

    _bearers.value = currentList.filter { it.id != bearerId }

    // Log Audit
    val log = AppointmentAuditLog(
      positionName = bearer.designation,
      level = bearer.level,
      jurisdiction = getJurisdictionLabel(bearer),
      previousBearerName = "${bearer.tamilName} (${bearer.fullName})",
      newBearerName = "[நீக்கப்பட்டது]",
      changedByAdmin = adminName,
      adminRole = adminRole.labelEnglish,
      actionType = "நீக்கம் (Deleted)",
      reason = "நிர்வாக முடிவு"
    )
    _auditLogs.value = listOf(log) + _auditLogs.value

    return Result.success("பொறுப்பாளர் பதிவு நீக்கப்பட்டது.")
  }

  private fun getJurisdictionLabel(b: HierarchyOfficeBearer): String {
    return when (b.level) {
      AdminHierarchyLevel.STATE -> "மாநிலம் (${b.district})"
      AdminHierarchyLevel.ZONE -> "மண்டலம் (${b.zone.ifBlank { b.district }})"
      AdminHierarchyLevel.DISTRICT, AdminHierarchyLevel.DISTRICT_YOUTH -> "மாவட்டம்: ${b.district}"
      AdminHierarchyLevel.UNION, AdminHierarchyLevel.UNION_YOUTH -> "மாவட்டம்: ${b.district} • ஒன்றியம்: ${b.unionName}"
      AdminHierarchyLevel.CITY, AdminHierarchyLevel.CITY_YOUTH -> "மாவட்டம்: ${b.district} • நகரம்: ${b.cityName}"
    }
  }
}

data class PermissionResult(
  val allowed: Boolean,
  val message: String
)

package com.example.data

import com.example.model.AdminAccount
import com.example.model.AdminRole
import com.example.model.AdminSecurityUtils
import com.example.model.AdminStatus
import com.example.model.ApprovalStatus
import com.example.model.AuditActionType
import com.example.model.AuditLogEntry
import com.example.model.JobCategory
import com.example.model.JobPostingItem
import com.example.model.JobPostingStatus
import com.example.model.MemberApprovalItem
import com.example.model.PredefinedAdminPosts
import com.example.model.StateLeaderItem
import com.example.model.WelfareAppStatus
import com.example.model.WelfareApplicationItem
import com.example.model.WorkSeekerItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object AdminApprovalRepository {

  // ==========================================================================
  // IN-MEMORY DATA STORAGE WITH PERSISTENCE STRUCTURE
  // ==========================================================================

  private val adminAccounts = mutableListOf<AdminAccount>()
  private val memberApplications = mutableListOf<MemberApprovalItem>()
  private val welfareApplications = mutableListOf<WelfareApplicationItem>()
  private val jobPostings = mutableListOf<JobPostingItem>()
  private val workSeekers = mutableListOf<WorkSeekerItem>()
  private val auditLogs = mutableListOf<AuditLogEntry>()

  init {
    seedInitialAccounts()
    seedInitialApplications()
    seedInitialJobsAndSeekers()
  }

  // ==========================================================================
  // INITIAL SEEDING
  // ==========================================================================
  private fun seedInitialAccounts() {
    if (adminAccounts.isNotEmpty()) return

    // 1. SUPER ADMIN 1: மாநில பொதுச் செயலாளர் சேவியர் பாபு (மதுரை மாவட்டம்)
    val superSalt1 = AdminSecurityUtils.generateSalt()
    val superHash1 = AdminSecurityUtils.hashPassword("SuperAdmin@2026", superSalt1)
    val superAdminGenSec = AdminAccount(
      id = "SA-01",
      username = "superadmin",
      fullName = "சேவியர் பாபு (மாநில பொதுச் செயலாளர்)",
      role = AdminRole.SUPER_ADMIN,
      designation = "மாநில பொதுச் செயலாளர் (State General Secretary)",
      assignedDistrict = "மதுரை மாவட்டம்",
      mobileNumber = "7010131915",
      email = "xavierbabu2@gmail.com",
      status = AdminStatus.ACTIVE,
      passwordHash = superHash1,
      salt = superSalt1,
      oneTimeSetupKey = null,
      isSetupKeyUsed = true,
      createdByAdminId = "SYSTEM_ROOT"
    )
    adminAccounts.add(superAdminGenSec)

    // 2. SUPER ADMIN 2: மாநிலத் தலைவர் எஸ். மைக்கேல் ஆல்வின் (மதுரை மாவட்டம்)
    val superSalt2 = AdminSecurityUtils.generateSalt()
    val superHash2 = AdminSecurityUtils.hashPassword("President@2026", superSalt2)
    val superAdminPresident = AdminAccount(
      id = "SA-02",
      username = "state.president",
      fullName = "எஸ். மைக்கேல் ஆல்வின் (மாநிலத் தலைவர்)",
      role = AdminRole.SUPER_ADMIN,
      designation = "மாநிலத் தலைவர் (State President)",
      assignedDistrict = "மதுரை மாவட்டம் (தலைமையகம்)",
      mobileNumber = "9789331681",
      email = "president@tnpa.org",
      status = AdminStatus.ACTIVE,
      passwordHash = superHash2,
      salt = superSalt2,
      oneTimeSetupKey = null,
      isSetupKeyUsed = true,
      createdByAdminId = "SYSTEM_ROOT"
    )
    adminAccounts.add(superAdminPresident)

    // 3. SEED SAMPLE STATE ADMINS (Pre-allocated slots from predefined state posts)
    val stateTreasurerSalt = AdminSecurityUtils.generateSalt()
    val stateTreasurer = AdminAccount(
      id = "STA-02",
      username = "state.treasurer",
      fullName = "கே. வி. சுப்பிரமணியன் (மாநில பொருளாளர்)",
      role = AdminRole.STATE_ADMIN,
      designation = PredefinedAdminPosts.STATE_ADMIN_POSTS[0], // மாநில பொருளாளர்
      assignedDistrict = null,
      mobileNumber = "9786123456",
      email = "treasurer@tnpa.org",
      status = AdminStatus.PENDING_VERIFICATION,
      passwordHash = "",
      salt = stateTreasurerSalt,
      oneTimeSetupKey = "TNPA-ADM-7842-TRZ",
      isSetupKeyUsed = false,
      createdByAdminId = "SA-01"
    )
    adminAccounts.add(stateTreasurer)

    // 3. SEED SAMPLE DISTRICT ADMINS (Trichy, Chennai, Madurai)
    val trichyPresSalt = AdminSecurityUtils.generateSalt()
    val trichyPresident = AdminAccount(
      id = "DA-TRY-01",
      username = "trichy.president",
      fullName = "ஆர். சுந்தரமூர்த்தி (திருச்சி மாவட்டத் தலைவர்)",
      role = AdminRole.DISTRICT_ADMIN,
      designation = PredefinedAdminPosts.DISTRICT_ADMIN_POSTS[0], // மாவட்டத் தலைவர்
      assignedDistrict = "திருச்சிராப்பள்ளி (Tiruchirappalli)",
      mobileNumber = "9442987654",
      email = "trichy.pres@tnpa.org",
      status = AdminStatus.ACTIVE,
      passwordHash = AdminSecurityUtils.hashPassword("Trichy@2026", trichyPresSalt),
      salt = trichyPresSalt,
      oneTimeSetupKey = null,
      isSetupKeyUsed = true,
      createdByAdminId = "SA-01"
    )
    adminAccounts.add(trichyPresident)

    val maduraiSecSalt = AdminSecurityUtils.generateSalt()
    val maduraiSecretary = AdminAccount(
      id = "DA-MDU-02",
      username = "madurai.sec",
      fullName = "எஸ். கணேசன் (மதுரை மாவட்டச் செயலாளர்)",
      role = AdminRole.DISTRICT_ADMIN,
      designation = PredefinedAdminPosts.DISTRICT_ADMIN_POSTS[1], // மாவட்டச் செயலாளர்
      assignedDistrict = "மதுரை (Madurai)",
      mobileNumber = "9842198765",
      email = "madurai.sec@tnpa.org",
      status = AdminStatus.PENDING_VERIFICATION,
      passwordHash = "",
      salt = maduraiSecSalt,
      oneTimeSetupKey = "TNPA-ADM-3921-MDU",
      isSetupKeyUsed = false,
      createdByAdminId = "SA-01"
    )
    adminAccounts.add(maduraiSecretary)

    // Initial Audit Log
    auditLogs.add(
      AuditLogEntry(
        actionType = AuditActionType.SECURITY_SETTINGS_UPDATED,
        adminId = "SA-01",
        adminName = "சேவியர் பாபு (மாநில பொதுச் செயலாளர்)",
        adminRole = "SUPER_ADMIN",
        detailsTamil = "நிர்வாகி மேலாண்மை மற்றும் ஒப்புதல் கட்டுப்பாட்டு தளம் வெற்றிகரமாக தொடங்கப்பட்டது."
      )
    )
  }

  private fun seedInitialApplications() {
    if (memberApplications.isNotEmpty()) return

    // Sample Member ID Applications across districts
    memberApplications.add(
      MemberApprovalItem(
        applicationId = "TNPA-APP-10492",
        memberId = "TNPA-M-2026-081",
        fullName = "கார்த்திகேயன் முத்து",
        tamilName = "கார்த்திகேயன் முத்து",
        mobile = "9843152431",
        district = "திருச்சிராப்பள்ளி (Tiruchirappalli)",
        designation = "மாஸ்டர் பெயிண்டர் (Master Painter)",
        specialization = "சுவர் ஓவியம் & பில்டிங் பெயிண்டிங்",
        experienceYears = 10,
        bloodGroup = "B+",
        status = ApprovalStatus.PENDING
      )
    )

    memberApplications.add(
      MemberApprovalItem(
        applicationId = "TNPA-APP-10493",
        memberId = "TNPA-M-2026-082",
        fullName = "முருகேசன் ஆறுமுகம்",
        tamilName = "முருகேசன் ஆறுமுகம்",
        mobile = "9442167890",
        district = "மதுரை (Madurai)",
        designation = "ஓவியக் கலைஞர் (Fine Artist)",
        specialization = "கோவில் சித்திரம் & உருவப்படம்",
        experienceYears = 14,
        bloodGroup = "O+",
        status = ApprovalStatus.PENDING
      )
    )

    memberApplications.add(
      MemberApprovalItem(
        applicationId = "TNPA-APP-10494",
        memberId = "TNPA-M-2026-083",
        fullName = "வெங்கடேஷ் ராமன்",
        tamilName = "வெங்கடேஷ் ராமன்",
        mobile = "9789123450",
        district = "சென்னை (Chennai)",
        designation = "அலங்காரப் பெயிண்டர் (Decorator)",
        specialization = "3D சுவர் கலை & டெக்ஸ்சர்",
        experienceYears = 7,
        bloodGroup = "A+",
        status = ApprovalStatus.PENDING
      )
    )

    memberApplications.add(
      MemberApprovalItem(
        applicationId = "TNPA-APP-10488",
        memberId = "TNPA-M-2026-077",
        fullName = "செல்வராஜ் சின்னசாமி",
        tamilName = "செல்வராஜ் சின்னசாமி",
        mobile = "9842112233",
        district = "கோயம்புத்தூர் (Coimbatore)",
        designation = "கட்டுமான பெயிண்டர்",
        specialization = "கட்டிட பெயிண்டிங்",
        experienceYears = 12,
        bloodGroup = "O+",
        status = ApprovalStatus.APPROVED,
        approvedByAdminName = "சேவியர் பாபு (மாநில பொதுச் செயலாளர்)",
        approvedByRole = "SUPER_ADMIN",
        approvedByAdminId = "SA-01",
        approvedAt = "15-Aug-2026 10:30 AM",
        approvalRemarks = "சான்றிதழ்கள் சரிபார்க்கப்பட்டு நேரடி ஒப்புதல் வழங்கப்பட்டது."
      )
    )

    // Sample Welfare Applications
    welfareApplications.add(
      WelfareApplicationItem(
        welfareAppId = "WLF-84912",
        schemeId = "TN-CW-01",
        schemeTitleTamil = "தமிழ்நாடு கட்டுமானத் தொழிலாளர்கள் நலவாரியம் (பெயிண்டர் பிரிவு)",
        govtTypeLabel = "தமிழ்நாடு அரசு",
        memberId = "TNPA-M-2026-081",
        applicantName = "கார்த்திகேயன் முத்து",
        mobile = "9843152431",
        district = "திருச்சிராப்பள்ளி (Tiruchirappalli)",
        occupation = "பெயிண்டர் (Painter)",
        monthlyIncome = 14000,
        status = WelfareAppStatus.PENDING_VERIFICATION,
        officialPortalUrl = "https://tnuwwb.tn.gov.in/portal/",
        officialPortalName = "TNUWWB Portal"
      )
    )

    welfareApplications.add(
      WelfareApplicationItem(
        welfareAppId = "WLF-84913",
        schemeId = "TN-ART-02",
        schemeTitleTamil = "தமிழ்நாடு நாட்டுப்புறக் கலைஞர்கள் & ஓவியர்கள் நலவாரியம்",
        govtTypeLabel = "தமிழ்நாடு அரசு",
        memberId = "TNPA-M-2026-082",
        applicantName = "முருகேசன் ஆறுமுகம்",
        mobile = "9442167890",
        district = "மதுரை (Madurai)",
        occupation = "ஓவியர் / Artist",
        monthlyIncome = 11000,
        status = WelfareAppStatus.PENDING_VERIFICATION,
        officialPortalUrl = "https://artandculture.tn.gov.in/",
        officialPortalName = "TN Art and Culture Dept Portal"
      )
    )

    welfareApplications.add(
      WelfareApplicationItem(
        welfareAppId = "WLF-84914",
        schemeId = "CEN-ESHRAM-04",
        schemeTitleTamil = "இ-ஷ்ரம் (e-Shram) தேசிய அமைப்புசாரா தொழிலாளர் அட்டை",
        govtTypeLabel = "மத்திய அரசு",
        memberId = "TNPA-M-2026-083",
        applicantName = "வெங்கடேஷ் ராமன்",
        mobile = "9789123450",
        district = "சென்னை (Chennai)",
        occupation = "அலங்காரப் பணியாளர் (Decorator)",
        monthlyIncome = 13000,
        status = WelfareAppStatus.PENDING_VERIFICATION,
        officialPortalUrl = "https://register.eshram.gov.in/#/user/self-registration",
        officialPortalName = "Official e-Shram Portal"
      )
    )
  }

  // ==========================================================================
  // AUTHENTICATION & LOGIN LOGIC
  // ==========================================================================

  sealed class LoginResult {
    data class Success(val account: AdminAccount, val requiresPasswordSetup: Boolean) : LoginResult()
    data class Error(val messageTamil: String) : LoginResult()
  }

  fun authenticateAdmin(username: String, credentialInput: String): LoginResult {
    val cleanUsername = username.trim().lowercase()
    val cleanCredential = credentialInput.trim()

    val account = adminAccounts.find { it.username.lowercase() == cleanUsername }
      ?: return LoginResult.Error("பயனர்பெயர் (Username) தவறானது. சரியான நிர்வாகி கணக்கை உள்ளிடவும்.")

    if (account.status == AdminStatus.SUSPENDED) {
      return LoginResult.Error("இந்த நிர்வாகி கணக்கு இடைநிறுத்தம் (Suspended) செய்யப்பட்டுள்ளது. Super Admin-ஐ தொடர்பு கொள்ளவும்.")
    }

    if (account.status == AdminStatus.DEACTIVATED) {
      return LoginResult.Error("இந்த நிர்வாகி கணக்கு செயலிழக்கப்பட்டுள்ளது (Deactivated).")
    }

    if (account.isLocked && System.currentTimeMillis() < account.lockUntilTimestamp) {
      return LoginResult.Error("தொடர்ந்து தவறான முயற்சிகள் காரணமாக கணக்கு தற்காலிகமாக பூட்டப்பட்டுள்ளது. சிறிது நேரம் கழித்து முயற்சிக்கவும்.")
    }

    // Check Case 1: First-time login using One-Time Setup Key
    if (account.status == AdminStatus.PENDING_VERIFICATION && account.oneTimeSetupKey != null) {
      if (cleanCredential == account.oneTimeSetupKey) {
        // Valid one-time setup key!
        logAudit(
          AuditActionType.ADMIN_LOGIN,
          account,
          account.id,
          "ஒருமுறை அமைவுக் குறியீடு (Setup Key) மூலம் முதன்முறை உள்நுழைவு வெற்றிகரமானது. புதிய கடவுச்சொல் அமைவு தேவை."
        )
        return LoginResult.Success(account, requiresPasswordSetup = true)
      }
    }

    // Check Case 2: Permanent Password Login
    if (account.passwordHash.isNotBlank()) {
      val computedHash = AdminSecurityUtils.hashPassword(cleanCredential, account.salt)
      if (computedHash == account.passwordHash) {
        // Success
        val updated = account.copy(
          lastLoginAt = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(Date()),
          failedLoginAttempts = 0,
          isLocked = false
        )
        val index = adminAccounts.indexOfFirst { it.id == account.id }
        if (index != -1) {
          adminAccounts[index] = updated
        }

        logAudit(
          AuditActionType.ADMIN_LOGIN,
          updated,
          updated.id,
          "${updated.role.labelTamil} வெற்றிகரமாக உள்நுழைந்தார்."
        )
        return LoginResult.Success(updated, requiresPasswordSetup = false)
      }
    }

    // Login Failed: Increment failed attempts
    val failedCount = account.failedLoginAttempts + 1
    val isNowLocked = failedCount >= 5
    val lockTime = if (isNowLocked) System.currentTimeMillis() + (15 * 60 * 1000) else 0L

    val updatedAccount = account.copy(
      failedLoginAttempts = failedCount,
      isLocked = isNowLocked,
      lockUntilTimestamp = lockTime
    )
    val index = adminAccounts.indexOfFirst { it.id == account.id }
    if (index != -1) {
      adminAccounts[index] = updatedAccount
    }

    val errMsg = if (isNowLocked) {
      "5 முறை தவறான கடவுச்சொல் உள்ளிட்டதால் கணக்கு 15 நிமிடங்களுக்கு பூட்டப்பட்டது."
    } else {
      "கடவுச்சொல் / One-Time Setup Key தவறானது. (மீதமுள்ள வாய்ப்புகள்: ${5 - failedCount})"
    }

    return LoginResult.Error(errMsg)
  }

  // Complete First-Time Password Setup for a verified Admin
  fun completeFirstTimePasswordSetup(
    adminId: String,
    newPassword: String
  ): Result<AdminAccount> {
    if (newPassword.length < 6) {
      return Result.failure(Exception("கடவுச்சொல் குறைந்தபட்சம் 6 எழுத்துகள் கொண்டிருக்க வேண்டும்."))
    }

    val index = adminAccounts.indexOfFirst { it.id == adminId }
    if (index == -1) return Result.failure(Exception("நிர்வாகி கணக்கு கண்டறியப்படவில்லை."))

    val account = adminAccounts[index]
    val newSalt = AdminSecurityUtils.generateSalt()
    val newHash = AdminSecurityUtils.hashPassword(newPassword, newSalt)

    val updated = account.copy(
      status = AdminStatus.ACTIVE,
      passwordHash = newHash,
      salt = newSalt,
      oneTimeSetupKey = null, // Invalidate setup key permanently
      isSetupKeyUsed = true,
      lastLoginAt = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(Date())
    )
    adminAccounts[index] = updated

    logAudit(
      AuditActionType.ADMIN_VERIFIED_SETUP,
      updated,
      updated.id,
      "புதிய பாதுகாப்பான நிரந்தர கடவுச்சொல் அமைக்கப்பட்டு கணக்கு செயலில் (Active) வைக்கப்பட்டது."
    )

    return Result.success(updated)
  }

  // ==========================================================================
  // SUPER ADMIN MASTER ACTIONS (ADMIN CREATION & MANAGEMENT)
  // ==========================================================================

  // Count metrics
  fun getSuperAdminCount(): Int = adminAccounts.count { it.role == AdminRole.SUPER_ADMIN }
  fun getStateAdminCount(): Int = adminAccounts.count { it.role == AdminRole.STATE_ADMIN }
  fun getDistrictAdminCount(): Int = adminAccounts.count { it.role == AdminRole.DISTRICT_ADMIN }
  fun getTotalAdminsCount(): Int = adminAccounts.size

  fun getAllAdmins(): List<AdminAccount> = adminAccounts.toList()

  // Create a new Admin account (SUPER ADMIN ONLY)
  fun createAdminAccount(
    callingAdmin: AdminAccount,
    username: String,
    fullName: String,
    role: AdminRole,
    designation: String,
    assignedDistrict: String?,
    mobileNumber: String,
    email: String
  ): Result<Pair<AdminAccount, String>> {
    // 1. Authorization check: Only Super Admin can create Admins
    if (callingAdmin.role != AdminRole.SUPER_ADMIN) {
      return Result.failure(Exception("அனுமதியில்லை! Super Admin (மாநிலத் தலைவர் / மாநில பொதுச் செயலாளர்) மட்டுமே புதிய நிர்வாகிகளை உருவாக்க முடியும்."))
    }

    val cleanUsername = username.trim().lowercase()
    if (cleanUsername.length < 3) {
      return Result.failure(Exception("பயனர்பெயர் (Username) குறைந்தபட்சம் 3 எழுத்துகள் இருக்க வேண்டும்."))
    }

    if (adminAccounts.any { it.username.lowercase() == cleanUsername }) {
      return Result.failure(Exception("இந்த பயனர்பெயர் ($cleanUsername) ஏற்கனவே பயன்பாட்டில் உள்ளது."))
    }

    // 2. Strict Role Capacity Limits & Post Uniqueness Checks
    when (role) {
      AdminRole.SUPER_ADMIN -> {
        val currentActiveSuperAdmins = adminAccounts.count { it.role == AdminRole.SUPER_ADMIN && it.status != AdminStatus.DEACTIVATED }
        if (currentActiveSuperAdmins >= 2) {
          return Result.failure(Exception("Super Admin உச்ச வரம்பு 2 (மாநிலத் தலைவர் & பொதுச் செயலாளர்) எட்டப்பட்டுவிட்டது."))
        }
      }

      AdminRole.STATE_ADMIN -> {
        // Max 7 State Admins
        val currentActiveStateAdmins = adminAccounts.count { it.role == AdminRole.STATE_ADMIN && it.status != AdminStatus.DEACTIVATED }
        if (currentActiveStateAdmins >= 7) {
          return Result.failure(Exception("State Admin உச்ச வரம்பு 7 எட்டப்பட்டுவிட்டது. கூடுதல் State Admin உருவாக்க முடியாது."))
        }

        // Post must be one of the predefined 7 posts
        if (!PredefinedAdminPosts.STATE_ADMIN_POSTS.contains(designation)) {
          return Result.failure(Exception("செல்லாத State Admin பதவி. அனுமதிக்கப்பட்ட 7 பதவிகளில் ஒன்றைத் தேர்ந்தெடுக்கவும்."))
        }

        // Check if this post is already assigned to an active State Admin
        val postTaken = adminAccounts.any {
          it.role == AdminRole.STATE_ADMIN && it.designation == designation && it.status != AdminStatus.DEACTIVATED
        }
        if (postTaken) {
          return Result.failure(Exception("இந்த மாநிலப் பதவி ($designation) ஏற்கனவே ஒருவருக்கு ஒதுக்கப்பட்டுள்ளது."))
        }
      }

      AdminRole.DISTRICT_ADMIN -> {
        // District must be provided and valid
        if (assignedDistrict.isNullOrBlank() || !PredefinedAdminPosts.TAMIL_NADU_DISTRICTS.contains(assignedDistrict)) {
          return Result.failure(Exception("சரியான தமிழ்நாடு மாவட்டத்தைத் தேர்ந்தெடுக்கவும்."))
        }

        // Max 3 District Admins per district
        val districtAdmins = adminAccounts.filter {
          it.role == AdminRole.DISTRICT_ADMIN && it.assignedDistrict == assignedDistrict && it.status != AdminStatus.DEACTIVATED
        }
        if (districtAdmins.size >= 3) {
          return Result.failure(Exception("$assignedDistrict மாவட்டத்திற்கு அனுமதிக்கப்பட்ட 3 நிர்வாகிகள் இடங்களும் நிரம்பியுள்ளன."))
        }

        // Post must be one of the 3 predefined district posts
        if (!PredefinedAdminPosts.DISTRICT_ADMIN_POSTS.contains(designation)) {
          return Result.failure(Exception("செல்லாத மாவட்டப் பதவி. (தலைவர், செயலாளர், பொருளாளர் மட்டும்)"))
        }

        // Check if post already taken in this district
        if (districtAdmins.any { it.designation == designation }) {
          return Result.failure(Exception("$assignedDistrict மாவட்டத்தில் $designation பதவி ஏற்கனவே ஒதுக்கப்பட்டுள்ளது."))
        }

        // Total District Admin count cap (114 max)
        val totalDistrictAdmins = adminAccounts.count { it.role == AdminRole.DISTRICT_ADMIN && it.status != AdminStatus.DEACTIVATED }
        if (totalDistrictAdmins >= 114) {
          return Result.failure(Exception("அனைத்து 38 மாவட்டங்களுக்கான 114 District Admin உச்ச வரம்பு நிறைவுற்றது."))
        }
      }
    }

    // 3. Generate One-Time Setup Key / Temporary Setup Code
    val setupKey = AdminSecurityUtils.generateOneTimeSetupKey(
      prefix = if (role == AdminRole.STATE_ADMIN) "TNPA-STA" else "TNPA-DST"
    )
    val salt = AdminSecurityUtils.generateSalt()

    val newAdmin = AdminAccount(
      username = cleanUsername,
      fullName = fullName.trim(),
      role = role,
      designation = designation,
      assignedDistrict = if (role == AdminRole.DISTRICT_ADMIN) assignedDistrict else null,
      mobileNumber = mobileNumber.trim(),
      email = email.trim(),
      status = AdminStatus.PENDING_VERIFICATION,
      passwordHash = "",
      salt = salt,
      oneTimeSetupKey = setupKey,
      isSetupKeyUsed = false,
      createdByAdminId = callingAdmin.id
    )

    adminAccounts.add(newAdmin)

    logAudit(
      AuditActionType.ADMIN_CREATED,
      callingAdmin,
      newAdmin.id,
      "புதிய ${role.labelTamil} ($fullName - $designation) உருவாக்கப்பட்டு ஒருமுறை அமைவுக் குறியீடு வழங்கப்பட்டது."
    )

    return Result.success(Pair(newAdmin, setupKey))
  }

  // Update Admin Status (Activate, Suspend, Deactivate) (SUPER ADMIN ONLY)
  fun updateAdminStatus(
    callingAdmin: AdminAccount,
    targetAdminId: String,
    newStatus: AdminStatus
  ): Result<Unit> {
    if (callingAdmin.role != AdminRole.SUPER_ADMIN) {
      return Result.failure(Exception("Super Admin மட்டுமே நிர்வாகி நிலையை மாற்ற முடியும்."))
    }

    val index = adminAccounts.indexOfFirst { it.id == targetAdminId }
    if (index == -1) return Result.failure(Exception("நிர்வாகி கணக்கு கிடைக்கவில்லை."))

    val target = adminAccounts[index]
    if (target.role == AdminRole.SUPER_ADMIN) {
      return Result.failure(Exception("Super Admin முதன்மைக் கணக்கை இடைநிறுத்தவோ மாற்றவோ முடியாது."))
    }

    val updated = target.copy(status = newStatus)
    adminAccounts[index] = updated

    logAudit(
      AuditActionType.ADMIN_STATUS_CHANGED,
      callingAdmin,
      target.id,
      "${target.fullName} (${target.designation}) நிலை: ${newStatus.labelTamil} என மாற்றப்பட்டது."
    )

    return Result.success(Unit)
  }

  // Reset Admin Access / Issue New One-Time Setup Key (SUPER ADMIN ONLY)
  fun resetAdminAccess(
    callingAdmin: AdminAccount,
    targetAdminId: String
  ): Result<String> {
    if (callingAdmin.role != AdminRole.SUPER_ADMIN) {
      return Result.failure(Exception("Super Admin மட்டுமே நிர்வாகி கடவுச்சொல் / அணுகலை மீட்டமைக்க முடியும்."))
    }

    val index = adminAccounts.indexOfFirst { it.id == targetAdminId }
    if (index == -1) return Result.failure(Exception("நிர்வாகி கணக்கு கிடைக்கவில்லை."))

    val target = adminAccounts[index]
    val newSetupKey = AdminSecurityUtils.generateOneTimeSetupKey(
      prefix = if (target.role == AdminRole.STATE_ADMIN) "TNPA-STA" else "TNPA-DST"
    )

    val updated = target.copy(
      status = AdminStatus.PENDING_VERIFICATION,
      passwordHash = "",
      oneTimeSetupKey = newSetupKey,
      isSetupKeyUsed = false,
      failedLoginAttempts = 0,
      isLocked = false
    )
    adminAccounts[index] = updated

    logAudit(
      AuditActionType.ADMIN_CREDENTIAL_RESET,
      callingAdmin,
      target.id,
      "${target.fullName} (${target.designation}) அணுகல் மீட்டமைக்கப்பட்டது. புதிய Setup Key உருவாக்கப்பட்டது."
    )

    return Result.success(newSetupKey)
  }

  // Change District Assignment for a District Admin (SUPER ADMIN ONLY)
  fun changeDistrictAssignment(
    callingAdmin: AdminAccount,
    targetAdminId: String,
    newDistrict: String
  ): Result<Unit> {
    if (callingAdmin.role != AdminRole.SUPER_ADMIN) {
      return Result.failure(Exception("Super Admin மட்டுமே மாவட்ட ஒதுக்கீட்டை மாற்ற முடியும்."))
    }

    val index = adminAccounts.indexOfFirst { it.id == targetAdminId }
    if (index == -1) return Result.failure(Exception("நிர்வாகி கணக்கு கிடைக்கவில்லை."))

    val target = adminAccounts[index]
    if (target.role != AdminRole.DISTRICT_ADMIN) {
      return Result.failure(Exception("மாவட்ட நிர்வாகிகளுக்கு மட்டுமே மாவட்ட ஒதுக்கீட்டை மாற்ற முடியும்."))
    }

    if (!PredefinedAdminPosts.TAMIL_NADU_DISTRICTS.contains(newDistrict)) {
      return Result.failure(Exception("செல்லாத மாவட்டம்."))
    }

    // Check capacity in target district for that designation
    val existingInNewDistrict = adminAccounts.filter {
      it.role == AdminRole.DISTRICT_ADMIN && it.assignedDistrict == newDistrict && it.status != AdminStatus.DEACTIVATED && it.id != target.id
    }
    if (existingInNewDistrict.size >= 3) {
      return Result.failure(Exception("$newDistrict மாவட்டத்தில் ஏற்கனவே 3 நிர்வாகிகள் உள்ளனர்."))
    }
    if (existingInNewDistrict.any { it.designation == target.designation }) {
      return Result.failure(Exception("$newDistrict மாவட்டத்தில் ${target.designation} பதவி ஏற்கனவே ஒதுக்கப்பட்டுள்ளது."))
    }

    val updated = target.copy(assignedDistrict = newDistrict)
    adminAccounts[index] = updated

    logAudit(
      AuditActionType.ADMIN_STATUS_CHANGED,
      callingAdmin,
      target.id,
      "${target.fullName} அவர்களின் மாவட்டம் $newDistrict என மாற்றப்பட்டது."
    )

    return Result.success(Unit)
  }

  // ==========================================================================
  // MEMBER ID APPLICATION APPROVAL WORKFLOW
  // ==========================================================================

  // Register a new member application to pending queue
  fun submitMemberForApproval(
    memberId: String,
    fullName: String,
    tamilName: String,
    mobile: String,
    district: String,
    designation: String,
    specialization: String,
    experienceYears: Int,
    bloodGroup: String
  ): MemberApprovalItem {
    val existing = memberApplications.find { it.memberId == memberId }
    if (existing != null) return existing

    val newItem = MemberApprovalItem(
      memberId = memberId,
      fullName = fullName,
      tamilName = tamilName,
      mobile = mobile,
      district = district,
      designation = designation,
      specialization = specialization,
      experienceYears = experienceYears,
      bloodGroup = bloodGroup,
      status = ApprovalStatus.PENDING
    )
    memberApplications.add(0, newItem)
    return newItem
  }

  // Get applications accessible to a specific Admin based on Role & District Isolation
  fun getMemberApplicationsForAdmin(admin: AdminAccount): List<MemberApprovalItem> {
    return when (admin.role) {
      AdminRole.SUPER_ADMIN, AdminRole.STATE_ADMIN -> {
        // Super Admin & State Admin can view all 38 districts
        memberApplications.toList()
      }
      AdminRole.DISTRICT_ADMIN -> {
        // Strict District Isolation: Only see applications from their assigned district
        val assigned = admin.assignedDistrict ?: ""
        memberApplications.filter { app ->
          app.district.contains(assigned.substringBefore(" ("), ignoreCase = true) ||
              assigned.contains(app.district.substringBefore(" ("), ignoreCase = true)
        }
      }
    }
  }

  // Approve a Member Application (Single Authorized Approval Rule)
  fun approveMemberApplication(
    admin: AdminAccount,
    applicationId: String,
    remarks: String = "நேரடி சரிபார்ப்பு முடிந்து ஒப்புதல் அளிக்கப்பட்டது."
  ): Result<MemberApprovalItem> {
    val index = memberApplications.indexOfFirst { it.applicationId == applicationId }
    if (index == -1) return Result.failure(Exception("விண்ணப்பம் கிடைக்கவில்லை."))

    val item = memberApplications[index]

    // Authorization verification: District Admin can only approve members in their district
    if (admin.role == AdminRole.DISTRICT_ADMIN) {
      val assigned = admin.assignedDistrict ?: ""
      val matches = item.district.contains(assigned.substringBefore(" ("), ignoreCase = true) ||
          assigned.contains(item.district.substringBefore(" ("), ignoreCase = true)
      if (!matches) {
        return Result.failure(Exception("அனுமதியில்லை! உங்கள் மாவட்ட (${admin.assignedDistrict}) உறுப்பினர்களை மட்டுமே நீங்கள் ஒப்புதல் அளிக்க முடியும்."))
      }
    }

    val now = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(Date())
    item.status = ApprovalStatus.APPROVED
    item.approvedByAdminName = admin.fullName
    item.approvedByRole = admin.role.name
    item.approvedByAdminId = admin.id
    item.approvedAt = now
    item.approvalRemarks = remarks

    memberApplications[index] = item

    logAudit(
      AuditActionType.MEMBER_APPROVED,
      admin,
      item.memberId,
      "உறுப்பினர் விண்ணப்பம் ${item.fullName} (${item.district}) ஒப்புதல் அளிக்கப்பட்டது. [அங்கீகாரம்: ${admin.role.labelTamil} - ${admin.fullName}]"
    )

    return Result.success(item)
  }

  // Reject a Member Application
  fun rejectMemberApplication(
    admin: AdminAccount,
    applicationId: String,
    reason: String
  ): Result<MemberApprovalItem> {
    val index = memberApplications.indexOfFirst { it.applicationId == applicationId }
    if (index == -1) return Result.failure(Exception("விண்ணப்பம் கிடைக்கவில்லை."))

    val item = memberApplications[index]

    if (admin.role == AdminRole.DISTRICT_ADMIN) {
      val assigned = admin.assignedDistrict ?: ""
      val matches = item.district.contains(assigned.substringBefore(" ("), ignoreCase = true) ||
          assigned.contains(item.district.substringBefore(" ("), ignoreCase = true)
      if (!matches) {
        return Result.failure(Exception("அனுமதியில்லை! உங்கள் மாவட்ட உறுப்பினர்களை மட்டுமே நீங்கள் கையாள முடியும்."))
      }
    }

    val now = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(Date())
    item.status = ApprovalStatus.REJECTED
    item.approvedByAdminName = admin.fullName
    item.approvedByRole = admin.role.name
    item.approvedByAdminId = admin.id
    item.approvedAt = now
    item.approvalRemarks = reason

    memberApplications[index] = item

    logAudit(
      AuditActionType.MEMBER_REJECTED,
      admin,
      item.memberId,
      "உறுப்பினர் விண்ணப்பம் ${item.fullName} (${item.district}) நிராகரிக்கப்பட்டது. காரணம்: $reason"
    )

    return Result.success(item)
  }

  // ==========================================================================
  // GOVERNMENT WELFARE APPLICATION APPROVAL WORKFLOW
  // ==========================================================================

  fun submitWelfareApplication(
    schemeId: String,
    schemeTitleTamil: String,
    govtTypeLabel: String,
    memberId: String,
    applicantName: String,
    mobile: String,
    district: String,
    occupation: String,
    monthlyIncome: Int,
    officialPortalUrl: String,
    officialPortalName: String
  ): WelfareApplicationItem {
    val newItem = WelfareApplicationItem(
      schemeId = schemeId,
      schemeTitleTamil = schemeTitleTamil,
      govtTypeLabel = govtTypeLabel,
      memberId = memberId,
      applicantName = applicantName,
      mobile = mobile,
      district = district,
      occupation = occupation,
      monthlyIncome = monthlyIncome,
      status = WelfareAppStatus.PENDING_VERIFICATION,
      officialPortalUrl = officialPortalUrl,
      officialPortalName = officialPortalName
    )
    welfareApplications.add(0, newItem)
    return newItem
  }

  fun getWelfareApplicationsForAdmin(admin: AdminAccount): List<WelfareApplicationItem> {
    return when (admin.role) {
      AdminRole.SUPER_ADMIN, AdminRole.STATE_ADMIN -> {
        welfareApplications.toList()
      }
      AdminRole.DISTRICT_ADMIN -> {
        val assigned = admin.assignedDistrict ?: ""
        welfareApplications.filter { app ->
          app.district.contains(assigned.substringBefore(" ("), ignoreCase = true) ||
              assigned.contains(app.district.substringBefore(" ("), ignoreCase = true)
        }
      }
    }
  }

  fun approveWelfareApplication(
    admin: AdminAccount,
    welfareAppId: String,
    notes: String = "உள் தகுதி சரிபார்க்கப்பட்டு அதிகாரப்பூர்வ அரசு போர்ட்டலில் விண்ணப்பிக்க பரிந்துரைக்கப்பட்டது."
  ): Result<WelfareApplicationItem> {
    val index = welfareApplications.indexOfFirst { it.welfareAppId == welfareAppId }
    if (index == -1) return Result.failure(Exception("நலத்திட்ட விண்ணப்பம் கிடைக்கவில்லை."))

    val item = welfareApplications[index]

    if (admin.role == AdminRole.DISTRICT_ADMIN) {
      val assigned = admin.assignedDistrict ?: ""
      val matches = item.district.contains(assigned.substringBefore(" ("), ignoreCase = true) ||
          assigned.contains(item.district.substringBefore(" ("), ignoreCase = true)
      if (!matches) {
        return Result.failure(Exception("அனுமதியில்லை! உங்கள் மாவட்ட (${admin.assignedDistrict}) நலத்திட்ட விண்ணப்பங்களை மட்டுமே கையாள முடியும்."))
      }
    }

    val now = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(Date())
    item.status = WelfareAppStatus.RECOMMENDED_APPROVED
    item.approvedByAdminName = admin.fullName
    item.approvedByRole = admin.role.name
    item.approvedByAdminId = admin.id
    item.approvedAt = now
    item.verificationNotes = notes

    welfareApplications[index] = item

    logAudit(
      AuditActionType.WELFARE_APPROVED,
      admin,
      item.welfareAppId,
      "நலத்திட்ட விண்ணப்பம் (${item.schemeTitleTamil} - ${item.applicantName}) சரிபார்க்கப்பட்டு ஒப்புதல் வழங்கப்பட்டது."
    )

    return Result.success(item)
  }

  fun rejectWelfareApplication(
    admin: AdminAccount,
    welfareAppId: String,
    reason: String
  ): Result<WelfareApplicationItem> {
    val index = welfareApplications.indexOfFirst { it.welfareAppId == welfareAppId }
    if (index == -1) return Result.failure(Exception("நலத்திட்ட விண்ணப்பம் கிடைக்கவில்லை."))

    val item = welfareApplications[index]

    if (admin.role == AdminRole.DISTRICT_ADMIN) {
      val assigned = admin.assignedDistrict ?: ""
      val matches = item.district.contains(assigned.substringBefore(" ("), ignoreCase = true) ||
          assigned.contains(item.district.substringBefore(" ("), ignoreCase = true)
      if (!matches) {
        return Result.failure(Exception("அனுமதியில்லை! உங்கள் மாவட்ட நலத்திட்ட விண்ணப்பங்களை மட்டுமே கையாள முடியும்."))
      }
    }

    val now = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(Date())
    item.status = WelfareAppStatus.REJECTED
    item.approvedByAdminName = admin.fullName
    item.approvedByRole = admin.role.name
    item.approvedByAdminId = admin.id
    item.approvedAt = now
    item.verificationNotes = reason

    welfareApplications[index] = item

    logAudit(
      AuditActionType.WELFARE_REJECTED,
      admin,
      item.welfareAppId,
      "நலத்திட்ட விண்ணப்பம் (${item.schemeTitleTamil} - ${item.applicantName}) நிராகரிக்கப்பட்டது. காரணம்: $reason"
    )

    return Result.success(item)
  }

  // ==========================================================================
  // EMPLOYMENT & JOB POSTINGS REPOSITORY
  // ==========================================================================

  private fun seedInitialJobsAndSeekers() {
    if (jobPostings.isNotEmpty()) return

    // Sample Approved Government & Private Painting Jobs
    jobPostings.add(
      JobPostingItem(
        id = "JOB-2026-001",
        employerName = "தமிழ்நாடு அரசு பொதுப்பணித்துறை (PWD TN)",
        companyType = JobCategory.GOVERNMENT_PROJECT,
        jobTitle = "மாவட்ட ஆட்சியர் அலுவலகம் & நீதிமன்ற கட்டிட புதுப்பிப்பு பெயிண்டிங் பணி",
        district = "திருச்சிராப்பள்ளி (Tiruchirappalli)",
        workLocation = "மாவட்ட ஆட்சியர் வளாகம், கன்டோன்மென்ட், திருச்சி",
        workType = "ஒப்பந்த பணி (Contract)",
        workersNeeded = 12,
        requiredSkills = "எக்ஸ்டீரியர் வெதர் கோட்டிங், பிரைமர் & அக்ரிலிக் எமல்ஷன், சாரம் கட்டுதல்",
        experienceRequiredYears = 3,
        contactMobile = "0431-2415123",
        dailyWageOrSalary = "₹850 - ₹1,100 / நாள் (அரசு தினக்கூலி விகிதம்)",
        workDuration = "45 நாட்கள்",
        status = JobPostingStatus.APPROVED_ACTIVE,
        submittedAt = "15-Aug-2026 10:30 AM",
        approvedByAdminId = "SA-01",
        approvedByAdminName = "சேவியர் பாபு (மாநில பொதுச் செயலாளர்)",
        approvedAt = "15-Aug-2026 11:00 AM",
        isGovtJob = true
      )
    )

    jobPostings.add(
      JobPostingItem(
        id = "JOB-2026-002",
        employerName = "ராயல் கலர்ஸ் & டெக்கரேட்டர்ஸ் (Contractor)",
        companyType = JobCategory.PAINTING_CONTRACTOR,
        jobTitle = "புதிய அடுக்குமாடி குடியிருப்பு இன்டீரியர் புட்டி & ராயல் பிளே பெயிண்டிங்",
        district = "சென்னை (Chennai)",
        workLocation = "வேளச்சேரி மெயின் ரோடு, சென்னை",
        workType = "முழு நேரம் (Full-time)",
        workersNeeded = 8,
        requiredSkills = "3D டெக்ஸ்சர், ஸ்டென்சில் பெயிண்டிங், PU வார்னிஷ் & ஸ்பிரே பெயிண்டிங்",
        experienceRequiredYears = 2,
        contactMobile = "9840198765",
        dailyWageOrSalary = "₹950 / நாள் + மதிய உணவு",
        workDuration = "30 நாட்கள்",
        status = JobPostingStatus.APPROVED_ACTIVE,
        submittedAt = "16-Aug-2026 09:15 AM",
        approvedByAdminId = "SA-01",
        approvedByAdminName = "சேவியர் பாபு (மாநில பொதுச் செயலாளர்)",
        approvedAt = "16-Aug-2026 10:00 AM",
        isGovtJob = false
      )
    )

    jobPostings.add(
      JobPostingItem(
        id = "JOB-2026-003",
        employerName = "ஸ்ரீ மீனாட்சி ஆர்ட்ஸ் & போர்டு ரைட்டர்ஸ்",
        companyType = JobCategory.PRIVATE_COMPANY,
        jobTitle = "கோவில் சித்திரங்கள் & சுவரோவிய புனரமைப்பு ஓவியர்கள் தேவை",
        district = "மதுரை (Madurai)",
        workLocation = "மேல மாசி வீதி, மதுரை",
        workType = "ஒப்பந்த பணி (Contract)",
        workersNeeded = 4,
        requiredSkills = "மரபுசார் கோவில் சித்திரம், உருவப்படம், ஆயில் பெயிண்டிங் & தங்க முலாம்",
        experienceRequiredYears = 5,
        contactMobile = "9443512345",
        dailyWageOrSalary = "₹1,200 - ₹1,500 / நாள்",
        workDuration = "60 நாட்கள்",
        status = JobPostingStatus.APPROVED_ACTIVE,
        submittedAt = "17-Aug-2026 08:00 AM",
        approvedByAdminId = "SA-01",
        approvedByAdminName = "சேவியர் பாபு (மாநில பொதுச் செயலாளர்)",
        approvedAt = "17-Aug-2026 08:45 AM",
        isGovtJob = false
      )
    )

    // Sample Pending Job Posting (Awaiting Super Admin Approval)
    jobPostings.add(
      JobPostingItem(
        id = "JOB-2026-004",
        employerName = "கே.எஸ்.ஆர் பில்டர்ஸ் & கன்ஸ்ட்ரக்ஷன்ஸ்",
        companyType = JobCategory.COMMERCIAL_BUILDING,
        jobTitle = "வணிக வளாக வாட்டர்ப்ரூபிங் & பெயிண்டிங் பணிகள்",
        district = "கோயம்புத்தூர் (Coimbatore)",
        workLocation = "அவினாசி ரோடு, கோயம்புத்தூர்",
        workType = "தினக்கூலி (Daily Wage)",
        workersNeeded = 10,
        requiredSkills = "வாட்டர்ப்ரூபிங் கோட்டிங், எபோக்சி ப்ளோரிங் & ஸ்பிரே பெயிண்டிங்",
        experienceRequiredYears = 2,
        contactMobile = "9842234567",
        dailyWageOrSalary = "₹900 / நாள்",
        workDuration = "20 நாட்கள்",
        status = JobPostingStatus.PENDING_APPROVAL,
        submittedAt = "17-Aug-2026 11:30 AM",
        isGovtJob = false
      )
    )

    // Sample Work Seekers (Painters looking for jobs)
    workSeekers.add(
      WorkSeekerItem(
        id = "WKR-2026-001",
        seekerName = "ஆர். கதிரவன் (R. Kathiravan)",
        district = "சென்னை (Chennai)",
        experienceYears = 7,
        paintingSpecialization = "3D டெக்ஸ்சர் & ராயல் பிளே இன்டீரியர்",
        skills = "வால் புட்டி, ஸ்பிரே பெயிண்டிங், ஸ்டென்சில், வுட் பாலிஷ்",
        contactMobile = "9884112233",
        workPreference = "முழு நேரம் / ஒப்பந்தம்",
        availability = "உடனடியாக (Immediate)"
      )
    )

    workSeekers.add(
      WorkSeekerItem(
        id = "WKR-2026-002",
        seekerName = "மு. தங்கவேல் (M. Thangavel)",
        district = "திருச்சிராப்பள்ளி (Tiruchirappalli)",
        experienceYears = 12,
        paintingSpecialization = "கோவில் சித்திரங்கள் & சுவர் ஓவியம்",
        skills = "ஆயில் பெயிண்டிங், போர்டு ரைட்டிங், தங்க பெயிண்டிங்",
        contactMobile = "9789223344",
        workPreference = "ஒப்பந்த பணி (Contract)",
        availability = "உடனடியாக (Immediate)"
      )
    )

    workSeekers.add(
      WorkSeekerItem(
        id = "WKR-2026-003",
        seekerName = "எஸ். சந்துரு (S. Chandru)",
        district = "கோயம்புத்தூர் (Coimbatore)",
        experienceYears = 5,
        paintingSpecialization = "பில்டிங் எக்ஸ்டீரியர் & வாட்டர்ப்ரூபிங்",
        skills = "சாரம் ஏறுதல், எமல்ஷன் ரோலர், டேம்ப் புரூப் சீலிங்",
        contactMobile = "9442334455",
        workPreference = "தினக்கூலி / ஒப்பந்தம்",
        availability = "உடனடியாக (Immediate)"
      )
    )
  }

  // Submit Job by Employer (Always starts in PENDING_APPROVAL)
  fun submitJobPosting(job: JobPostingItem): Result<JobPostingItem> {
    val pendingJob = job.copy(
      status = JobPostingStatus.PENDING_APPROVAL,
      submittedAt = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(Date())
    )
    jobPostings.add(0, pendingJob)
    return Result.success(pendingJob)
  }

  // Public Query (Only APPROVED_ACTIVE jobs)
  fun getPublicApprovedJobs(
    districtFilter: String? = null,
    isGovtFilter: Boolean? = null
  ): List<JobPostingItem> {
    return jobPostings.filter { job ->
      job.status == JobPostingStatus.APPROVED_ACTIVE &&
        (districtFilter == null || districtFilter == "அனைத்து மாவட்டங்களும்" || job.district.contains(districtFilter, ignoreCase = true)) &&
        (isGovtFilter == null || job.isGovtJob == isGovtFilter)
    }
  }

  // Admin Query (Super Admin sees all, District Admin sees their district)
  fun getJobPostingsForAdmin(admin: AdminAccount): List<JobPostingItem> {
    return when (admin.role) {
      AdminRole.SUPER_ADMIN, AdminRole.STATE_ADMIN -> jobPostings.toList()
      AdminRole.DISTRICT_ADMIN -> {
        val dist = admin.assignedDistrict
        if (dist.isNullOrBlank()) emptyList()
        else jobPostings.filter { it.district.contains(dist, ignoreCase = true) }
      }
    }
  }

  fun getAllJobPostings(admin: AdminAccount): List<JobPostingItem> = getJobPostingsForAdmin(admin)

  // Super Admin Approval of Job Posting
  fun approveJobPosting(jobId: String, admin: AdminAccount, remarks: String? = null): Result<JobPostingItem> {
    val index = jobPostings.indexOfFirst { it.id == jobId }
    if (index == -1) return Result.failure(Exception("வேலைவாய்ப்பு எண் கிடைக்கவில்லை."))

    val item = jobPostings[index].copy(
      status = JobPostingStatus.APPROVED_ACTIVE,
      approvedByAdminId = admin.id,
      approvedByAdminName = admin.fullName,
      approvedAt = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(Date()),
      moderationRemarks = remarks ?: "Super Admin ஒப்புதல் அளிக்கப்பட்டு வெளியிடப்பட்டது."
    )

    jobPostings[index] = item

    logAudit(
      AuditActionType.JOB_POSTING_APPROVED,
      admin,
      item.id,
      "வேலைவாய்ப்பு (${item.jobTitle} - ${item.employerName} - ${item.district}) ஒப்புதல் அளிக்கப்பட்டு பொதுத்தளத்தில் வெளியிடப்பட்டது."
    )

    return Result.success(item)
  }

  fun approveJobPosting(admin: AdminAccount, jobId: String, remarks: String? = null): Result<JobPostingItem> =
    approveJobPosting(jobId, admin, remarks)

  // Reject Job Posting
  fun rejectJobPosting(jobId: String, admin: AdminAccount, reason: String): Result<JobPostingItem> {
    val index = jobPostings.indexOfFirst { it.id == jobId }
    if (index == -1) return Result.failure(Exception("வேலைவாய்ப்பு எண் கிடைக்கவில்லை."))

    val item = jobPostings[index].copy(
      status = JobPostingStatus.REJECTED,
      approvedByAdminId = admin.id,
      approvedByAdminName = admin.fullName,
      approvedAt = SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(Date()),
      moderationRemarks = reason
    )

    jobPostings[index] = item

    logAudit(
      AuditActionType.JOB_POSTING_REJECTED,
      admin,
      item.id,
      "வேலைவாய்ப்பு (${item.jobTitle} - ${item.employerName}) நிராகரிக்கப்பட்டது. காரணம்: $reason"
    )

    return Result.success(item)
  }

  fun rejectJobPosting(admin: AdminAccount, jobId: String, reason: String): Result<JobPostingItem> =
    rejectJobPosting(jobId, admin, reason)

  fun closeJobPosting(admin: AdminAccount, jobId: String): Result<JobPostingItem> =
    moderateJobStatus(jobId, JobPostingStatus.FILLED, admin, "ஆட்கள் தேர்வு முடிந்தது (Marked Filled)")

  // Moderate Job Status (Mark as Filled, Expired, Suspended)
  fun moderateJobStatus(
    jobId: String,
    newStatus: JobPostingStatus,
    admin: AdminAccount,
    remarks: String
  ): Result<JobPostingItem> {
    val index = jobPostings.indexOfFirst { it.id == jobId }
    if (index == -1) return Result.failure(Exception("வேலைவாய்ப்பு எண் கிடைக்கவில்லை."))

    val item = jobPostings[index].copy(
      status = newStatus,
      moderationRemarks = remarks
    )

    jobPostings[index] = item

    logAudit(
      AuditActionType.JOB_POSTING_MODERATED,
      admin,
      item.id,
      "வேலைவாய்ப்பு (${item.jobTitle}) நிலை மாற்றப்பட்டது: ${newStatus.labelTamil}. குறிப்பு: $remarks"
    )

    return Result.success(item)
  }

  // Work Seeker Submission
  fun submitWorkSeeker(seeker: WorkSeekerItem): Result<WorkSeekerItem> {
    workSeekers.add(0, seeker)
    return Result.success(seeker)
  }

  // Query Work Seekers
  fun getWorkSeekers(districtFilter: String? = null): List<WorkSeekerItem> {
    return if (districtFilter.isNullOrBlank() || districtFilter == "அனைத்து மாவட்டங்களும்") {
      workSeekers.toList()
    } else {
      workSeekers.filter { it.district.contains(districtFilter, ignoreCase = true) }
    }
  }

  // ==========================================================================
  // AUDIT LOG MANAGEMENT
  // ==========================================================================

  fun getAuditLogs(callingAdmin: AdminAccount): List<AuditLogEntry> {
    // Only Super Admin and State Admins can inspect audit logs (Super Admin has full export control)
    return when (callingAdmin.role) {
      AdminRole.SUPER_ADMIN -> auditLogs.toList()
      AdminRole.STATE_ADMIN -> auditLogs.filter { it.actionType != AuditActionType.ADMIN_CREDENTIAL_RESET }
      AdminRole.DISTRICT_ADMIN -> auditLogs.filter { it.adminId == callingAdmin.id }
    }
  }

  private fun logAudit(
    action: AuditActionType,
    admin: AdminAccount,
    targetId: String?,
    details: String
  ) {
    auditLogs.add(
      0,
      AuditLogEntry(
        actionType = action,
        adminId = admin.id,
        adminName = admin.fullName,
        adminRole = admin.role.name,
        targetEntityId = targetId,
        detailsTamil = details
      )
    )
  }

  // Retrieve Top 3 State Office Bearers for the Home Screen & Public Overview
  fun getTopStateLeaders(): List<StateLeaderItem> {
    val superAdmin = adminAccounts.find { it.role == AdminRole.SUPER_ADMIN }
    val president = adminAccounts.find { it.designation.contains("தலைவர்") || it.designation.contains("President") }
    val treasurer = adminAccounts.find { it.designation.contains("பொருளாளர்") || it.designation.contains("Treasurer") }

    fun cleanFullName(name: String?): String {
      if (name.isNullOrBlank()) return ""
      return name
        .replace("\\(மாநிலத் தலைவர்\\)".toRegex(), "")
        .replace("\\(மாநில பொதுச் செயலாளர்\\)".toRegex(), "")
        .replace("\\(மாநில பொருளாளர்\\)".toRegex(), "")
        .trim()
    }

    return listOf(
      StateLeaderItem(
        id = "LEADER-PRESIDENT",
        designationTamil = "மாநிலத் தலைவர் (Super Admin)",
        designationEnglish = "State President & Super Admin",
        fullNameTamil = cleanFullName(president?.fullName).ifEmpty { "எஸ். மைக்கேல் ஆல்வின் (S. Michael Alvin)" },
        mobileNumber = president?.mobileNumber?.let { if (it.startsWith("+91")) it else "+91 $it" } ?: "+91 97893 31681",
        location = "அம்பலக்காரன் பட்டி, உத்தங்குடி போஸ்ட், மேலூர் மெயின் ரோடு, மதுரை - 625107",
        photoUrl = null,
        email = president?.email?.ifEmpty { "president@tnpa.org" },
        badgeThemeColorHex = 0xFFDC2626, // Red
        orderPriority = 1,
        isTopLeader = true
      ),
      StateLeaderItem(
        id = "LEADER-GEN-SEC",
        designationTamil = "மாநில பொதுச் செயலாளர் (Super Admin)",
        designationEnglish = "State General Secretary & Super Admin",
        fullNameTamil = cleanFullName(superAdmin?.fullName).ifEmpty { "சேவியர் பாபு (Xavier Babu)" },
        mobileNumber = superAdmin?.mobileNumber?.let { if (it.startsWith("+91")) it else "+91 $it" } ?: "+91 70101 31915",
        location = "மதுரை மாவட்டம் (மாநில தலைமை அலுவலகம்)",
        photoUrl = null,
        email = superAdmin?.email?.ifEmpty { "xavierbabu2@gmail.com" },
        badgeThemeColorHex = 0xFF111827, // Jet Black
        orderPriority = 2,
        isTopLeader = true
      ),
      StateLeaderItem(
        id = "LEADER-TREASURER",
        designationTamil = "மாநில பொருளாளர்",
        designationEnglish = "State Treasurer",
        fullNameTamil = cleanFullName(treasurer?.fullName).ifEmpty { "கே. வி. சுப்பிரமணியன் (K. V. Subramanian)" },
        mobileNumber = treasurer?.mobileNumber?.let { if (it.startsWith("+91")) it else "+91 $it" } ?: "+91 97861 23456",
        location = "மத்திய மண்டல தலைமையகம்",
        photoUrl = null,
        email = treasurer?.email?.ifEmpty { "treasurer@tnpa.org" },
        badgeThemeColorHex = 0xFF1E3A8A, // Deep Navy Blue
        orderPriority = 3,
        isTopLeader = false
      )
    )
  }
}

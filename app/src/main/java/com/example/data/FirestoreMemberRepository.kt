package com.example.data

import android.util.Log
import com.example.model.MemberProfile
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreMemberRepository {
  private val firestore: FirebaseFirestore by lazy {
    FirebaseFirestore.getInstance()
  }

  suspend fun saveMember(member: MemberProfile): Result<String> {
    return try {
      val data = hashMapOf(
        "id" to member.id,
        "fullName" to member.fullName,
        "tamilName" to member.tamilName,
        "age" to member.age,
        "experienceYears" to member.experienceYears,
        "mobile" to member.mobile,
        "email" to member.email,
        "whatsapp" to member.whatsapp,
        "district" to member.district,
        "address" to member.address,
        "specialization" to member.specialization,
        "designation" to member.designation,
        "bloodGroup" to member.bloodGroup,
        "joinedDate" to member.joinedDate,
        "status" to member.status,
        "photoUri" to (member.photoUri ?: ""),
        "createdAt" to System.currentTimeMillis(),
        "isApprovedBySuperAdmin" to true,
        "approvedBy" to "Super Admins (Michael Alvin & Xavier Babu)"
      )
      val docRef = firestore.collection("tnpa_members").document(member.id)
      docRef.set(data).await()
      Log.d("FirestoreMemberRepo", "Successfully saved member ${member.id} to Firestore")
      Result.success(docRef.id)
    } catch (e: Exception) {
      Log.e("FirestoreMemberRepo", "Failed to save member to Firestore: ${e.message}", e)
      Result.failure(e)
    }
  }

  // Real-time updates Flow: triggers whenever ANY member registers anywhere across Tamil Nadu
  fun observeMembersRealtime(): Flow<List<MemberProfile>> = callbackFlow {
    var registration: ListenerRegistration? = null
    try {
      registration = firestore.collection("tnpa_members")
        .orderBy("createdAt", Query.Direction.DESCENDING)
        .addSnapshotListener { snapshot, error ->
          if (error != null) {
            Log.w("FirestoreMemberRepo", "Firestore snapshot listen error: ${error.message}")
            return@addSnapshotListener
          }
          if (snapshot != null) {
            val members = snapshot.documents.mapNotNull { doc ->
              val id = doc.getString("id") ?: doc.id
              val fullName = doc.getString("fullName") ?: return@mapNotNull null
              MemberProfile(
                id = id,
                fullName = fullName,
                tamilName = doc.getString("tamilName") ?: fullName,
                age = doc.getLong("age")?.toInt() ?: 30,
                experienceYears = doc.getLong("experienceYears")?.toInt() ?: 5,
                mobile = doc.getString("mobile") ?: "",
                email = doc.getString("email") ?: "",
                whatsapp = doc.getString("whatsapp") ?: "",
                district = doc.getString("district") ?: "சென்னை (Chennai)",
                address = doc.getString("address") ?: "",
                specialization = doc.getString("specialization") ?: "சுவர் ஓவியம் / பில்டிங் பெயிண்டிங் (Wall & Building)",
                designation = doc.getString("designation") ?: "உறுப்பினர் (Member)",
                bloodGroup = doc.getString("bloodGroup") ?: "O+",
                joinedDate = doc.getString("joinedDate") ?: "17-Aug-2026",
                status = doc.getString("status") ?: "செயலில் உள்ளது (Active)",
                isSyncedToFirestore = true,
                photoUri = doc.getString("photoUri")?.takeIf { it.isNotBlank() }
              )
            }
            trySend(members)
          }
        }
    } catch (e: Exception) {
      Log.e("FirestoreMemberRepo", "Error starting realtime observer", e)
    }
    awaitClose {
      registration?.remove()
    }
  }

  suspend fun fetchMembers(): List<MemberProfile> {
    return try {
      val snapshot = firestore.collection("tnpa_members")
        .orderBy("createdAt", Query.Direction.DESCENDING)
        .get()
        .await()
      snapshot.documents.mapNotNull { doc ->
        val id = doc.getString("id") ?: doc.id
        val fullName = doc.getString("fullName") ?: return@mapNotNull null
        MemberProfile(
          id = id,
          fullName = fullName,
          tamilName = doc.getString("tamilName") ?: fullName,
          age = doc.getLong("age")?.toInt() ?: 30,
          experienceYears = doc.getLong("experienceYears")?.toInt() ?: 5,
          mobile = doc.getString("mobile") ?: "",
          email = doc.getString("email") ?: "",
          whatsapp = doc.getString("whatsapp") ?: "",
          district = doc.getString("district") ?: "சென்னை (Chennai)",
          address = doc.getString("address") ?: "",
          specialization = doc.getString("specialization") ?: "சுவர் ஓவியம் / பில்டிங் பெயிண்டிங் (Wall & Building)",
          designation = doc.getString("designation") ?: "உறுப்பினர் (Member)",
          bloodGroup = doc.getString("bloodGroup") ?: "O+",
          joinedDate = doc.getString("joinedDate") ?: "17-Aug-2026",
          status = doc.getString("status") ?: "செயலில் உள்ளது (Active)",
          isSyncedToFirestore = true,
          photoUri = doc.getString("photoUri")?.takeIf { it.isNotBlank() }
        )
      }
    } catch (e: Exception) {
      Log.w("FirestoreMemberRepo", "Firestore fetch error: ${e.message}")
      emptyList()
    }
  }
}


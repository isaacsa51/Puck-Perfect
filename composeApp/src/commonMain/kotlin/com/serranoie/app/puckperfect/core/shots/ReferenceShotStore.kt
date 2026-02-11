package com.serranoie.app.puckperfect.core.shots

import androidx.compose.runtime.mutableStateMapOf

/**
 * Simple in-memory store for a per-bean "God Shot" reference.
 *
 * Note: This is intentionally lightweight (no persistence). It can be swapped later
 * for a repository/DataStore-backed implementation.
 */
object ReferenceShotStore {
    data class ReferenceShot(
        val shotId: Int,
        val grams: Float,
        val timeSeconds: Int,
    )

    /**
     * Key: bean name (as displayed on the shot).
     */
    val referenceByBean = mutableStateMapOf<String, ReferenceShot>()
}

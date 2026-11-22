package com.example.focusscrollblocker

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast

class ScrollBlockerService : AccessibilityService() {
    private var lastBlockTime = 0L
    private val blockedViewIds = listOf(
        "com.instagram.android:id/clips_viewer_view_pager",
        "com.instagram.android:id/clips_video_container",
        "com.google.android.youtube:id/reel_recycler",
        "com.google.android.youtube:id/reel_watch_fragment_root"
    )

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return
        val prefs = getSharedPreferences("FocusPrefs", Context.MODE_PRIVATE)
        if (!prefs.getBoolean("focus_mode", false)) return
        val rootNode: AccessibilityNodeInfo = rootInActiveWindow ?: return
        for (viewId in blockedViewIds) {
            val nodes = rootNode.findAccessibilityNodeInfosByViewId(viewId)
            if (!nodes.isNullOrEmpty()) {
                val now = System.currentTimeMillis()
                if (now - lastBlockTime > 1200) {
                    lastBlockTime = now
                    performGlobalAction(GLOBAL_ACTION_BACK)
                    Toast.makeText(this, "Focus Mode: Scrolling blocked", Toast.LENGTH_SHORT).show()
                }
                break
            }
        }
    }
    override fun onInterrupt() {}
}

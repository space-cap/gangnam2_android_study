
package com.survivalcoding.gangnam2kiandroidstudy.provider

import android.net.Uri
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContentProviderTest {

    private val authority = "com.survivalcoding.gangnam2kiandroidstudy.provider"
    private val path = "bookmarks"
    private val contentUri = Uri.parse("content://$authority/$path")

    @Test
    fun queryBookmarks() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val cursor = context.contentResolver.query(contentUri, null, null, null, null)

        assert(cursor != null)
        if (cursor == null) {
            Log.e("ContentProviderTest", "Cursor is null!")
            return
        }

        Log.d("ContentProviderTest", "--- Query Result ---")
        Log.d("ContentProviderTest", "Total bookmarks: ${cursor.count}")

        val idColumnIndex = cursor.getColumnIndex("recipeId")
        if (idColumnIndex == -1) {
            Log.e("ContentProviderTest", "\'recipeId\' column not found!")
            return
        }

        while (cursor.moveToNext()) {
            val recipeId = cursor.getLong(idColumnIndex)
            Log.d("ContentProviderTest", "Found bookmarked recipeId: $recipeId")
        }
        Log.d("ContentProviderTest", "--------------------")

        cursor.close()
    }
}

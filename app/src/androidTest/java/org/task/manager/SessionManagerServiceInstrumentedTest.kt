package org.task.manager

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject
import org.task.manager.shared.service.SessionManagerService
import kotlin.random.Random

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SessionManagerServiceInstrumentedTest : KoinTest {

    private val sessionManagerService: SessionManagerService by inject()

    @Test
    fun saveValueSharedPreferences() {
        val value = Random.nextInt(1, 100).toString()

        sessionManagerService.saveAuthToken(value)

        val result = sessionManagerService.fetchAuthToken()

        assertEquals(value, result)
    }

    @Test
    fun removeValueSharedPreferences() {
        sessionManagerService.removeAuthToken()

        val result = sessionManagerService.fetchAuthToken()

        assertEquals(result, null)
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("org.task.manager", appContext.packageName)
    }
}
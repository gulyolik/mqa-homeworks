package ru.netology.testing.uiautomator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

const val MODEL__PACKAGE = "ru.netology.testing.uiautomator"
const val TIME_OUT = 5000L

@RunWith(AndroidJUnit4::class)
class InputTextTest {

    private lateinit var device: UiDevice
    private val spaceToSet = "\u0020"
    private val textToSet = "This Text Must Be In Result"

    @Before
    fun beforeEachPressHomeWaitLauncher() {
        // Press home
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Wait for Launcher
        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIME_OUT)
    }
    @Test
    fun testInputEmptyText () {
        val packageName = MODEL__PACKAGE
        waitForPackage(packageName)

        val expected = device.findObject(By.res(packageName, "textToBeChanged")).text
        device.findObject(By.res(packageName, "userInput")).text = spaceToSet
        device.findObject(By.res(packageName, "buttonChange")).click()

        val actual = device.findObject(By.res(packageName, "textToBeChanged")).text
        assertEquals(actual, expected)
    }

    @Test
    fun testInputTextNewActivity() {
        val packageName = MODEL__PACKAGE
        waitForPackage(packageName)

        device.findObject(By.res(packageName, "userInput")).text = textToSet
        device.findObject(By.res(packageName, "buttonChange")).click()
        device.findObject(By.res(packageName, "buttonActivity")).click()
        device.wait(Until.hasObject(By.res(packageName, "text")), TIME_OUT)
        val actual = device.findObject(By.res(packageName, "text")).text

        assertEquals(actual, textToSet)

    }

    private fun waitForPackage (packageName: String) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(packageName)), TIME_OUT)
    }

}
import com.readdle.codegen.anotation.JavaSwift
import com.readdle.codegen.anotation.SwiftError
import com.readdle.codegen.anotation.SwiftRuntimeError
import com.readdle.swiftjava.sample.SampleDelegateAndroid
import com.readdle.swiftjava.sample.SampleReference
import com.readdle.swiftjava.sample.SampleReference.Companion.staticString
import com.readdle.swiftjava.sample.SampleReference.CompletionBlock
import com.readdle.swiftjava.sample.SampleReference.SampleInterfaceDelegateAndroid
import com.readdle.swiftjava.sample.SampleValue
import com.readdle.swiftjava.sample.SampleValue.Companion.randomValue
import com.readdle.swiftjava.sample.SwiftEnvironment
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.lang.ref.WeakReference
import java.util.*

class SampleReferenceTest {
    private var sampleReference: SampleReference? = null
    @Before
    fun setUp() {
        System.loadLibrary("SampleAppCore")
        JavaSwift.init()
        SwiftEnvironment.initEnvironment()
        sampleReference = SampleReference.init()
    }

    @After
    fun clear() {
        sampleReference!!.release()
    }

    @Test
    fun testGetRandomValue() {
        Assert.assertNotNull(sampleReference!!.randomValue)
    }

    @Test
    fun testSaveValue() {
        val sampleValue = randomValue
        sampleValue.string = UUID.randomUUID().toString()
        sampleReference!!.saveValue(sampleValue)
    }

    @Test
    fun testThrows() {
        try {
            sampleReference!!.funcThrows()
            Assert.fail()
        } catch (swiftError: SwiftError) {
            Assert.assertEquals("Error: 1", swiftError.message)
        }
    }

    @Test
    fun testNil() {
        Assert.assertNull(sampleReference!!.funcWithNil())
    }

    @Test
    fun testDelegate() {
        val isFlag = BooleanArray(1)
        val delegateAndroid: SampleDelegateAndroid = object : SampleDelegateAndroid() {
            override fun onSetSampleValue(value: SampleValue?) {
                isFlag[0] = true
            }
        }
        sampleReference!!.setDelegate(delegateAndroid)
        Assert.assertTrue(System.currentTimeMillis() - sampleReference!!.tick() < 1000)
        Assert.assertTrue(delegateAndroid.getSampleValue().string.isNotEmpty())
        Assert.assertTrue(isFlag[0])
        Assert.assertNull(sampleReference!!.funcWithNil())
        delegateAndroid.release()
    }

    @Test
    fun testLocalTableOverflow() {
        val isFlag = IntArray(1)
        //JavaSwift.dumpReferenceTables()
        sampleReference!!.tickWithBlock(object : SampleInterfaceDelegateAndroid {
            override fun onCall(pr1: Int, pr2: Int, pr3: Double, pr4: Double) {
                isFlag[0] = pr1
            }
        })
        //JavaSwift.dumpReferenceTables()
        Assert.assertTrue(isFlag[0] == 128)
    }

    @Test
    fun testLocalTableOverflow2() {
        val sampleValueList: MutableList<SampleValue> = ArrayList()
        //JavaSwift.dumpReferenceTables()
        for (i in 0..1023) {
            sampleValueList.add(sampleReference!!.randomValue)
        }
        Assert.assertTrue(sampleValueList.size == 1024)
        for (i in 0..1023) {
            sampleReference!!.saveValue(sampleValueList[i])
        }
        //JavaSwift.dumpReferenceTables()
        Assert.assertTrue(sampleValueList.size == 1024)
    }

    @Test
    fun testFloatingPointer() {
        Assert.assertTrue(sampleReference!!.floatCheck(1.0f) == 3.0f)
        Assert.assertTrue(sampleReference!!.doubleCheck(1.0) == 3.0)
    }

    @Test
    fun testException() {
        val exception1 = Exception("")
        val exception2 = Exception("QWERTY")
        val exception3 = Exception("QWERTY:1")
        Assert.assertEquals("java.lang.Exception:0", sampleReference!!.exceptionCheck(exception1).message)
        Assert.assertEquals("java.lang.Exception:0", sampleReference!!.exceptionCheck(exception2).message)
        Assert.assertEquals(sampleReference!!.exceptionCheck(exception3).message, exception3.message)
    }

    @Test
    fun testEnclose() {
        Assert.assertNotNull(sampleReference!!.enclose(SampleReference.SampleReferenceEnclose.init()))
    }

    @Test
    fun testGetterSetter() {
        var string = sampleReference!!.string
        sampleReference!!.string = string
        Assert.assertTrue(string == sampleReference!!.string)
        string = staticString
        staticString = string
        Assert.assertTrue(string == staticString)
    }

    @Test
    fun testBlock() {
        val result = sampleReference!!.funcWithBlock { error: Exception?, string: String? ->
            if (error == null) {
                return@funcWithBlock string
            } else {
                return@funcWithBlock null
            }
        }
        Assert.assertNotNull(result)
        Assert.assertTrue(result == "123")
    }

    @Test
    fun testAbstractTypes() {
        var child = sampleReference!!.abstractType
        Assert.assertEquals("FirstChild", child.basicMethod())
        child = sampleReference!!.firstChild
        Assert.assertEquals("FirstChild", child.basicMethod())
        child = sampleReference!!.secondChild
        Assert.assertEquals("SecondChild", child.basicMethod())
        child = sampleReference!!.thirdChild
        Assert.assertEquals("ThirdChild", child.basicMethod())
        child = sampleReference!!.fourthChild
        Assert.assertEquals("SecondChild", child.basicMethod())
    }

    @Test
    fun testThrowableFunc() {
        val sampleDelegateAndroid: SampleDelegateAndroid = object : SampleDelegateAndroid() {
            override fun onSetSampleValue(value: SampleValue?) {}
        }
        try {
            sampleReference!!.throwableFunc(sampleDelegateAndroid, true)
            Assert.fail()
        } catch (e: Exception) {
            Assert.assertEquals("java.lang.IllegalArgumentException: 0", e.message)
        }
        try {
            sampleReference!!.throwableFunc(sampleDelegateAndroid, false)
        } catch (e: Exception) {
            Assert.fail()
        }
        try {
            sampleReference!!.throwableFuncWithReturnType(sampleDelegateAndroid, true)
            Assert.fail()
        } catch (e: Exception) {
            Assert.assertEquals("java.lang.IllegalArgumentException: 0", e.message)
        }
        try {
            Assert.assertEquals(
                "throwableFuncWithReturnType",
                sampleReference!!.throwableFuncWithReturnType(sampleDelegateAndroid, false)
            )
        } catch (e: Exception) {
            Assert.fail()
        }
    }

    @Test
    fun testNullPointerAfterRelease() {
        val sampleReference = SampleReference.init()
        sampleReference.release()
        try {
            sampleReference.funcWithNil()
            Assert.fail()
        } catch (error: SwiftRuntimeError) {
            Assert.assertEquals(true, error.message?.contains("java.lang.NullPointerException"))
        }
    }

    // Run on Android 7.0 to fail
    @Test
    fun testLocalTableOverflow3() {
        sampleReference!!.oneMoreReferenceTableOverflow(object : SampleDelegateAndroid() {
            override fun onSetSampleValue(value: SampleValue?) {
                // empty
            }
        })
    }
}
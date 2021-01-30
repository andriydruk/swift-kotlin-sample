import com.readdle.codegen.anotation.JavaSwift
import com.readdle.swiftjava.sample.FloatTest
import com.readdle.swiftjava.sample.FloatTest.*
import com.readdle.swiftjava.sample.FloatTest.Companion.testBlock
import com.readdle.swiftjava.sample.FloatTest.Companion.testDecode
import com.readdle.swiftjava.sample.FloatTest.Companion.testInfinite
import com.readdle.swiftjava.sample.FloatTest.Companion.testNan
import com.readdle.swiftjava.sample.FloatTest.Companion.testOptionalBlock
import com.readdle.swiftjava.sample.FloatTest.Companion.testOptionalParam
import com.readdle.swiftjava.sample.FloatTest.Companion.testParam
import com.readdle.swiftjava.sample.FloatTest.Companion.testProtocolOptionalParam
import com.readdle.swiftjava.sample.FloatTest.Companion.testProtocolOptionalReturnType
import com.readdle.swiftjava.sample.FloatTest.Companion.testProtocolParam
import com.readdle.swiftjava.sample.FloatTest.Companion.testProtocolReturnType
import com.readdle.swiftjava.sample.FloatTestStruct
import com.readdle.swiftjava.sample.SwiftEnvironment
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FloatTests {
    @Before
    fun setUp() {
        System.loadLibrary("SampleAppCore")
        JavaSwift.init()
        SwiftEnvironment.initEnvironment()
    }

    @Test
    fun testZero() {
        Assert.assertEquals(FloatTest.testZero(), 0f, DELTA)
    }

    @Test
    fun testMin() {
        Assert.assertEquals(testNan(), Float.NaN, DELTA)
    }

    @Test
    fun testMax() {
        Assert.assertEquals(testInfinite(), Float.POSITIVE_INFINITY, DELTA)
    }

    @Test
    fun testParam() {
        Assert.assertTrue(testParam(Float.POSITIVE_INFINITY))
        Assert.assertFalse(testParam(Float.NaN))
    }

    @Test
    fun testReturnType() {
        Assert.assertEquals(FloatTest.testReturnType(), Float.POSITIVE_INFINITY, DELTA)
    }

    @Test
    fun testOptionalParam() {
        Assert.assertTrue(testOptionalParam(Float.POSITIVE_INFINITY))
        Assert.assertFalse(testOptionalParam(Float.NaN))
    }

    fun testOptionalReturnType() {
        val result = FloatTest.testOptionalReturnType()
        Assert.assertNotNull(result)
        Assert.assertEquals(result!!.toFloat(), Float.POSITIVE_INFINITY, DELTA)
    }

    @Test
    fun testProtocolParam() {
        val result = testProtocolParam(FloatParamProtocol { param: Float -> param == Float.POSITIVE_INFINITY })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolReturnType() {
        val result = testProtocolReturnType(FloatReturnTypeProtocol {
            print("WTF! 42 hey hey hey...")
            return@FloatReturnTypeProtocol 42f
        })
        print("WTF! hey hey hey... $result")
        Assert.assertEquals(42f, result, DELTA)
    }

    @Test
    fun testProtocolOptionalParam() {
        val result =
            testProtocolOptionalParam(FloatOptionalParamProtocol { param: Float? -> param != null && param == Float.POSITIVE_INFINITY })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolOptionalReturnType() {
        val result = testProtocolOptionalReturnType(FloatOptionalReturnTypeProtocol { 42f })
        Assert.assertNotNull(result)
        Assert.assertEquals(result!!.toFloat(), 42f, DELTA)
    }

    @Test
    fun testEncode() {
        val result = FloatTest.testEncode()
        Assert.assertEquals(result, FloatTestStruct())
    }

    @Test
    fun testDecode() {
        val goodParam = FloatTestStruct()
        val badParam = FloatTestStruct(42f, 42f, 42f, 42f, 42f)
        Assert.assertTrue(testDecode(goodParam))
        Assert.assertFalse(testDecode(badParam))
    }

    @Test
    fun testBlock() {
        Assert.assertTrue(testBlock(KotlinFloatBlock { value: Float -> value }))
    }

    @Test
    fun testOptionalBlock() {
        Assert.assertTrue(testOptionalBlock(KotlinOptionalFloatBlock { value: Float? -> value }))
    }

    companion object {
        private const val DELTA = 0.000001f
    }
}
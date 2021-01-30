import com.readdle.codegen.anotation.JavaSwift
import com.readdle.swiftjava.sample.DoubleTest
import com.readdle.swiftjava.sample.DoubleTest.*
import com.readdle.swiftjava.sample.DoubleTest.Companion.testBlock
import com.readdle.swiftjava.sample.DoubleTest.Companion.testDecode
import com.readdle.swiftjava.sample.DoubleTest.Companion.testInfinite
import com.readdle.swiftjava.sample.DoubleTest.Companion.testNan
import com.readdle.swiftjava.sample.DoubleTest.Companion.testOptionalBlock
import com.readdle.swiftjava.sample.DoubleTest.Companion.testOptionalParam
import com.readdle.swiftjava.sample.DoubleTest.Companion.testParam
import com.readdle.swiftjava.sample.DoubleTest.Companion.testProtocolOptionalParam
import com.readdle.swiftjava.sample.DoubleTest.Companion.testProtocolOptionalReturnType
import com.readdle.swiftjava.sample.DoubleTest.Companion.testProtocolParam
import com.readdle.swiftjava.sample.DoubleTest.Companion.testProtocolReturnType
import com.readdle.swiftjava.sample.DoubleTestStruct
import com.readdle.swiftjava.sample.SwiftEnvironment
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DoubleTests {
    @Before
    fun setUp() {
        System.loadLibrary("SampleAppCore")
        JavaSwift.init()
        SwiftEnvironment.initEnvironment()
    }

    @Test
    fun testZero() {
        Assert.assertEquals(DoubleTest.testZero(), 0.0, DELTA)
    }

    @Test
    fun testMin() {
        Assert.assertEquals(testNan(), Double.NaN, DELTA)
    }

    @Test
    fun testMax() {
        Assert.assertEquals(testInfinite(), Double.POSITIVE_INFINITY, DELTA)
    }

    @Test
    fun testParam() {
        Assert.assertTrue(testParam(Double.POSITIVE_INFINITY))
        Assert.assertFalse(testParam(Double.NaN))
    }

    @Test
    fun testReturnType() {
        Assert.assertEquals(DoubleTest.testReturnType(), Double.POSITIVE_INFINITY, DELTA)
    }

    @Test
    fun testOptionalParam() {
        Assert.assertTrue(testOptionalParam(Double.POSITIVE_INFINITY))
        Assert.assertFalse(testOptionalParam(Double.NaN))
    }

    fun testOptionalReturnType() {
        val result = DoubleTest.testOptionalReturnType()
        Assert.assertNotNull(result)
        Assert.assertEquals(result!!.toFloat().toDouble(), Double.POSITIVE_INFINITY, DELTA)
    }

    @Test
    fun testProtocolParam() {
        val result = testProtocolParam(DoubleParamProtocol { param: Double -> param == Double.POSITIVE_INFINITY })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolReturnType() {
        val result = testProtocolReturnType(DoubleReturnTypeProtocol { 42.0 })
        Assert.assertEquals(result, 42.0, DELTA)
    }

    @Test
    fun testProtocolOptionalParam() {
        val result =
            testProtocolOptionalParam(DoubleOptionalParamProtocol { param: Double? -> param != null && param == Double.POSITIVE_INFINITY })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolOptionalReturnType() {
        val result = testProtocolOptionalReturnType(DoubleOptionalReturnTypeProtocol { 42.0 })
        Assert.assertNotNull(result)
        Assert.assertEquals(result!!, 42.0, DELTA)
    }

    @Test
    fun testEncode() {
        val result = DoubleTest.testEncode()
        Assert.assertEquals(result, DoubleTestStruct())
    }

    @Test
    fun testDecode() {
        val goodParam = DoubleTestStruct()
        val badParam = DoubleTestStruct(42.0, 42.0, 42.0, 42.0, 42.0)
        Assert.assertTrue(testDecode(goodParam))
        Assert.assertFalse(testDecode(badParam))
    }

    @Test
    fun testBlock() {
        Assert.assertTrue(testBlock(KotlinDoubleBlock { value: Double -> value }))
    }

    @Test
    fun testOptionalBoolBlock() {
        Assert.assertTrue(testOptionalBlock(KotlinOptionalDoubleBlock { value: Double? -> value }))
    }

    companion object {
        private const val DELTA = 0.000001
    }
}
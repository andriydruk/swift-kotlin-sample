import com.readdle.codegen.anotation.JavaSwift
import com.readdle.swiftjava.sample.SwiftEnvironment
import com.readdle.swiftjava.sample.UInt64Enum
import com.readdle.swiftjava.sample.UInt64OptionsSet.Companion.one
import com.readdle.swiftjava.sample.UInt64OptionsSet.Companion.three
import com.readdle.swiftjava.sample.UInt64OptionsSet.Companion.two
import com.readdle.swiftjava.sample.UInt64Test
import com.readdle.swiftjava.sample.UInt64Test.*
import com.readdle.swiftjava.sample.UInt64Test.Companion.testBlock
import com.readdle.swiftjava.sample.UInt64Test.Companion.testDecode
import com.readdle.swiftjava.sample.UInt64Test.Companion.testEnumDecode
import com.readdle.swiftjava.sample.UInt64Test.Companion.testEnumEncode
import com.readdle.swiftjava.sample.UInt64Test.Companion.testOptionSetDecode
import com.readdle.swiftjava.sample.UInt64Test.Companion.testOptionSetEncode
import com.readdle.swiftjava.sample.UInt64Test.Companion.testOptionalBlock
import com.readdle.swiftjava.sample.UInt64Test.Companion.testOptionalParam
import com.readdle.swiftjava.sample.UInt64Test.Companion.testParam
import com.readdle.swiftjava.sample.UInt64Test.Companion.testProtocolOptionalParam
import com.readdle.swiftjava.sample.UInt64Test.Companion.testProtocolOptionalReturnType
import com.readdle.swiftjava.sample.UInt64Test.Companion.testProtocolParam
import com.readdle.swiftjava.sample.UInt64Test.Companion.testProtocolReturnType
import com.readdle.swiftjava.sample.UInt64TestStruct
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UInt64Tests {
    @Before
    fun setUp() {
        System.loadLibrary("SampleAppCore")
        JavaSwift.init()
        SwiftEnvironment.initEnvironment()
    }

    @Test
    fun testZero() {
        Assert.assertEquals(UInt64Test.testZero(), 0)
    }

    @Test
    fun testMin() {
        Assert.assertEquals(UInt64Test.testMin(), MIN_VALUE)
    }

    @Test
    fun testMax() {
        Assert.assertEquals(UInt64Test.testMax(), MAX_VALUE)
    }

    @Test
    fun testParam() {
        Assert.assertTrue(testParam(MAX_VALUE))
        Assert.assertFalse(testParam(MIN_VALUE))
    }

    @Test
    fun testReturnType() {
        Assert.assertEquals(UInt64Test.testReturnType(), MAX_VALUE)
    }

    @Test
    fun testOptionalParam() {
        Assert.assertTrue(testOptionalParam(MAX_VALUE))
        Assert.assertFalse(testOptionalParam(MIN_VALUE))
    }

    fun testOptionalReturnType() {
        val result = UInt64Test.testOptionalReturnType()
        Assert.assertNotNull(result)
        Assert.assertEquals(result!!.toByte().toLong(), MAX_VALUE)
    }

    @Test
    fun testProtocolParam() {
        val result = testProtocolParam(UInt64ParamProtocol { param: Long -> param == MAX_VALUE })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolReturnType() {
        val result = testProtocolReturnType(UInt64ReturnTypeProtocol { 42.toLong() })
        Assert.assertEquals(result, 42)
    }

    @Test
    fun testProtocolOptionalParam() {
        val result =
            testProtocolOptionalParam(UInt64OptionalParamProtocol { param: Long? -> param != null && param == MAX_VALUE })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolOptionalReturnType() {
        val result = testProtocolOptionalReturnType(UInt64OptionalReturnTypeProtocol { 42.toLong() })
        Assert.assertNotNull(result)
        Assert.assertEquals(result!!.toByte().toLong(), 42)
    }

    @Test
    fun testEncode() {
        val result = UInt64Test.testEncode()
        Assert.assertEquals(result, UInt64TestStruct())
    }

    @Test
    fun testDecode() {
        val goodParam = UInt64TestStruct()
        val badParam = UInt64TestStruct(42.toLong(), 42.toLong(), 42.toLong(), 42.toLong(), 42.toLong())
        Assert.assertTrue(testDecode(goodParam))
        Assert.assertFalse(testDecode(badParam))
    }

    @Test
    fun testEnumEncode() {
        Assert.assertEquals(UInt64Enum.ONE, testEnumEncode(UInt64Enum.ONE.rawValue))
        Assert.assertEquals(UInt64Enum.TWO, testEnumEncode(UInt64Enum.TWO.rawValue))
        Assert.assertEquals(UInt64Enum.THREE, testEnumEncode(UInt64Enum.THREE.rawValue))
    }

    @Test
    fun testEnumDecode() {
        Assert.assertEquals(UInt64Enum.ONE.rawValue, testEnumDecode(UInt64Enum.ONE))
        Assert.assertEquals(UInt64Enum.TWO.rawValue, testEnumDecode(UInt64Enum.TWO))
        Assert.assertEquals(UInt64Enum.THREE.rawValue, testEnumDecode(UInt64Enum.THREE))
    }

    @Test
    fun testOptionSetEncode() {
        Assert.assertEquals(one, testOptionSetEncode(one.rawValue))
        Assert.assertEquals(two, testOptionSetEncode(two.rawValue))
        Assert.assertEquals(three, testOptionSetEncode(three.rawValue))
    }

    @Test
    fun testOptionSetDecode() {
        Assert.assertEquals(one.rawValue, testOptionSetDecode(one))
        Assert.assertEquals(two.rawValue, testOptionSetDecode(two))
        Assert.assertEquals(three.rawValue, testOptionSetDecode(three))
    }

    @Test
    fun testBlock() {
        Assert.assertTrue(testBlock(KotlinUInt64Block { value: Long -> value }))
    }

    @Test
    fun testOptionalBlock() {
        Assert.assertTrue(testOptionalBlock(KotlinOptionalUInt64Block { value: Long? -> value }))
    }

    companion object {
        private const val MIN_VALUE: Long = 0
        private const val MAX_VALUE: Long = -1
    }
}
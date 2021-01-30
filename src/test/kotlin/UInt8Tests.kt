import com.readdle.codegen.anotation.JavaSwift
import com.readdle.swiftjava.sample.SwiftEnvironment
import com.readdle.swiftjava.sample.UInt8Enum
import com.readdle.swiftjava.sample.UInt8OptionsSet.Companion.one
import com.readdle.swiftjava.sample.UInt8OptionsSet.Companion.three
import com.readdle.swiftjava.sample.UInt8OptionsSet.Companion.two
import com.readdle.swiftjava.sample.UInt8Test
import com.readdle.swiftjava.sample.UInt8Test.*
import com.readdle.swiftjava.sample.UInt8Test.Companion.testBlock
import com.readdle.swiftjava.sample.UInt8Test.Companion.testDecode
import com.readdle.swiftjava.sample.UInt8Test.Companion.testEnumDecode
import com.readdle.swiftjava.sample.UInt8Test.Companion.testEnumEncode
import com.readdle.swiftjava.sample.UInt8Test.Companion.testOptionSetDecode
import com.readdle.swiftjava.sample.UInt8Test.Companion.testOptionSetEncode
import com.readdle.swiftjava.sample.UInt8Test.Companion.testOptionalBlock
import com.readdle.swiftjava.sample.UInt8Test.Companion.testOptionalParam
import com.readdle.swiftjava.sample.UInt8Test.Companion.testParam
import com.readdle.swiftjava.sample.UInt8Test.Companion.testProtocolOptionalParam
import com.readdle.swiftjava.sample.UInt8Test.Companion.testProtocolOptionalReturnType
import com.readdle.swiftjava.sample.UInt8Test.Companion.testProtocolParam
import com.readdle.swiftjava.sample.UInt8Test.Companion.testProtocolReturnType
import com.readdle.swiftjava.sample.UInt8TestStruct
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UInt8Tests {
    @Before
    fun setUp() {
        System.loadLibrary("SampleAppCore")
        JavaSwift.init()
        SwiftEnvironment.initEnvironment()
    }

    @Test
    fun testZero() {
        Assert.assertEquals(UInt8Test.testZero().toLong(), 0)
    }

    @Test
    fun testMin() {
        Assert.assertEquals(UInt8Test.testMin().toLong(), MIN_VALUE.toLong())
    }

    @Test
    fun testMax() {
        Assert.assertEquals(UInt8Test.testMax().toLong(), MAX_VALUE.toLong())
    }

    @Test
    fun testParam() {
        Assert.assertTrue(testParam(MAX_VALUE))
        Assert.assertFalse(testParam(MIN_VALUE))
    }

    @Test
    fun testReturnType() {
        Assert.assertEquals(UInt8Test.testReturnType().toLong(), MAX_VALUE.toLong())
    }

    @Test
    fun testOptionalParam() {
        Assert.assertTrue(testOptionalParam(MAX_VALUE))
        Assert.assertFalse(testOptionalParam(MIN_VALUE))
    }

    fun testOptionalReturnType() {
        val result = UInt8Test.testOptionalReturnType()
        Assert.assertNotNull(result)
        Assert.assertEquals(result!!.toByte().toLong(), MAX_VALUE.toLong())
    }

    @Test
    fun testProtocolParam() {
        val result = testProtocolParam(UInt8ParamProtocol { param: Byte -> param == MAX_VALUE })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolReturnType() {
        val result = testProtocolReturnType(UInt8ReturnTypeProtocol { 42.toByte() })
        Assert.assertEquals(result.toLong(), 42)
    }

    @Test
    fun testProtocolOptionalParam() {
        val result =
            testProtocolOptionalParam(UInt8OptionalParamProtocol { param: Byte? -> param != null && param == MAX_VALUE })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolOptionalReturnType() {
        val result = testProtocolOptionalReturnType(UInt8OptionalReturnTypeProtocol { 42.toByte() })
        Assert.assertNotNull(result)
        Assert.assertEquals(result!!.toByte().toLong(), 42)
    }

    @Test
    fun testEncode() {
        val result = UInt8Test.testEncode()
        Assert.assertEquals(result, UInt8TestStruct())
    }

    @Test
    fun testDecode() {
        val goodParam = UInt8TestStruct()
        val badParam = UInt8TestStruct(42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte())
        Assert.assertTrue(testDecode(goodParam))
        Assert.assertFalse(testDecode(badParam))
    }

    @Test
    fun testEnumEncode() {
        Assert.assertEquals(UInt8Enum.ONE, testEnumEncode(UInt8Enum.ONE.rawValue))
        Assert.assertEquals(UInt8Enum.TWO, testEnumEncode(UInt8Enum.TWO.rawValue))
        Assert.assertEquals(UInt8Enum.THREE, testEnumEncode(UInt8Enum.THREE.rawValue))
    }

    @Test
    fun testEnumDecode() {
        Assert.assertEquals(UInt8Enum.ONE.rawValue.toLong(), testEnumDecode(UInt8Enum.ONE).toLong())
        Assert.assertEquals(UInt8Enum.TWO.rawValue.toLong(), testEnumDecode(UInt8Enum.TWO).toLong())
        Assert.assertEquals(UInt8Enum.THREE.rawValue.toLong(), testEnumDecode(UInt8Enum.THREE).toLong())
    }

    @Test
    fun testOptionSetEncode() {
        Assert.assertEquals(one, testOptionSetEncode(one.rawValue))
        Assert.assertEquals(two, testOptionSetEncode(two.rawValue))
        Assert.assertEquals(three, testOptionSetEncode(three.rawValue))
    }

    @Test
    fun testOptionSetDecode() {
        Assert.assertEquals(one.rawValue.toLong(), testOptionSetDecode(one).toLong())
        Assert.assertEquals(two.rawValue.toLong(), testOptionSetDecode(two).toLong())
        Assert.assertEquals(three.rawValue.toLong(), testOptionSetDecode(three).toLong())
    }

    @Test
    fun testBlock() {
        Assert.assertTrue(testBlock(KotlinUInt8Block { value: Byte -> value }))
    }

    @Test
    fun testOptionalBlock() {
        Assert.assertTrue(testOptionalBlock(KotlinOptionalUInt8Block { value: Byte? -> value }))
    }

    companion object {
        private const val MIN_VALUE: Byte = 0
        private const val MAX_VALUE: Byte = -1
    }
}
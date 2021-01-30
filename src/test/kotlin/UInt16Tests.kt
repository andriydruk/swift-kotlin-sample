import com.readdle.codegen.anotation.JavaSwift
import com.readdle.swiftjava.sample.SwiftEnvironment
import com.readdle.swiftjava.sample.UInt16Enum
import com.readdle.swiftjava.sample.UInt16OptionsSet.Companion.one
import com.readdle.swiftjava.sample.UInt16OptionsSet.Companion.three
import com.readdle.swiftjava.sample.UInt16OptionsSet.Companion.two
import com.readdle.swiftjava.sample.UInt16Test
import com.readdle.swiftjava.sample.UInt16Test.*
import com.readdle.swiftjava.sample.UInt16Test.Companion.testBlock
import com.readdle.swiftjava.sample.UInt16Test.Companion.testDecode
import com.readdle.swiftjava.sample.UInt16Test.Companion.testEnumDecode
import com.readdle.swiftjava.sample.UInt16Test.Companion.testEnumEncode
import com.readdle.swiftjava.sample.UInt16Test.Companion.testOptionSetDecode
import com.readdle.swiftjava.sample.UInt16Test.Companion.testOptionSetEncode
import com.readdle.swiftjava.sample.UInt16Test.Companion.testOptionalBlock
import com.readdle.swiftjava.sample.UInt16Test.Companion.testOptionalParam
import com.readdle.swiftjava.sample.UInt16Test.Companion.testParam
import com.readdle.swiftjava.sample.UInt16Test.Companion.testProtocolOptionalParam
import com.readdle.swiftjava.sample.UInt16Test.Companion.testProtocolOptionalReturnType
import com.readdle.swiftjava.sample.UInt16Test.Companion.testProtocolParam
import com.readdle.swiftjava.sample.UInt16Test.Companion.testProtocolReturnType
import com.readdle.swiftjava.sample.UInt16TestStruct
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UInt16Tests {
    @Before
    fun setUp() {
        System.loadLibrary("SampleAppCore")
        JavaSwift.init()
        SwiftEnvironment.initEnvironment()
    }

    @Test
    fun testZero() {
        Assert.assertEquals(UInt16Test.testZero().toLong(), 0)
    }

    @Test
    fun testMin() {
        Assert.assertEquals(UInt16Test.testMin().toLong(), MIN_VALUE.toLong())
    }

    @Test
    fun testMax() {
        Assert.assertEquals(UInt16Test.testMax().toLong(), MAX_VALUE.toLong())
    }

    @Test
    fun testParam() {
        Assert.assertTrue(testParam(MAX_VALUE))
        Assert.assertFalse(testParam(MIN_VALUE))
    }

    @Test
    fun testReturnType() {
        Assert.assertEquals(UInt16Test.testReturnType().toLong(), MAX_VALUE.toLong())
    }

    @Test
    fun testOptionalParam() {
        Assert.assertTrue(testOptionalParam(MAX_VALUE))
        Assert.assertFalse(testOptionalParam(MIN_VALUE))
    }

    fun testOptionalReturnType() {
        val result = UInt16Test.testOptionalReturnType()
        Assert.assertNotNull(result)
        Assert.assertEquals(result!!.toByte().toLong(), MAX_VALUE.toLong())
    }

    @Test
    fun testProtocolParam() {
        val result = testProtocolParam(UInt16ParamProtocol { param: Short -> param == MAX_VALUE })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolReturnType() {
        val result = testProtocolReturnType(UInt16ReturnTypeProtocol { 42.toShort() })
        Assert.assertEquals(result.toLong(), 42)
    }

    @Test
    fun testProtocolOptionalParam() {
        val result =
            testProtocolOptionalParam(UInt16OptionalParamProtocol { param: Short? -> param != null && param == MAX_VALUE })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolOptionalReturnType() {
        val result = testProtocolOptionalReturnType(UInt16OptionalReturnTypeProtocol { 42.toShort() })
        Assert.assertNotNull(result)
        Assert.assertEquals(result!!.toByte().toLong(), 42)
    }

    @Test
    fun testEncode() {
        val result = UInt16Test.testEncode()
        Assert.assertEquals(result, UInt16TestStruct())
    }

    @Test
    fun testDecode() {
        val goodParam = UInt16TestStruct()
        val badParam = UInt16TestStruct(42.toShort(), 42.toShort(), 42.toShort(), 42.toShort(), 42.toShort())
        Assert.assertTrue(testDecode(goodParam))
        Assert.assertFalse(testDecode(badParam))
    }

    @Test
    fun testEnumEncode() {
        Assert.assertEquals(UInt16Enum.ONE, testEnumEncode(UInt16Enum.ONE.rawValue))
        Assert.assertEquals(UInt16Enum.TWO, testEnumEncode(UInt16Enum.TWO.rawValue))
        Assert.assertEquals(UInt16Enum.THREE, testEnumEncode(UInt16Enum.THREE.rawValue))
    }

    @Test
    fun testEnumDecode() {
        Assert.assertEquals(UInt16Enum.ONE.rawValue.toLong(), testEnumDecode(UInt16Enum.ONE).toLong())
        Assert.assertEquals(UInt16Enum.TWO.rawValue.toLong(), testEnumDecode(UInt16Enum.TWO).toLong())
        Assert.assertEquals(UInt16Enum.THREE.rawValue.toLong(), testEnumDecode(UInt16Enum.THREE).toLong())
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
        Assert.assertTrue(testBlock(KotlinUInt16Block { value: Short -> value }))
    }

    @Test
    fun testOptionalBlock() {
        Assert.assertTrue(testOptionalBlock(KotlinOptionalUInt16Block { value: Short? -> value }))
    }

    companion object {
        private const val MIN_VALUE: Short = 0
        private const val MAX_VALUE: Short = -1
    }
}
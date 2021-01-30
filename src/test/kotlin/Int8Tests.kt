import com.readdle.codegen.anotation.JavaSwift
import com.readdle.swiftjava.sample.Int8Enum
import com.readdle.swiftjava.sample.Int8OptionsSet.Companion.one
import com.readdle.swiftjava.sample.Int8OptionsSet.Companion.three
import com.readdle.swiftjava.sample.Int8OptionsSet.Companion.two
import com.readdle.swiftjava.sample.Int8Test
import com.readdle.swiftjava.sample.Int8Test.*
import com.readdle.swiftjava.sample.Int8Test.Companion.testBlock
import com.readdle.swiftjava.sample.Int8Test.Companion.testDecode
import com.readdle.swiftjava.sample.Int8Test.Companion.testEnumDecode
import com.readdle.swiftjava.sample.Int8Test.Companion.testEnumEncode
import com.readdle.swiftjava.sample.Int8Test.Companion.testOptionSetDecode
import com.readdle.swiftjava.sample.Int8Test.Companion.testOptionSetEncode
import com.readdle.swiftjava.sample.Int8Test.Companion.testOptionalBlock
import com.readdle.swiftjava.sample.Int8Test.Companion.testOptionalParam
import com.readdle.swiftjava.sample.Int8Test.Companion.testParam
import com.readdle.swiftjava.sample.Int8Test.Companion.testProtocolOptionalParam
import com.readdle.swiftjava.sample.Int8Test.Companion.testProtocolOptionalReturnType
import com.readdle.swiftjava.sample.Int8Test.Companion.testProtocolParam
import com.readdle.swiftjava.sample.Int8Test.Companion.testProtocolReturnType
import com.readdle.swiftjava.sample.Int8TestStruct
import com.readdle.swiftjava.sample.SwiftEnvironment
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class Int8Tests {
    @Before
    fun setUp() {
        System.loadLibrary("SampleAppCore")
        JavaSwift.init()
        SwiftEnvironment.initEnvironment()
    }

    @Test
    fun testZero() {
        Assert.assertEquals(Int8Test.testZero().toLong(), 0)
    }

    @Test
    fun testMin() {
        Assert.assertEquals(Int8Test.testMin().toLong(), Byte.MIN_VALUE.toLong())
    }

    @Test
    fun testMax() {
        Assert.assertEquals(Int8Test.testMax().toLong(), Byte.MAX_VALUE.toLong())
    }

    @Test
    fun testParam() {
        Assert.assertTrue(testParam(Byte.MAX_VALUE))
        Assert.assertFalse(testParam(Byte.MIN_VALUE))
    }

    @Test
    fun testReturnType() {
        Assert.assertEquals(Int8Test.testReturnType().toLong(), Byte.MAX_VALUE.toLong())
    }

    @Test
    fun testOptionalParam() {
        Assert.assertTrue(testOptionalParam(Byte.MAX_VALUE))
        Assert.assertFalse(testOptionalParam(Byte.MIN_VALUE))
    }

    fun testOptionalReturnType() {
        val result = Int8Test.testOptionalReturnType()
        Assert.assertNotNull(result)
        Assert.assertEquals(result!!.toByte().toLong(), Byte.MAX_VALUE.toLong())
    }

    @Test
    fun testProtocolParam() {
        val result = testProtocolParam(Int8ParamProtocol { param: Byte -> param == Byte.MAX_VALUE })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolReturnType() {
        val result = testProtocolReturnType(Int8ReturnTypeProtocol { 42.toByte() })
        Assert.assertEquals(result.toLong(), 42)
    }

    @Test
    fun testProtocolOptionalParam() {
        val result =
            testProtocolOptionalParam(Int8OptionalParamProtocol { param: Byte? -> param != null && param == Byte.MAX_VALUE })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolOptionalReturnType() {
        val result = testProtocolOptionalReturnType(Int8OptionalReturnTypeProtocol { 42.toByte() })
        Assert.assertNotNull(result)
        Assert.assertEquals(result!!.toByte().toLong(), 42)
    }

    @Test
    fun testEncode() {
        val result = Int8Test.testEncode()
        Assert.assertEquals(result, Int8TestStruct())
    }

    @Test
    fun testDecode() {
        val goodParam = Int8TestStruct()
        val badParam = Int8TestStruct(42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte())
        Assert.assertTrue(testDecode(goodParam))
        Assert.assertFalse(testDecode(badParam))
    }

    @Test
    fun testEnumEncode() {
        Assert.assertEquals(Int8Enum.ONE, testEnumEncode(Int8Enum.ONE.rawValue))
        Assert.assertEquals(Int8Enum.TWO, testEnumEncode(Int8Enum.TWO.rawValue))
        Assert.assertEquals(Int8Enum.THREE, testEnumEncode(Int8Enum.THREE.rawValue))
    }

    @Test
    fun testEnumDecode() {
        Assert.assertEquals(Int8Enum.ONE.rawValue.toLong(), testEnumDecode(Int8Enum.ONE).toLong())
        Assert.assertEquals(Int8Enum.TWO.rawValue.toLong(), testEnumDecode(Int8Enum.TWO).toLong())
        Assert.assertEquals(Int8Enum.THREE.rawValue.toLong(), testEnumDecode(Int8Enum.THREE).toLong())
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
        Assert.assertTrue(testBlock(KotlinInt8Block { value: Byte -> value }))
    }

    @Test
    fun testOptionalBlock() {
        Assert.assertTrue(testOptionalBlock(KotlinOptionalInt8Block { value: Byte? -> value }))
    }
}
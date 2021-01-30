import com.readdle.codegen.anotation.JavaSwift
import com.readdle.swiftjava.sample.Int16Enum
import com.readdle.swiftjava.sample.Int16OptionsSet.Companion.one
import com.readdle.swiftjava.sample.Int16OptionsSet.Companion.three
import com.readdle.swiftjava.sample.Int16OptionsSet.Companion.two
import com.readdle.swiftjava.sample.Int16Test
import com.readdle.swiftjava.sample.Int16Test.*
import com.readdle.swiftjava.sample.Int16Test.Companion.testBlock
import com.readdle.swiftjava.sample.Int16Test.Companion.testDecode
import com.readdle.swiftjava.sample.Int16Test.Companion.testEnumDecode
import com.readdle.swiftjava.sample.Int16Test.Companion.testEnumEncode
import com.readdle.swiftjava.sample.Int16Test.Companion.testOptionSetDecode
import com.readdle.swiftjava.sample.Int16Test.Companion.testOptionSetEncode
import com.readdle.swiftjava.sample.Int16Test.Companion.testOptionalBlock
import com.readdle.swiftjava.sample.Int16Test.Companion.testOptionalParam
import com.readdle.swiftjava.sample.Int16Test.Companion.testParam
import com.readdle.swiftjava.sample.Int16Test.Companion.testProtocolOptionalParam
import com.readdle.swiftjava.sample.Int16Test.Companion.testProtocolOptionalReturnType
import com.readdle.swiftjava.sample.Int16Test.Companion.testProtocolParam
import com.readdle.swiftjava.sample.Int16Test.Companion.testProtocolReturnType
import com.readdle.swiftjava.sample.Int16TestStruct
import com.readdle.swiftjava.sample.SwiftEnvironment
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class Int16Tests {
    @Before
    fun setUp() {
        System.loadLibrary("SampleAppCore")
        JavaSwift.init()
        SwiftEnvironment.initEnvironment()
    }

    @Test
    fun testZero() {
        Assert.assertEquals(Int16Test.testZero().toLong(), 0)
    }

    @Test
    fun testMin() {
        Assert.assertEquals(Int16Test.testMin().toLong(), Short.MIN_VALUE.toLong())
    }

    @Test
    fun testMax() {
        Assert.assertEquals(Int16Test.testMax().toLong(), Short.MAX_VALUE.toLong())
    }

    @Test
    fun testParam() {
        Assert.assertTrue(testParam(Short.MAX_VALUE))
        Assert.assertFalse(testParam(Short.MIN_VALUE))
    }

    @Test
    fun testReturnType() {
        Assert.assertEquals(Int16Test.testReturnType().toLong(), Short.MAX_VALUE.toLong())
    }

    @Test
    fun testOptionalParam() {
        Assert.assertTrue(testOptionalParam(Short.MAX_VALUE))
        Assert.assertFalse(testOptionalParam(Short.MIN_VALUE))
    }

    fun testOptionalReturnType() {
        val result = Int16Test.testOptionalReturnType()
        Assert.assertNotNull(result)
        Assert.assertEquals(result!!.toShort().toLong(), Short.MAX_VALUE.toLong())
    }

    @Test
    fun testProtocolParam() {
        val result = testProtocolParam(Int16ParamProtocol { param: Short -> param == Short.MAX_VALUE })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolReturnType() {
        val result = testProtocolReturnType(Int16ReturnTypeProtocol { 42.toShort() })
        Assert.assertEquals(result.toLong(), 42)
    }

    @Test
    fun testProtocolOptionalParam() {
        val result =
            testProtocolOptionalParam(Int16OptionalParamProtocol { param: Short? -> param != null && param == Short.MAX_VALUE })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolOptionalReturnType() {
        val result = testProtocolOptionalReturnType(Int16OptionalReturnTypeProtocol { 42.toShort() })
        Assert.assertNotNull(result)
        Assert.assertEquals(result!!.toShort().toLong(), 42)
    }

    @Test
    fun testEncode() {
        val result = Int16Test.testEncode()
        Assert.assertEquals(result, Int16TestStruct())
    }

    @Test
    fun testDecode() {
        val goodParam = Int16TestStruct()
        val badParam = Int16TestStruct(42.toShort(), 42.toShort(), 42.toShort(), 42.toShort(), 42.toShort())
        Assert.assertTrue(testDecode(goodParam))
        Assert.assertFalse(testDecode(badParam))
    }

    @Test
    fun testEnumEncode() {
        Assert.assertEquals(Int16Enum.ONE, testEnumEncode(Int16Enum.ONE.rawValue))
        Assert.assertEquals(Int16Enum.TWO, testEnumEncode(Int16Enum.TWO.rawValue))
        Assert.assertEquals(Int16Enum.THREE, testEnumEncode(Int16Enum.THREE.rawValue))
    }

    @Test
    fun testEnumDecode() {
        Assert.assertEquals(Int16Enum.ONE.rawValue.toLong(), testEnumDecode(Int16Enum.ONE).toLong())
        Assert.assertEquals(Int16Enum.TWO.rawValue.toLong(), testEnumDecode(Int16Enum.TWO).toLong())
        Assert.assertEquals(Int16Enum.THREE.rawValue.toLong(), testEnumDecode(Int16Enum.THREE).toLong())
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
        Assert.assertTrue(testBlock(KotlinInt16Block { value: Short -> value }))
    }

    @Test
    fun testOptionalBlock() {
        Assert.assertTrue(testOptionalBlock(KotlinOptionalInt16Block { value: Short? -> value }))
    }
}
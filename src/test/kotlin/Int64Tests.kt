import com.readdle.codegen.anotation.JavaSwift
import com.readdle.swiftjava.sample.Int64Enum
import com.readdle.swiftjava.sample.Int64OptionsSet.Companion.one
import com.readdle.swiftjava.sample.Int64OptionsSet.Companion.three
import com.readdle.swiftjava.sample.Int64OptionsSet.Companion.two
import com.readdle.swiftjava.sample.Int64Test
import com.readdle.swiftjava.sample.Int64Test.*
import com.readdle.swiftjava.sample.Int64Test.Companion.testBlock
import com.readdle.swiftjava.sample.Int64Test.Companion.testDecode
import com.readdle.swiftjava.sample.Int64Test.Companion.testEnumDecode
import com.readdle.swiftjava.sample.Int64Test.Companion.testEnumEncode
import com.readdle.swiftjava.sample.Int64Test.Companion.testOptionSetDecode
import com.readdle.swiftjava.sample.Int64Test.Companion.testOptionSetEncode
import com.readdle.swiftjava.sample.Int64Test.Companion.testOptionalBlock
import com.readdle.swiftjava.sample.Int64Test.Companion.testOptionalParam
import com.readdle.swiftjava.sample.Int64Test.Companion.testParam
import com.readdle.swiftjava.sample.Int64Test.Companion.testProtocolOptionalParam
import com.readdle.swiftjava.sample.Int64Test.Companion.testProtocolOptionalReturnType
import com.readdle.swiftjava.sample.Int64Test.Companion.testProtocolParam
import com.readdle.swiftjava.sample.Int64Test.Companion.testProtocolReturnType
import com.readdle.swiftjava.sample.Int64TestStruct
import com.readdle.swiftjava.sample.SwiftEnvironment
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class Int64Tests {
    @Before
    fun setUp() {
        System.loadLibrary("SampleAppCore")
        JavaSwift.init()
        SwiftEnvironment.initEnvironment()
    }

    @Test
    fun testZero() {
        Assert.assertEquals(Int64Test.testZero(), 0)
    }

    @Test
    fun testMin() {
        Assert.assertEquals(Int64Test.testMin(), Long.MIN_VALUE)
    }

    @Test
    fun testMax() {
        Assert.assertEquals(Int64Test.testMax(), Long.MAX_VALUE)
    }

    @Test
    fun testParam() {
        Assert.assertTrue(testParam(Long.MAX_VALUE))
        Assert.assertFalse(testParam(Long.MIN_VALUE))
    }

    @Test
    fun testReturnType() {
        Assert.assertEquals(Int64Test.testReturnType(), Long.MAX_VALUE)
    }

    @Test
    fun testOptionalParam() {
        Assert.assertTrue(testOptionalParam(Long.MAX_VALUE))
        Assert.assertFalse(testOptionalParam(Long.MIN_VALUE))
    }

    fun testOptionalReturnType() {
        val result = Int64Test.testOptionalReturnType()
        Assert.assertNotNull(result)
        Assert.assertEquals(result!!.toShort().toLong(), Long.MAX_VALUE)
    }

    @Test
    fun testProtocolParam() {
        val result = testProtocolParam(Int64ParamProtocol { param: Long -> param == Long.MAX_VALUE })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolReturnType() {
        val result = testProtocolReturnType(Int64ReturnTypeProtocol { 42.toLong() })
        Assert.assertEquals(result, 42)
    }

    @Test
    fun testProtocolOptionalParam() {
        val result =
            testProtocolOptionalParam(Int64OptionalParamProtocol { param: Long? -> param != null && param == Long.MAX_VALUE })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolOptionalReturnType() {
        val result = testProtocolOptionalReturnType(Int64OptionalReturnTypeProtocol { 42.toLong() })
        Assert.assertNotNull(result)
        Assert.assertEquals(result!!.toShort().toLong(), 42)
    }

    @Test
    fun testEncode() {
        val result = Int64Test.testEncode()
        Assert.assertEquals(result, Int64TestStruct())
    }

    @Test
    fun testDecode() {
        val goodParam = Int64TestStruct()
        val badParam = Int64TestStruct(42, 42, 42, 42.toLong(), 42.toLong())
        Assert.assertTrue(testDecode(goodParam))
        Assert.assertFalse(testDecode(badParam))
    }

    @Test
    fun testEnumEncode() {
        Assert.assertEquals(Int64Enum.ONE, testEnumEncode(Int64Enum.ONE.rawValue))
        Assert.assertEquals(Int64Enum.TWO, testEnumEncode(Int64Enum.TWO.rawValue))
        Assert.assertEquals(Int64Enum.THREE, testEnumEncode(Int64Enum.THREE.rawValue))
    }

    @Test
    fun testEnumDecode() {
        Assert.assertEquals(Int64Enum.ONE.rawValue, testEnumDecode(Int64Enum.ONE))
        Assert.assertEquals(Int64Enum.TWO.rawValue, testEnumDecode(Int64Enum.TWO))
        Assert.assertEquals(Int64Enum.THREE.rawValue, testEnumDecode(Int64Enum.THREE))
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
        Assert.assertTrue(testBlock(KotlinInt64Block { value: Long -> value }))
    }

    @Test
    fun testOptionalBlock() {
        Assert.assertTrue(testOptionalBlock(KotlinOptionalInt64Block { value: Long? -> value }))
    }
}
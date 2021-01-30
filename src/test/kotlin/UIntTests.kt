import com.readdle.codegen.anotation.JavaSwift
import com.readdle.codegen.anotation.SwiftRuntimeError
import com.readdle.swiftjava.sample.SwiftEnvironment
import com.readdle.swiftjava.sample.UIntEnum
import com.readdle.swiftjava.sample.UIntOptionsSet.Companion.one
import com.readdle.swiftjava.sample.UIntOptionsSet.Companion.three
import com.readdle.swiftjava.sample.UIntOptionsSet.Companion.two
import com.readdle.swiftjava.sample.UIntTest
import com.readdle.swiftjava.sample.UIntTest.*
import com.readdle.swiftjava.sample.UIntTest.Companion.testBlock
import com.readdle.swiftjava.sample.UIntTest.Companion.testDecode
import com.readdle.swiftjava.sample.UIntTest.Companion.testEnumDecode
import com.readdle.swiftjava.sample.UIntTest.Companion.testEnumEncode
import com.readdle.swiftjava.sample.UIntTest.Companion.testOptionSetDecode
import com.readdle.swiftjava.sample.UIntTest.Companion.testOptionSetEncode
import com.readdle.swiftjava.sample.UIntTest.Companion.testOptionalBlock
import com.readdle.swiftjava.sample.UIntTest.Companion.testOptionalParam
import com.readdle.swiftjava.sample.UIntTest.Companion.testParam
import com.readdle.swiftjava.sample.UIntTest.Companion.testProtocolOptionalParam
import com.readdle.swiftjava.sample.UIntTest.Companion.testProtocolOptionalReturnType
import com.readdle.swiftjava.sample.UIntTest.Companion.testProtocolParam
import com.readdle.swiftjava.sample.UIntTest.Companion.testProtocolReturnType
import com.readdle.swiftjava.sample.UIntTestStruct
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UIntTests {
    @Before
    fun setUp() {
        System.loadLibrary("SampleAppCore")
        JavaSwift.init()
        SwiftEnvironment.initEnvironment()
    }

    @Test
    fun testZero() {
        Assert.assertEquals(UIntTest.testZero().toLong(), 0)
    }

    @Test
    fun testMin() {
        Assert.assertEquals(UIntTest.testMin().toLong(), MIN_VALUE.toLong())
    }

    @Test
    fun testMax() {
        try {
            Assert.assertEquals(UIntTest.testMax().toLong(), MAX_VALUE.toLong())
            Assert.fail()
        } catch (e: Exception) {
            Assert.assertTrue(e is SwiftRuntimeError)
            Assert.assertEquals(
                e.message,
                "Invalid value \"18446744073709551615\": Not enough bits to represent UInt []"
            )
        }
    }

    @Test
    fun testParam() {
        Assert.assertTrue(testParam(MAX_VALUE))
        Assert.assertFalse(testParam(MIN_VALUE))
    }

    @Test
    fun testReturnType() {
        Assert.assertEquals(UIntTest.testReturnType().toLong(), MAX_VALUE.toLong())
    }

    @Test
    fun testOptionalParam() {
        Assert.assertTrue(testOptionalParam(MAX_VALUE))
        Assert.assertFalse(testOptionalParam(MIN_VALUE))
    }

    @Test
    fun testOptionalReturnType() {
        try {
            UIntTest.testOptionalReturnType()
        } catch (e: Exception) {
            Assert.assertTrue(e is SwiftRuntimeError)
            Assert.assertEquals(
                e.message,
                "Invalid value \"18446744073709551615\": Not enough bits to represent UInt []"
            )
        }
    }

    @Test
    fun testProtocolParam() {
        val result = testProtocolParam(UIntParamProtocol { param: Int -> param == MAX_VALUE })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolReturnType() {
        val result = testProtocolReturnType(UIntReturnTypeProtocol { 42 })
        Assert.assertEquals(result.toLong(), 42)
    }

    @Test
    fun testProtocolOptionalParam() {
        val result =
            testProtocolOptionalParam(UIntOptionalParamProtocol { param: Int? -> param != null && param == MAX_VALUE })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolOptionalReturnType() {
        val result = testProtocolOptionalReturnType(UIntOptionalReturnTypeProtocol { 42 })
        Assert.assertNotNull(result)
        Assert.assertEquals(result!!.toInt().toLong(), 42)
    }

    @Test
    fun testEncode() {
        val result = UIntTest.testEncode()
        Assert.assertEquals(result, UIntTestStruct())
    }

    @Test
    fun testDecode() {
        val goodParam = UIntTestStruct()
        val badParam = UIntTestStruct(42, 42, 42, 42, 42)
        Assert.assertTrue(testDecode(goodParam))
        Assert.assertFalse(testDecode(badParam))
    }

    @Test
    fun testEnumEncode() {
        Assert.assertEquals(UIntEnum.ONE, testEnumEncode(UIntEnum.ONE.rawValue))
        Assert.assertEquals(UIntEnum.TWO, testEnumEncode(UIntEnum.TWO.rawValue))
        Assert.assertEquals(UIntEnum.THREE, testEnumEncode(UIntEnum.THREE.rawValue))
    }

    @Test
    fun testEnumDecode() {
        Assert.assertEquals(UIntEnum.ONE.rawValue.toLong(), testEnumDecode(UIntEnum.ONE).toLong())
        Assert.assertEquals(UIntEnum.TWO.rawValue.toLong(), testEnumDecode(UIntEnum.TWO).toLong())
        Assert.assertEquals(UIntEnum.THREE.rawValue.toLong(), testEnumDecode(UIntEnum.THREE).toLong())
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
        Assert.assertTrue(testBlock(KotlinUIntBlock { value: Int -> value }))
    }

    @Test
    fun testOptionalBlock() {
        Assert.assertTrue(testOptionalBlock(KotlinOptionalUIntBlock { value: Int? -> value }))
    }

    companion object {
        private const val MIN_VALUE = 0
        private const val MAX_VALUE = -1
    }
}
import com.readdle.codegen.anotation.JavaSwift
import com.readdle.codegen.anotation.SwiftRuntimeError
import com.readdle.swiftjava.sample.IntEnum
import com.readdle.swiftjava.sample.IntOptionsSet.Companion.one
import com.readdle.swiftjava.sample.IntOptionsSet.Companion.three
import com.readdle.swiftjava.sample.IntOptionsSet.Companion.two
import com.readdle.swiftjava.sample.IntTest
import com.readdle.swiftjava.sample.IntTest.*
import com.readdle.swiftjava.sample.IntTest.Companion.testBlock
import com.readdle.swiftjava.sample.IntTest.Companion.testDecode
import com.readdle.swiftjava.sample.IntTest.Companion.testEnumDecode
import com.readdle.swiftjava.sample.IntTest.Companion.testEnumEncode
import com.readdle.swiftjava.sample.IntTest.Companion.testOptionSetDecode
import com.readdle.swiftjava.sample.IntTest.Companion.testOptionSetEncode
import com.readdle.swiftjava.sample.IntTest.Companion.testOptionalBlock
import com.readdle.swiftjava.sample.IntTest.Companion.testOptionalParam
import com.readdle.swiftjava.sample.IntTest.Companion.testParam
import com.readdle.swiftjava.sample.IntTest.Companion.testProtocolOptionalParam
import com.readdle.swiftjava.sample.IntTest.Companion.testProtocolOptionalReturnType
import com.readdle.swiftjava.sample.IntTest.Companion.testProtocolParam
import com.readdle.swiftjava.sample.IntTest.Companion.testProtocolReturnType
import com.readdle.swiftjava.sample.IntTestStruct
import com.readdle.swiftjava.sample.SwiftEnvironment
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class IntTests {
    @Before
    fun setUp() {
        System.loadLibrary("SampleAppCore")
        JavaSwift.init()
        SwiftEnvironment.initEnvironment()
    }

    @Test
    fun testZero() {
        Assert.assertEquals(IntTest.testZero().toLong(), 0)
    }

    @Test
    fun testMin() {
        try {
            Assert.assertEquals(IntTest.testMin().toLong(), Int.MIN_VALUE.toLong())
            Assert.fail()
        } catch (e: Exception) {
            Assert.assertTrue(e is SwiftRuntimeError)
            Assert.assertEquals(
                e.message,
                "Invalid value \"" + Long.MIN_VALUE + "\": Not enough bits to represent Int []"
            )
        }
    }

    @Test
    fun testMax() {
        try {
            Assert.assertEquals(IntTest.testMax().toLong(), Int.MAX_VALUE.toLong())
            Assert.fail()
        } catch (e: Exception) {
            Assert.assertTrue(e is SwiftRuntimeError)
            Assert.assertEquals(
                e.message,
                "Invalid value \"" + Long.MAX_VALUE + "\": Not enough bits to represent Int []"
            )
        }
    }

    @Test
    fun testParam() {
        Assert.assertTrue(testParam(Int.MAX_VALUE))
        Assert.assertFalse(testParam(Int.MIN_VALUE))
    }

    @Test
    fun testReturnType() {
        Assert.assertEquals(IntTest.testReturnType().toLong(), Int.MAX_VALUE.toLong())
    }

    @Test
    fun testOptionalParam() {
        Assert.assertTrue(testOptionalParam(Int.MAX_VALUE))
        Assert.assertFalse(testOptionalParam(Int.MIN_VALUE))
    }

    @Test
    fun testOptionalReturnType() {
        try {
            IntTest.testOptionalReturnType()
        } catch (e: Exception) {
            Assert.assertTrue(e is SwiftRuntimeError)
            Assert.assertEquals(
                e.message,
                "Invalid value \"" + Long.MAX_VALUE + "\": Not enough bits to represent Int []"
            )
        }
    }

    @Test
    fun testProtocolParam() {
        val result = testProtocolParam(IntParamProtocol { param: Int -> param == Int.MAX_VALUE })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolReturnType() {
        val result = testProtocolReturnType(IntReturnTypeProtocol { 42 })
        Assert.assertEquals(result.toLong(), 42)
    }

    @Test
    fun testProtocolOptionalParam() {
        val result =
            testProtocolOptionalParam(IntOptionalParamProtocol { param: Int? -> param != null && param == Int.MAX_VALUE })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolOptionalReturnType() {
        val result = testProtocolOptionalReturnType(IntOptionalReturnTypeProtocol { 42 })
        Assert.assertNotNull(result)
        Assert.assertEquals(result!!.toInt().toLong(), 42)
    }

    @Test
    fun testEncode() {
        val result = IntTest.testEncode()
        Assert.assertEquals(result, IntTestStruct())
    }

    @Test
    fun testDecode() {
        val goodParam = IntTestStruct()
        val badParam = IntTestStruct(42, 42, 42, 42, 42)
        Assert.assertTrue(testDecode(goodParam))
        Assert.assertFalse(testDecode(badParam))
    }

    @Test
    fun testEnumEncode() {
        Assert.assertEquals(IntEnum.ONE, testEnumEncode(IntEnum.ONE.rawValue))
        Assert.assertEquals(IntEnum.TWO, testEnumEncode(IntEnum.TWO.rawValue))
        Assert.assertEquals(IntEnum.THREE, testEnumEncode(IntEnum.THREE.rawValue))
    }

    @Test
    fun testEnumDecode() {
        Assert.assertEquals(IntEnum.ONE.rawValue.toLong(), testEnumDecode(IntEnum.ONE).toLong())
        Assert.assertEquals(IntEnum.TWO.rawValue.toLong(), testEnumDecode(IntEnum.TWO).toLong())
        Assert.assertEquals(IntEnum.THREE.rawValue.toLong(), testEnumDecode(IntEnum.THREE).toLong())
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
        Assert.assertTrue(testBlock(KotlinIntBlock { value: Int -> value }))
    }

    @Test
    fun testOptionalBlock() {
        Assert.assertTrue(testOptionalBlock(KotlinOptionalIntBlock { value: Int? -> value }))
    }
}
import com.readdle.codegen.anotation.JavaSwift
import com.readdle.swiftjava.sample.BoolTest
import com.readdle.swiftjava.sample.BoolTest.*
import com.readdle.swiftjava.sample.BoolTest.Companion.testBlock
import com.readdle.swiftjava.sample.BoolTest.Companion.testDecode
import com.readdle.swiftjava.sample.BoolTest.Companion.testNo
import com.readdle.swiftjava.sample.BoolTest.Companion.testOptionalBlock
import com.readdle.swiftjava.sample.BoolTest.Companion.testOptionalParam
import com.readdle.swiftjava.sample.BoolTest.Companion.testParam
import com.readdle.swiftjava.sample.BoolTest.Companion.testProtocolOptionalParam
import com.readdle.swiftjava.sample.BoolTest.Companion.testProtocolOptionalReturnType
import com.readdle.swiftjava.sample.BoolTest.Companion.testProtocolParam
import com.readdle.swiftjava.sample.BoolTest.Companion.testProtocolReturnType
import com.readdle.swiftjava.sample.BoolTest.Companion.testYes
import com.readdle.swiftjava.sample.BoolTestStruct
import com.readdle.swiftjava.sample.SwiftEnvironment
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class BoolTests {

    @Before
    fun setUp() {
        System.loadLibrary("SampleAppCore")
        JavaSwift.init()
        SwiftEnvironment.initEnvironment()
    }

    @Test
    fun testZero() {
        Assert.assertTrue(testYes())
    }

    @Test
    fun testMin() {
        Assert.assertFalse(testNo())
    }

    @Test
    fun testParam() {
        Assert.assertTrue(testParam(true))
        Assert.assertFalse(testParam(false))
    }

    @Test
    fun testReturnType() {
        Assert.assertTrue(BoolTest.testReturnType())
    }

    @Test
    fun testOptionalParam() {
        Assert.assertTrue(testOptionalParam(true))
        Assert.assertFalse(testOptionalParam(false))
    }

    fun testOptionalReturnType() {
        val result = BoolTest.testOptionalReturnType()
        Assert.assertNotNull(result)
        Assert.assertEquals(result, true)
    }

    @Test
    fun testProtocolParam() {
        val result = testProtocolParam(BoolParamProtocol { param -> param })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolReturnType() {
        val result = testProtocolReturnType(BoolReturnTypeProtocol { true })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolOptionalParam() {
        val result = testProtocolOptionalParam(BoolOptionalParamProtocol { param -> param != null && param })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolOptionalReturnType() {
        val result = testProtocolOptionalReturnType(BoolOptionalReturnTypeProtocol { true })
        Assert.assertNotNull(result)
        Assert.assertEquals(result, true)
    }

    @Test
    fun testEncode() {
        val result = BoolTest.testEncode()
        Assert.assertEquals(result, BoolTestStruct())
    }

    @Test
    fun testDecode() {
        val goodParam = BoolTestStruct()
        val badParam = BoolTestStruct(yes = true, no = true, optional = true, optionalNil = true)
        Assert.assertTrue(testDecode(goodParam))
        Assert.assertFalse(testDecode(badParam))
    }

    @Test
    fun testBlock() {
        Assert.assertTrue(testBlock(KotlinBoolBlock { value -> value }))
    }

    @Test
    fun testOptionalBlock() {
        Assert.assertTrue(testOptionalBlock(KotlinOptionalBoolBlock { value -> value }))
    }
}
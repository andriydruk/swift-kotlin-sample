import com.readdle.codegen.anotation.JavaSwift
import com.readdle.swiftjava.sample.Int32Enum
import com.readdle.swiftjava.sample.Int32OptionsSet.Companion.one
import com.readdle.swiftjava.sample.Int32OptionsSet.Companion.three
import com.readdle.swiftjava.sample.Int32OptionsSet.Companion.two
import com.readdle.swiftjava.sample.Int32Test
import com.readdle.swiftjava.sample.Int32Test.Companion.testDecode
import com.readdle.swiftjava.sample.Int32Test.Companion.testEnumDecode
import com.readdle.swiftjava.sample.Int32Test.Companion.testEnumEncode
import com.readdle.swiftjava.sample.Int32Test.Companion.testOptionSetDecode
import com.readdle.swiftjava.sample.Int32Test.Companion.testOptionSetEncode
import com.readdle.swiftjava.sample.Int32TestStruct
import com.readdle.swiftjava.sample.SwiftEnvironment
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class Int32Tests {
    @Before
    fun setUp() {
        System.loadLibrary("SampleAppCore")
        JavaSwift.init()
        SwiftEnvironment.initEnvironment()
    }

    @Test
    fun testEncode() {
        val result = Int32Test.testEncode()
        Assert.assertEquals(result, Int32TestStruct())
    }

    @Test
    fun testDecode() {
        val goodParam = Int32TestStruct()
        val badParam = Int32TestStruct(42, 42, 42, 42, 42)
        Assert.assertTrue(testDecode(goodParam))
        Assert.assertFalse(testDecode(badParam))
    }

    @Test
    fun testEnumEncode() {
        Assert.assertEquals(Int32Enum.ONE, testEnumEncode(Int32Enum.ONE.rawValue))
        Assert.assertEquals(Int32Enum.TWO, testEnumEncode(Int32Enum.TWO.rawValue))
        Assert.assertEquals(Int32Enum.THREE, testEnumEncode(Int32Enum.THREE.rawValue))
    }

    @Test
    fun testEnumDecode() {
        Assert.assertEquals(Int32Enum.ONE.rawValue.toLong(), testEnumDecode(Int32Enum.ONE).toLong())
        Assert.assertEquals(Int32Enum.TWO.rawValue.toLong(), testEnumDecode(Int32Enum.TWO).toLong())
        Assert.assertEquals(Int32Enum.THREE.rawValue.toLong(), testEnumDecode(Int32Enum.THREE).toLong())
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
}
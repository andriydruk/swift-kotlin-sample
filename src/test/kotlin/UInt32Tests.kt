import com.readdle.codegen.anotation.JavaSwift
import com.readdle.swiftjava.sample.SwiftEnvironment
import com.readdle.swiftjava.sample.UInt32Enum
import com.readdle.swiftjava.sample.UInt32OptionsSet.Companion.one
import com.readdle.swiftjava.sample.UInt32OptionsSet.Companion.three
import com.readdle.swiftjava.sample.UInt32OptionsSet.Companion.two
import com.readdle.swiftjava.sample.UInt32Test
import com.readdle.swiftjava.sample.UInt32Test.Companion.testDecode
import com.readdle.swiftjava.sample.UInt32Test.Companion.testEnumDecode
import com.readdle.swiftjava.sample.UInt32Test.Companion.testEnumEncode
import com.readdle.swiftjava.sample.UInt32Test.Companion.testOptionSetDecode
import com.readdle.swiftjava.sample.UInt32Test.Companion.testOptionSetEncode
import com.readdle.swiftjava.sample.UInt32TestStruct
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UInt32Tests {
    @Before
    fun setUp() {
        System.loadLibrary("SampleAppCore")
        JavaSwift.init()
        SwiftEnvironment.initEnvironment()
    }

    @Test
    fun testEncode() {
        val result = UInt32Test.testEncode()
        Assert.assertEquals(result, UInt32TestStruct())
    }

    @Test
    fun testDecode() {
        val goodParam = UInt32TestStruct()
        val badParam = UInt32TestStruct(42, 42, 42, 42, 42)
        Assert.assertTrue(testDecode(goodParam))
        Assert.assertFalse(testDecode(badParam))
    }

    @Test
    fun testEnumEncode() {
        Assert.assertEquals(UInt32Enum.ONE, testEnumEncode(UInt32Enum.ONE.rawValue))
        Assert.assertEquals(UInt32Enum.TWO, testEnumEncode(UInt32Enum.TWO.rawValue))
        Assert.assertEquals(UInt32Enum.THREE, testEnumEncode(UInt32Enum.THREE.rawValue))
    }

    @Test
    fun testEnumDecode() {
        Assert.assertEquals(UInt32Enum.ONE.rawValue.toLong(), testEnumDecode(UInt32Enum.ONE).toLong())
        Assert.assertEquals(UInt32Enum.TWO.rawValue.toLong(), testEnumDecode(UInt32Enum.TWO).toLong())
        Assert.assertEquals(UInt32Enum.THREE.rawValue.toLong(), testEnumDecode(UInt32Enum.THREE).toLong())
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
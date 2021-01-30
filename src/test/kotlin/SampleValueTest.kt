import com.readdle.codegen.anotation.JavaSwift
import com.readdle.codegen.anotation.SwiftError
import com.readdle.swiftjava.sample.SampleValue.Companion.funcThrows
import com.readdle.swiftjava.sample.SampleValue.Companion.randomValue
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

class SampleValueTest {
    @Before
    fun setUp() {
        System.loadLibrary("SampleAppCore")
        JavaSwift.init()
    }

    @Test
    fun testGetRandomValue() {
        val sampleValue = randomValue.copy()
        Assert.assertNotNull(sampleValue)
        Assert.assertTrue(sampleValue.string.isNotEmpty())
        Assert.assertTrue(sampleValue.integer == 32)
        Assert.assertTrue(sampleValue.int8.toInt() == 8)
        Assert.assertTrue(sampleValue.int16.toInt() == 16)
        Assert.assertTrue(sampleValue.int32 == 32)
        Assert.assertTrue(sampleValue.int64 == 64L)
        Assert.assertTrue(sampleValue.uint == 32U)
        Assert.assertTrue(sampleValue.uint8.toUInt() == 8U)
        Assert.assertTrue(sampleValue.uint16.toUInt() == 16U)
        Assert.assertTrue(sampleValue.uint32 == 32U)
        Assert.assertTrue(sampleValue.uint64.toUInt() == 64U)
        Assert.assertTrue(sampleValue.objectArray.isEmpty())
        Assert.assertTrue(sampleValue.stringArray.size == 3)
        Assert.assertTrue(sampleValue.numberArray.size == 3)
        Assert.assertTrue(sampleValue.arrayInArray[0].size == 3)
        Assert.assertTrue(sampleValue.dictInArray[0].size == 3)
        Assert.assertTrue(sampleValue.dictSampleClass.isEmpty())
        Assert.assertTrue(sampleValue.dictStrings.size == 1)
        Assert.assertTrue(sampleValue.dictNumbers.size == 1)
        Assert.assertTrue(sampleValue.dict64Numbers.size == 1)
        Assert.assertTrue(sampleValue.dictInDict.size == 1)
        Assert.assertTrue(sampleValue.arrayInDict.size == 1)
        Assert.assertTrue(sampleValue.set.size == 3)
        Assert.assertTrue(sampleValue.setValues.isEmpty())
    }

    @Test
    fun testSaveValue() {
        val sampleValue = randomValue
        sampleValue.string = UUID.randomUUID().toString()
        sampleValue.saveValue()
    }

    @Test
    fun testIsSame() {
        val otherValue = randomValue
        val sampleValue = randomValue
        sampleValue.string = otherValue.string
        Assert.assertTrue(sampleValue.isSame(otherValue))
    }

    @Test
    fun testThrows() {
        try {
            funcThrows()
            Assert.fail()
        } catch (swiftError: SwiftError) {
            Assert.assertTrue(swiftError.message != null)
        }
    }
}
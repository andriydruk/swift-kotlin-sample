import com.readdle.codegen.anotation.JavaSwift
import com.readdle.swiftjava.sample.Issue31ReferenceTestProgress
import com.readdle.swiftjava.sample.Issue31TestProgress
import com.readdle.swiftjava.sample.SwiftEnvironment
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class Issue31Tests {

    @Before
    fun setUp() {
        System.loadLibrary("SampleAppCore")
        JavaSwift.init()
        SwiftEnvironment.initEnvironment()
    }

    @Test
    fun testValueTypeNanBug() {
        val progress = Issue31TestProgress(elapsed = 8, total = 8)
        Assert.assertNotEquals(Double.NaN, progress.percentage)
        Assert.assertNotEquals(Double.NaN, progress.calculatePercentage())
    }

    @Test
    fun testReferenceTypeNanBug() {
        val progress = Issue31ReferenceTestProgress.init(elapsed = 8, total = 8)
        Assert.assertNotEquals(Double.NaN, progress.percentage)
        Assert.assertNotEquals(Double.NaN, progress.calculatePercentage())
    }
}
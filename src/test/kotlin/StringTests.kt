package com.readdle.swiftjava.sample

import com.readdle.codegen.anotation.JavaSwift
import com.readdle.codegen.anotation.SwiftRuntimeError
import com.readdle.swiftjava.sample.StringTest.*
import com.readdle.swiftjava.sample.StringTest.Companion.testBlock
import com.readdle.swiftjava.sample.StringTest.Companion.testDecode
import com.readdle.swiftjava.sample.StringTest.Companion.testEnumDecode
import com.readdle.swiftjava.sample.StringTest.Companion.testEnumEncode
import com.readdle.swiftjava.sample.StringTest.Companion.testOptionalBlock
import com.readdle.swiftjava.sample.StringTest.Companion.testOptionalParam
import com.readdle.swiftjava.sample.StringTest.Companion.testParam
import com.readdle.swiftjava.sample.StringTest.Companion.testProtocolOptionalParam
import com.readdle.swiftjava.sample.StringTest.Companion.testProtocolOptionalReturnType
import com.readdle.swiftjava.sample.StringTest.Companion.testProtocolParam
import com.readdle.swiftjava.sample.StringTest.Companion.testProtocolReturnType
import com.readdle.swiftjava.sample.SwiftEnvironment.Companion.initEnvironment
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class StringTests {

    @Before
    fun setUp() {
        System.loadLibrary("SampleAppCore")
        JavaSwift.init()
        initEnvironment()
    }

    @Test
    fun testZero() {
        Assert.assertEquals(StringTest.testZero(), "")
    }

    @Test
    fun testParam() {
        Assert.assertTrue(testParam(""))
        Assert.assertFalse(testParam("42"))
    }

    @Test
    fun testReturnType() {
        Assert.assertEquals(StringTest.testReturnType(), "")
    }

    @Test
    fun testOptionalParam() {
        Assert.assertTrue(testOptionalParam(""))
        Assert.assertFalse(testOptionalParam(null))
    }

    @Test
    fun testOptionalReturnType() {
        try {
            StringTest.testOptionalReturnType()
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
        val result = testProtocolParam(StringParamProtocol { param: String -> param == "" })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolReturnType() {
        val result = testProtocolReturnType(StringReturnTypeProtocol { "42" })
        Assert.assertEquals(result, "42")
    }

    @Test
    fun testProtocolOptionalParam() {
        val result =
            testProtocolOptionalParam(StringOptionalParamProtocol { param: String? -> param != null && param == "" })
        Assert.assertTrue(result)
    }

    @Test
    fun testProtocolOptionalReturnType() {
        val result = testProtocolOptionalReturnType(StringOptionalReturnTypeProtocol { "42" })
        Assert.assertNotNull(result)
        Assert.assertEquals(result, "42")
    }

    @Test
    fun testEncode() {
        val result = StringTest.testEncode()
        Assert.assertEquals(result, StringTestStruct())
    }

    @Test
    fun testDecode() {
        val goodParam = StringTestStruct()
        val badParam = StringTestStruct("42", "42", "42")
        Assert.assertTrue(testDecode(goodParam))
        Assert.assertFalse(testDecode(badParam))
    }

    @Test
    fun testEnumEncode() {
        Assert.assertEquals(StringEnum.ONE, testEnumEncode(StringEnum.ONE.rawValue))
        Assert.assertEquals(StringEnum.TWO, testEnumEncode(StringEnum.TWO.rawValue))
        Assert.assertEquals(StringEnum.THREE, testEnumEncode(StringEnum.THREE.rawValue))
    }

    @Test
    fun testEnumDecode() {
        Assert.assertEquals(StringEnum.ONE.rawValue, testEnumDecode(StringEnum.ONE))
        Assert.assertEquals(StringEnum.TWO.rawValue, testEnumDecode(StringEnum.TWO))
        Assert.assertEquals(StringEnum.THREE.rawValue, testEnumDecode(StringEnum.THREE))
    }

    @Test
    fun testBlock() {
        Assert.assertTrue(testBlock(KotlinStringBlock { value: String -> value }))
    }

    @Test
    fun testOptionalBlock() {
        Assert.assertTrue(testOptionalBlock(KotlinOptionalStringBlock { value: String? -> value }))
    }
}
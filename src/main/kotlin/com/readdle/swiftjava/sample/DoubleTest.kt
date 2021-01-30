package com.readdle.swiftjava.sample

import com.readdle.codegen.anotation.*
import java.lang.annotation.Native

@SwiftValue
data class DoubleTestStruct(var zero: Double = 0.0,
                            var infinity: Double = Double.POSITIVE_INFINITY,
                            var negativeInfinity: Double = Double.NEGATIVE_INFINITY,
                            var optional: Double? = 0.0,
                            var optionalNil: Double? = null)

@SwiftReference
class DoubleTest private constructor() {

    @SwiftDelegate(protocols = ["DoubleTestParamProtocol"])
    fun interface DoubleParamProtocol {
        @SwiftCallbackFunc
        fun testParam(param: Double): Boolean
    }

    @SwiftDelegate(protocols = ["DoubleTestReturnTypeProtocol"])
    fun interface DoubleReturnTypeProtocol {
        @SwiftCallbackFunc
        fun testReturnType(): Double
    }

    @SwiftDelegate(protocols = ["DoubleTestOptionalParamProtocol"])
    fun interface DoubleOptionalParamProtocol {
        @SwiftCallbackFunc
        fun testOptionalParam(param: Double?): Boolean
    }

    @SwiftDelegate(protocols = ["DoubleTestOptionalReturnTypeProtocol"])
    fun interface DoubleOptionalReturnTypeProtocol {
        @SwiftCallbackFunc
        fun testOptionalReturnType(): Double?
    }

    @SwiftBlock("(Double) -> Double")
    fun interface KotlinDoubleBlock {
        fun call(value: Double): Double
    }

    @SwiftBlock("(Double?) -> Double?")
    fun interface KotlinOptionalDoubleBlock {
        fun call(value: Double?): Double?
    }

    companion object {
        @JvmStatic
        external fun testZero(): Double

        @JvmStatic
        external fun testInfinite(): Double

        @JvmStatic
        external fun testNan(): Double

        @JvmStatic
        external fun testParam(param: Double): Boolean

        @JvmStatic
        external fun testReturnType(): Double

        @JvmStatic
        external fun testOptionalParam(param: Double?): Boolean

        @JvmStatic
        external fun testOptionalReturnType(): Double?

        @JvmStatic
        external fun testProtocolParam(callback: DoubleParamProtocol): Boolean

        @JvmStatic
        external fun testProtocolReturnType(callback: DoubleReturnTypeProtocol): Double

        @JvmStatic
        external fun testProtocolOptionalParam(callback: DoubleOptionalParamProtocol): Boolean

        @JvmStatic
        external fun testProtocolOptionalReturnType(callback: DoubleOptionalReturnTypeProtocol): Double?

        @JvmStatic
        external fun testEncode():  DoubleTestStruct

        @JvmStatic
        external fun testDecode(value: DoubleTestStruct): Boolean

        @JvmStatic
        external fun testBlock(@SwiftBlock block: KotlinDoubleBlock): Boolean

        @JvmStatic
        external fun testOptionalBlock(@SwiftBlock block: KotlinOptionalDoubleBlock): Boolean
    }

    @Native
    var nativePointer: Long = 0

    external fun release()

}
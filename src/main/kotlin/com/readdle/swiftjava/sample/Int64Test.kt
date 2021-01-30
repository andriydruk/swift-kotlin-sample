package com.readdle.swiftjava.sample

import com.readdle.codegen.anotation.*
import java.lang.annotation.Native

@SwiftValue
enum class Int64Enum(val rawValue: Long) {

    ONE(0), TWO(1), THREE(2);

    companion object {

        private val values = HashMap<Long, Int64Enum>()

        @JvmStatic
        fun valueOf(rawValue: Long): Int64Enum {
            return values[rawValue]!!
        }

        init {
            enumValues<Int64Enum>().forEach {
                values[it.rawValue] = it
            }
        }
    }

}

@SwiftValue
data class Int64OptionsSet(val rawValue: Long = 0) {
    companion object {
        @JvmStatic
        val  one = Int64OptionsSet(1)
        @JvmStatic
        val  two = Int64OptionsSet(2)
        @JvmStatic
        val  three = Int64OptionsSet(4)
    }
}

@SwiftValue
data class Int64TestStruct(var zero: Long = 0,
                           var max: Long = Long.MAX_VALUE,
                           var min: Long = Long.MIN_VALUE,
                           var optional: Long? = 0,
                           var optionalNil: Long? = null)

@SwiftReference
class Int64Test private constructor() {

    @SwiftDelegate(protocols = ["Int64TestParamProtocol"])
    fun interface Int64ParamProtocol {
        @SwiftCallbackFunc
        fun testParam(param: Long): Boolean
    }

    @SwiftDelegate(protocols = ["Int64TestReturnTypeProtocol"])
    fun interface Int64ReturnTypeProtocol {
        @SwiftCallbackFunc
        fun testReturnType(): Long
    }

    @SwiftDelegate(protocols = ["Int64TestOptionalParamProtocol"])
    fun interface Int64OptionalParamProtocol {
        @SwiftCallbackFunc
        fun testOptionalParam(param: Long?): Boolean
    }

    @SwiftDelegate(protocols = ["Int64TestOptionalReturnTypeProtocol"])
    fun interface Int64OptionalReturnTypeProtocol {
        @SwiftCallbackFunc
        fun testOptionalReturnType(): Long?
    }

    @SwiftBlock("(Int64) -> Int64")
    fun interface KotlinInt64Block {
        fun call(value: Long): Long
    }

    @SwiftBlock("(Int64?) -> Int64?")
    fun interface KotlinOptionalInt64Block {
        fun call(value: Long?): Long?
    }

    companion object {
        @JvmStatic
        external fun testZero(): Long

        @JvmStatic
        external fun testMin(): Long

        @JvmStatic
        external fun testMax(): Long

        @JvmStatic
        external fun testParam(param: Long): Boolean

        @JvmStatic
        external fun testReturnType(): Long

        @JvmStatic
        external fun testOptionalParam(param: Long?): Boolean

        @JvmStatic
        external fun testOptionalReturnType(): Long?

        @JvmStatic
        external fun testProtocolParam(callback: Int64ParamProtocol): Boolean

        @JvmStatic
        external fun testProtocolReturnType(callback: Int64ReturnTypeProtocol): Long

        @JvmStatic
        external fun testProtocolOptionalParam(callback: Int64OptionalParamProtocol): Boolean

        @JvmStatic
        external fun testProtocolOptionalReturnType(callback: Int64OptionalReturnTypeProtocol): Long?

        @JvmStatic
        external fun testEncode(): Int64TestStruct

        @JvmStatic
        external fun testDecode(value: Int64TestStruct): Boolean
        
        @JvmStatic
        external fun testEnumEncode(rawValue: Long) : Int64Enum

        @JvmStatic
        external fun testEnumDecode(enum: Int64Enum) : Long

        @JvmStatic
        external fun testOptionSetEncode(rawValue: Long) : Int64OptionsSet

        @JvmStatic
        external fun testOptionSetDecode(enum: Int64OptionsSet) : Long
        
        @JvmStatic
        external fun testBlock(@SwiftBlock block: KotlinInt64Block): Boolean

        @JvmStatic
        external fun testOptionalBlock(@SwiftBlock blockKotlin: KotlinOptionalInt64Block): Boolean
    }

    @Native
    var nativePointer: Long = 0

    external fun release()

}
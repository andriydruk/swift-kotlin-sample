package com.readdle.swiftjava.sample

import com.readdle.codegen.anotation.*
import java.lang.annotation.Native

private const val MIN_VALUE: Long = 0
private const val MAX_VALUE: Long = -1

@SwiftValue
enum class UInt64Enum(@Unsigned val rawValue: Long) {

    ONE(0), TWO(1), THREE(2);

    companion object {

        private val values = HashMap<Long, UInt64Enum>()

        @JvmStatic
        fun valueOf(rawValue: Long): UInt64Enum {
            return values[rawValue]!!
        }

        init {
            enumValues<UInt64Enum>().forEach {
                values[it.rawValue] = it
            }
        }
    }

}

@SwiftValue
data class UInt64OptionsSet(@Unsigned val rawValue: Long = 0) {
    companion object {
        @JvmStatic
        val  one = UInt64OptionsSet(1)
        @JvmStatic
        val  two = UInt64OptionsSet(2)
        @JvmStatic
        val  three = UInt64OptionsSet(4)
    }
}

@SwiftValue
data class UInt64TestStruct(@Unsigned var zero: Long = 0,
                            @Unsigned var max: Long = MAX_VALUE,
                            @Unsigned var min: Long = MIN_VALUE,
                            @Unsigned var optional: Long? = 0,
                            @Unsigned var optionalNil: Long? = null)

@SwiftReference
class UInt64Test private constructor() {

    @SwiftDelegate(protocols = ["UInt64TestParamProtocol"])
    fun interface UInt64ParamProtocol {
        @SwiftCallbackFunc
        fun testParam(@Unsigned param: Long): Boolean
    }

    @SwiftDelegate(protocols = ["UInt64TestReturnTypeProtocol"])
    fun interface UInt64ReturnTypeProtocol {
        @SwiftCallbackFunc @Unsigned
        fun testReturnType(): Long
    }

    @SwiftDelegate(protocols = ["UInt64TestOptionalParamProtocol"])
    fun interface UInt64OptionalParamProtocol {
        @SwiftCallbackFunc
        fun testOptionalParam(@Unsigned param: Long?): Boolean
    }

    @SwiftDelegate(protocols = ["UInt64TestOptionalReturnTypeProtocol"])
    fun interface UInt64OptionalReturnTypeProtocol {
        @SwiftCallbackFunc @Unsigned
        fun testOptionalReturnType(): Long?
    }

    @SwiftBlock("(UInt64) -> UInt64")
    fun interface KotlinUInt64Block {
        @Unsigned
        fun call(@Unsigned value: Long): Long
    }

    @SwiftBlock("(UInt64?) -> UInt64?")
    fun interface KotlinOptionalUInt64Block {
        @Unsigned
        fun call(@Unsigned value: Long?): Long?
    }

    companion object {
        @JvmStatic @Unsigned
        external fun testZero(): Long

        @JvmStatic @Unsigned
        external fun testMin(): Long

        @JvmStatic @Unsigned
        external fun testMax(): Long

        @JvmStatic
        external fun testParam(@Unsigned param: Long): Boolean

        @JvmStatic @Unsigned
        external fun testReturnType(): Long

        @JvmStatic
        external fun testOptionalParam(@Unsigned param: Long?): Boolean

        @JvmStatic @Unsigned
        external fun testOptionalReturnType(): Long?

        @JvmStatic
        external fun testProtocolParam(callback: UInt64ParamProtocol): Boolean

        @JvmStatic
        external fun testProtocolReturnType(callback: UInt64ReturnTypeProtocol): Long

        @JvmStatic
        external fun testProtocolOptionalParam(callback: UInt64OptionalParamProtocol): Boolean

        @JvmStatic
        external fun testProtocolOptionalReturnType(callback: UInt64OptionalReturnTypeProtocol): Long?

        @JvmStatic
        external fun testEncode():  UInt64TestStruct

        @JvmStatic
        external fun testDecode(value: UInt64TestStruct): Boolean

        @JvmStatic
        external fun testEnumEncode(@Unsigned rawValue: Long) : UInt64Enum

        @JvmStatic @Unsigned
        external fun testEnumDecode(enum: UInt64Enum) : Long

        @JvmStatic
        external fun testOptionSetEncode(@Unsigned rawValue: Long) : UInt64OptionsSet

        @JvmStatic @Unsigned
        external fun testOptionSetDecode(enum: UInt64OptionsSet) : Long
        
        @JvmStatic
        external fun testBlock(@SwiftBlock block: KotlinUInt64Block): Boolean

        @JvmStatic
        external fun testOptionalBlock(@SwiftBlock block: KotlinOptionalUInt64Block): Boolean
    }

    @Native
    var nativePointer: Long = 0

    external fun release()

}
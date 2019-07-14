package pl.kwiatekmichal.downloadmanager

import android.graphics.Typeface
import android.support.annotation.ColorInt
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.MetricAffectingSpan

object MKSpannatics {
    fun make(vararg items: MKSpannaticItem): SpannableStringBuilder {
        var result = ""
        items.forEach {
            result += it.text
        }
        val spannableStringBuilder = SpannableStringBuilder(result)
        var start = 0
        var end: Int
        items.forEach {
            end = start + it.text.length
            spannableStringBuilder.setSpan(
                it.span,
                start,
                end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            start = end
        }

        return spannableStringBuilder
    }
}

data class MKSpannaticItem(val text: String, val span: MKCustomFontSpan)

class MKCustomFontSpan(
    @ColorInt private val color: Int,
    private val font: Typeface?,
    private val size: Float
) : MetricAffectingSpan() {
    override fun updateMeasureState(textPaint: TextPaint) {
        spanning(textPaint)
    }

    override fun updateDrawState(tp: TextPaint?) {
        spanning(tp)
    }

    private fun spanning(textPaint: TextPaint?) {
        textPaint?.let {
            it.color = color
            it.textSize = size
            font?.let { type ->
                it.typeface = type
            }
        }
    }
}
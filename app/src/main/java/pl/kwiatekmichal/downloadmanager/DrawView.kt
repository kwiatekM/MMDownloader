package pl.kwiatekmichal.downloadmanager

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class DrawView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(
    context,
    attrs,
    defStyleAttr
) {
    private val paint = Paint()
    private val elementsNumber = 4
    private var widthView = 0
    private var heightView = 0
    private val selectedColor = Color.GREEN
    private val unselectedColor = Color.GRAY

    init {

    }

    override fun onDraw(canvas: Canvas) {
        paint.strokeWidth = 20F
        paint.color = Color.GREEN
        val y = heightView / 2
        val elementWidthWithMargin = widthView / elementsNumber
        val elementWidthMargin = elementWidthWithMargin / 10
        val elementWidthWithoutMargin = elementWidthWithMargin - (2 * elementWidthMargin)
        var start = elementWidthMargin.toFloat()
        var end: Float = (elementWidthMargin + elementWidthWithoutMargin).toFloat()
        for (i in 1..elementsNumber) {
            canvas.drawLine(start, y.toFloat(), end, y.toFloat(), paint)
            start = elementWidthMargin + end
            end = elementWidthMargin + elementWidthWithoutMargin + start
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        widthView = MeasureSpec.getSize(widthMeasureSpec)
        heightView = MeasureSpec.getSize(heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun invalidate() {
        super.invalidate()
    }
}
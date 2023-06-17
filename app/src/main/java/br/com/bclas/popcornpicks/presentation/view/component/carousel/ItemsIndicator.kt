package br.com.bclas.popcornpicks.presentation.view.component.carousel

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemsIndicator : RecyclerView.ItemDecoration() {

    private val mPaint = Paint()

    private val colorActive = Color.parseColor("#D32F2F")
    private val colorInactive = Color.parseColor("#ffeded")

    private val mIndicatorHeight = (23 * DP).toInt()
    private val mIndicatorStrokeWidth = 8 * DP
    private val mIndicatorItemLength = 8 * DP
    private val mIndicatorItemPadding = 12 * DP
    private val mInterpolator = AccelerateDecelerateInterpolator()

    companion object{
        private val DP = Resources.getSystem().displayMetrics.density
    }

    init {
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeWidth = mIndicatorStrokeWidth
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val itemCount = parent.adapter?.itemCount ?: return

        val totalLenght = mIndicatorItemLength * itemCount
        val paddingBetweenItems = 0.coerceAtLeast(itemCount - 1) * mIndicatorItemPadding
        val indicatorTotalWidth = totalLenght + paddingBetweenItems
        val indicatorStartX = (parent.width - indicatorTotalWidth) / 2f

        val indicatorPosY = parent.height - mIndicatorHeight / 2f

        drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount)

        val layoutManager = parent.layoutManager as LinearLayoutManager?: return
        val activePosition = layoutManager.findFirstVisibleItemPosition()
        if(activePosition == RecyclerView.NO_POSITION){
            return
        }

        val activeChild = layoutManager.findViewByPosition(activePosition) ?: return
        val left = activeChild.left
        val width = activeChild.width

        drawHighlightIndicator(c, indicatorStartX, indicatorPosY, activePosition)

    }

    private fun drawHighlightIndicator(c: Canvas, indicatorStartX: Float, indicatorPosY: Float, activePosition: Int) {
        mPaint.color = colorActive
        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding
        val highlightStart = indicatorStartX + itemWidth * activePosition
        c.drawCircle(highlightStart, indicatorPosY, 8f, mPaint)
    }

        private fun drawHighlightIndicator(c: Canvas, indicatorStartX: Float, indicatorPosY: Float, activePosition: Int, left: Int, width: Int) {
        mPaint.color = colorActive

        val progress = mInterpolator.getInterpolation(left * -1 / width.toFloat())

        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding

        if(progress < 0.5f){
            val rectLeft = indicatorStartX + itemWidth * activePosition
            val rectRight = rectLeft + mIndicatorItemLength

            c.drawRoundRect(
                rectLeft,
                indicatorPosY - mIndicatorItemLength / 2f,
                rectRight,
                indicatorPosY + mIndicatorItemLength / 2f,
                mIndicatorItemLength / 2f,
                mIndicatorItemLength / 2f,
                mPaint
            )

            val rx = mIndicatorItemLength / 2f
            val ry = mIndicatorItemLength / 2f
            c.drawRoundRect(
                rectLeft,
                indicatorPosY - mIndicatorItemLength / 2f,
                rectLeft + mIndicatorItemLength,
                indicatorPosY + mIndicatorItemLength / 2f,
                rx,
                ry,
                mPaint
            )
        }else{
            val rectLeft = indicatorStartX + itemWidth * activePosition + mIndicatorItemPadding
            val rectRight = rectLeft + mIndicatorItemLength

            val rx = mIndicatorItemLength / 2f
            val ry = mIndicatorItemLength / 2f
            c.drawRoundRect(
                rectLeft,
                indicatorPosY - mIndicatorItemLength / 2f,
                rectRight,
                indicatorPosY + mIndicatorItemLength / 2f,
                rx,
                ry,
                mPaint
            )

            val circleCenter = rectLeft - mIndicatorItemPadding / 2f
            c.drawCircle(circleCenter, indicatorPosY, mIndicatorItemLength / 2f, mPaint)
        }
    }

    private fun drawInactiveIndicators(c: Canvas, indicatorStartX: Float, indicatorPosY: Float, itemCount: Int) {
        mPaint.color = colorInactive

        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding

        var itemStartX = indicatorStartX
        for (i in 0 until itemCount){
            c.drawCircle(itemStartX, indicatorPosY, mIndicatorItemLength / 2f, mPaint)
            itemStartX += itemWidth
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = mIndicatorHeight
    }

}
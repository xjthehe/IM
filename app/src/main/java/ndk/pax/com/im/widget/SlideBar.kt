package ndk.pax.com.im.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import ndk.pax.com.im.R
import org.jetbrains.anko.sp

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/15:16:40
 *
 */

class SlideBar(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var sectionHeight=0f
    val paint=Paint()
    var textBaseline=0f
    companion object {
        private val SECTIONS = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L","M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.e("onMeasure","onMeasure")
    }

    //获取每个字母所占领域高度
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        sectionHeight=h*1.0f/ SECTIONS.size;

        val fontMetrics = paint.fontMetrics
        //计算文本高度
        val textHeight=fontMetrics.descent-fontMetrics.ascent
        //计算基准线************************************************
        textBaseline =sectionHeight/2+(textHeight/2-fontMetrics.descent)
    }

    init {
        paint.apply {
            color=resources.getColor(R.color.material_grey_100)
            textSize= sp(12).toFloat()
            textAlign=Paint.Align.CENTER
        }
    }

    override fun onDraw(canvas: Canvas?) {
        //绘制所有字母
        val x=width*1.0f/2;//每个字母宽度
        var baseline=textBaseline;

        SECTIONS.forEach {
            canvas?.drawText(it,x,baseline,paint)
            baseline+=sectionHeight
        }
    }

//    View的三大核心方法onMeasure、onLayout、onDraw
//    onMeasure：用于测量视图的大小；
//    onLayout：用于给视图进行布局；
//    onDraw：用于对视图进行绘制；

}
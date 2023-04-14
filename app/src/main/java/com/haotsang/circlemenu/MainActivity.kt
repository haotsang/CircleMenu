package com.haotsang.circlemenu

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val fabList = mutableListOf<View>()
    private var isOpened = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fabMenu = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        val fab1 = findViewById<FloatingActionButton>(R.id.fab_1)
        val fab2 = findViewById<FloatingActionButton>(R.id.fab_2)
        val fab3 = findViewById<FloatingActionButton>(R.id.fab_3)

        fabList.apply {
            add(fab1)
            add(fab2)
            add(fab3)
        }

        fabMenu.setOnClickListener {
            switchMenu(isOpened)
            isOpened = !isOpened
        }

    }

    private fun switchMenu(opened: Boolean) {
        val startRadius = dpToPx(if (opened) 90 else 0)
        val endRadius = dpToPx(if (opened) 0 else 90)

        val animator = ValueAnimator.ofInt(startRadius, endRadius)
        animator.addUpdateListener { valueAnimator ->
            val radius = valueAnimator.animatedValue as Int
            fabList.forEach {
                it.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    circleRadius = radius
                }
            }
        }
        animator.start()
    }

}

fun Context.dpToPx(dp: Int): Int {
    val displayMetrics = resources.displayMetrics
    return if (dp < 0) dp else Math.round(dp * displayMetrics.density)
}
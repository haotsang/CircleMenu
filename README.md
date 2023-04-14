# 使用ConstraintLayout实现圆形菜单

## 效果图

<img src="screenshots\device-2023-04-13.gif" alt="device-2023-04-13" style="zoom:25%;" align="left" />

## activity_main.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fab_1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:tint="@color/white"
        android:scaleType="centerCrop"
        app:elevation="@null"
        app:srcCompat="@drawable/ic_alert_error"
        app:layout_constraintCircle="@id/floatingActionButton"
        app:layout_constraintCircleAngle="0"
        app:layout_constraintCircleRadius="0dp"/>
    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fab_2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:tint="@color/white"
        android:scaleType="centerCrop"
        app:elevation="@null"
        app:srcCompat="@drawable/ic_alert_ask"
        app:layout_constraintCircle="@id/floatingActionButton"
        app:layout_constraintCircleAngle="315"
        app:layout_constraintCircleRadius="0dp"/>
    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fab_3"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:tint="@color/white"
        android:scaleType="centerCrop"
        app:elevation="@null"
        app:srcCompat="@drawable/ic_alert_wait"
        app:layout_constraintCircle="@id/floatingActionButton"
        app:layout_constraintCircleAngle="270"
        app:layout_constraintCircleRadius="0dp"/>

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/floatingActionButton"
        android:layout_width="55dp"
        android:layout_height="55dp"
        android:layout_margin="24dp"
        android:clickable="true"
        android:focusable="true"
        android:scaleType="centerCrop"
        android:tint="@color/white"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:srcCompat="@drawable/ic_top_menu" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

## Java

```kotlin
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
```

[参考文章](https://blog.csdn.net/xuyonghong1122/article/details/82185347)
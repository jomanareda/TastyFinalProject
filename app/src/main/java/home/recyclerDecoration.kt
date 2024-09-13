package home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacingItemDecoration(
    private val leftSpacing: Int,
    private val rightSpacing: Int,
    private val bottomSpacing: Int,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = leftSpacing
        outRect.right = rightSpacing
        outRect.bottom = bottomSpacing

    }
}
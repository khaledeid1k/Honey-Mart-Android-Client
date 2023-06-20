package org.the_chance.honeymart.util

import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import org.the_chance.design_system.R
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.ui.BaseAdapter

@BindingAdapter(value = ["app:items"])
fun <T> setRecyclerItems(view: RecyclerView, items: List<T>?) {
    (view.adapter as BaseAdapter<T>?)?.setItems(items ?: emptyList())
}

@BindingAdapter("app:showIfTrue")
fun showIfTrue(view: View, condition: Boolean) {
    view.isVisible = condition
}

@BindingAdapter("app:changeChipColorIfSelected")
fun changeChipColorIfSelected(chip: Chip, isSelected: Boolean) {
    val context = chip.context
    if (isSelected) {
        val textColor = ContextCompat.getColor(context, R.color.white_100)
        chip.setChipBackgroundColorResource(R.color.primary_100)
        chip.setTextColor(textColor)
    } else {
        val textColor = ContextCompat.getColor(context, R.color.primary_100)
        chip.setChipBackgroundColorResource(R.color.white_100)
        chip.setTextColor(textColor)
    }
}

@BindingAdapter(value = ["app:showIfNoProducts", "app:hideIfLoading"])
fun <T> showIfEmpty(view: View, items: List<T>, condition: Boolean) {
    view.isVisible = condition == false && items.isEmpty()
}

@BindingAdapter(value = ["app:showIfFirsTrue" ,"app:showIfSecondTrue"] )
fun showIfBothLoading(view: View, condition1: Boolean, condition2: Boolean) {
    if (!condition1 && !condition2) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter("app:changeIfSelected" )
fun changeIfSelected(view: View, isSelected: Boolean) {
    val context = view.context

    when (view) {
        is CardView -> {
            val colorRes = if (isSelected) R.color.primary_100 else R.color.white_100
            val color = ContextCompat.getColor(context, colorRes)
            view.setCardBackgroundColor(color)
        }

        is ShapeableImageView -> {
            val drawableRes =
                if (isSelected) R.drawable.icon_category_white else R.drawable.icon_category
            val drawable = ContextCompat.getDrawable(context, drawableRes)
            view.setImageDrawable(drawable)
        }

        is MaterialTextView -> {
            val colorRes = if (isSelected) R.color.primary_100 else R.color.black_60
            val color = ContextCompat.getColor(context, colorRes)
            view.setTextColor(color)
        }
    }
}

@BindingAdapter("app:changeColorIfSelected")
fun changeColorIfSelected(view: View, isFavorite: Boolean) {
    val context = view.context
    when (view) {
        is CardView -> {
            val colorRes = if (isFavorite) R.color.white else R.color.primary_100
            val color = ContextCompat.getColor(context, colorRes)
            view.setCardBackgroundColor(color)
        }

        is ShapeableImageView -> {
            val drawableRes =
                if (isFavorite) R.drawable.icon_favorite_selected else R.drawable.icon_favorite_unselected
            val drawable = ContextCompat.getDrawable(context, drawableRes)
            view.setImageDrawable(drawable)
        }
    }
}


@BindingAdapter("scrollToPosition")
fun scrollToPosition(recyclerView: RecyclerView, position: Int) {
    recyclerView.scrollToPosition(position)
}

@BindingAdapter("app:validationState")
fun setValidationState(textInputLayout: TextInputLayout, validationState: ValidationState) {
    val context = textInputLayout.context

    when (validationState) {
        ValidationState.INVALID_PASSWORD -> {
            textInputLayout.error = context.getString(handleValidation(validationState))

        }

        ValidationState.INVALID_EMAIL -> {
            textInputLayout.error = context.getString(handleValidation(validationState))
        }

        ValidationState.INVALID_FULL_NAME -> {
            textInputLayout.error = context.getString(handleValidation(validationState))
        }

        ValidationState.INVALID_CONFIRM_PASSWORD -> {
            textInputLayout.error = context.getString(handleValidation(validationState))
        }

        ValidationState.BLANK_EMAIL -> {
            textInputLayout.error = context.getString(handleValidation(validationState))

        }

        ValidationState.BLANK_FULL_NAME -> {
            textInputLayout.error = context.getString(handleValidation(validationState))

        }

        ValidationState.BLANK_PASSWORD -> {
            textInputLayout.error = context.getString(handleValidation(validationState))


        }

        ValidationState.INVALID_PASSWORD_LENGTH -> {
            textInputLayout.error = context.getString(handleValidation(validationState))

        }

        else -> {
            textInputLayout.error = null
            textInputLayout.isErrorEnabled = false
        }
    }

}
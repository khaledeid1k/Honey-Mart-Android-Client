package org.the_chance.honeymart.util

import android.icu.text.DecimalFormat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import org.the_chance.design_system.R
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.feature.uistate.OrderStates
import org.the_chance.ui.BaseAdapter

@BindingAdapter(value = ["app:items"])
fun <T> setRecyclerItems(view: RecyclerView, items: List<T>?) {
    (view.adapter as BaseAdapter<T>?)?.setItems(items ?: emptyList())
}

@BindingAdapter(value = ["app:recyclerItemsByCount", "app:recyclerItemCount"])
fun <T> setRecyclerItemsByCount(view: RecyclerView, items: List<T>?, count: Int) {
    (view.adapter as BaseAdapter<T>?)?.setItems(
        items?.subList(0, items.size.coerceAtMost(count)) ?: emptyList()
    )
}

@BindingAdapter("app:showIfTrue")
fun showIfTrue(view: View, condition: Boolean) {
    view.isVisible = condition
}

@BindingAdapter("app:changeChipColorForOrderProcessing")
fun changeChipColorIfProcessingSelected(chip: Chip, orderStates: OrderStates) {
    val context = chip.context
    when (orderStates) {
        OrderStates.PROCESSING -> {
            val textColor = ContextCompat.getColor(context, R.color.white)
            chip.setChipBackgroundColorResource(R.color.primary_100)
            chip.setTextColor(textColor)
        }

        else -> {
            val textColor = ContextCompat.getColor(context, R.color.primary_100)
            chip.setChipBackgroundColorResource(R.color.white)
            chip.setTextColor(textColor)
        }
    }
}

@BindingAdapter("app:changeChipColorForOrderDone")
fun changeChipColorIfDoneSelected(chip: Chip, orderStates: OrderStates) {
    val context = chip.context
    when (orderStates) {
        OrderStates.DONE -> {
            val textColor = ContextCompat.getColor(context, R.color.white)
            chip.setChipBackgroundColorResource(R.color.primary_100)
            chip.setTextColor(textColor)
        }

        else -> {
            val textColor = ContextCompat.getColor(context, R.color.primary_100)
            chip.setChipBackgroundColorResource(R.color.white)
            chip.setTextColor(textColor)
        }
    }
}

@BindingAdapter("app:changeChipColorForOrderCanceled")
fun changeChipColorIfCanceledSelected(chip: Chip, orderStates: OrderStates) {
    val context = chip.context
    when (orderStates) {
        OrderStates.CANCELED -> {
            val textColor = ContextCompat.getColor(context, R.color.white)
            chip.setChipBackgroundColorResource(R.color.primary_100)
            chip.setTextColor(textColor)
        }

        else -> {
            val textColor = ContextCompat.getColor(context, R.color.primary_100)
            chip.setChipBackgroundColorResource(R.color.white)
            chip.setTextColor(textColor)
        }
    }
}

@BindingAdapter("app:placeHolderText")
fun setOrderPlaceHolderText(view: TextView, orderStates: OrderStates) {
    when (orderStates) {
        OrderStates.PROCESSING -> view.text =
            view.context.getString(R.string.you_dont_have_any_orders)

        OrderStates.DONE -> view.text =
            view.context.getString(R.string.you_dont_have_any_completed_orders)

        OrderStates.CANCELED -> view.text =
            view.context.getString(R.string.you_don_t_have_any_canceled_orders)
    }
}

@BindingAdapter("app:orderDialogText")
fun setOrderDialogText(view: TextView, orderStates: OrderStates) {
    when (orderStates) {
        OrderStates.PROCESSING -> view.text =
            view.context.getString(R.string.order_dialog_Cancel_Text)

        OrderStates.CANCELED -> view.text =
            view.context.getString(R.string.order_dialog_Delete_Text)

        else -> {}
    }
}

@BindingAdapter(value = ["app:showIfNoItems", "app:hideIfLoading"])
fun <T> showIfEmpty(view: View, items: List<T>, condition: Boolean) {
    view.isVisible = condition == false && items.isEmpty()
}

@BindingAdapter("app:showIfNotEmpty")
fun <T> showIfNotEmpty(view: View, items: List<T>) {
    view.isVisible = items.isNotEmpty()
}

@BindingAdapter(value = ["app:showIfFirsTrue", "app:showIfSecondTrue"])
fun showIfBothLoading(view: View, condition1: Boolean, condition2: Boolean) {
    if (!condition1 && !condition2) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter(value = ["app:showState"])
fun showState(textView: TextView, state: Int) {
    val context = textView.context
    when (state) {
        1 -> textView.text = context.getString(R.string.Processing)
        2 -> textView.text = context.getString(R.string.Done)
        3 -> textView.text = context.getString(R.string.canceled)
        4 -> textView.text = context.getString(R.string.deleted)
    }
}

@BindingAdapter("app:changeIfSelected")
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

@BindingAdapter(value = ["app:imageUrl"])
fun setImageFromUrl(view: ImageView, url: String?) {
    url.let {
        Glide
            .with(view)
            .load(url)
            .placeholder(R.drawable.placeholder_wish_list)
            .centerCrop()
            .into(view)
    }
}

@BindingAdapter("app:hideIfLoading")
fun hideIfLoading(view: View, condition: Boolean) {
    view.isVisible = !condition
}

@BindingAdapter("app:formattedPrice")
fun setFormattedPrice(view: TextView, price: Double) {
    val formattedPrice = String.format("%,.0f$", price)
    view.text = formattedPrice
}

@BindingAdapter("app:disableIfNoQuantity")
fun disableIfNoQuantity(view: View, quantity: Int?) {
    if (quantity != null) {
        view.isEnabled = quantity > 0
    }
}

@BindingAdapter("app:disableIfLoading")
fun disableIfLoading(view: View, isLoading: Boolean) {
    view.isEnabled = !isLoading
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

@BindingAdapter("app:loadImage")
fun bindImage(image: ImageView, imageURL: String?) {
    if (imageURL.isNullOrEmpty()) {
        image.setImageResource(R.drawable.product_error_placeholder)
    } else {
        image.load(imageURL) {
            placeholder(R.drawable.loading)
            error(R.drawable.product_error_placeholder)
            crossfade(true)
            crossfade(1000)
        }
    }
}

@BindingAdapter("FormatCurrency")
fun TextView.formatCurrencyWithNearestFraction(amount: Double) {
    val decimalFormat = DecimalFormat("#,##0.0'$'")
    val formattedAmount = decimalFormat.format(amount)
    text = formattedAmount
}

@BindingAdapter("app:errorState")
fun setError(view: View, error: ErrorHandler?) {
    error?.let {
        if (error is ErrorHandler.NoConnection) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
            val message = view.context.getString(R.string.connection_restored)
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
        }
    }
}

@BindingAdapter("app:emailErrorState")
fun setEmailError(textInputLayout: TextInputLayout, error: ErrorHandler?) {
    error?.let {
        if (error is ErrorHandler.AlreadyExist) {
            textInputLayout.error = textInputLayout.context.getString(R.string.email_exist)
        } else {
            textInputLayout.error = null
            textInputLayout.isErrorEnabled = false
        }
    }
}

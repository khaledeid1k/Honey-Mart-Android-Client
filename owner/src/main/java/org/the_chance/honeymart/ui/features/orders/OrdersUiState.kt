package org.the_chance.honeymart.ui.features.orders

import org.the_chance.honeymart.domain.model.MarketOrderEntity
import org.the_chance.honeymart.domain.model.OrderDetailsEntity
import org.the_chance.honeymart.domain.model.OrderProductDetailsEntity
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.features.category.CategoriesUiState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//region Ui State
data class OrdersUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val orders: List<OrderUiState> = emptyList(),
    val orderStates: OrderStates = OrderStates.ALL,
    val orderDetails: OrderUiState = OrderUiState(),
    val products: List<OrderDetailsProductUiState> = emptyList(),
    val product: OrderDetailsProductUiState= OrderDetailsProductUiState(),
    val showState: ShowState = ShowState(),
    val isSelected: Boolean = false,
    val orderId: Long = 0,
)

data class ShowState(
    val showProductDetails: Boolean = false
)

data class OrderUiState(
    val orderId: Long = 1,
    val totalPrice: String = "",
    val time: String = "",
    val userName: String = "",
    val isOrderSelected: Boolean = false,
    val state: Int = 0,
)

data class OrderDetailsProductUiState(
    val id: Long = 0L,
    val name: String = "",
    val count: Int = 0,
    val price: Double = 0.0,
    val images: List<String> = emptyList(),
)

enum class OrderStates(val state: Int) {
    ALL(0),
    PENDING(1),
    PROCESSING(2),
    DONE(3),
    CANCELED(5)
}
// endregion

// region Mappers
fun MarketOrderEntity.toOrderUiState(): OrderUiState {
    return OrderUiState(
        orderId = orderId,
        totalPrice = totalPrice.toPriceFormat(),
        state = state,
        time = date.toDateFormat(),
        userName = user.fullName
    )
}

fun Long.toDateFormat(): String {
    val dateFormat = SimpleDateFormat("d MMM hh:mm a", Locale.getDefault())
    val date = Date(this)

    return dateFormat.format(date)
}

fun OrderDetailsEntity.toOrderParentDetailsUiState(): OrderUiState {
    return OrderUiState(
        totalPrice = totalPrice.toString(),
        state = state,
        time = date,
        orderId = orderId
    )
}

fun List<OrderProductDetailsEntity>.toOrderDetailsProductUiState(): List<OrderDetailsProductUiState> {
    return map {
        OrderDetailsProductUiState(
            id = it.id,
            name = it.name,
            price = it.price,
            count = it.count,
            images = it.images
        )
    }
}

fun OrdersUiState.formatOrder(order: Long): String {
    return "Order #${order}"
}
fun OrdersUiState.errorPlaceHolderCondition() = isError
fun OrdersUiState.contentScreen() = !this.isLoading && !this.isError



fun Double.toPriceFormat(): String = "$this$"
fun Int.toCountFormat(): String = "$this items"
// endregion

// region Extensions
fun OrdersUiState.all() = orderStates == OrderStates.ALL

fun OrdersUiState.pending() = orderStates == OrderStates.PENDING

fun OrdersUiState.processing() = orderStates == OrderStates.PROCESSING

fun OrdersUiState.done() = orderStates == OrderStates.DONE

fun OrdersUiState.cancel() = orderStates == OrderStates.CANCELED

fun OrdersUiState.emptyOrdersPlaceHolder() = orders.isEmpty() && !isError && !isLoading

fun OrdersUiState.screenContent() = orders.isNotEmpty() && !isError
// endregion
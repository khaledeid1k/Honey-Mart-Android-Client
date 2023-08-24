package org.the_chance.honeymart.ui.features.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.components.ContentVisibility
import org.the_chance.honeymart.ui.features.category.composable.HoneyMartTitle
import org.the_chance.honeymart.ui.features.orders.composables.AllOrdersContent
import org.the_chance.honeymart.ui.features.orders.composables.OrderDetailsContent
import org.the_chance.honeymart.ui.features.orders.composables.ProductDetailsInOrderContent
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.features.category.errorPlaceHolderCondition
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder

@Composable
fun OrdersScreen(
    viewModel: OrdersViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(lifecycleOwner) {
        viewModel.getAllMarketOrders(OrderStates.ALL)
    }

    OrdersContent(state, viewModel)
}

@Composable
fun OrdersContent(
    state: OrdersUiState,
    listener: OrdersInteractionsListener,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        HoneyMartTitle()
        ConnectionErrorPlaceholder(
            state = state.errorPlaceHolderCondition(),
            onClickTryAgain = { listener.getAllMarketOrders(OrderStates.ALL) }
        )
        Loading(state = state.isLoading)
//        EmptyOrdersPlaceholder(
//            painter = painterResource(id = R.drawable.owner_empty_order),
//            text = stringResource(R.string.there_are_no_order_for_this_day),
//            visibilityState = state.emptyOrdersPlaceHolder(),
//
//            )
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                ContentVisibility(state = !state.showState.showProductDetails)
                {
                    AllOrdersContent(state = state, listener = listener)
                }
                ContentVisibility(state = state.products.isNotEmpty()
                        && state.showState.showProductDetails) {
                    OrderDetailsContent(
                        state = state,
                        listener = listener
                    )
                }

            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                ContentVisibility(state = state.products.isNotEmpty()
                        && !state.showState.showProductDetails) {
                    OrderDetailsContent(
                        state = state,
                        listener = listener
                    )
                }
                ContentVisibility(state = state.showState.showProductDetails) {
                    ProductDetailsInOrderContent(titleScreen =
                    stringResource(id = R.string.product_details), state =state )

                }

            }

        }



    }
}
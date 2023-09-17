package org.the_chance.honeymart.ui.features.category.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.components.EmptyPlaceholder
import org.the_chance.honeymart.ui.features.category.CategoriesInteractionsListener
import org.the_chance.honeymart.ui.features.category.CategoriesUiState
import org.the_chance.honeymart.ui.features.category.Visibility
import org.the_chance.honeymart.ui.features.category.composable.AddProductButton
import org.the_chance.honeymart.ui.features.category.composable.DropDownMenuList
import org.the_chance.honeymart.ui.features.category.composable.ProductCard
import org.the_chance.honymart.ui.composables.HoneyOutlineText
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.composables.categoryIcons
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun CategoryProductsContent(
    state: CategoriesUiState,
    listener: CategoriesInteractionsListener,
) {
    val products = state.products

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = MaterialTheme.dimens.space24,
                        start = MaterialTheme.dimens.space16,
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    if (state.categories.isNotEmpty()) {
                        Icon(
                            modifier = Modifier.size(MaterialTheme.dimens.icon48),
                            painter = painterResource(
                                id = categoryIcons[state.categories[state.position]
                                    .categoryIconUIState.categoryIconId]
                                    ?: R.drawable.icon_category
                            ),
                            contentDescription = stringResource(org.the_chance.owner.R.string.category_icon),
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = state.categories[state.position].categoryName,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HoneyOutlineText(text = "${products.size} Products")
                    DropDownMenuList(
                        onClickUpdate = { listener.resetShowState(Visibility.UPDATE_CATEGORY) },
                        onClickDelete = { listener.resetShowState(Visibility.DELETE_CATEGORY) }
                    )
                }
            }
        },
        floatingActionButton = {
            AddProductButton(
                state = state,
                onClick = listener::onClickAddProductButton
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.onTertiary,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(paddingValues)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                contentPadding = PaddingValues(vertical = MaterialTheme.dimens.space24)
            ) {
                items(products.size) { index ->
                    listener.onChangeProductScrollPosition(index)
                    products[index].let {
                        ProductCard(
                            onClick = { listener.onClickProduct(it.productId) },
                            imageUrl = it.productImage.firstOrNull() ?: "",
                            productName = it.productName,
                            productPrice = it.productPrice,
                            description = it.productDescription
                        )
                    }
                }
                item{
                    Spacer(modifier = Modifier.padding(MaterialTheme.dimens.space8))
                    Loading(state = state.isLoadingPaging)
                }
            }
            EmptyPlaceholder(
                state = products.isEmpty(),
                emptyObjectName = "Product"
            )
        }
    }
}

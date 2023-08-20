package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.delay
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.category.CategoryUiState
import org.the_chance.honeymart.ui.feature.home.CouponUiState
import org.the_chance.honeymart.ui.feature.home.HomeInteractionListener
import org.the_chance.honeymart.ui.feature.home.HomeUiState
import org.the_chance.honeymart.ui.feature.home.RecentProductUiState
import org.the_chance.honeymart.ui.feature.home.composables.coupon.CouponsItem
import org.the_chance.honeymart.ui.feature.market.MarketUiState
import org.the_chance.honeymart.ui.feature.orders.OrderUiState
import org.the_chance.honymart.ui.composables.CustomChip
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.dimens

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun HomeContentSuccessScreen(
    state: HomeUiState,
    pagerState: PagerState,
    listener: HomeInteractionListener
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
        contentPadding = PaddingValues(bottom = MaterialTheme.dimens.space16),
        columns = GridCells.Fixed(2)
    ) {

        item(span = { GridItemSpan(2) })
        {
            MarketsPager(
                markets = state.markets,
                pagerState = pagerState,
                onClickPagerItem = listener::onClickPagerItem
            )
        }

        item(
            span = { GridItemSpan(2) },
        ) {
            SearchBar(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.dimens.space16)
                    .padding(top = MaterialTheme.dimens.space8),
                icon = painterResource(id = R.drawable.ic_search),
                onClick = listener::onClickSearchBar
            )
        }

        item(
            span = { GridItemSpan(2) },
        ) {
            Markets(
                markets = state.markets,
                onClickMarket = listener::onClickPagerItem,
                onClickSeeAll = {}
            )
        }

        item(
            span = { GridItemSpan(2) },
        ) {
            Categories(
                markets = state.markets,
                categories = state.categories,
                selectedMarketId = state.selectedMarketId,
                onChipClick = listener::onClickChipCategory,
                onClickSeeAll = {}
            )
        }

        item(
            span = { GridItemSpan(2) },
        ) {
            Coupons(
                coupons = state.coupons,
                onClickCoupon = listener::onClickCouponClipped
            )
        }

        item(
            span = { GridItemSpan(2) },
        ) {
            RecentProducts(
                recentProducts = state.recentProducts,
                onClickRecentProduct = listener::onClickProductItem,
                onClickFavorite = listener::onClickFavoriteNewProduct
            )
        }

        item(
            span = { GridItemSpan(2) },
        ) {
            LastPurchases(
                lastPurchases = state.lastPurchases,
                onClickProduct = listener::onClickProductItem,
                onClickSeeAll = {}
            )
        }

        item(span = { GridItemSpan(2) }) {
            Text(
                text = stringResource(R.string.discover_products),
                style = MaterialTheme.typography.bodySmall.copy(MaterialTheme.colorScheme.onSecondary),
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space16)
            )
        }

        itemsIndexed(items = state.discoverProducts) { index, discoverProduct ->
            ProductItem(
                modifier = if (index % 2 == 0) Modifier.padding(start = MaterialTheme.dimens.space16)
                else Modifier.padding(end = MaterialTheme.dimens.space16),
                productName = discoverProduct.productName,
                productPrice = discoverProduct.productPrice.toString(),
                imageUrl = discoverProduct.productImages.takeIf { it.isNotEmpty() }
                    ?.get(0) ?: "",
                onClickFavorite = { listener.onClickFavoriteDiscoverProduct(discoverProduct.productId) },
                onClick = { listener.onClickProductItem(discoverProduct.productId) },
                isFavoriteIconClicked = discoverProduct.isFavorite
            )
        }
    }

    LaunchedEffect(key1 = state.markets.isNotEmpty()) {
        while (true) {
            delay(3000)
            pagerState.animateScrollToPage(page = (pagerState.currentPage + 1) % 3)
        }
    }
}


@Composable
private fun LastPurchases(
    lastPurchases: List<OrderUiState>,
    onClickProduct: (Long) -> Unit,
    onClickSeeAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
    ) {
        ItemLabel(
            label = stringResource(R.string.last_purchases),
            modifier = modifier
                .padding(horizontal = MaterialTheme.dimens.space16)
                .padding(
                    top =
                    MaterialTheme.dimens.space8
                ),
            onClick = onClickSeeAll
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
            contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space16)
        ) {
            items(lastPurchases) { lastPurchase ->
                LastPurchasesItems(
                    image = lastPurchase.imageUrl,
                    label = lastPurchase.marketName,
                    onClick = { onClickProduct(lastPurchase.orderId) }
                )
            }
        }
    }
}

@Composable
private fun RecentProducts(
    recentProducts: List<RecentProductUiState>,
    onClickRecentProduct: (Long) -> Unit,
    onClickFavorite: (Long) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
    ) {
        Text(
            text = stringResource(R.string.new_products),
            style = MaterialTheme.typography.bodySmall.copy(MaterialTheme.colorScheme.onSecondary),
            modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space16)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
            contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space16)
        ) {
            items(items = recentProducts, key = { it.productId }) { recentProduct ->
                ProductItem(
                    productName = recentProduct.productName,
                    productPrice = recentProduct.price.toString(),
                    imageUrl = recentProduct.productImage,
                    onClickFavorite = { onClickFavorite(recentProduct.productId) },
                    isFavoriteIconClicked = recentProduct.isFavorite,
                    onClick = { onClickRecentProduct(recentProduct.productId) }
                )
            }
        }
    }
}


@Composable
private fun Coupons(
    coupons: List<CouponUiState>,
    onClickCoupon: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(
            horizontal = MaterialTheme.dimens.space16,
            vertical = MaterialTheme.dimens.space8
        )
    )
    {
        items(coupons) { coupon ->
            CouponsItem(
                coupon = coupon,
                onClickGetCoupon = { onClickCoupon(coupon.couponId) })
        }
    }
}

@Composable
private fun Categories(
    categories: List<CategoryUiState>,
    markets: List<MarketUiState>,
    selectedMarketId: Long,
    onChipClick: (Long) -> Unit,
    onClickSeeAll: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
    ) {
        ItemLabel(
            label = stringResource(R.string.categories),
            modifier = modifier
                .padding(horizontal = MaterialTheme.dimens.space16)
                .padding(top = MaterialTheme.dimens.space8),
            onClick = onClickSeeAll
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space16),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
        ) {
            items(items = markets) { market ->
                CustomChip(
                    state = selectedMarketId == market.marketId,
                    text = market.marketName,
                    onClick = { onChipClick(market.marketId) })
            }
        }
        LazyRow(contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space16)) {
            items(categories, key = { it.categoryId }) {
                HomeCategoriesItem(label = it.categoryName)
            }
        }
    }
}

@Composable
private fun Markets(
    markets: List<MarketUiState>,
    onClickMarket: (Long) -> Unit,
    onClickSeeAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
    ) {
        ItemLabel(
            label = stringResource(R.string.markets),
            modifier = Modifier
                .padding(horizontal = MaterialTheme.dimens.space16)
                .padding(top = MaterialTheme.dimens.space8),
            onClick = onClickSeeAll
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
            contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space16)
        ) {
            items(markets) { market ->
                HomeMarketItem(
                    name = market.marketName,
                    image = market.marketImage,
                    onclick = { onClickMarket(market.marketId) }
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun MarketsPager(
    markets: List<MarketUiState>,
    pagerState: PagerState,
    onClickPagerItem: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        HorizontalPager(
            contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space12),
            pageCount = markets.size,
            state = pagerState,
        ) {
            ImageNetwork(
                imageUrl = markets[it].marketImage,
                contentDescription = "Market Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.space4)
                    .clip(shape = RoundedCornerShape(MaterialTheme.dimens.space24))
                    .height(MaterialTheme.dimens.heightItemMarketCard)
                    .clickable(onClick = { onClickPagerItem(markets[it].marketId) }),
            )
        }
        HorizontalPagerIndicator(
            itemCount = 3,
            selectedPage = pagerState.currentPage,
        )
    }
}
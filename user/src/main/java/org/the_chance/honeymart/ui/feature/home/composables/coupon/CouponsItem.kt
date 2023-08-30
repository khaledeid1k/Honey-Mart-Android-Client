package org.the_chance.honeymart.ui.feature.home.composables.coupon

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.coupons.CouponUiState
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun CouponsItem(
    coupon: CouponUiState,
    modifier: Modifier = Modifier,
    onClickGetCoupon: () -> Unit = {},
    isExpired: Boolean = false,
    isClipped: Boolean = false
) {
        Row(
        modifier = modifier.height(IntrinsicSize.Min),
    ) {
        CouponDetails(
            modifier = Modifier.fillMaxHeight(),
            productName = coupon.product.productName,
            expirationDate = coupon.expirationDateFormat,
            count = coupon.count,
            productPrice = coupon.product.priceInCurrency,
            discountPercentage = coupon.discountPriceInCurrency,
            onClick = onClickGetCoupon,
            isExpired = isExpired,
            isClipped = isClipped
        )
        CouponImage(
            modifier = Modifier.fillMaxHeight(),
            productImageUrl = coupon.imageUrl,
            couponCode = coupon.couponId.toString(),
        )
    }
}

@Composable
fun CouponDetails(
    productName: String,
    expirationDate: String,
    count: Int,
    productPrice: String,
    discountPercentage: String,
    isExpired: Boolean,
    isClipped: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val dimens = MaterialTheme.dimens

    Column(
        modifier = modifier
            .clip(
                CouponDetailsShape(
                    cornerRadius = 12.dp,
                    notchRadius = 8.dp
                )
            )
            .background(colors.onTertiary)
            .padding(
                horizontal = dimens.space16,
                vertical = dimens.space8
            ),
        verticalArrangement = Arrangement.spacedBy(dimens.space8)
    ) {
        Text(
            text = productName,
            style = MaterialTheme.typography.displaySmall.copy(color = colors.onSecondary),
        )

        CouponDataRow(
            items = listOf(
                Pair(stringResource(R.string.expiration_date), expirationDate),
            )
        )

        CouponDataRow(
            items = listOf(
                Pair(stringResource(R.string.no_deal), count.toString()),
                Pair(stringResource(R.string.price), productPrice),
                Pair(stringResource(R.string.offer_price), discountPercentage)
            )
        )

        AnimatedVisibility(visible = !isClipped) {
            Button(
                onClick = { onClick() },
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .height(21.dp)
                    .width(74.dp),
                contentPadding = PaddingValues(
                    bottom = 2.dp,
                    top = 0.dp,
                    end = 0.dp,
                    start = 0.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary,
                    disabledContentColor = colors.onPrimary,
                    disabledContainerColor = colors.primary.copy(.5F),
                )
            ) {
                Text(
                    text = stringResource(R.string.get_coupon),
                    style = typography.titleMedium,
                    color = colors.onPrimary
                )
            }
        }

        AnimatedVisibility(visible = isClipped) {
            Text(
                modifier = Modifier.padding(vertical = dimens.space4),
                text = if (isExpired) stringResource(id = R.string.expired) else stringResource(
                    id = R.string.valid
                ),
                style = typography.titleMedium,
                color = if (isExpired) colors.error else colors.primary
            )
        }
    }
}

@Composable
fun CouponDataRow(items: List<Pair<String, String>>) {

    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val dimens = MaterialTheme.dimens

    Row(horizontalArrangement = Arrangement.spacedBy(dimens.space16)) {
        items.forEachIndexed { index, item ->
            Column {
                Text(
                    text = item.first,
                    style = typography.displaySmall.copy(color = colors.onBackground)
                )

                Text(
                    text = item.second,
                    style = typography.displaySmall.copy(
                        color = if (index == 2) colors.primary
                        else colors.onSecondary
                    )
                )
            }
        }
    }
}

@Composable
fun CouponImage(
    couponCode: String,
    productImageUrl: String,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val dimens = MaterialTheme.dimens

    Column(
        modifier = modifier
            .clip(
                CouponImageShape(
                    middleNotchRadius = 8.dp,
                    sideNotchRadius = 2.dp,
                    sideNotchGap = 2.dp
                )
            )
            .background(colors.primary)
            .padding(
                top = dimens.space16, bottom = dimens.space4,
                end = dimens.space16, start = dimens.space16
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ImageNetwork(
            modifier = Modifier
                .size(dimens.itemProductImage)
                .clip(RoundedCornerShape(dimens.space12)),
            imageUrl = productImageUrl,
            contentDescription = stringResource(R.string.product_image),
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier.padding(top = dimens.space4),
            text = stringResource(R.string.coupon_code),
            style = typography.titleMedium.copy(color = colors.onPrimary)
        )
        Text(
            text = couponCode,
            style = typography.titleMedium.copy(color = colors.onPrimary)
        )
    }
}



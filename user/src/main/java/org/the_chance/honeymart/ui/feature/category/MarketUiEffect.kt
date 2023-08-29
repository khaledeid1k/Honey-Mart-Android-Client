package org.the_chance.honeymart.ui.feature.category

import org.the_chance.honeymart.ui.base.BaseUiEffect


sealed class MarketUiEffect : BaseUiEffect {
    data class ClickMarketEffect(val categoryId: Long, val marketId: Long, val position: Int) :
        MarketUiEffect()
}
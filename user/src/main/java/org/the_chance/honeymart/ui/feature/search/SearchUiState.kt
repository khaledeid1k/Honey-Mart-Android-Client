package org.the_chance.honeymart.ui.feature.search

data class SearchUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val products: List<ProductUiState> = emptyList(),
    val searchStates: SearchStates = SearchStates.RANDOM,
)

data class ProductUiState(
    val productId: Long = 0L,
    val productName: String = "",
    val productPrice: Double = 0.0,
    val marketName: String = "",
    val productImages: List<String> = emptyList()
)

enum class SearchStates(val state: Int) {
    RANDOM(1),
    ASCENDING(2),
    DESCENDING(3),
}

fun SearchUiState.random() = this.searchStates == SearchStates.RANDOM
fun SearchUiState.ascending() = this.searchStates == SearchStates.ASCENDING
fun SearchUiState.descending() = this.searchStates == SearchStates.DESCENDING

fun SearchUiState.emptySearchPlaceHolder() =
    this.products.isEmpty() && !this.isError && !this.isLoading

fun SearchUiState.screenContent() = this.products.isNotEmpty() && !this.isError




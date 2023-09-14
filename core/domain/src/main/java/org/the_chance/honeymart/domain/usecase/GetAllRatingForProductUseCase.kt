package org.the_chance.honeymart.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.the_chance.honeymart.domain.model.ReviewStatistic
import org.the_chance.honeymart.domain.model.Reviews
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetAllRatingForProductUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(productId: Long, page : Int): List<Reviews> =
        honeyMartRepository.getReviewsForProduct(page ,productId)
}
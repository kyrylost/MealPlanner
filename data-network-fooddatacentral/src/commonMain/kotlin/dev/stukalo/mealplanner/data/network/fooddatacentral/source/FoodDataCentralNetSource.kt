package dev.stukalo.mealplanner.data.network.fooddatacentral.source

import dev.stukalo.mealplanner.data.network.fooddatacentral.model.FDCSearchResponseNetModel

interface FoodDataCentralNetSource {
    suspend fun searchProduct(query: String, pageSize: Int, pageNumber: Int): FDCSearchResponseNetModel
}

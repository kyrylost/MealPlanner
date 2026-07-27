package dev.stukalo.mealplanner.data.repository.impl.mapper

import dev.stukalo.mealplanner.common.core.mapper.BaseMapper
import dev.stukalo.mealplanner.data.network.edamam.recipe.model.EdamamRecipeNetModel
import dev.stukalo.mealplanner.domain.model.recipe.IngredientDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel

internal class RecipeMapper(
    private val edamamProductMapper: EdamamProductMapper,
) : BaseMapper<EdamamRecipeNetModel, RecipeDomainModel> {

    override fun mapTo(model: EdamamRecipeNetModel): RecipeDomainModel {
        return RecipeDomainModel(
            product = edamamProductMapper.mapTo(model),
            servings = model.yield?.toInt(),
            totalTime = model.totalTime?.toInt(),
            ingredients = model.ingredients?.map {
                IngredientDomainModel(
                    name = it.food,
                    quantity = it.quantity?.toFloat(),
                    measure = it.measure,
                    weight = it.weight?.toFloat(),
                    imageUrl = it.image,
                    category = it.foodCategory
                )
            },
            ingredientLines = model.ingredientLines,
            url = model.url,
            cuisineType = model.cuisineType,
            dishType = model.dishType,
            healthLabels = model.healthLabels,
            id = model.uri?.substringAfterLast("_"),
            instructionLines = model.instructionLines
        )
    }
}

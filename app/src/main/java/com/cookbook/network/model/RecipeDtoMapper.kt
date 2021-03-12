package com.cookbook.network.model

import com.cookbook.domain.model.Recipe
import com.cookbook.domain.util.DomainMapper

class RecipeDtoMapper : DomainMapper<RecipeDto, Recipe> {
    override fun mapToDomainModel(model: RecipeDto): Recipe {
        return Recipe(
            id = model.id,
            title = model.title,
            featuredImage = model.featuredImage,
            cookingInstructions = model.cookingInstructions,
            dateAdded = model.dateAdded,
            dateUpdated = model.dateUpdated,
            description = model.description,
            ingredients = model.ingredients,
            longDateAdded = model.longDateAdded,
            longDateUpdated = model.longDateUpdated,
            publisher = model.publisher,
            rating = model.rating,
            sourceUrl = model.sourceUrl
        )
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeDto {
        return RecipeDto(
            id = domainModel.id,
            title = domainModel.title,
            featuredImage = domainModel.featuredImage,
            cookingInstructions = domainModel.cookingInstructions,
            dateAdded = domainModel.dateAdded,
            dateUpdated = domainModel.dateUpdated,
            description = domainModel.description,
            ingredients = domainModel.ingredients,
            longDateAdded = domainModel.longDateAdded,
            longDateUpdated = domainModel.longDateUpdated,
            publisher = domainModel.publisher,
            rating = domainModel.rating,
            sourceUrl = domainModel.sourceUrl
        )
    }

    fun toDomainList(initial: List<RecipeDto>): List<Recipe> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Recipe>): List<RecipeDto> {
        return initial.map { mapFromDomainModel(it) }
    }
}
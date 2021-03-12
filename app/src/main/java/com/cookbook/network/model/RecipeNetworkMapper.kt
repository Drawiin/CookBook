package com.cookbook.network.model

import com.cookbook.domain.model.Recipe
import com.cookbook.domain.util.EntityMapper

class RecipeNetworkMapper : EntityMapper<RecipeNetworkEntity, Recipe> {
    override fun mapFromEntity(entity: RecipeNetworkEntity): Recipe {
        return Recipe(
            id = entity.id,
            title = entity.title,
            featuredImage = entity.featuredImage,
            cookingInstructions = entity.cookingInstructions,
            dateAdded = entity.dateAdded,
            dateUpdated = entity.dateUpdated,
            description = entity.description,
            ingredients = entity.ingredients,
            longDateAdded = entity.longDateAdded,
            longDateUpdated = entity.longDateUpdated,
            publisher = entity.publisher,
            rating = entity.rating,
            sourceUrl = entity.sourceUrl
        )
    }

    override fun mapToEntity(domainModel: Recipe): RecipeNetworkEntity {
        return RecipeNetworkEntity(
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

    fun fromEntityList(initial: List<RecipeNetworkEntity>): List<Recipe> {
        return initial.map { mapFromEntity(it) }
    }

    fun toEntityList(initial: List<Recipe>): List<RecipeNetworkEntity> {
        return initial.map { mapToEntity(it) }
    }
}
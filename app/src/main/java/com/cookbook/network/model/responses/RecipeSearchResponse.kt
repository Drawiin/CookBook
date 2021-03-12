package com.cookbook.network.model.responses


import com.cookbook.network.model.RecipeDto
import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<RecipeDto>?
)
package com.example.photosearch.services.networks

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class PhotoItemResponse(
    @JsonProperty("id") val id: String?,
    @JsonProperty("created_at") val createdAt: String?,
    @JsonProperty("color") val color: String?,
    @JsonProperty("description") val description: String?,
    @JsonProperty("alt_description") val altDescription: String?,
    @JsonProperty("user")  val author: AuthorResponse?,
    @JsonProperty("urls") val url: UrlResponse?,
    @JsonProperty("tags")  val tags: List<TagResponse?>?
)
package com.example.photosearch.services.networks

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthorProfileImageResponse(
    @JsonProperty("small") private val small: String?,
    @JsonProperty("medium") private val medium: String?,
    @JsonProperty("large") private val large: String?
)
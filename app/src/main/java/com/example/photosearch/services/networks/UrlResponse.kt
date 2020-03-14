package com.example.photosearch.services.networks

import com.fasterxml.jackson.annotation.JsonProperty

data class UrlResponse(
    @JsonProperty("thumb") val thumb: String?,
    @JsonProperty("small") val small: String?,
    @JsonProperty("regular") val regular: String?,
    @JsonProperty("full") val full: String?,
    @JsonProperty("raw") val raw: String?
)
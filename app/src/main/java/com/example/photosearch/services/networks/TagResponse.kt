package com.example.photosearch.services.networks

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class TagResponse(
    @JsonProperty("title") val title: String?
)
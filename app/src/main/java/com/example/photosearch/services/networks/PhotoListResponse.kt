package com.example.photosearch.services.networks

import com.fasterxml.jackson.annotation.JsonProperty

data class PhotoListResponse(
    @JsonProperty("total") val total: Int?,
    @JsonProperty("total_pages") val totalPages: Int?,
    @JsonProperty("results") val results: List<PhotoItemResponse>?
)
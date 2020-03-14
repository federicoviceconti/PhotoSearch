package com.example.photosearch.services.networks

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
data class AuthorResponse(
    @JsonProperty("name") val name: String?,
    @JsonProperty("username") val username: String?,
    @JsonProperty("profile_image") val profileImage: AuthorProfileImageResponse?
)
package com.nsumba.feed_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// I use a record so Jackson writes the keys in the same order as the spec: status, db, uptime_s
public record HealthResponseDto(String status, String db, @JsonProperty("uptime_s") long uptimeS) {
}

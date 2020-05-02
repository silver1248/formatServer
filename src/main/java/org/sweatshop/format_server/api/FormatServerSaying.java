package org.sweatshop.format_server.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FormatServerSaying {
    private long id;

    private String content;

    public FormatServerSaying() {
        // Jackson deserialization
    }

    public FormatServerSaying(long id, String content) {
        this.id = id;
        this.content = content;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }
}

package org.sweatshop.format_server.config;

import java.nio.file.Path;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value
public class FilesConfig {
    @NotNull Path headerFile;
    @NotNull Path footerFile;
    @NotNull Path filesDir;
    @NotNull Path error404;

    @JsonCreator
    public FilesConfig(
            @JsonProperty("headerFile") Path headerFile, @JsonProperty("footerFile") Path footerFile,
            @JsonProperty("filesDir") Path filesDir, @JsonProperty("error404") Path error404)
    {
        this.error404 = error404;
        this.headerFile = headerFile;
        this.footerFile = footerFile;
        this.filesDir = filesDir;
    }
}

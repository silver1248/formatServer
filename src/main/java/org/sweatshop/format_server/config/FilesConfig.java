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

    @JsonCreator
    public FilesConfig(
            @JsonProperty("headerFile") Path headerFile, @JsonProperty("footerFile") Path footerFile,
            @JsonProperty("filesDir") Path filesDir)
    {
        this.headerFile = headerFile;
        this.footerFile = footerFile;
        this.filesDir = filesDir;
    }
}

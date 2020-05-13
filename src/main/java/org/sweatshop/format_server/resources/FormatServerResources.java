package org.sweatshop.format_server.resources;

import com.codahale.metrics.annotation.Timed;

import lombok.Value;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.sweatshop.format_server.api.FormatServerSaying;
import org.sweatshop.format_server.config.FilesConfig;

import java.util.stream.Collectors;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Optional;

@javax.ws.rs.Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Value
public class FormatServerResources {
    String template;
    String defaultName;
    FilesConfig filesConfig;

    @javax.ws.rs.Path("hello-world")
    @GET
    @Timed
    public FormatServerSaying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.orElse(defaultName));
        return new FormatServerSaying(2, value);
    }

    public static String readFile(Path fileName) throws FileNotFoundException, NoSuchFileException, IOException {
        return Files.lines(fileName).collect(Collectors.joining("\n", "", ""));
    }

    @javax.ws.rs.Path("hello-world2")
    @Produces(MediaType.TEXT_HTML)

    @GET
    @Timed
    public String sayHello2(@QueryParam("name") Optional<String> name) throws FileNotFoundException, IOException {
        final String value = String.format("%s %s %s"
                , readFile(filesConfig.getHeaderFile())
                , name.orElse(defaultName)
                , readFile(filesConfig.getFooterFile()));
        return value;
    }

    @javax.ws.rs.Path("/files/{file}")
    @Produces(MediaType.TEXT_HTML)
    @GET
    @Timed
    public String sayHello3(@PathParam("file") String fileName) throws FileNotFoundException, IOException {
        Path path = filesConfig.getFilesDir().resolve(fileName);
        if (path.toFile().exists()) {
            return readFile(filesConfig.getHeaderFile()) + " " 
                    + readFile(path) + " " 
                    + readFile(filesConfig.getFooterFile());
        } else {
            return readFile(filesConfig.getHeaderFile()) + " " 
                    + readFile(filesConfig.getError404()) + " " 
                    + readFile(filesConfig.getFooterFile());
        }
    }
}

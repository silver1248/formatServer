package org.sweatshop.format_server.resources;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.sweatshop.format_server.api.FormatServerSaying;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@javax.ws.rs.Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class FormatServerResources {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;
    private final Path headerFile;
    private final Path footerFile;
    private final Path filesDir;

    public FormatServerResources(String template, String defaultName, Path headerFile, Path footerFile, Path filesDir) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
        this.headerFile = headerFile;
        this.footerFile = footerFile;
        this.filesDir = filesDir;
    }

    @javax.ws.rs.Path("hello-world")
    @GET
    @Timed
    public FormatServerSaying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.orElse(defaultName));
        return new FormatServerSaying(counter.incrementAndGet(), value);
    }

    /*
     * 1. make this return header+name+footer WITHOUT changing the one above
     * 2. make two files, src/main/resources/header.html & src/main/resources/footer.html and return what's in them with name in the middle
     * 3. have the paths of those files in the config.yml, and pass them through to here
     * 4. learn how to intercept the path, (from files/ so localhost:8080/files/a/ would mean find the dir/a/) and print that out
     * maybe use filesDir.resolve("example")
     * 5. put files in a directory, and find the file at that relative path, and return header file contents, path file contents, footer file contents
     * 6. if the path doesn't exist, header, error, footer (you'll need to pass an error file path in as well)
     */
    public static String readLine(Path fileName) throws FileNotFoundException, IOException {
        return Files.lines(fileName).collect(Collectors.joining("\n", "", ""));
    }

    @javax.ws.rs.Path("hello-world2")
    @Produces(MediaType.TEXT_HTML)
    
    @GET
    @Timed
    public String sayHello2(@QueryParam("name") Optional<String> name) throws FileNotFoundException, IOException {
        final String value = String.format("%s %s %s", readLine(headerFile), name.orElse(defaultName), readLine(footerFile));
        return value;
    }

    @javax.ws.rs.Path("/files/{file}")
    @Produces(MediaType.TEXT_HTML)
    @GET
    @Timed
    public String sayHello3(@PathParam("file") String file) throws FileNotFoundException, IOException {
        String combinedPath = filesDir.resolve(file).toString();
        String newPath = "dir" + combinedPath.substring(25);
        return filesDir.resolve(file).toString();
    }
}

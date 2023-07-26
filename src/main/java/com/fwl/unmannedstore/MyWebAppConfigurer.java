package com.fwl.unmannedstore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.upload.path.relative}")
    private String relativeUploadPath;

    // Link relativeUploadPath to the actual uploadPath
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(relativeUploadPath).addResourceLocations("file:" + uploadPath);
    }

}

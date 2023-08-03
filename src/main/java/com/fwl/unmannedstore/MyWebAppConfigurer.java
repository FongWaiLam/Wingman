package com.fwl.unmannedstore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
    @Value("${file.upload.path}")
    private String productPhotoUploadPath;

    @Value("${file.upload.path.relative}")
    private String productPhotoRelativeUploadPath;


    @Value("${profile.upload.path}")
    private String profilePhotoUploadPath;

    @Value("${profile.upload.path.relative}")
    private String profilePhotoRelativeUploadPath;

    // Link relativeUploadPath to the actual uploadPath
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(productPhotoRelativeUploadPath).addResourceLocations("file:" + productPhotoUploadPath);
        registry.addResourceHandler(profilePhotoRelativeUploadPath).addResourceLocations("file:" + profilePhotoUploadPath);
    }

}

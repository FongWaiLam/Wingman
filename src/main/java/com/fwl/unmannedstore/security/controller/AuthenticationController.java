package com.fwl.unmannedstore.security.controller;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.fwl.unmannedstore.controller.requestResponse.Message;
import com.fwl.unmannedstore.security.authentication.*;
import com.fwl.unmannedstore.security.config.AuthenticationService;
import com.fwl.unmannedstore.security.entity.Role;
import com.fwl.unmannedstore.security.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/usms")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    // Endpoints to create a new acc and authenticate an existing user

    // Delegate the register and authenticate to a service
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

//    @Value("${profile.upload.path}")
//    private String uploadPath;

    @Autowired
    private BlobServiceClient blobServiceClient;

    @Value("${azure.storage.blob.container-name}")
    private String containerName;

//
//    // Helper Method to save a photo
//    private String savePhoto(int prodId, MultipartFile photo) throws IOException {
//        String photoName = photo.getOriginalFilename();
//        String productUploadPath = uploadPath + File.separator + prodId;
//        log.info("productUploadPath: " + productUploadPath);
//
//        File targetedFilePath = new File(productUploadPath, photoName);
//        if (!targetedFilePath.getParentFile().exists()) {
//            targetedFilePath.getParentFile().mkdir();
//        }
//        int indexOfSuffix = photo.getOriginalFilename().lastIndexOf(".");
//        String photoExtension = photoName.substring(indexOfSuffix);
//        log.info("photoExtension: " + photoExtension);
//
//        if(targetedFilePath.exists() && targetedFilePath.isFile()){
//            log.info("targetedFilePath (Already Exist): " + targetedFilePath.getAbsolutePath());
//            photoName = photoName.substring(0,indexOfSuffix) + new Timestamp(System.currentTimeMillis()).toString().replace(":","") + photoExtension;
//        }
//        log.info("photoName: " + photoName);
//
//        File newPhoto = new File(productUploadPath, photoName);
//        photo.transferTo(newPhoto);
//        return photoName;
//    }

    // Save profile to Azure Blob Storage
    public String savePhoto(int prodId, MultipartFile photo) throws IOException {
        String photoName = FilenameUtils.getBaseName(photo.getOriginalFilename());
        String photoExtension = FilenameUtils.getExtension(photo.getOriginalFilename());
        String photoRename =  photoName
                + new Timestamp(System.currentTimeMillis()).toString().replace(":","").
                replace(".", "") + "."
                + photoExtension;
        String blobName = "profile" + File.separator + prodId + File.separator + photoRename;
        log.info("Renamed Profile: " + blobName);

        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(blobName);
        blobClient.upload(photo.getInputStream(), photo.getSize(), true);
        return photoRename;
    }

    @Transactional
    // Only business user can create a new manager (user) to manage his or her shop
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestParam("firstname") String firstname,
                                         @RequestParam("lastname") String lastname,
                                         @RequestParam("role") Role role,
                                         @RequestParam("email") String email,
                                         @RequestParam("password") String password,
                                         @RequestParam("profile") MultipartFile profile) {
        RegisterRequest registerRequest = new RegisterRequest(firstname, lastname, email, password, role);
        User user = authenticationService.register(registerRequest);
        log.info("User registered: " + user);
        String photoName;
        int userId = user.getId();
        try {
            if (profile != null && !profile.isEmpty()) {
                photoName = savePhoto(userId, profile);
                if (photoName != null) {
                    user.setProfile(photoName);
                }
            }
        } catch (IOException e) {
            log.error("Profile photo " + profile.getOriginalFilename() + " cannot be saved.");
        }
        authenticationService.save(user);
        // After registered, signin to the new account automatically
        // (Not implemented, as users are created by business owner (admin)
//        AuthenticationRequest authenticationRequest = new AuthenticationRequest(registerRequest.getEmail(),registerRequest.getPassword());
//        UserInformation userInformation = signIn(authenticationRequest).getBody();

        return ResponseEntity.ok(user);
//        return "redirect:/usms";
    }

//    @PostMapping("/register")
//    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest request) {
//        return ResponseEntity.ok(authService.register(request));
//    }

    // RegisterRequest holds the registration information
    @PostMapping("/signin")
    public ResponseEntity<UserInformation> signIn(@RequestBody AuthenticationRequest request) {
        log.info("authenticate entered");

//        AuthenticationRequest userLoginRequest = new AuthenticationRequest(email, password);
        log.info("Authentication Request " + request);
        Authentication authentication = authenticationService.authenticate(request);

        log.info("Authentication User: " + authentication.getPrincipal());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        log.info("SecurityContextHolder: " + SecurityContextHolder.getContext());
        ResponseCookie jwtCookie = jwtService.generateJwtCookie(user);

        List<String> roles = user.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInformation(user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole()));
    }

    @PostMapping("/signout")
    public ResponseEntity<Message> signOut() {
        ResponseCookie cookie = jwtService.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new Message("You've been signed out!"));
    }

}

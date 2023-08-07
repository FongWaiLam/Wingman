package com.fwl.unmannedstore.controller;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.service.ProductService;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Controller
@Log4j2
@RequestMapping("/usms")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private EntityManager entityManager;
//    @Value("${file.upload.path}")
//    private String uploadPath;
    @Autowired
    private BlobServiceClient blobServiceClient;

    @Value("${azure.storage.blob.container-name}")
    private String containerName;

    @GetMapping("/add_product")
    public String addProduct() {
        return "add_product";
    }

    // Helper Method to save a photo
//    private String savePhoto(int prodId, MultipartFile photo) throws IOException{
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

    // Save to Azure Blob Storage
    public String savePhoto(int prodId, MultipartFile photo) throws IOException {
        String photoName = FilenameUtils.getBaseName(photo.getOriginalFilename());
        String photoExtension = FilenameUtils.getExtension(photo.getOriginalFilename());
        String photoRename =  photoName
                            + new Timestamp(System.currentTimeMillis()).toString().replace(":","").replace(".", "") + "."
                            + photoExtension;
        String blobName = "product" + File.separator + prodId + File.separator + photoRename;
        log.info("Renamed Photo: " + blobName);

        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(blobName);
        blobClient.upload(photo.getInputStream(), photo.getSize(), true);
        return photoRename;
    }

    @Transactional
    @PostMapping("/save_product")
    public String addProduct(@ModelAttribute Product product,
                             @RequestParam("photoFile") MultipartFile photoFile,
                             @RequestParam("photoFiles") MultipartFile[] photoFiles) {
        String photoName;
        entityManager.persist(product);
        int prodId = product.getProdId();
        try {
            if (photoFile != null && !photoFile.isEmpty()) {
                photoName = savePhoto(prodId, photoFile);
                if (photoName != null) {
                    product.setPhoto(photoName);
                }
            }
        } catch (IOException e) {
            log.error("Product photo " + photoFile.getOriginalFilename() + " cannot be saved.");
        }

            for (MultipartFile photo: photoFiles) {
                try {
                    if (photo != null && !photo.isEmpty()) {
                        photoName = savePhoto(prodId, photo);
                        if (photoName != null) {
                            product.addPhotos(photoName);
                        }
                    }
                } catch (IOException e) {
                    log.error("Photo " + photo.getOriginalFilename() + " cannot be saved.");
                }
            }

        productService.save(product);
        return "redirect:/usms/products";
    }

    @GetMapping("/products")
    public ModelAndView getProducts() {
        List<Product> allProducts = productService.getAllProducts();
        return new ModelAndView("products", "allProducts", allProducts);
    }

    @GetMapping("/product/{prodId}")
    public ModelAndView getProduct(@PathVariable("prodId") int prodId) {
        Product product = productService.getProductById(prodId);
        return new ModelAndView("product", "product", product);
    }

    @GetMapping("/product/{prodId}/update_product")
    public String showUpdateProductForm(@PathVariable int prodId, Model model) {
        Product product = productService.getProductById(prodId);
        model.addAttribute("product", product);
        return"update_product";
    }

    @PostMapping("/product/{prodId}/update_product_info")
    public String updateProductById(@PathVariable int prodId, @ModelAttribute Product updatedProduct) {
        Product product = productService.getProductById(prodId);
        product.setName(updatedProduct.getName());
        product.setActive(updatedProduct.isActive());
        product.setCategory(updatedProduct.getCategory());
        product.setPrice(updatedProduct.getPrice());
        product.setDescription(updatedProduct.getDescription());
        productService.save(product);
        return"redirect:/usms/products";
    }

    @GetMapping("/product/{prod_id}/deactivate_product")
    public String deleteProductById(@PathVariable int prod_id) {
        productService.deactivateById(prod_id);
        return "redirect:/usms/products";
    }

}

package com.fwl.unmannedstore.controller;

import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.service.ProductService;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
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
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private EntityManager entityManager;

    @Value("${file.upload.path}")
    private String uploadPath;

    @GetMapping("/add_product")
    public String addProduct() {
        return "add_product";
    }

    // Helper Method to save a photo
    private String savePhoto(int prodId, MultipartFile photo) throws IOException{
        String photoName = photo.getOriginalFilename();
        String productUploadPath = uploadPath + File.separator + prodId;
        log.info("productUploadPath: " + productUploadPath);

        File targetedFilePath = new File(productUploadPath, photoName);
        if (!targetedFilePath.getParentFile().exists()) {
            targetedFilePath.getParentFile().mkdir();
        }
        int indexOfSuffix = photo.getOriginalFilename().lastIndexOf(".");
        String photoExtension = photoName.substring(indexOfSuffix);
        log.info("photoExtension: " + photoExtension);

        if(targetedFilePath.exists() && targetedFilePath.isFile()){
            log.info("targetedFilePath (Already Exist): " + targetedFilePath.getAbsolutePath());
            photoName = photoName.substring(0,indexOfSuffix) + new Timestamp(System.currentTimeMillis()).toString().replace(":","") + photoExtension;
        }
        log.info("photoName: " + photoName);

        File newPhoto = new File(productUploadPath, photoName);
        photo.transferTo(newPhoto);
        return photoName;
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
            photoName = savePhoto(prodId, photoFile);
            if (photoName != null) {
                product.setPhoto(photoName);
            }
        } catch (IOException e) {
            log.error("Photo " + photoFile.getOriginalFilename() + " cannot be saved.");
        }
        for (MultipartFile photo: photoFiles) {
            try {
                photoName = savePhoto(prodId, photo);
                if (photoName != null) {
                    product.addPhotos(photoName);
                }
            } catch (IOException e) {
                log.error("Photo " + photo.getOriginalFilename() + " cannot be saved.");
            }
        }
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("/products")
    public ModelAndView getProducts() {
        List<Product> allProducts = productService.getAllProducts();
        return new ModelAndView("products", "allProducts", allProducts);
    }

    @GetMapping("/product/{prodId}")
    public ModelAndView getProduct(int prodId) {
        Product product = productService.getProductById(prodId);
        return new ModelAndView("product", "product", product);
    }

    @RequestMapping("/updateProduct/{prodId}")
    public String editBookById(@PathVariable int prodId, Model model) {
        Product product = productService.getProductById(prodId);
        model.addAttribute("product", product);
        return"update_product";
    }

    @GetMapping("/deactivate_Product/{prod_id}")
    public String deleteBookById(@PathVariable int prod_id) {
        productService.deactivateById(prod_id);
        return "redirect:/products_inventory";
    }

}

package com.ra.controller;

import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.service.CategoryService;
import com.ra.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {
    @Value("${path-upload}")
    private String pathUpload;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    // lấy toàn bộ dữ liệu trong table product
    @GetMapping("/product")
    public String product(Model model) {
        List<Product> productList = productService.getAll();
        model.addAttribute("productList", productList);
        return "product/index";
    }

    // thêm mới
    @GetMapping("/add-product")//đưa về trang thêm mới
    public String addProduct(Model model) {
        List<Category> listCategory = categoryService.getAll();
        model.addAttribute("listCategory", listCategory);
        Product product = new Product();
        model.addAttribute("product", product);
        return "product/addProduct";
    }

    @PostMapping("/add-product")
    public String create(
            @ModelAttribute("product") Product product,
            @RequestParam("img") MultipartFile file
    ) {
        // upload ảnh
        String fileName = file.getOriginalFilename();// lấy về tên file
        try {
            FileCopyUtils.copy(file.getBytes(), new File(pathUpload + fileName));

            // lưa tên file vào database
            product.setImage(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productService.save(product);
        return "redirect:/product";
    }

    // xóa dữ liệu
    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Xóa thành công");
        return "redirect:/product";
    }

    // cập nhật dữ liệu
    @GetMapping("/update-product/{id}")
    public String update(@PathVariable Long id, Model model

    ) {
        Product product = productService.findById(id);
        List<Category> listCategory = categoryService.getAll();
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("product", product);
        return "/product/editProduct";
    }

    @PostMapping("/update-product")
    public String updateProduct(@ModelAttribute("product") Product product,
                                RedirectAttributes redirectAttributes,
                                @RequestParam("img") MultipartFile file
    ) {
        String fileName = file.getOriginalFilename();// lấy về tên file
        try {
            FileCopyUtils.copy(file.getBytes(), new File(pathUpload + fileName));

            // lưa tên file vào database
            product.setImage(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productService.save(product);
            redirectAttributes.addFlashAttribute("success", "Cập nhật thành công");
        return "redirect:/product";
    }
}
//    @PostMapping("/update-product")
//    public String updateProduct(@ModelAttribute("product") Product product,
//                                RedirectAttributes redirectAttributes) {
//        if (productService.save(product)) {
//
//            redirectAttributes.addFlashAttribute("success", "Cập nhật thành công");
//            return "redirect:/product";
//        }
//        return "redirect:/product";
//    }
//}

//    @GetMapping("/update-product/{id}")
//    public String update(@PathVariable Long id, Model model) {
//        Product product = productService.findById(id);
//        List<Category> listCategory = categoryService.getAll();
//        model.addAttribute("listCategory", listCategory);
//        model.addAttribute("product", product);
//        return "/product/editProduct";
//    }


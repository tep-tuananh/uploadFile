package com.ra.controller;

import com.ra.model.entity.Category;
import com.ra.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // lấy toàn bộ dữ liệu trong bảng category
    @GetMapping("/category")
    public String category(Model model) {// mang dữ liệu đi thì dùng model
        List<Category> list = categoryService.getAll();
        model.addAttribute("list", list);
        return "category/index";
    }

    // thêm mới dữ liệu vào bảng category
    @GetMapping("/add-category") // lấy giao diện
    public String save(Model model) { // model để mang đối tượng đi
        Category category = new Category();
        model.addAttribute("category", category);
        return "category/add";
    }

    @PostMapping("/add-category")
    public String create(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        return "redirect:/category";
    }

    // xóa dữ liệu
    @GetMapping("/category/delete-category/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoryService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Xóa thành công");
        return "redirect:/category";
    }
    // Sửa dữ liệu
    @GetMapping("/category/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "/category/edit";
    }

    @PostMapping("/update-category")
    public String update(@ModelAttribute("category") Category category, RedirectAttributes redirectAttrs) {
        if (categoryService.save(category)) {
            redirectAttrs.addFlashAttribute("success", "Cập nhật thành công");
            return "redirect:/category";
        }
        return "redirect:/category";
    }
}


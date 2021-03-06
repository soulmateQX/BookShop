package org.som.bookshop.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.som.bookshop.entity.Book;
import org.som.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @outhor soulmate
 * @create 2019/10/31-23:09
 * @Description:
 */
@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    /**
     * 图书主页controller
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    /**
     * 获取图书信息controller
     */
    @RequestMapping("/getBookData")
    public String getBookData(Model model,Integer page,Integer category){
        //mybatis-plus分页功能
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category",category);
        IPage<Book> iPage = bookService.page(new Page<>(page,4),queryWrapper);
        //查询书本信息,查出来的信息放在booKList
        model.addAttribute("bookList",iPage.getRecords());
        //记录前一页的值和后一页的值
        model.addAttribute("pre",iPage.getCurrent()-1);
        model.addAttribute("next",iPage.getCurrent()+1);
        //记录当前页的值和页面的值
        model.addAttribute("cur",iPage.getCurrent());
        //总页数的值
        model.addAttribute("lastPages",iPage.getPages());

        //类别通过前台取值
        model.addAttribute("category",category);
        return "bookData";
    }

    /**
     * 图书列表页
     */
    @RequestMapping("/bookList")
    public String bookList(String category,Model model){
        model.addAttribute("category",category);
        return "books_list";

    }

    /**
     * 获取图书列表数据
     */
    @RequestMapping("/getBookListData")
    public String getBookListData(String category,Integer page,Integer pageSize, Model model){
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category",category);
        IPage<Book> iPage = bookService.page(new Page<Book>(page,pageSize),queryWrapper);
        //记录前一页的值和后一页的值
        model.addAttribute("pre",iPage.getCurrent()-1);
        model.addAttribute("next",iPage.getCurrent()+1);
        //记录当前页的值和页面的值
        model.addAttribute("cur",iPage.getCurrent());
        //总页数的值
        model.addAttribute("pages",iPage.getPages());
        model.addAttribute("bookList",iPage.getRecords());
        model.addAttribute("category",category);
        model.addAttribute("pageSize",pageSize);
        return "booksListData";
    }

    //查询书的详情数据
    @RequestMapping("/detail")
    public String getDetail(Integer id,Model model){
        Book book = bookService.getById(id);
        model.addAttribute("book",book);

        return "details";
    }

}

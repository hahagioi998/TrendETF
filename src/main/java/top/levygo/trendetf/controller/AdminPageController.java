package top.levygo.trendetf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description：访问首页及帮助页
 * @author：LevyXie
 * @create：2022-03-10 21:21
 */
@Controller
@RequestMapping("/trend")
public class AdminPageController {
    @GetMapping(value="/")
    public String main(){
        return "main";
    }
    @GetMapping(value="/help")
    public String help(){
        return "help";
    }
}

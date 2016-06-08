package oe.roma.photodoc.controllers;


import oe.roma.photodoc.services.ContractService;
import oe.roma.photodoc.services.RemService;
import oe.roma.photodoc.services.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by us8610 on 19.06.14.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @Resource(name="contractService")
    private ContractService contractService;

    @Resource(name="remService")
    private RemService remService;

    @Resource(name="reportService")
    private ReportService reportService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model, HttpSession session){
        model.addAttribute("active", "index");
        model.addAttribute("rems",remService.getRems());
        return "index";
    }

    @RequestMapping(value = "/report",method = RequestMethod.GET)
    public String report(ModelMap model){
        model.addAttribute("active", "index");
        model.addAttribute("rems",remService.getRems());
        return "index";
    }
}

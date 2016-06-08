package oe.roma.photodoc.controllers;

import oe.roma.photodoc.services.ContractService;
import oe.roma.photodoc.services.RemService;
import oe.roma.photodoc.services.TypeDocService;
import oe.roma.photodoc.utils.ListFiles;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by us8610 on 19.06.14.
 */
@Controller
@RequestMapping("/contract")
public class ContractController {



    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @Resource(name="contractService")
    private ContractService contractService;

    @Resource(name="remService")
    private RemService remService;

    @Resource(name="typeDocService")
    private TypeDocService typeDocService;

    @RequestMapping(method = RequestMethod.GET)
    public String searchByCN(@RequestParam(value = "counterNum",required = false) String counterNum,@RequestParam(value = "contractNum",required = false) String contractNum,@RequestParam(value = "value_id", required = false)String value_id, @RequestParam(value = "rem_id",required = false) Integer rem_id, @RequestParam(value = "start_date", required = false) Date start_date, @RequestParam(value = "end_date",required = false) Date end_date, ModelMap model) {

        if(counterNum == null && rem_id == null) {
            model.addAttribute("rems",remService.getRems());
            model.addAttribute("active", "search");
            return "search";
        }
        model.addAttribute("searchResult",contractService.customerList(contractNum+"%",counterNum+"%",rem_id,start_date,end_date));
        model.addAttribute("rem_id",rem_id);
        model.addAttribute("active", "search");
        return "searchResult";
    }



    @RequestMapping(value="/addDocument",method = RequestMethod.GET)
    public String addDocument(
            @RequestParam(value = "counter_id", required = false) Integer counter_id,
            @RequestParam(value = "rem_id", required = false) Integer rem_id,
            @RequestParam(value = "value_id", required = false) Integer value_id,
            @RequestParam(required = false) String counterNum,
            @RequestParam(required = false) String contractNum,
            @RequestParam(required = false) String start_date,
            @RequestParam(required = false) String end_date,
            ModelMap model) {

        model.addAttribute("counter_id",counter_id);
        model.addAttribute("rem_id",rem_id);
        model.addAttribute("value_id",value_id);
        model.addAttribute("search_counter",counterNum);
        model.addAttribute("search_contract",contractNum);
        model.addAttribute("start_date",start_date);
        model.addAttribute("end_date",end_date);
        model.addAttribute("typesDoc", typeDocService.getTypes());
        model.addAttribute("active", "search");
        return "addDocument";
    }


    //Сторінка на яку повертаємось після додавання документа, сюди додати поле value_type_id
    @RequestMapping(value="/addDocument",method = RequestMethod.POST)
    public String addDocumentDo(
            @RequestParam Integer counter_id,
            @RequestParam Integer rem_id,
            @RequestParam Integer type_id,
            @RequestParam Integer value_type_id,
            @RequestParam Date date,
            @RequestParam String user_name,
            @RequestParam(required = false) String search_counter,
            @RequestParam(required = false) String search_contract,
            @RequestParam(required = false) String start_date,
            @RequestParam(required = false) String end_date,
            @RequestParam(value = "file", required = false) MultipartFile[] source) throws IOException {
        for(MultipartFile src : source){
            contractService.saveImageToDB(counter_id,rem_id,type_id,date,src,value_type_id, user_name);
        }
        return "redirect:/contract?counterNum="+search_counter+"&contractNum="+search_contract+"&rem_id="+rem_id+
                "&start_date="+start_date+"&end_date="+end_date+"&document_type="+"&value_id="+value_type_id;
    }

    @RequestMapping(value = "/getFiles", method = RequestMethod.GET)
    public @ResponseBody ListFiles getFilesByCountID(@RequestParam int count_id,
                                                     @RequestParam(required = false) int rem_id,
                                                     @RequestParam(required = false) Date start_date,
                                                     @RequestParam(required = false) Date end_date)
    {
        ListFiles listFiles = new ListFiles();
        listFiles.addAll(contractService.getFilesByCountID(count_id, rem_id,start_date,end_date));
        return listFiles;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> delete(@RequestParam int id, @RequestParam String filename){
        boolean isDeleted = contractService.delete(id, filename);
        if (!isDeleted){
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }




}

package oe.roma.photodoc.controllers;



import oe.roma.photodoc.domain.User;
import oe.roma.photodoc.services.RemService;
import oe.roma.photodoc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

/**
 * Created by us8610 on 19.06.14.
 */
@Controller
@RequestMapping("/login")
@SessionAttributes({"user"})
public class LoginController {
    @Autowired
    private UserService userService;

    @Resource(name="remService")
    private RemService remService;


        @RequestMapping(method = RequestMethod.GET)
            public String login(ModelMap model){
            model.addAttribute("rems",remService.getRems());
            return "login";
        }

        @RequestMapping(method = RequestMethod.GET,value = "/logout")
         public String logout(HttpSession session,ModelMap model){
            session.invalidate();
            if(model.containsAttribute("user")) model.remove("user");
            return "redirect:/";
        }

        @RequestMapping()
        public String loginPage(
                @RequestParam(required = false) String error,
                @RequestParam(required = false) String logout,
                HttpSession session,
                ModelMap model) {
            if (error != null) {
                model.addAttribute("msg", "Помилка входу!");
            }
            if (logout != null) {
                if(session!=null){
                    session.invalidate();
                }
            }
            return "login";
        }

        @RequestMapping(method = RequestMethod.POST)
        public String login(HttpSession session, @RequestParam String login,
                            @RequestParam String password, ModelMap model) {

            User user = null;
            try {
                user = userService.getUser(login, password);
            } catch (NamingException e) {
                e.printStackTrace();
            }
            user = userService.getUser(login);
                /*Старий функціонал, потрібен для формування юзера з нашої бази*/
                /*  Присвоюємо юзеру якого ми отримали ззовні
                *   властивості юзера, якого ми отримали з нашої бази*/
            user.getPermission();
            user.getId();
            user.getRem();
            user.getFull_name();
            user.getEmail();
                //////////////////////////////////////////////////
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("login", login);

            if (!userService.checkAuthority(login) || user == null) {
                System.out.println("not found user");
                return "redirect:/login";
            }

            if (session != null) {
                session.setAttribute("user", user);
            }
            System.out.println(user.toString());
            return "redirect:/";
        }
}

package com.example.Authentication.Controller;

import com.example.Authentication.Models.Posts;
import com.example.Authentication.Models.SiteUser;
import com.example.Authentication.Repositories.PostsRepo;
import com.example.Authentication.Repositories.SiteUserRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AuthenticationController {
    @Autowired
    SiteUserRepo siteUserRepo;
    @Autowired
    PostsRepo postsRepo;

    @GetMapping("/homeAfterAuth")
    public String getHomePage(HttpServletRequest request, Model m)
    {
        HttpSession session=request.getSession();
        String username=session.getAttribute("username").toString();
        m.addAttribute("username",username);

        SiteUser userFromDb = siteUserRepo.findByUsername(username);
        List<Posts> userPosts = userFromDb.getPosts();
        m.addAttribute("userPosts", userPosts);

        return "/home.html";
    }

    @GetMapping("/")
    public String getHomePageBeforeAuth()
    {
        return "/homeBeforeAuth.html";
    }


    @GetMapping("/signup")
    public String getSignupPage()
    {
        return "/signup.html";
    }
    @PostMapping("/signup")
    public RedirectView signUpUser(String username, String password)
    {
        String hashedPassword= BCrypt.hashpw(password, BCrypt.gensalt(12));
        SiteUser siteUser=new SiteUser(username,hashedPassword);
        siteUserRepo.save(siteUser);
        return new RedirectView("/login");
    }
    @GetMapping("/login")
    public String getLoginPage() {
        return "/login.html";
    }
    @PostMapping("/login")
    public RedirectView logInUser(HttpServletRequest request,String username, String password)
    {
        SiteUser userFromDb=siteUserRepo.findByUsername(username);
        if((userFromDb==null) || !(BCrypt.checkpw(password,userFromDb.getPassword())))
        {
            return new RedirectView("/login");
        }
        HttpSession session=request.getSession();
        session.setAttribute("username",username);

        return new RedirectView("/homeAfterAuth");
    }

    @PostMapping("/addpost")
    public RedirectView addPost(HttpServletRequest request, String postText) {

        HttpSession session = request.getSession();
        String username = session.getAttribute("username").toString();

        SiteUser userFromDb = siteUserRepo.findByUsername(username);

        Posts post = new Posts(postText);
        post.setUserId(userFromDb);
        postsRepo.save(post);

        return new RedirectView("/homeAfterAuth");
    }


    @PostMapping("/logout")
    public RedirectView logoutUser(HttpServletRequest request)
    {
        HttpSession session=request.getSession();
        session.invalidate();

        return new RedirectView("/login");
    }
}


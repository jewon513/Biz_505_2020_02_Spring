package com.biz.sec.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.sec.domain.UserDetailsVO;
import com.biz.sec.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping(value="/user")
public class UserController {
	
	private final UserService userService;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		return "auth/login";
	}

	@RequestMapping(value="/join",method=RequestMethod.GET)
	public String join() {
		return "auth/join";
	}

	@ResponseBody
	@RequestMapping(value="/join",
				method=RequestMethod.POST,
				produces = "text/html;charset=UTF-8")
	public String join(String username, String password) {

		log.debug("아이디 {}, 비번 {}",
				username, 
				password);
		
		userService.insert(username, password);
		
		// return "redirect:/";
		return String.format("아이디 : <b>%s</b>, 비번 : <b>%s</b>", 
						username, password);
	
	}
	
	@ResponseBody
	@RequestMapping(value = "password", method = RequestMethod.POST)
	public String password(String pass) {
		
		boolean ret = userService.checkPassword(pass);
		
		if(ret) return "PASS_OK";
		
		
		return "PASS_FAIL";
		
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/idcheck",method=RequestMethod.POST)
	public String idcheck(String username) {
		
		boolean ret = userService.isExistsUserName(username);
		if(ret) {
			return "Exists".toUpperCase(); // EXISTS
		}
		return "NonExists".toUpperCase(); // NONEXISTS
	
	}
	
	@ResponseBody
	@RequestMapping(value="",method=RequestMethod.GET)
	public String user() {
		return "user HOME";
	}
	
	@RequestMapping(value="/mypage",method=RequestMethod.GET)
	public String mypage(Principal principal, Model model) {
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
		
		UserDetailsVO userVO = (UserDetailsVO) usernamePasswordAuthenticationToken.getPrincipal();
		
		userVO.setAuthorities(usernamePasswordAuthenticationToken.getAuthorities());
		
		model.addAttribute("userVO", userVO);
		
		return "auth/user_view";
		
	}
	
	@RequestMapping(value = "/mypage", method = RequestMethod.POST)
	public String mypageUpdate(UserDetailsVO userVO, String[] auth, Model model) {
		
		
		int ret = userService.update(userVO,auth);
		
		return "redirect:/user/mypage";
	}

}

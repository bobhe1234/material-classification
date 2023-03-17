package com.jswy.interfaces.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jswy.application.service.UserService;
import com.jswy.domain.generic.demo.model.User;
import com.jswy.domain.generic.demo.repository.UserRepository;
import com.jswy.interfaces.assembler.DataResponse;
import com.jswy.interfaces.assembler.UserAssembler;
import com.jswy.interfaces.assembler.UserDto;

import io.swagger.annotations.Api;

/**
 * 用户控制器
 */
@RestController
@Api(tags = "用户管理")
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	/**
	 * 保存用户并返回到用户列表页面
	 * 
	 * @param user
	 * @param errors
	 * @param model
	 * @return
	 */
	@PostMapping
	public ModelAndView saveUser(@Valid User user, Errors errors, Model model) {
		if (errors.hasErrors()) {
			model.addAttribute("user", user);
			if (errors.getFieldError("name") != null) {
				model.addAttribute("nameError", errors.getFieldError("name").getDefaultMessage());
			}
			if (errors.getFieldError("email") != null) {
				model.addAttribute("emailError", errors.getFieldError("email").getDefaultMessage());
			}
			return new ModelAndView("userform", "userModel", model);
		}
		userService.saveUser(user);
		// 重定向到list页面：templates是controller后的地址跳转
		return new ModelAndView("redirect:/user");
	}

	/**
	 * 获取用户操作表单页面
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("/form")
	public ModelAndView createForm(Model model, @RequestParam(defaultValue = "0") Long id) {
		if (id > 0) {
			model.addAttribute("user", userService.findUser(id));
		} else {
			model.addAttribute("user", new User());
		}
		return new ModelAndView("userform", "userModel", model);
	}

	/**
	 * 获取用户列表显示页面
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping
	public ModelAndView list(Model model) {
		model.addAttribute("userList", userService.listUsers());
		return new ModelAndView("userlist", "userModel", model);
	}

	/**
	 * 点击查找用户，是打开新页面：模糊查找输入页面
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/findUser")
	public ModelAndView index(Model model) {
		model.addAttribute("user", new User());
		return new ModelAndView("findUser", "userModel", model);
	}

	/**
	 * 查找提交并跳转用户列表
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@PostMapping("/search")
	public ModelAndView search(@ModelAttribute User user, Model model) {
		model.addAttribute("userList", userService.searchUser(user.getName()));
		// 查询后，跳转到重定向到list页面
		return new ModelAndView("redirect:/user", "userModel", model);
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(name = "id") Long id) {
		userService.deleteUser(id);
		return new ModelAndView("redirect:/user");
	}

	/**
	 * 输入条件查询
	 * 
	 * @param page     page表示页数,默认是第0页
	 * @param size     size表示每页显示条数，默认显示10条
	 * @param sex
	 * @param ageBegin
	 * @param ageEnd
	 * @return
	 */
	@PostMapping(value = "/findByCondition")
	public Object findBy(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size,
			@RequestParam(value = "sex", defaultValue = "男") String sex,
			@RequestParam(value = "ageBegin") Integer ageBegin, @RequestParam(value = "ageEnd") Integer ageEnd) {
		List<User> list = userService.findBy(page, size, sex, ageBegin, ageEnd).getContent();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", list.size());
		map.put("listData", list);
		return map;
	}

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserAssembler userAssembler;

	/**
	 * 保存用户: 可以根据UserDto扩展属性
	 * 
	 * @param user
	 * @param errors
	 * @param model
	 * @return
	 */
	@PostMapping("create")
	public DataResponse<User> createUser(@RequestBody UserDto dto) {
		User user = userRepository.saveAndFlush(userAssembler.create(dto));
		System.out.println(user.toString());
		return new DataResponse<User>(user);
	}

	/**
	 * 
	 * @param id
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/{id}")
	public DataResponse<User> updateUser(@PathVariable String id, @RequestBody UserDto dto) throws Exception {
		DataResponse reps = new DataResponse();
		Optional<User> optional = userRepository.findById(Long.parseLong(id));
		// 查询数据是否不存在
		if (!optional.isPresent()) {
			throw new Exception("User is not exist!");
		}

		User user = optional.get();
		if (userAssembler.update(user, dto)) {
			User user1 = userRepository.saveAndFlush(userAssembler.create(dto));
			return new DataResponse<User>(user1);
		}
		return new DataResponse(500, "update failed!");
	}

}

/*
 * Copyright 2015-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package example.users.web;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;
import example.users.Password;
import example.users.User;
import example.users.UserManagement;
import example.users.Username;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * A sample controller implementation to showcase Spring Data web support:
 * <ol>
 * <li>Automatic population of a {@link Pageable} instance as controller method argument. This is
 * achieved by the automatic activation of {@link EnableSpringDataWebSupport} and in turn its
 * registration of a {@link PageableHandlerMethodArgumentResolver}.</li>
 * <li>Usage of proxy-backed interfaces to bind request parameters.</li>
 * </ol>
 *
 * @author Oliver Gierke
 */
@Controller
@RequestMapping("/users")
class UserController {
	private final UserManagement userManagement;

	public UserController(final UserManagement userManagement) {
		this.userManagement = userManagement;
	}

	/**
	 * Equis the model with a {@link Page} of {@link User}s. Spring Data automatically populates the
	 * {@link Pageable} from request data according to the setup of
	 * {@link PageableHandlerMethodArgumentResolver}. Note how the defaults can be tweaked by using
	 * {@link PageableDefault}.
	 *
	 * @param pageable will never be {@literal null}.
	 * @return
	 */
	@ModelAttribute("users")
	public Page<User> users(@PageableDefault(size = 5) Pageable pageable) {
		return userManagement.findAll(pageable);
	}

	/**
	 * Registers a new {@link User} for the data provided by the given {@link UserForm}. Note, how
	 * an interface is used to bind request parameters.
	 *
	 * @param userForm the request data bound to the {@link UserForm} instance.
	 * @param binding the result of the binding operation.
	 * @param model the Spring MVC {@link Model}.
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Object register(UserForm userForm, BindingResult binding, Model model) {
		userForm.validate(binding, userManagement);
		if (binding.hasErrors()) {
			return "users";
		}
		userManagement.register(new Username(userForm.getUsername()),
				Password.raw(userForm.getPassword()));
		RedirectView redirectView = new RedirectView("/users");
		redirectView.setPropagateQueryParams(true);
		return redirectView;
	}

	/**
	 * Populates the {@link Model} with the {@link UserForm} automatically created by Spring Data
	 * web components. It will create a {@link Map}-backed proxy for the interface.
	 *
	 * @param model will never be {@literal null}.
	 * @param userForm will never be {@literal null}.
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String listUsers(Model model, UserForm userForm) {
		model.addAttribute("userForm", userForm);
		return "users";
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(path = "/delete", method = RequestMethod.POST)
	public Object deleteUser(UserForm userForm, BindingResult binding, Model model) {
		// Actually delete a user here
		RedirectView redirectView = new RedirectView("/users");
		redirectView.setPropagateQueryParams(true);
		return redirectView;
	}

	/**
	 * An interface to represent the form to be used
	 *
	 * @author Oliver Gierke
	 */
	class UserForm {
		private String username;
		private String password;
		private String repeatedPassword;

		public UserForm(String username, String password, String repeatedPassword) {
			this.username = username;
			this.password = password;
			this.repeatedPassword = repeatedPassword;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getRepeatedPassword() {
			return repeatedPassword;
		}

		public void setRepeatedPassword(String repeatedPassword) {
			this.repeatedPassword = repeatedPassword;
		}

		/**
		 * Validates the {@link UserForm}.
		 *
		 * @param errors
		 * @param userManagement
		 */
		public void validate(BindingResult errors, UserManagement userManagement) {
			if (!isFieldValid(getUsername())) {
				errors.rejectValue("username", "user.username.empty");
			}
			if (!isFieldValid(getPassword())) {
				errors.rejectValue("password", "user.password.empty");
			}
			if (!isFieldValid(getRepeatedPassword())) {
				errors.rejectValue("repeatedPassword", "user.repeatedPassword.empty");
			}
			if (!getPassword().equals(getRepeatedPassword())) {
				errors.rejectValue("repeatedPassword", "user.password.no-match");
			}
			try {
				userManagement.findByUsername(new Username(getUsername()))
						.ifPresent(user -> errors.rejectValue("username", "user.username.exists"));
			} catch (IllegalArgumentException o_O) {
				errors.rejectValue("username", "user.username.invalidFormat");
			}
		}

		private boolean isFieldValid(String field) {
			StandardEvaluationContext context = new StandardEvaluationContext(this);
			SpelExpressionParser parser = new SpelExpressionParser();
			Expression expression = parser.parseExpression("{\"" + field + "\".length > 0}");
			List<?> res = (List<?>) expression.getValue(context);
			return (boolean) res.get(0);
		}
	}
}

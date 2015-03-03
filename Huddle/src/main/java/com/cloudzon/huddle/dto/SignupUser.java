package com.cloudzon.huddle.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import com.cloudzon.huddle.constraint.PasswordMatch;
import com.cloudzon.huddle.constraint.UserExist;

//@PasswordMatch(password = "password", repassword = "retypePassword", property = "retypePassword", message = "Password Not Match", groups = {
@PasswordMatch(password = "password", passwordproperty = "password", repassword = "retypePassword", repasswordproperty = "retypePassword", message = "Password Not Match", emptyPasswordMessage = "password must not empty", emptyRepasswordMessage = "retype password must not empty", max = 40, min = 8, passwordLengthMessage = "Password must be 8 to 40 char long", groups = {
		SignupUser.MvcSignUpUser.class, SignupUser.RestSignUpUser.class })
@UserExist(email = "email", emailEmptyError = "{NotEmpty.userMaster.email}", invalidEmailError = "Not Valid Email", emailExistError = "Email Already Exist!", emailProperty = "email", userName = "userName", userNameEmptyError = "{NotEmpty.userMaster.userName}", userNameExistError = "UserName Already Exist!", userNameProperty = "userName", groups = {SignupUser.MvcSignUpUser.class, SignupUser.RestSignUpUser.class })
public class SignupUser {

	public interface RestSignUpUser {
	}

	public interface MvcSignUpUser {
	}
	private Long id;
	private String firstName;

	private String lastName;

	// @NotEmpty(message = "{NotEmpty.userMaster.userName}", groups = {
	// RestSignUpUser.class, MvcSignUpUser.class })
	private String userName;

	// @NotEmpty(message = "{NotEmpty.userMaster.email}", groups = {
	// RestSignUpUser.class, MvcSignUpUser.class })
	// @Email(message = "{Email}")
	private String email;
	
	private String mobileNumber;
	
	// @NotEmpty(message = "{NotEmpty.userMaster.password}", groups = {
	// RestSignUpUser.class, MvcSignUpUser.class })
	// @Size(min = 8, max = 40, groups = { RestSignUpUser.class,
	// MvcSignUpUser.class })
	private String password;

	// @NotEmpty(message = "{NotEmpty.userMaster.retypePassword}", groups = {
	// RestSignUpUser.class, MvcSignUpUser.class })
	private String retypePassword;

	// @AssertTrue(message = "{NotEmpty.userMaster.termAgreement}")
	// private Boolean termAgree;
	private String dob;

	private String joiningDate;

	private String profilePic;
	
	@NotEmpty(message = "Please enter redirect url after user activation", groups = { RestSignUpUser.class })
	@URL(message = "Not valid url", groups = { RestSignUpUser.class })
	private String redirectURL;
	
	private List<Long> rolesId;

	/*
	 * @AssertTrue(message = "Please aggree terms and conditions", groups = {
	 * RestSignUpUser.class, MvcSignUpUser.class })
	 * 
	 * @NotNull(message = "Please aggree terms and conditions", groups = {
	 * RestSignUpUser.class, MvcSignUpUser.class })
	 */
	// private Boolean isAgree;
	
	

	public String getUserName() {
		return userName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

	/*
	 * public Boolean getTermAgree() { return termAgree; }
	 * 
	 * public void setTermAgree(Boolean termAgree) { this.termAgree = termAgree;
	 * }
	 */

	public String getRedirectURL() {
		return redirectURL;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	public List<Long> getRolesId() {
		return rolesId;
	}

	public void setRolesId(List<Long> rolesId) {
		this.rolesId = rolesId;
	}

	
	/*
	 * public Boolean getIsAgree() { return isAgree; }
	 * 
	 * public void setIsAgree(Boolean isAgree) { this.isAgree = isAgree; }
	 */

}
package za.co.standardbank.sbg.cda.mvc.portlet.validation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import za.co.standardbank.sbg.cda.mvc.portlet.domain.ContactMeInfo;
import za.co.standardbank.sbg.cda.mvc.portlet.util.ContactMeUtil;

public class ContactMeValidator implements Validator {
	private static Log log = LogFactory.getLog(ContactMeValidator.class);

	public boolean supports(Class givenClass) {
		return givenClass.equals(ContactMeInfo.class);
	}

	public void validate(Object object, Errors errors) {
		if (log.isDebugEnabled())
			log.debug("Entering ContactMeValidator.validate " + object);
		ContactMeInfo contactInfo = (ContactMeInfo) object;
		validateName(contactInfo, errors);
		validateEmail(contactInfo, errors);
		validatePhone(contactInfo, errors);
		validateSubject(contactInfo, errors);
		validateDescription(contactInfo, errors);
		if (log.isDebugEnabled())
			log.debug("Exiting ContactMeValidator.validate " + errors);
	}

	public void validateName(ContactMeInfo contactInfo, Errors errors) {
		if (!ContactMeUtil.validateRequired(contactInfo.getName())) {
			errors.rejectValue("name", "required.contact.name");
		}
	}

	public void validateEmail(ContactMeInfo contactInfo, Errors errors) {
		String emailAdress = contactInfo.getEmail();
		if (!ContactMeUtil.validateRequired(emailAdress)) {
			errors.rejectValue("email", "required.contact.email");
		} else if (!ContactMeUtil.validateMailId(emailAdress)) {
			errors.rejectValue("email", "invalid.contact.email");
		}
	}

	public void validatePhone(ContactMeInfo contactInfo, Errors errors) {
		if (!ContactMeUtil.validateRequired(contactInfo.getPhone())) {
			errors.rejectValue("phone", "required.contact.phone");
		}
	}

	public void validateSubject(ContactMeInfo contactInfo, Errors errors) {
		if (!ContactMeUtil.validateRequired("" + contactInfo.getSubject())) {
			errors.rejectValue("subject", "required.contact.subject");
		}
	}

	public void validateDescription(ContactMeInfo contactInfo, Errors errors) {
		if (!ContactMeUtil.validateRequired("" + contactInfo.getDescription())) {
			errors.rejectValue("description", "required.contact.description");
		}
	}
}

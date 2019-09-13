package com.agileengine.document.html.searcher.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agileengine.document.html.searcher.exception.ValidationException;

public class ArgumentValidator implements Validator {
	private static final int COUNT_OF_VALID_ARGS = 3;
	private static final Logger LOG = LoggerFactory.getLogger(ArgumentValidator.class);

	public void validate(String[] args) throws ValidationException {
		if(args.length > COUNT_OF_VALID_ARGS) {
			LOG.error("Wrong count of arguments");
			throw new ValidationException ("Wrong count argument");
		}
	}
}

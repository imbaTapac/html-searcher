package com.agileengine.document.html.searcher.validator;

import com.agileengine.document.html.searcher.exception.ValidationException;

public interface Validator {
	void validate (String [] args) throws ValidationException;
}

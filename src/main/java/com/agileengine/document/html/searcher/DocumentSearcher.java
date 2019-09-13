package com.agileengine.document.html.searcher;

import static com.agileengine.document.html.searcher.constant.Constants.UTF_8;

import java.io.FileNotFoundException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agileengine.document.html.searcher.exception.ValidationException;
import com.agileengine.document.html.searcher.util.DocumentUtils;
import com.agileengine.document.html.searcher.validator.ArgumentValidator;

public class DocumentSearcher {
	private static final Logger LOG = LoggerFactory.getLogger(DocumentSearcher.class);

	public static void main(String[] args) throws FileNotFoundException{
		ArgumentValidator argumentValidator = new ArgumentValidator();
		try {
			argumentValidator.validate(args);
		}catch(ValidationException e){
			LOG.error("{}",e);
		}
		Document origin = DocumentUtils.getDocument(args[0], UTF_8);
		LOG.info("Origin file was successfully opened");
		Document target = DocumentUtils.getDocument(args[1], UTF_8);
		LOG.info("Target file was successfully opened");
		LOG.info("Staring finding element [{}]",args[2]);
		Element originEl = DocumentUtils.findElement(origin,args[2]);
		LOG.info("Element was successfully find");
		LOG.info("Starting searching element [{}] in target file",args[2]);
		Element targetEl = DocumentUtils.compare(target,originEl);
		LOG.info("Path of origin element is [{}]",DocumentUtils.getPathOfElement(originEl));
		LOG.info("Path of target element is [{}]",DocumentUtils.getPathOfElement(targetEl));
	}
}

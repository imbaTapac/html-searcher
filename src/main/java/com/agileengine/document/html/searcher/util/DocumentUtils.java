package com.agileengine.document.html.searcher.util;

import static com.agileengine.document.html.searcher.constant.Constants.CHARSETS;
import static com.agileengine.document.html.searcher.constant.Constants.DELIMITER;
import static java.util.Collections.max;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DocumentUtils {

	private static final Logger LOG = LoggerFactory.getLogger(DocumentUtils.class);

	private DocumentUtils() {
		LOG.error("Util class cannot be initialized");
		throw new IllegalStateException();
	}

	public static Document getDocument(String path, String charsetName) throws FileNotFoundException {
		File file = new File(path);
		Document document = null;
		if(file.exists()) {
			LOG.info("Trying to open target file [{}]", file.getName());
			try {
				if(CHARSETS.contains(charsetName)) {
					document = Jsoup.parse(file, charsetName);
				} else {
					LOG.error("Charset of [{}] not supported",charsetName);
					throw new UnsupportedCharsetException(String.format("Charset of [%s] not supported", charsetName));
				}
			} catch(IOException e) {
				LOG.error("{}", e);
			}
		} else {
			LOG.error("File [{}] does not exist",file.getAbsolutePath());
			throw new FileNotFoundException(String.format("File [%s] does not exist", file.getAbsolutePath()));
		}

		return document;
	}

	public static Element findElement(Document document, String elem) {
		Element element = document.getElementById(elem);
		if(Objects.isNull(element)) {
			LOG.error("Element [{}] is not present in document",elem);
			throw new NoSuchElementException(String.format("Element [%s] does not present in document", elem));
		}
		return element;
	}

	private static List<Element> getAllElementEntries(Document document, Element element) {
		return document.getAllElements().stream()
				.filter(doc -> doc.tagName().equalsIgnoreCase(element.tagName()))
				.collect(toList());
	}


	public static String getPathOfElement(Element element) {
		StringBuilder sb = new StringBuilder();
		List<Element> elements = element.parents();
		ListIterator listIterator = elements.listIterator(elements.size());
		while(listIterator.hasPrevious()) {
			Element e = (Element) listIterator.previous();
			sb.append(e.tagName());
			sb.append(DELIMITER);
		}
		sb.append(element.tagName());
		return sb.toString();
	}


	public static Element compare(Document document, Element element) {
		List<Element> elements = getAllElementEntries(document, element);
		Map<Element, Integer> sameElements = new HashMap<>();
		for(Element el : elements) {
			sameElements.putAll(getAllSameElements(el, element));
		}

		return max(sameElements.entrySet(), comparing(Map.Entry::getValue)).getKey();
	}

	private static Map<Element, Integer> getAllSameElements(Element element, Element toSearch) {
		Map<Element, Integer> sameElements = new HashMap<>();
		List<Attribute> originElementAttrs = element.attributes().asList();
		List<Attribute> searchElementAttrs = toSearch.attributes().asList();
		List<Attribute> attributes = originElementAttrs.stream().filter(searchElementAttrs::contains).collect(toList());
		if(!attributes.isEmpty()) {
			sameElements.put(element, attributes.size());
		}
		return sameElements;
	}
}

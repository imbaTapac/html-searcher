package com.agileengine.document.html.searcher;


import static com.agileengine.document.html.searcher.constant.Constants.UTF_8;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.NoSuchElementException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.agileengine.document.html.searcher.exception.ValidationException;
import com.agileengine.document.html.searcher.util.DocumentUtils;
import com.agileengine.document.html.searcher.validator.ArgumentValidator;
import com.agileengine.document.html.searcher.validator.Validator;

@RunWith(JUnit4.class)
public class DocumentSearcherTest {
	private Validator validator;
	private static final String[] PAGES = new String[]{"pages/sample-0-origin.html", "pages/sample-1-evil-gemini.html", "pages/sample-2-container-and-clone.html", "pages/sample-4-the-mash.html"};

	private static final String BUTTON_ID = "make-everything-ok-button";
	private static final String FIRST_SAMPLE_RESULT = "<a class=\"btn btn-success\" href=\"#check-and-ok\" title=\"Make-Button\" rel=\"done\" onclick=\"javascript:window.okDone(); return false;\"> Make everything OK </a>";
	private static final String SECOND_SAMPLE_RESULT = "<a class=\"btn test-link-ok\" href=\"#ok\" title=\"Make-Button\" rel=\"next\" onclick=\"javascript:window.okComplete(); return false;\"> Make everything OK </a>";
	private static final String THIRD_SAMPLE_RESULT = "<a class=\"btn btn-success\" href=\"#ok\" title=\"Do-Link\" rel=\"next\" onclick=\"javascript:window.okDone(); return false;\"> Do anything perfect </a>";
	private static final String FOURTH_SAMPLE_RESULT = "<a class=\"btn btn-success\" href=\"#ok\" title=\"Make-Button\" rel=\"next\" onclick=\"javascript:window.okFinalize(); return false;\"> Do all GREAT </a>";

	@Before
	public void init() {
		validator = new ArgumentValidator();
	}

	@Test(expected = ValidationException.class)
	public void parseWithWrongArgumentTest() throws ValidationException {
		String[] args = new String[]{PAGES[0], PAGES[1], BUTTON_ID, ""};
		validator.validate(args);
	}

	@Test(expected = FileNotFoundException.class)
	public void parseWithWrongFileNameTest() throws FileNotFoundException {
		String fileName = "error";
		DocumentUtils.getDocument(fileName, UTF_8);
	}

	@Test(expected = UnsupportedCharsetException.class)
	public void parseWithWrongCharsetTest() throws FileNotFoundException {
		String charset = "UTF-1";
		DocumentUtils.getDocument(PAGES[0], charset);
	}

	@Test(expected = NoSuchElementException.class)
	public void parseWithWrongElementIdTest() throws FileNotFoundException {
		Document document = DocumentUtils.getDocument(PAGES[0],UTF_8);
		DocumentUtils.findElement(document,"error");
	}

	@Test
	public void successElementSearchTest() throws FileNotFoundException {
		Document d1 = DocumentUtils.getDocument(PAGES[0], UTF_8);
		Document d2 = DocumentUtils.getDocument(PAGES[1], UTF_8);
		Element origin = DocumentUtils.findElement(d1, BUTTON_ID);
		Element target = DocumentUtils.compare(d2, origin);
		String originPath = DocumentUtils.getPathOfElement(origin);
		String targetPath = DocumentUtils.getPathOfElement(target);
		assertEquals(FIRST_SAMPLE_RESULT,target.toString());
		assertEquals(originPath,targetPath);
	}

	@Test(expected = InvocationTargetException.class)
	public void illegalUtilInstance() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		Constructor<DocumentUtils> constructor = DocumentUtils.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}
}

/*
 * ao-fluent-html-util - Utilities for Fluent Java DSL for high-performance HTML generation.
 * Copyright (C) 2020, 2021  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of ao-fluent-html-util.
 *
 * ao-fluent-html-util is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ao-fluent-html-util is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ao-fluent-html-util.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aoindustries.html.util;

import com.aoindustries.html.any.AnyDocument;
import java.io.IOException;

/**
 * Writes image preload scripts.
 *
 * @author  AO Industries, Inc.
 */
public class ImagePreload {

	// Make no instances
	private ImagePreload() {}

	/**
	 * Prints a JavaScript script that will preload the image at the provided URL.
	 *
	 * @param url This should be the URL-encoded URL, but with only a standalone ampersand (&amp;) as parameter separator
	 *             (not &amp;amp;)
	 */
	public static void writeImagePreloadScript(String url, AnyDocument<?> document) throws IOException {
		document.script().out(script -> script.indent()
			.append("var img=new Image();").nli()
			.append("img.src=").text(url).append(';')
		).__();
	}
}

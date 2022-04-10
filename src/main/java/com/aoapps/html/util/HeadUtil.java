/*
 * ao-fluent-html-util - Utilities for Fluent Java DSL for high-performance HTML generation.
 * Copyright (C) 2022  AO Industries, Inc.
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
 * along with ao-fluent-html-util.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.aoapps.html.util;

import com.aoapps.encoding.Doctype;
import com.aoapps.html.any.AnyHEAD;
import com.aoapps.html.any.AnyMETA;
import com.aoapps.html.any.AnySCRIPT;
import com.aoapps.html.any.AnySTYLE;
import com.aoapps.html.any.AnyUnion_Metadata_Phrasing;
import java.io.IOException;

/**
 * Utilities for working with {@link AnyHEAD}.
 *
 * @author  AO Industries, Inc.
 */
public final class HeadUtil {

	/** Make no instances. */
	private HeadUtil() {throw new AssertionError();}

	/**
	 * Adds the standard minimal meta tags.
	 */
	@SuppressWarnings("deprecation")
	public static void standardMeta(AnyUnion_Metadata_Phrasing<?, ?> head, String contentType) throws IOException {
		if(head.getDocument().encodingContext.getDoctype() == Doctype.HTML5) {
			head.meta().charset().__();
		} else {
			head
				.meta().httpEquiv(AnyMETA.HttpEquiv.CONTENT_TYPE).content(contentType).__()
				// Default style language
				.meta().httpEquiv(AnyMETA.HttpEquiv.CONTENT_STYLE_TYPE).content(AnySTYLE.Type.TEXT_CSS).__()
				.meta().httpEquiv(AnyMETA.HttpEquiv.CONTENT_SCRIPT_TYPE).content(AnySCRIPT.Type.TEXT_JAVASCRIPT).__();
		}
	}
}

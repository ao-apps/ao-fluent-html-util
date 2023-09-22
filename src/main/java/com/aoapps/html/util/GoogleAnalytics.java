/*
 * ao-fluent-html-util - Utilities for Fluent Java DSL for high-performance HTML generation.
 * Copyright (C) 2019, 2020, 2021, 2022, 2023  AO Industries, Inc.
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
import com.aoapps.html.any.AnyLINK;
import com.aoapps.html.any.AnyScriptSupportingContent;
import com.aoapps.html.any.AnyUnion_Metadata_Phrasing;
import com.aoapps.lang.Strings;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Writes various versions of Google Analytics tracking scripts.
 *
 * @author  AO Industries, Inc.
 */
public final class GoogleAnalytics {

  /** Make no instances. */
  private GoogleAnalytics() {
    throw new AssertionError();
  }

  /**
   * Writes the modern Google Analytics <a href="https://support.google.com/analytics/answer/1008080?hl=en&amp;ref_topic=1008079#GA">Global Site Tag</a>.
   * This is best used with {@link Doctype#HTML5}.
   * This should be added first, or very high up, in the <code>&lt;head&gt;</code>.
   *
   * @param trackingId  No script will be written when {@code null} or empty (after trimming)
   */
  // Matches ao-ant-tasks:InsertGoogleAnalyticsTracking.java:generateGlobalSiteTag
  public static void writeGlobalSiteTag(AnyUnion_Metadata_Phrasing<?, ?> content, String trackingId) throws IOException {
    String trimmedId = Strings.trimNullIfEmpty(trackingId);
    if (trimmedId != null) {
      // See https://blog.luke.lol/webmaster/optimize-google-analytics-google-tag-manager-via-preconnect-headers/
      content
          .link(AnyLINK.Rel.DNS_PREFETCH).href("https://www.google-analytics.com/").__()
          .link(AnyLINK.Rel.PRECONNECT).href("https://www.google-analytics.com/").crossorigin(AnyLINK.Crossorigin.ANONYMOUS).__()
          // .out.write("<!-- Google tag (gtag.js) -->").autoNl()
          .script().async(true).src("https://www.googletagmanager.com/gtag/js?id=" + URLEncoder.encode(trimmedId, StandardCharsets.UTF_8)).__()
          .script().out(script -> script.indent()
              .append("window.dataLayer = window.dataLayer || [];").nli()
              .append("function gtag(){dataLayer.push(arguments);}").nli()
              .append("gtag(\"js\", new Date());").nli()
              .append("gtag(\"config\", ").text(trimmedId).append(");")
      ).__();
    }
  }

  /**
   * Writes an older-style Google Analytics <a href="https://developers.google.com/analytics/devguides/collection/analyticsjs">analytics.js tracking script</a>.
   * This is best used for compatibility with doctypes prior to {@link Doctype#HTML5}.
   * This should be added first, or very high up, in the <code>&lt;head&gt;</code>.
   *
   * @param trackingId  No script will be written when {@code null} or empty (after trimming)
   *
   * @deprecated  All sites should be HTML 5 now, please use {@link #writeGlobalSiteTag(com.aoapps.html.any.AnyUnion_Metadata_Phrasing, java.lang.String)}.
   */
  // TODO: Support hitType exception? https://developers.google.com/analytics/devguides/collection/analyticsjs/field-reference
  @Deprecated
  public static void writeAnalyticsJs(AnyScriptSupportingContent<?, ?> content, String trackingId) throws IOException {
    String trimmedId = Strings.trimNullIfEmpty(trackingId);
    if (trimmedId != null) {
      content.script().out(script -> script.indent()
          .append("(function(i,s,o,g,r,a,m){i[\"GoogleAnalyticsObject\"]=r;i[r]=i[r] || function(){").nli()
          .append("(i[r].q=i[r].q || []).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),").nli()
          .append("m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)").nli()
          .append("})(window,document,\"script\",\"https://www.google-analytics.com/analytics.js\",\"ga\");").nli()
          .append("ga(\"create\",").text(trimmedId).append(",\"auto\");").nli()
          .append("ga(\"send\",\"pageview\");")
      ).__();
    }
  }
}

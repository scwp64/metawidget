// Metawidget
//
// This file is dual licensed under both the LGPL
// (http://www.gnu.org/licenses/lgpl-2.1.html) and the EPL
// (http://www.eclipse.org/org/documents/epl-v10.php). As a
// recipient of Metawidget, you may choose to receive it under either
// the LGPL or the EPL.
//
// Commercial licenses are also available. See http://metawidget.org
// for details.

package org.metawidget;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import junit.framework.TestCase;

import org.metawidget.util.IOUtils;

/**
 * @author <a href="http://kennardconsulting.com">Richard Kennard</a>
 */

public class AllJarTest
	extends TestCase {

	//
	// Private statics
	//

	private static final String	METAWIDGET_ALL_JAR	= "metawidget-all.jar";

	//
	// Public methods
	//

	/**
	 * Test metawidget-all.jar doesn't contain any incorrect files.
	 */

	public void testAllJar()
		throws Exception {

		JarFile jar = new JarFile( "../../../modules/java/all/target/" + METAWIDGET_ALL_JAR );

		boolean hadManifest = false;
		int hadTlds = 0;
		int hadXsds = 0;
		int hadXmls = 0;

		for ( Enumeration<JarEntry> e = jar.entries(); e.hasMoreElements(); ) {

			String name = e.nextElement().getName();

			if ( name.equals( "org/" ) ) {
				continue;
			}

			if ( name.startsWith( "META-INF/" ) ) {

				if ( name.equals( "META-INF/" ) ) {
					continue;
				}

				if ( name.equals( "META-INF/MANIFEST.MF" ) ) {
					hadManifest = true;
					continue;
				}

				if ( name.equals( "META-INF/faces-config.xml" ) ) {
					continue;
				}

				if ( name.equals( "META-INF/metawidget-faces.taglib.xml" ) ) {
					continue;
				}

				if ( name.endsWith( ".tld" ) ) {
					hadTlds++;
					continue;
				}

			} else if ( name.startsWith( "org/metawidget/" ) ) {

				if ( name.endsWith( "/" ) ) {
					continue;
				}

				if ( name.endsWith( ".class" ) ) {
					continue;
				}

				if ( name.endsWith( "-1.0.xsd" ) ) {
					hadXsds++;
					continue;
				}

				if ( name.endsWith( "-default.xml" ) ) {
					hadXmls++;
					continue;
				}

				if ( name.startsWith( "org/metawidget/swing/icon" ) && name.endsWith( ".gif" ) ) {
					continue;
				}
			}

			assertTrue( "File should not be packaged in " + METAWIDGET_ALL_JAR + ": " + name, false );
		}

		assertTrue( hadManifest );
		assertEquals( 4, hadTlds );
		assertEquals( 2, hadXsds );
		assertEquals( 12, hadXmls );
	}

	/**
	 * Test JavaScript files appear minified (not just aggregated)
	 */

	public void testJavaScript()
		throws Exception {

		testJavaScript( "angularjs/target/metawidget-angularjs/lib/metawidget/angular/metawidget-angular.min.js" );
		testJavaScript( "corejs/target/metawidget-corejs/lib/metawidget/core/metawidget-core.min.js" );
		testJavaScript( "jquery/ui/target/metawidget-jqueryui/lib/metawidget/jquery-ui/metawidget-jqueryui.min.js" );
		testJavaScript( "jquery/mobile/target/metawidget-jquerymobile/lib/metawidget/jquery.mobile/metawidget-jquerymobile.min.js" );
	}

	//
	// Private methods
	//

	public void testJavaScript( String filename )
		throws Exception {

		ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
		IOUtils.streamBetween( new FileInputStream( new File( "../../../modules/js/" + filename ) ), streamOut );
		String contents = streamOut.toString();

		assertTrue( contents.contains( "This file is dual licensed" ) );
		assertTrue( contents.contains( "minified" ) );
		assertTrue( contents, !contents.contains( "${" ) );
		assertTrue( contents, !contents.contains( "config." ) );
		assertTrue( contents, !contents.contains( "function _" ) );
		assertTrue( contents, !contents.contains( "/**" ) );
		assertTrue( contents, !contents.contains( "@class" ) );

		// Should always go via ownerDocument.create
		
		assertTrue( contents, !contents.contains( "document.create" ) );
	}
}
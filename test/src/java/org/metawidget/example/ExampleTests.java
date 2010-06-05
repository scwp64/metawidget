// Metawidget
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package org.metawidget.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.metawidget.example.shared.addressbook.model.Communication;
import org.metawidget.example.swing.addressbook.SwingAddressBookTest;
import org.metawidget.example.swing.animalraces.SwingAnimalRacesTest;
import org.metawidget.example.swing.appframework.SwingAppFrameworkTest;
import org.metawidget.example.swing.tutorial.SwingTutorialTest;
import org.metawidget.example.swing.userguide.CastorInspectorExampleTest;
import org.metawidget.example.swing.userguide.ExcludingWidgetBuilderExampleTest;
import org.metawidget.example.swing.userguide.IncludingInspectionResultProcessorExampleTest;
import org.metawidget.example.swing.userguide.PropertyStyleExampleTest;
import org.metawidget.example.swing.userguide.ReadOnlyWidgetBuilderExampleTest;
import org.metawidget.example.swing.userguide.TooltipInspectorExampleTest;
import org.metawidget.example.swing.userguide.WidgetBuilderExampleTest;
import org.metawidget.example.swing.userguide.WidgetProcessorExampleTest;
import org.metawidget.example.swt.addressbook.SwtAddressBookTest;
import org.metawidget.example.swt.tutorial.SwtTutorialTest;
import org.metawidget.util.TestUtils;

/**
 * @author Richard Kennard
 */

public class ExampleTests
	extends TestCase
{
	//
	// Public statics
	//

	public static Test suite()
	{
		TestSuite suite = new TestSuite( "Example Tests" );
		suite.addTestSuite( CastorInspectorExampleTest.class );
		suite.addTestSuite( ExcludingWidgetBuilderExampleTest.class );
		suite.addTestSuite( IncludingInspectionResultProcessorExampleTest.class );
		suite.addTestSuite( PropertyStyleExampleTest.class );
		suite.addTestSuite( ReadOnlyWidgetBuilderExampleTest.class );
		suite.addTestSuite( SwingAddressBookTest.class );
		suite.addTestSuite( SwingAnimalRacesTest.class );
		suite.addTestSuite( SwingAppFrameworkTest.class );
		suite.addTestSuite( SwingTutorialTest.class );
		suite.addTestSuite( SwtAddressBookTest.class );
		suite.addTestSuite( SwtTutorialTest.class );
		suite.addTestSuite( TooltipInspectorExampleTest.class );
		suite.addTestSuite( WidgetBuilderExampleTest.class );
		suite.addTestSuite( WidgetProcessorExampleTest.class );

		// Note: GwtAddressBookTest is performed separately

		// Note: SwingAllWidgetsTest.class is performed separately to test JDK 1.4 compatibiltiy

		// Note: Web tests are performed by /test/web/examples/*/addressbook-test.xml

		return suite;
	}

	public void testEqualsAndHashCode()
	{
		TestUtils.testEqualsAndHashcode( Communication.class, new Communication()
		{
			final static long	serialVersionUID	= 1l;
		} );
	}
}
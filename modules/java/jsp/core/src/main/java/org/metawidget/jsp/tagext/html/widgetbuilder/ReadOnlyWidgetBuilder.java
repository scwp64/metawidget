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

package org.metawidget.jsp.tagext.html.widgetbuilder;

import static org.metawidget.inspector.InspectionResultConstants.*;
import static org.metawidget.inspector.jsp.JspInspectionResultConstants.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.tagext.Tag;

import org.metawidget.jsp.tagext.LiteralTag;
import org.metawidget.jsp.tagext.MetawidgetTag;
import org.metawidget.jsp.tagext.html.HtmlStubTag;
import org.metawidget.jsp.tagext.html.widgetprocessor.HiddenFieldProcessor;
import org.metawidget.jsp.tagext.layout.SimpleLayout;
import org.metawidget.util.ClassUtils;
import org.metawidget.util.CollectionUtils;
import org.metawidget.util.WidgetBuilderUtils;
import org.metawidget.widgetbuilder.iface.WidgetBuilder;

/**
 * ReadOnlyWidgetBuilder for 'plain' JSP environment (eg. just a servlet-based backend, no
 * Struts/Spring etc) that outputs HTML.
 * <p>
 * Outputs raw text for fields that are marked <code>read-only</code>.
 * <p>
 * When used in a JSP 2.0 environment, automatically initializes tags using JSP EL.
 *
 * @author <a href="http://kennardconsulting.com">Richard Kennard</a>
 */

public class ReadOnlyWidgetBuilder
	implements WidgetBuilder<Tag, MetawidgetTag> {

	//
	// Public methods
	//

	public Tag buildWidget( String elementName, Map<String, String> attributes, MetawidgetTag metawidget ) {

		// Not read-only?

		if ( !WidgetBuilderUtils.isReadOnly( attributes ) ) {
			return null;
		}

		// Hidden

		if ( TRUE.equals( attributes.get( HIDDEN ) ) ) {
			attributes.put( HiddenFieldProcessor.ATTRIBUTE_NEEDS_HIDDEN_FIELD, TRUE );
			return new HtmlStubTag();
		}

		// Action

		if ( ACTION.equals( elementName ) ) {
			return createReadOnlyButton( attributes, metawidget );
		}

		// Masked (return an empty String, so that we DO still render a label)

		if ( TRUE.equals( attributes.get( MASKED ) ) ) {
			return new LiteralTag( "" );
		}

		// Lookups

		String lookup = attributes.get( LOOKUP );

		if ( lookup != null && !"".equals( lookup ) ) {
			return setAttributeAndCreateReadOnlyLabelTag( attributes, metawidget );
		}

		String jspLookup = attributes.get( JSP_LOOKUP );

		if ( jspLookup != null && !"".equals( jspLookup ) ) {
			return setAttributeAndCreateReadOnlyLabelTag( attributes, metawidget );
		}

		// Lookup the class

		Class<?> clazz = WidgetBuilderUtils.getActualClassOrType( attributes, String.class );

		if ( clazz != null ) {
			// Primitives

			if ( clazz.isPrimitive() ) {
				return setAttributeAndCreateReadOnlyLabelTag( attributes, metawidget );
			}

			// Object primitives

			if ( ClassUtils.isPrimitiveWrapper( clazz ) ) {
				return setAttributeAndCreateReadOnlyLabelTag( attributes, metawidget );
			}

			// Dates

			if ( Date.class.isAssignableFrom( clazz ) ) {
				return setAttributeAndCreateReadOnlyLabelTag( attributes, metawidget );
			}

			// Strings

			if ( String.class.equals( clazz ) ) {
				return setAttributeAndCreateReadOnlyLabelTag( attributes, metawidget );
			}

			// Collections

			if ( Collection.class.isAssignableFrom( clazz ) ) {
				return new HtmlStubTag();
			}
		}

		// Not simple, but don't expand

		if ( TRUE.equals( attributes.get( DONT_EXPAND ) ) || metawidget.getLayout() instanceof SimpleLayout ) {
			return setAttributeAndCreateReadOnlyLabelTag( attributes, metawidget );
		}

		// Nested Metawidget

		return null;
	}

	//
	// Protected methods
	//

	protected Tag createReadOnlyLabelTag( Map<String, String> attributes, MetawidgetTag metawidget ) {

		String value = HtmlWidgetBuilderUtils.evaluateAsText( attributes, metawidget );

		// Support lookup labels

		String lookupLabels = attributes.get( LOOKUP_LABELS );

		if ( lookupLabels != null ) {
			List<String> lookupList = CollectionUtils.fromString( attributes.get( LOOKUP ) );
			int indexOf = lookupList.indexOf( value );

			if ( indexOf != -1 ) {
				List<String> lookupLabelsList = CollectionUtils.fromString( lookupLabels );

				if ( indexOf < lookupLabelsList.size() ) {
					value = lookupLabelsList.get( indexOf );
				}
			}
		}

		return new LiteralTag( value );
	}

	protected Tag createReadOnlyButton( Map<String, String> attributes, MetawidgetTag metawidget ) {

		StringBuilder builder = new StringBuilder();

		builder.append( "<input type=\"submit\" value=\"" );
		builder.append( metawidget.getLabelString( attributes ) );
		builder.append( "\"" );
		builder.append( HtmlWidgetBuilderUtils.writeAttributes( attributes, metawidget ) );
		builder.append( " disabled=\"disabled\"/>" );

		return new LiteralTag( builder.toString() );
	}

	//
	// Private methods
	//

	protected Tag setAttributeAndCreateReadOnlyLabelTag( Map<String, String> attributes, MetawidgetTag metawidget ) {

		attributes.put( HiddenFieldProcessor.ATTRIBUTE_NEEDS_HIDDEN_FIELD, TRUE );
		return createReadOnlyLabelTag( attributes, metawidget );
	}
}

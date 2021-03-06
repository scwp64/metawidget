// Metawidget
//
// For historical reasons, this file is licensed under the LGPL
// (http://www.gnu.org/licenses/lgpl-2.1.html).
//
// Most other files in Metawidget are licensed under both the
// LGPL/EPL and a commercial license. See http://metawidget.org
// for details.

package org.metawidget.statically.html.layout;

import static org.metawidget.inspector.InspectionResultConstants.*;

import java.util.Map;

import org.metawidget.layout.iface.AdvancedLayout;
import org.metawidget.layout.iface.LayoutException;
import org.metawidget.statically.StaticXmlStub;
import org.metawidget.statically.StaticXmlWidget;
import org.metawidget.statically.html.StaticHtmlMetawidget;
import org.metawidget.statically.html.widgetbuilder.HtmlTable;
import org.metawidget.statically.html.widgetbuilder.HtmlTableBody;
import org.metawidget.statically.html.widgetbuilder.HtmlTableCell;
import org.metawidget.statically.html.widgetbuilder.HtmlTableHeader;
import org.metawidget.statically.html.widgetbuilder.HtmlTableRow;
import org.metawidget.statically.html.widgetbuilder.HtmlTag;
import org.metawidget.util.WidgetBuilderUtils;

/**
 * Layout to arrange widgets using an HTML table.
 *
 * @author <a href="http://kennardconsulting.com">Richard Kennard</a>
 * @author Ryan Bradley
 */

public class HtmlTableLayout
	extends HtmlLayout
	implements AdvancedLayout<StaticXmlWidget, StaticXmlWidget, StaticHtmlMetawidget> {

	//
	// Private members
	//

	private String	mTableStyle;

	private String	mTableStyleClass;

	private String  mLabelColumnClass;

	private String  mComponentColumnClass;

	private String  mRequiredColumnClass;

	//
	// Constructor
	//

	public HtmlTableLayout() {

		this( new HtmlTableLayoutConfig() );
	}

	public HtmlTableLayout( HtmlTableLayoutConfig config ) {

		mTableStyle = config.getTableStyle();
		mTableStyleClass = config.getTableStyleClass();
		mLabelColumnClass = config.getLabelColumnStyleClass();
		mComponentColumnClass = config.getComponentColumnStyleClass();
		mRequiredColumnClass = config.getRequiredColumnStyleClass();
	}

	//
	// Public methods
	//

	public void onStartBuild( StaticHtmlMetawidget metawidget ) {

		// Do nothing
	}

	public void startContainerLayout( StaticXmlWidget container, StaticHtmlMetawidget metawidget ) {

		try {
			HtmlTable table = new HtmlTable();

			// Id

			table.setId( metawidget.getId() );

			// Styles

			if ( mTableStyle != null ) {
				table.putAttribute( "style", mTableStyle );
			}

			if ( mTableStyleClass != null ) {
				table.putAttribute( "class", mTableStyleClass );
			}

			table.getChildren().add( new HtmlTableBody() );

			container.getChildren().add( table );
		} catch ( Exception e ) {
			throw LayoutException.newException( e );
		}
	}

	public void layoutWidget( StaticXmlWidget widget, String elementName, Map<String, String> attributes, StaticXmlWidget container, StaticHtmlMetawidget metawidget ) {

		try {
			// Ignore stubs

			if ( widget instanceof StaticXmlStub && widget.getChildren().isEmpty() ) {
				return;
			}

			HtmlTableBody body = (HtmlTableBody) container.getChildren().get( 0 ).getChildren().get( 0 );
			HtmlTableRow row = new HtmlTableRow();

			// Label

			layoutLabel( row, widget, elementName, attributes, metawidget );

			// Add widget to layout

			HtmlTableCell cell = new HtmlTableCell();
			cell.getChildren().add( widget );
			cell.putAttribute( "class", mComponentColumnClass );
			row.getChildren().add( cell );

			// Indicate whether the field is required or not.

			HtmlTableCell requiredCell = new HtmlTableCell();
			requiredCell.putAttribute( "class", mRequiredColumnClass );

			if ( TRUE.equals( attributes.get( REQUIRED ) ) && !WidgetBuilderUtils.isReadOnly( attributes ) && !TRUE.equals( attributes.get( HIDDEN ) ) ) {
				requiredCell.setTextContent( "*" );
			}

			row.getChildren().add( requiredCell );
			body.getChildren().add( row );

		} catch ( Exception e ) {
			throw LayoutException.newException( e );
		}
	}

	public void endContainerLayout( StaticXmlWidget container, StaticHtmlMetawidget metawidget ) {

		// Do nothing
	}

	public void onEndBuild( StaticHtmlMetawidget metawidget ) {

		// Do nothing
	}

	//
	// Protected methods
	//

	/**
	 * @param elementName
	 *            can be useful if the Layout needs to call a WidgetProcessor
	 * @return whether a label was written
	 */

	@Override
	protected boolean layoutLabel( HtmlTag row, StaticXmlWidget widgetNeedingLabel, String elementName, Map<String, String> attributes, StaticHtmlMetawidget metawidget ) {

		HtmlTableHeader labelCell = new HtmlTableHeader();
		labelCell.putAttribute( "class", mLabelColumnClass );
		row.getChildren().add( labelCell );

		return super.layoutLabel( labelCell, widgetNeedingLabel, elementName, attributes, metawidget );
	}
}

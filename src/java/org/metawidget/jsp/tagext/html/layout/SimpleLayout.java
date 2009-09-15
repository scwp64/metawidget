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

package org.metawidget.jsp.tagext.html.layout;

import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;

import org.metawidget.jsp.JspUtils;
import org.metawidget.jsp.tagext.MetawidgetTag;
import org.metawidget.layout.iface.LayoutException;
import org.metawidget.layout.impl.BaseLayout;

/**
 * Layout to simply output components one after another, with no labels and no structure.
 * <p>
 * This Layout is suited to rendering single components, or for rendering components whose
 * layout relies entirely on CSS.
 *
 * @author Richard Kennard
 */

public class SimpleLayout
	extends BaseLayout<Tag, MetawidgetTag>
{
	//
	// Public methods
	//

	public void layoutChild( Tag tag, Map<String, String> attributes, MetawidgetTag metawidgetTag )
	{
		try
		{
			JspWriter writer = metawidgetTag.getPageContext().getOut();
			writer.write( JspUtils.writeTag( metawidgetTag.getPageContext(), tag, metawidgetTag, null ));
		}
		catch ( Exception e )
		{
			throw LayoutException.newException( e );
		}
	}
}
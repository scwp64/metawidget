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

package org.metawidget.example.swing.addressbook;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * @author Richard Kennard
 */

public class ImagePanel
	extends JPanel
{
	//
	//
	// Private statics
	//
	//

	private final static long	serialVersionUID	= 4911654330220639789L;

	//
	//
	// Private members
	//
	//

	private Image				mImage;

	//
	//
	// Constructor
	//
	//

	public ImagePanel()
	{
		setOpaque( false );
	}

	//
	//
	// Public methods
	//
	//

	public void setImage( URL url )
	{
		mImage = new ImageIcon( url ).getImage();
	}

	@Override
	public void paint( Graphics graphics )
	{
		graphics.drawImage( mImage, 0, 0, mImage.getWidth( null ), mImage.getHeight( null ), null );

		super.paint( graphics );
	}
}

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

package org.metawidget.example.faces.addressbook.managedbean;

import static org.metawidget.inspector.InspectionResultConstants.*;
import static org.metawidget.inspector.faces.FacesInspectionResultConstants.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.el.VariableResolver;
import javax.faces.model.ListDataModel;

import org.metawidget.example.shared.addressbook.model.Communication;
import org.metawidget.example.shared.addressbook.model.Contact;
import org.metawidget.inspector.annotation.UiAction;
import org.metawidget.inspector.annotation.UiAttribute;
import org.metawidget.inspector.annotation.UiComesAfter;
import org.metawidget.inspector.annotation.UiHidden;
import org.metawidget.inspector.faces.UiFacesAttribute;
import org.metawidget.inspector.faces.UiFacesAttributes;
import org.metawidget.util.CollectionUtils;

/**
 * @author Richard Kennard
 */

// Note: we SuppressWarnings( "deprecation" ) in a few places so that we can compile against JSF 2.0
// but still run against JSF 1.2
//
@SuppressWarnings( "deprecation" )
public class ContactBean {

	//
	// Private members
	//

	private boolean							mReadOnly;

	private Contact							mCurrent;

	private ListDataModel<Communication>	mModelCommunications;

	//
	// Public methods
	//

	@UiHidden
	public boolean isReadOnly() {

		return mReadOnly;
	}

	public void setReadOnly( boolean readOnly ) {

		mReadOnly = readOnly;
	}

	@UiHidden
	public Contact getCurrent() {

		return mCurrent;
	}

	public void setCurrent( Contact current ) {

		mCurrent = current;
		mModelCommunications = null;

		setReadOnly( mCurrent == null || mCurrent.getId() != 0 );
	}

	/**
	 * Note: this getter is JSF 1.2 compatible. As of JSF 2.0, ListDataModel itself can be
	 * parameterized instead (ie. ListDataModel&lt;Communication&gt;).
	 */

	@UiHidden
	public ListDataModel getCurrentCommunications() {

		if ( mModelCommunications == null ) {
			Contact contact = getCurrent();

			if ( contact == null ) {
				mModelCommunications = new ListDataModel<Communication>();
			} else {
				Set<Communication> communications = contact.getCommunications();

				if ( communications == null ) {
					mModelCommunications = new ListDataModel<Communication>();
				} else {
					// (sort for consistency in unit tests)

					List<Communication> sortedCommunications = CollectionUtils.newArrayList( communications );
					Collections.sort( sortedCommunications );

					mModelCommunications = new ListDataModel<Communication>( sortedCommunications );
				}
			}
		}

		return mModelCommunications;
	}

	@UiAction
	@UiFacesAttribute( name = HIDDEN, expression = "#{!contact.readOnly}" )
	public void edit() {

		mReadOnly = false;
	}

	@UiAction
	@UiFacesAttribute( name = HIDDEN, expression = "#{contact.readOnly}" )
	public String save()
		throws Exception {

		try {
			getContactsBean().save( mCurrent );
		} catch ( Exception e ) {
			FacesContext.getCurrentInstance().addMessage( null, new FacesMessage( e.getMessage() ) );
			return null;
		}

		return "index";
	}

	@UiAction
	@UiFacesAttribute( name = HIDDEN, expression = "#{contact.readOnly || contact.current.id == 0}" )
	@UiComesAfter( "save" )
	public String delete()
		throws Exception {

		getContactsBean().delete( getCurrent() );

		return "index";
	}

	@UiAction
	@UiComesAfter( { "edit", "delete" } )
	@UiFacesAttributes( { @UiFacesAttribute( name = LABEL, expression = "#{contact.readOnly ? 'Back' : null}" ) } )
	@UiAttribute( name = FACES_IMMEDIATE, value = "true" )
	public String cancel()
		throws Exception {

		return "index";
	}

	public void addCommunication() {

		CommunicationBean bean = getCommunicationBean();

		try {
			getCurrent().addCommunication( bean.getCurrent() );
		} catch ( Exception e ) {
			FacesContext.getCurrentInstance().addMessage( null, new FacesMessage( e.getMessage() ) );
			return;
		}

		bean.clear();
		mModelCommunications = null;
	}

	public void deleteCommunication() {

		Communication communication = mModelCommunications.getRowData();
		getCurrent().removeCommunication( communication );

		mModelCommunications = null;
	}

	//
	// Private methods
	//

	private ContactsBean getContactsBean() {

		FacesContext context = FacesContext.getCurrentInstance();
		VariableResolver variableResolver = context.getApplication().getVariableResolver();

		return (ContactsBean) variableResolver.resolveVariable( context, "contacts" );
	}

	private CommunicationBean getCommunicationBean() {

		FacesContext context = FacesContext.getCurrentInstance();
		VariableResolver variableResolver = context.getApplication().getVariableResolver();

		return (CommunicationBean) variableResolver.resolveVariable( context, "communication" );
	}
}

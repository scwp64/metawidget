// Metawidget Examples (licensed under BSD License)
//
// Copyright (c) Richard Kennard
// All rights reserved
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
//
// * Redistributions of source code must retain the above copyright notice,
//   this list of conditions and the following disclaimer.
// * Redistributions in binary form must reproduce the above copyright notice,
//   this list of conditions and the following disclaimer in the documentation
//   and/or other materials provided with the distribution.
// * Neither the name of Richard Kennard nor the names of its contributors may
//   be used to endorse or promote products derived from this software without
//   specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
// FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
// OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
// OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
// OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
// OF THE POSSIBILITY OF SUCH DAMAGE.

package org.metawidget.example.gwt.addressbook.client.ui;

import java.util.Date;
import java.util.Set;

import org.metawidget.example.gwt.addressbook.client.ui.converter.DateConverter;
import org.metawidget.example.gwt.addressbook.client.ui.converter.EnumConverter;
import org.metawidget.example.shared.addressbook.model.Communication;
import org.metawidget.example.shared.addressbook.model.Contact;
import org.metawidget.example.shared.addressbook.model.Gender;
import org.metawidget.example.shared.addressbook.model.PersonalContact;
import org.metawidget.gwt.client.ui.Facet;
import org.metawidget.gwt.client.ui.GwtMetawidget;
import org.metawidget.gwt.client.ui.Stub;
import org.metawidget.gwt.client.ui.layout.FlexTableLayout;
import org.metawidget.gwt.client.ui.layout.FlexTableLayoutConfig;
import org.metawidget.gwt.client.ui.layout.FlowLayout;
import org.metawidget.gwt.client.ui.layout.LabelLayoutDecorator;
import org.metawidget.gwt.client.ui.layout.LabelLayoutDecoratorConfig;
import org.metawidget.gwt.client.widgetprocessor.binding.simple.SimpleBindingProcessor;
import org.metawidget.gwt.client.widgetprocessor.binding.simple.SimpleBindingProcessorAdapter;
import org.metawidget.gwt.client.widgetprocessor.binding.simple.SimpleBindingProcessorConfig;
import org.metawidget.util.simple.StringUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Dialog box for Address Book Contacts.
 * <p>
 * Note: for performance, this example is optimized to use <code>rebind</code> (see 'rebinding' in
 * the Reference Documentation). This results in slightly more complex code. For a more
 * straightforward example, see <code>org.metawidget.example.swing.addressbook.ContactDialog</code>.
 * 
 * @author <a href="http://kennardconsulting.com">Richard Kennard</a>
 */

public class ContactDialog
	extends DialogBox {

	//
	// Package-level members
	//

	AddressBookModule	mAddressBookModule;

	GwtMetawidget		mMetawidget;

	/**
	 * Manually-created nested Metawidet. This is an optimization. By creating it manually, rather
	 * than having Metawidget automatically create it, we avoid Metawidget destroying it and
	 * recreating it (and hence re-running a setToInspect) upon a rebind
	 */

	GwtMetawidget		mAddressMetawidget;

	FlexTable			mCommunications;

	Button				mEditButton;

	Button				mSaveButton;

	Button				mDeleteButton;

	Button				mCancelButton;

	//
	// Constructor
	//

	public ContactDialog( AddressBookModule addressBookModule, final Contact contact ) {

		mAddressBookModule = addressBookModule;

		setStyleName( "contact-dialog" );
		setPopupPosition( 100, 50 );
		Grid grid = new Grid( 1, 2 );
		grid.setWidth( "100%" );
		setWidget( grid );

		// Left-hand image

		CellFormatter gridFormatter = grid.getCellFormatter();
		Image image = new Image();
		gridFormatter.setStyleName( 0, 0, "page-image" );
		grid.setWidget( 0, 0, image );
		gridFormatter.setStyleName( 0, 1, "content" );

		// Bundle

		Dictionary dictionary = Dictionary.getDictionary( "bundle" );

		// Title

		StringBuilder builder = new StringBuilder( contact.getFullname() );

		if ( builder.length() > 0 ) {
			builder.append( " - " );
		}

		// Personal/business icon

		if ( contact instanceof PersonalContact ) {
			builder.append( dictionary.get( "personalContact" ) );
			image.setUrl( "media/personal.gif" );
		} else {
			builder.append( dictionary.get( "businessContact" ) );
			image.setUrl( "media/business.gif" );
		}

		setText( builder.toString() );

		// Metawidget

		mMetawidget = new GwtMetawidget();
		mMetawidget.setReadOnly( contact.getId() != 0 );
		mMetawidget.setDictionaryName( "bundle" );

		FlexTableLayoutConfig layoutConfig = new FlexTableLayoutConfig();
		layoutConfig.setTableStyleName( "table-form" );
		layoutConfig.setColumnStyleNames( "table-label-column", "table-component-column", "table-required-column" );
		layoutConfig.setFooterStyleName( "buttons" );
		LabelLayoutDecoratorConfig layoutDecoratorConfig = new LabelLayoutDecoratorConfig();
		layoutDecoratorConfig.setStyleName( "section-heading" );
		layoutDecoratorConfig.setLayout( new FlexTableLayout( layoutConfig ) );

		// Use Label for headings. You can try changing this line to...
		//
		// TabPanelLayoutDecorator layout = new TabPanelLayoutDecorator( layoutDecoratorConfig );
		//
		// ...to use a TabPanel instead

		LabelLayoutDecorator layout = new LabelLayoutDecorator( layoutDecoratorConfig );

		mMetawidget.setLayout( layout );
		mMetawidget.setToInspect( contact );
		grid.setWidget( 0, 1, mMetawidget );

		// Binding

		SimpleBindingProcessorConfig config = new SimpleBindingProcessorConfig();

		@SuppressWarnings( "unchecked" )
		SimpleBindingProcessorAdapter<Contact> contactAdapter = (SimpleBindingProcessorAdapter<Contact>) GWT.create( Contact.class );
		config.setAdapter( Contact.class, contactAdapter );
		config.setConverter( Date.class, new DateConverter() );
		config.setConverter( Gender.class, new EnumConverter<Gender>( Gender.class ) );

		SimpleBindingProcessor processor = new SimpleBindingProcessor( config );
		mMetawidget.addWidgetProcessor( processor );

		// Address override

		mAddressMetawidget = new GwtMetawidget();
		mAddressMetawidget.setName( "address" );
		mMetawidget.add( mAddressMetawidget );

		mAddressMetawidget.setReadOnly( contact.getId() != 0 );
		mAddressMetawidget.setDictionaryName( "bundle" );
		mAddressMetawidget.addWidgetProcessor( processor );
		mAddressMetawidget.setLayout( layout );
		mAddressMetawidget.setToInspect( contact );
		mAddressMetawidget.setPath( Contact.class.getName() + StringUtils.SEPARATOR_FORWARD_SLASH_CHAR + "address" );

		// Communications override

		Stub communicationsStub = new Stub( "communications" );
		mMetawidget.add( communicationsStub );

		mCommunications = new FlexTable();
		mCommunications.setStyleName( "data-table" );
		communicationsStub.add( mCommunications );

		// Header

		final CellFormatter cellFormatter = mCommunications.getCellFormatter();
		mCommunications.setText( 0, 0, "Type" );
		cellFormatter.setStyleName( 0, 0, "header" );
		cellFormatter.addStyleName( 0, 0, "column-half" );
		mCommunications.setText( 0, 1, "Value" );
		cellFormatter.setStyleName( 0, 1, "header" );
		cellFormatter.addStyleName( 0, 1, "column-half" );
		mCommunications.setHTML( 0, 2, "&nbsp;" );
		cellFormatter.setStyleName( 0, 2, "header" );
		cellFormatter.addStyleName( 0, 2, "column-tiny" );

		// Footer

		Communication communication = new Communication();

		final GwtMetawidget typeMetawidget = new GwtMetawidget();
		FlowLayout flowLayout = new FlowLayout();
		typeMetawidget.setLayout( flowLayout );
		typeMetawidget.setToInspect( communication );
		typeMetawidget.setPath( Communication.class.getName() + StringUtils.SEPARATOR_FORWARD_SLASH_CHAR + "type" );
		mCommunications.setWidget( 1, 0, typeMetawidget );

		final GwtMetawidget valueMetawidget = new GwtMetawidget();
		valueMetawidget.setLayout( flowLayout );
		valueMetawidget.setToInspect( communication );
		valueMetawidget.setPath( Communication.class.getName() + StringUtils.SEPARATOR_FORWARD_SLASH_CHAR + "value" );
		mCommunications.setWidget( 1, 1, valueMetawidget );

		Button addButton = new Button( dictionary.get( "add" ) );
		addButton.addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {

				Communication communicationToAdd = new Communication();
				communicationToAdd.setType( (String) typeMetawidget.getValue( "type" ) );
				communicationToAdd.setValue( (String) valueMetawidget.getValue( "value" ) );

				Contact currentContact = mMetawidget.getToInspect();

				try {
					currentContact.addCommunication( communicationToAdd );
				} catch ( Exception e ) {
					Window.alert( e.getMessage() );
					return;
				}

				loadCommunications();

				typeMetawidget.setValue( "", "type" );
				valueMetawidget.setValue( "", "value" );
			}
		} );
		mCommunications.setWidget( 1, 2, addButton );
		cellFormatter.setStyleName( 1, 2, "table-buttons" );

		// Embedded buttons

		Facet buttonsFacet = new Facet();
		buttonsFacet.setName( "buttons" );
		mMetawidget.add( buttonsFacet );
		FlowPanel panel = new FlowPanel();
		buttonsFacet.add( panel );

		mSaveButton = new Button( dictionary.get( "save" ) );
		mSaveButton.addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {

				try {
					mMetawidget.getWidgetProcessor( SimpleBindingProcessor.class ).save( mMetawidget );
				} catch ( Exception e ) {
					Window.alert( e.getMessage() );
				}

				mAddressBookModule.getContactsService().save( (Contact) mMetawidget.getToInspect(), new AsyncCallback<Void>() {

					public void onFailure( Throwable caught ) {

						Window.alert( caught.getMessage() );
					}

					public void onSuccess( Void result ) {

						ContactDialog.this.hide();
						mAddressBookModule.reloadContacts();
					}
				} );
			}
		} );
		panel.add( mSaveButton );

		mDeleteButton = new Button( dictionary.get( "delete" ) );
		mDeleteButton.addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {

				if ( mAddressBookModule.getPanel() instanceof RootPanel && !Window.confirm( "Sure you want to delete this contact?" ) ) {
					return;
				}

				mAddressBookModule.getContactsService().delete( (Contact) mMetawidget.getToInspect(), new AsyncCallback<Boolean>() {

					public void onFailure( Throwable caught ) {

						Window.alert( caught.getMessage() );
					}

					public void onSuccess( Boolean result ) {

						ContactDialog.this.hide();
						mAddressBookModule.reloadContacts();
					}
				} );
			}
		} );
		panel.add( mDeleteButton );

		mEditButton = new Button( dictionary.get( "edit" ) );
		mEditButton.addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {

				mMetawidget.setReadOnly( false );
				setVisibility();
			}
		} );
		panel.add( mEditButton );

		mCancelButton = new Button();
		mCancelButton.addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {

				ContactDialog.this.hide();
			}
		} );
		panel.add( mCancelButton );

		// Display

		setVisibility();
	}

	//
	// Public methods
	//

	public void rebind( Contact contact ) {

		// Reset the Metawidget to either readOnly (for existing contacts) or not readOnly (for new
		// contacts). If the Metawidget is ALREADY readOnly/not readOnly, then this method
		// does nothing. Otherwise it recreates all the child widgets based on the existing
		// setToInspect. It does NOT perform a full server-side re-inspection though

		mMetawidget.setReadOnly( contact.getId() != 0 );

		// Having recreated all child widgets (as readOnly/not readOnly), or done nothing (if the
		// child widgets were already in the desired state), load the new contact into the UI. We
		// use rebind (as opposed to setToInspect) to rebind the widget values without a full
		// server-side re-inspection

		mMetawidget.getWidgetProcessor( SimpleBindingProcessor.class ).rebind( contact, mMetawidget );

		setVisibility();
	}

	//
	// Private methods
	//

	/* package private */void setVisibility() {

		boolean readOnly = mMetawidget.isReadOnly();
		loadCommunications();

		Dictionary dictionary = Dictionary.getDictionary( "bundle" );

		if ( readOnly ) {
			mCancelButton.setText( dictionary.get( "back" ) );
		} else {
			mCancelButton.setText( dictionary.get( "cancel" ) );
		}

		mEditButton.setVisible( readOnly );
		mSaveButton.setVisible( !readOnly );
		mDeleteButton.setVisible( !readOnly );
		mAddressMetawidget.setReadOnly( readOnly );
		mCommunications.getRowFormatter().setVisible( mCommunications.getRowCount() - 1, !readOnly );
	}

	/* package private */void loadCommunications() {

		CellFormatter cellFormatter = mCommunications.getCellFormatter();
		final Contact contact = mMetawidget.getToInspect();
		Set<Communication> communications = contact.getCommunications();
		final boolean readOnly = mMetawidget.isReadOnly();
		final boolean confirm = mAddressBookModule.getPanel() instanceof RootPanel;

		// Communications

		int row = 1;

		if ( communications != null ) {
			for ( final Communication communication : communications ) {
				// (push the footer down)

				if ( mCommunications.getRowCount() - 1 <= row ) {
					mCommunications.insertRow( row );
				}

				mCommunications.setText( row, 0, communication.getType() );
				mCommunications.setText( row, 1, communication.getValue() );

				if ( readOnly ) {
					if ( mCommunications.getCellCount( row ) == 3 ) {
						mCommunications.removeCell( row, 2 );
					}
				} else {
					final Button deleteButton = new Button( "Delete" );
					deleteButton.addClickHandler( new ClickHandler() {

						public void onClick( ClickEvent event ) {

							if ( confirm && !Window.confirm( "Sure you want to delete this communication?" ) ) {
								return;
							}

							contact.removeCommunication( communication );
							loadCommunications();
						}
					} );

					mCommunications.setWidget( row, 2, deleteButton );
					cellFormatter.setStyleName( row, 2, "table-buttons" );
				}

				row++;
			}
		}

		// Cleanup any extra rows

		while ( mCommunications.getRowCount() - 1 > row ) {
			mCommunications.removeRow( row );
		}
	}
}

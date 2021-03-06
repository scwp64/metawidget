# Metawidget

Metawidget ${project.version} is a smart User Interface widget that
populates itself with UI components to match the properties of your
domain objects. Can be used through Node.js for server-side UI generation.

## Introduction
For an introduction to Metawidget, please see the tutorial at
http://metawidget.org/doc/reference/en/html/ch01s02.html

The Node.js version of Metawidget must be used in combination with a DOM
implementation. This can either be jsdom, envjs, or even a simple implementation
of your own (see test/render.js inside this module for an example). Metawidget
must be wrapped around a DOM element. The Metawidget constructor takes this
element, and thereafter always uses `element.ownerDocument` rather than
referencing any global `document` object.

## Installation
`npm install metawidget`

## Usage
See `test/render.js` inside this module for a working example. But briefly:

    var metawidget = require( 'metawidget' );
    ...
    var mw = new metawidget.Metawidget( yourElement );
    mw.toInspect = {
	    firstname: "Joe",
	    surname: "Bloggs"
    };
    mw.buildWidgets();
    ...
    // yourElement is now populated with child components
    
## Extras
This npm distribution also includes all client-side versions of
Metawidget, including pure JavaScript, AngularJS, Bootstrap, JQuery UI
and JQuery Mobile versions. These can be found in the `/extras` folder.
They are useful if you're employing npm for your client-side package
management (as opposed to, say, Bower).

## License
This file is dual licensed under both the LGPL
(http://www.gnu.org/licenses/lgpl-2.1.html) and the EPL
(http://www.eclipse.org/org/documents/epl-v10.php). As a
recipient of Metawidget, you may choose to receive it under either
the LGPL or the EPL.

Commercial licenses are also available. See http://metawidget.org
for details.

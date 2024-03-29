package org.synthful.gwt.widgets.client.ui;

/*
 * Copyright 2009 Google Inc. Licensed under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
/*
 * Modified by Blessed Geek as a patch to allow caption region to host a close
 * button that is sensitive to mouse events.
 * Modified 2009-07-09.
 */

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

/**
 * A form of popup that has a caption area at the top and can be dragged by the
 * user. Unlike a PopupPanel, calls to {@link #setWidth(String)} and
 * {@link #setHeight(String)} will set the width and height of the dialog box
 * itself, even if a widget has not been added as yet.
 * <p>
 * <img class='gallery' src='DialogBox.png'/>
 * </p>
 * <h3>CSS Style Rules</h3>
 * <ul>
 * <li>.gwt-DialogBox { the outside of the dialog }</li>
 * <li>.gwt-DialogBox .Caption { the caption }</li>
 * <li>.gwt-DialogBox .dialogContent { the wrapper around the content }</li>
 * <li>.gwt-DialogBox .dialogTopLeft { the top left cell }</li>
 * <li>.gwt-DialogBox .dialogTopLeftInner { the inner element of the cell }</li>
 * <li>.gwt-DialogBox .dialogTopCenter { the top center cell, where the caption
 * is located }</li>
 * <li>.gwt-DialogBox .dialogTopCenterInner { the inner element of the cell }</li>
 * <li>.gwt-DialogBox .dialogTopRight { the top right cell }</li>
 * <li>.gwt-DialogBox .dialogTopRightInner { the inner element of the cell }</li>
 * <li>.gwt-DialogBox .dialogMiddleLeft { the middle left cell }</li>
 * <li>.gwt-DialogBox .dialogMiddleLeftInner { the inner element of the cell }</li>
 * <li>.gwt-DialogBox .dialogMiddleCenter { the middle center cell, where the
 * content is located }</li>
 * <li>.gwt-DialogBox .dialogMiddleCenterInner { the inner element of the cell }
 * </li>
 * <li>.gwt-DialogBox .dialogMiddleRight { the middle right cell }</li>
 * <li>.gwt-DialogBox .dialogMiddleRightInner { the inner element of the cell }</li>
 * <li>.gwt-DialogBox .dialogBottomLeft { the bottom left cell }</li>
 * <li>.gwt-DialogBox .dialogBottomLeftInner { the inner element of the cell }</li>
 * <li>.gwt-DialogBox .dialogBottomCenter { the bottom center cell }</li>
 * <li>.gwt-DialogBox .dialogBottomCenterInner { the inner element of the cell }
 * </li>
 * <li>.gwt-DialogBox .dialogBottomRight { the bottom right cell }</li>
 * <li>.gwt-DialogBox .dialogBottomRightInner { the inner element of the cell }</li>
 * </ul>
 * <p>
 * <h3>Example</h3> {@example com.google.gwt.examples.DialogBoxExample}
 * </p>
 */
@SuppressWarnings("deprecation")
public class DialogPanel
	extends DecoratedPopupPanel
	implements MouseListener
	
{
	@UiTemplate("DialogPanel-Caption.ui.xml")
	interface UIDialogBoxUiBinder
		extends UiBinder<Widget, DialogPanel>{}

	private static UIDialogBoxUiBinder captionUiBinder =
		GWT.create(UIDialogBoxUiBinder.class);
	@UiField
	HTMLPanel captionPanel;
	
	@UiField
	HTML captionTitle;
	
	@UiField
	Label closer;


	// The events are not firing normally because DialogBox has over-riden
	// onBrowserEvent with some obtuse logic.
	// So we have to create our own tiny system of event handling for the closer
	// button
	// in conjunction with List<AntiObtusedCloserHandler>
	public interface CloserEventHandler
	{
		public void onClick(Event event);

		public void onMouseOver(Event event);

		public void onMouseOut(Event event);
	}

	/*
	 * 
	 * @gwt.TypeArgs parameters
	 * <java.util.ArrayList<com.google.gwt.user.client.ui
	 * .UIDialogBox.CloserEventHandler>>
	 */
	final public ArrayList<CloserEventHandler> CloserEventHandlers =
		new ArrayList<CloserEventHandler>();

	private class CloserHandler
		implements CloserEventHandler
	{

		public void onClick(Event event) {
			hide();
		}

		public void onMouseOver(Event event) {
			DOM.setStyleAttribute(closer.getElement(), "color", "red");
			DOM.setStyleAttribute(closer.getElement(), "borderTop", "2px solid #666");
			DOM.setStyleAttribute(closer.getElement(), "borderLeft", "2px solid #666");
			DOM.setStyleAttribute(closer.getElement(), "borderRight", "1px solid #999");
			DOM.setStyleAttribute(closer.getElement(), "borderBottom", "1px");
		}

		public void onMouseOut(Event event) {
			DOM.setStyleAttribute(closer.getElement(), "color", "black");
			DOM.setStyleAttribute(closer.getElement(), "borderRight", "2px solid #666");
			DOM.setStyleAttribute(closer.getElement(), "borderBottom", "2px solid #666");
			DOM.setStyleAttribute(closer.getElement(), "borderLeft", "1px solid #999");
			DOM.setStyleAttribute(closer.getElement(), "borderTop", "1px");
		}
	}

	private class CaptionMouseHandler
		implements MouseDownHandler, MouseUpHandler, MouseOutHandler,
		MouseOverHandler, MouseMoveHandler
	{

		public void onMouseDown(MouseDownEvent event) {
			beginDragging(event);
		}

		public void onMouseMove(MouseMoveEvent event) {
			continueDragging(event);
		}

		public void onMouseOut(MouseOutEvent event) {
			DialogPanel.this.onMouseLeave(captionPanel);
		}

		public void onMouseOver(MouseOverEvent event) {
			DialogPanel.this.onMouseEnter(captionPanel);
		}

		public void onMouseUp(MouseUpEvent event) {
			endDragging(event);
		}
	}

	/**
	 * The default style name.
	 */
	private static final String DEFAULT_STYLENAME = "gwt-DialogBox";

	//final public HorizontalPanel captionPanel = new HorizontalPanel();

	//final private FocusPanel contentPanel = new FocusPanel();

	//public int Width, Height;

	//private CaptionImpl caption = new CaptionImpl();

	protected boolean dragging;

	protected int dragStartX, dragStartY;

	protected int windowWidth;

	protected int clientLeft;

	protected int clientTop;

	private HandlerRegistration resizeHandlerRegistration;

	/**
	 * Creates an empty dialog box. It should not be shown until its child
	 * widget has been added using {@link #add(Widget)}.
	 */
	public DialogPanel() {
		this(false);
	}

	/**
	 * Creates an empty dialog box specifying its "auto-hide" property. It
	 * should not be shown until its child widget has been added using
	 * {@link #add(Widget)}.
	 * 
	 * @param autoHide
	 *            <code>true</code> if the dialog should be automatically hidden
	 *            when the user clicks outside of it
	 */
	public DialogPanel(boolean autoHide) {
		this(autoHide, true);
	}

	/**
	 * Creates an empty dialog box specifying its "auto-hide" property. It
	 * should not be shown until its child widget has been added using
	 * {@link #add(Widget)}.
	 * 
	 * @param autoHide
	 *            <code>true</code> if the dialog should be automatically hidden
	 *            when the user clicks outside of it
	 * @param modal
	 *            <code>true</code> if keyboard and mouse events for widgets not
	 *            contained by the dialog should be ignored
	 */
	public DialogPanel(boolean autoHide, boolean modal) {
		super(autoHide, modal);

		captionUiBinder.createAndBindUi(this);
		// Add the caption to the top row of the decorator panel. We need to
		// logically adopt the caption panel so we can catch mouse events.
		Element td01 = getCellElement(0, 1);
		DOM.appendChild(td01, this.captionPanel.getElement());
		adopt(this.captionPanel);

		this.CloserEventHandlers.add(new CloserHandler());

		this.captionPanel.setStyleName("Caption");

		// Set the style name
		//setStyleName(DEFAULT_STYLENAME);

		windowWidth = Window.getClientWidth();
		clientLeft = Document.get().getBodyOffsetLeft();
		clientTop = Document.get().getBodyOffsetTop();

		CaptionMouseHandler mouseHandler = new CaptionMouseHandler();
		addDomHandler(mouseHandler, MouseDownEvent.getType());
		addDomHandler(mouseHandler, MouseUpEvent.getType());
		addDomHandler(mouseHandler, MouseMoveEvent.getType());
		addDomHandler(mouseHandler, MouseOverEvent.getType());
		addDomHandler(mouseHandler, MouseOutEvent.getType());
	}

	@Override
	public void hide() {
		if (resizeHandlerRegistration != null) {
			resizeHandlerRegistration.removeHandler();
			resizeHandlerRegistration = null;
		}
		super.hide();
	}

	@Override
	public void onBrowserEvent(Event event) {
		if (isCaptionControlEvent(event)) {
			for (CloserEventHandler handler : this.CloserEventHandlers) {
				switch (event.getTypeInt())
				{
				case Event.ONMOUSEUP:
				case Event.ONCLICK:
					handler.onClick(event);
					break;
				case Event.ONMOUSEOVER:
					handler.onMouseOver(event);
					break;
				case Event.ONMOUSEOUT:
					handler.onMouseOut(event);
					break;
				}
			}
			return;
		}

		// If we're not yet dragging, only trigger mouse events if the event
		// occurs
		// in the caption wrapper
		switch (event.getTypeInt())
		{
		case Event.ONMOUSEDOWN:
		case Event.ONMOUSEUP:
		case Event.ONMOUSEMOVE:
		case Event.ONMOUSEOVER:
		case Event.ONMOUSEOUT:
			if (!dragging) {
				if (!isCaptionEvent(event)) return;
			}
		}

		super.onBrowserEvent(event);
	}

	/**
	 * @deprecated Use {@link #beginDragging} and {@link #getCaption} instead
	 */
	@Deprecated
	public void onMouseDown(Widget sender, int x, int y) {
		dragging = true;
		DOM.setCapture(getElement());
		dragStartX = x;
		dragStartY = y;
		// System.out.println("onMouseDown:"+sender);
	}

	/**
	 * @deprecated Use {@link Caption#addMouseOverHandler} instead
	 */
	@Deprecated
	public void onMouseEnter(Widget sender) {}

	/**
	 * @deprecated Use {@link Caption#addMouseOutHandler} instead
	 */
	@Deprecated
	public void onMouseLeave(Widget sender) {}

	/**
	 * @deprecated Use {@link #continueDragging} and {@link #getCaption} instead
	 */
	@Deprecated
	public void onMouseMove(Widget sender, int x, int y) {
		if (dragging) {
			int absX = x + getAbsoluteLeft();
			int absY = y + getAbsoluteTop();

			// if the mouse is off the screen to the left, right, or top, don't
			// move the dialog box. This would let users lose dialog boxes,
			// which
			// would be bad for modal popups.
			if (absX < clientLeft || absX >= windowWidth || absY < clientTop) {
				return;
			}

			setPopupPosition(absX - dragStartX, absY - dragStartY);
		}
	}

	/**
	 * @deprecated Use {@link #endDragging} and {@link #getCaption} instead
	 */
	@Deprecated
	public void onMouseUp(Widget sender, int x, int y) {
		dragging = false;
		DOM.releaseCapture(getElement());
		// System.out.println("onMouseUp:"+sender);
	}

	public final HTMLPanel getCaptionPanel() {
		return captionPanel;
	}

	public String getCaptionHTML() {
		return this.captionTitle.getHTML();
	}

	public String getCaptionText() {
		return this.captionTitle.getText();
	}

	/**
	 * Sets the html string inside the caption. Use {@link #setWidget(Widget)}
	 * to set the contents inside the {@link DialogPanel}.
	 * 
	 * @param html
	 *            the object's new HTML
	 */
	public void setCaptionHTML(String html) {
		this.captionTitle.setHTML(html);
	}

	/**
	 * Sets the text inside the caption. Use {@link #setWidget(Widget)} to set
	 * the contents inside the {@link DialogPanel}.
	 * 
	 * @param text
	 *            the object's new text
	 */
	public void setCaptionText(String text) {
		this.captionTitle.setText(text);
	}

	@Override
	public void show() {
		if (resizeHandlerRegistration == null) {
			resizeHandlerRegistration =
				Window.addResizeHandler(new ResizeHandler()
				{
					public void onResize(ResizeEvent event) {
						windowWidth = event.getWidth();
					}
				});
		}
		super.show();
	}

	public void setHeightPx(int hgt) {
		this.setWidth(hgt + "px");
	}

	public void setWidthPx(int wid) {
		this.setWidth(wid + "px");
	}

	public void setSizePx(int wid, int hgt) {
		this.setSize(wid + "px", hgt + "px");
	}


	/**
	 * Called on mouse down in the caption area, begins the dragging loop by
	 * turning on event capture.
	 * 
	 * @see DOM#setCapture
	 * @see #continueDragging
	 * @param event
	 *            the mouse down event that triggered dragging
	 */
	protected void beginDragging(MouseDownEvent event) {
		onMouseDown(this.captionPanel, event.getX(), event.getY());
	}

	/**
	 * Called on mouse move in the caption area, continues dragging if it was
	 * started by {@link #beginDragging}.
	 * 
	 * @see #beginDragging
	 * @see #endDragging
	 * @param event
	 *            the mouse move event that continues dragging
	 */
	protected void continueDragging(MouseMoveEvent event) {
		onMouseMove(this.captionPanel, event.getX(), event.getY());
	}

	@Override
	protected void doAttachChildren() {
		super.doAttachChildren();

		// See comment in doDetachChildren for an explanation of this call
		// caption.onAttach();
	}

	@Override
	protected void doDetachChildren() {
		super.doDetachChildren();

		// We need to detach the caption specifically because it is not part of
		// the
		// iterator of Widgets that the {@link SimplePanel} super class returns.
		// This is similar to a {@link ComplexPanel}, but we do not want to
		// expose
		// the caption widget, as its just an internal implementation.
		// caption.onDetach();
	}

	/**
	 * Called on mouse up in the caption area, ends dragging by ending event
	 * capture.
	 * 
	 * @param event
	 *            the mouse up event that ended dragging
	 * @see DOM#releaseCapture
	 * @see #beginDragging
	 * @see #endDragging
	 */
	protected void endDragging(MouseUpEvent event) {
		onMouseUp(this.captionPanel, event.getX(), event.getY());
	}

	/**
	 * <b>Affected Elements:</b>
	 * <ul>
	 * <li>-caption = text at the top of the {@link DialogPanel}.</li>
	 * <li>-content = the container around the content.</li>
	 * </ul>
	 * 
	 * @see UIObject#onEnsureDebugId(String)
	 */
	@Override
	protected void onEnsureDebugId(String baseID) {
		super.onEnsureDebugId(baseID);
		this.captionPanel.ensureDebugId(baseID + "-caption");
		ensureDebugId(getCellElement(1, 1), baseID, "content");
	}

	@Override
	protected void onPreviewNativeEvent(NativePreviewEvent event) {
		// We need to preventDefault() on mouseDown events (outside of the
		// DialogBox content) to keep text from being selected when it
		// is dragged.
		NativeEvent nativeEvent = event.getNativeEvent();

		if (!event.isCanceled() && (event.getTypeInt() == Event.ONMOUSEDOWN)) {
			// System.out.println("nativeEvent:"+nativeEvent);
			if (isCaptionEvent(nativeEvent)) nativeEvent.preventDefault();
		}

		super.onPreviewNativeEvent(event);
	}

	protected boolean isCaptionEvent(NativeEvent event) {
		return isWidgetEvent(event, this.captionPanel) &&
			!isWidgetEvent(event, this.closer);
	}

	protected boolean isCaptionControlEvent(NativeEvent event) {
		return isWidgetEvent(event, this.closer);
	}

	static protected boolean isWidgetEvent(NativeEvent event, Widget w) {
		EventTarget target = event.getEventTarget();
		if (Element.is(target)) {
			boolean t = w.getElement().isOrHasChild(Element.as(target));
			// if (t)
			// System.out.println("isWidgetEvent:"+w+':'+target+':'+t);
			return t;
		}
		return false;
	}
}

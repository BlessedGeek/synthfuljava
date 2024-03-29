package org.synthful.gwt.mvp.client.fields;

import org.synthful.gwt.mvp.client.PresentationEntity;

import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Widget;

public class FieldSet<FldEnt extends PresentationEntity>
extends FieldSetDescriptor<FldEnt, LabelledField<?, ?>> {

	@UiConstructor
	public FieldSet(String caption) {
		this.caption = caption;
	}
	
	public void add(Widget w){
		if (w instanceof LabelledField<?, ?>){
			LabelledField<?, ?> f = (LabelledField<?, ?>)w;
			this.fields.add(f);
		}
	}
			
	public int fillGrid(LabelledFieldGrid grid, int startRow){
		for (LabelledField<?, ?> f : this.getFields()){
			grid.setText(startRow, 0, f.getLabel());
			grid.setWidget(startRow++, 1, f.getField());
		}
		
		return startRow;
	}

}

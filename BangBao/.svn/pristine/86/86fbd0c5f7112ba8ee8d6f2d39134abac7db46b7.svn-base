package net.bangbao.common;

import net.bangbao.utils.ABRegUtil;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * @author mosl
 * 监听Edit ,不能输入中文，空格
 */
public class EditTextWatcher implements TextWatcher{

	private String str;
	private EditText edit;
	private int item;  //0：空格不行  1：空格或中文都不行
	
	public EditTextWatcher(EditText edit,int item) {
		this.edit = edit;
		this.item = item;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		str = s.toString();
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		
	}

	@Override
	public void afterTextChanged(Editable s) {
		
		if(s.toString().equals(str)){  
    		return;  
    	}
		
		if(item == 0){
			int editStart = edit.getSelectionStart();
			int offset = s.toString().length() - str.length();
			if (s.toString().endsWith(" ")){
				UIHelper.showToastMessage("不能输入空格");
				s.delete(s.toString().length() - offset, s.toString().length());
				int tempSelection = editStart - offset;
				edit.setText(s);
				edit.setSelection(tempSelection);
			}
		}else if(item == 1){
			
			int editStart = edit.getSelectionStart();
			int offset = s.toString().length() - str.length();
			if (ABRegUtil.isCN(s.toString()) || s.toString().endsWith(" ")){
				UIHelper.showToastMessage("不能输入中文或空格");
				s.delete(s.toString().length() - offset, s.toString().length());
				int tempSelection = editStart - offset;
				edit.setText(s);
				edit.setSelection(tempSelection);
			}
		}
		
	}

}

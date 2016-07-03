package net.bangbao.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.bangbao.R;
import net.bangbao.adapter.AbstractSpinerAdapter;
import net.bangbao.adapter.CustemSpinerAdapter;
import net.bangbao.widget.SpinerPopWindow;
import android.app.Activity;
import android.app.AlertDialog;
import android.database.DataSetObserver;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class DateTimePickDialogUtil implements OnDateChangedListener,OnClickListener,AbstractSpinerAdapter.IOnItemSelectListener{
	
	private DatePicker datePicker;
	private AlertDialog ad,ad1,ad2,ad3;
	private String dateTime;
	private String initDateTime;
	private Activity activity;
	private TextView textView;
	
	private TextView pr1,pr2,pr3;
	private TestResultUtil tru;
	
	private static final String UNSELECTED = "有未选选项";
	private boolean toNext = false;
	private boolean toNext1 = false;
	
	private TextView percent,accident,health,old,invest,person,one,two,three,four,detail;
	private Button ok;
	
	
	private int age;
	private boolean isMarried;
	private boolean hasBaby;
	private String babyAge;
	private int ageOfBaby;
	private EditText editText;
	private int index;//下拉框选择的次序
	private RadioGroup radioGroup,radioGroup1;
	
	private int something,something1;
	
	private RadioButton radioButton1,radioButton2;
	
	private TextView mTView;
	private ImageView mIView;
	private View mRootView;
	private AbstractSpinerAdapter mAdapter;
	private List<CustomObject> nameList = new ArrayList<CustomObject>();
	
	private Button button1,button2,button3;
	
	public DateTimePickDialogUtil(Activity activity,String initDateTime) {
		this.activity = activity;
		this.initDateTime = initDateTime;
	}
	
	public void init(DatePicker datePicker) {
		
		Calendar calendar = Calendar.getInstance();
		if(!(null == initDateTime || "".equals(initDateTime))) {
			calendar = this.getCalendarByInitData(initDateTime);
		}else{
			initDateTime = calendar.get(Calendar.YEAR)+"年"
					+calendar.get(Calendar.MONTH)+"月"
					+calendar.get(Calendar.DAY_OF_MONTH)+"日";
		}
		
		datePicker.init(calendar.get(Calendar.YEAR), calendar.get((Calendar.MONTH)), calendar.get(Calendar.DAY_OF_MONTH), this);
	}
	
	public AlertDialog dateTimePickDialog(final Button button){
		
		LinearLayout dateTimeLayout = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.test_dialog, null);
		
		datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datePicker1);
		init(datePicker);
		textView = (TextView) dateTimeLayout.findViewById(R.id.id_time);
		textView.setText(initDateTime);
		ad = new AlertDialog.Builder(activity).setView(dateTimeLayout)
				.show();
		
		button1 = (Button) dateTimeLayout.findViewById(R.id.id_next1);
		button1.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ad.dismiss();
				
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						//获取用户年龄
						age = getAgeByBirthday(getDate());
						
						Log.d("debug", "age"+age);
						
						LinearLayout dateTimeLayout1 = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.test_dialog2, null);
						
						pr1 = (TextView) dateTimeLayout1.findViewById(R.id.id_pr1);
						pr2 = (TextView) dateTimeLayout1.findViewById(R.id.id_pr2);
						pr3 = (TextView) dateTimeLayout1.findViewById(R.id.id_pr3);
						
						ad1 = new AlertDialog.Builder(activity).setView(dateTimeLayout1).show();
						
						radioButton1 = (RadioButton) dateTimeLayout1.findViewById(R.id.yes2);
						radioButton2 = (RadioButton) dateTimeLayout1.findViewById(R.id.no2);
						radioGroup = (RadioGroup) dateTimeLayout1.findViewById(R.id.rg1);
						radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							
							@Override
							public void onCheckedChanged(RadioGroup group, int checkedId) {
								
								if(checkedId!=R.id.yes1&&checkedId!=R.id.no1){
									something = 0;
//									button2.setClickable(false);
//									UIHelper.showToastMessage(activity, UNSELECTED);
								}else{
									something = 1;
									isMarried = checkedId==R.id.yes1?true:false;
								}
								if(!isMarried){
									radioButton1.setChecked(false);
									radioButton2.setChecked(true);
									radioGroup1.setVisibility(View.GONE);
									pr1.setVisibility(View.GONE);
									pr2.setVisibility(View.GONE);
									pr3.setVisibility(View.GONE);
									editText.setVisibility(View.GONE);
								}
								else{
									radioGroup1.setVisibility(View.VISIBLE);
									pr1.setVisibility(View.VISIBLE);
//									pr2.setVisibility(View.VISIBLE);
//									pr3.setVisibility(View.VISIBLE);
//									editText.setVisibility(View.VISIBLE);
								}
							}
						});
						
						radioGroup1 = (RadioGroup) dateTimeLayout1.findViewById(R.id.rg2);
						radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							
							@Override
							public void onCheckedChanged(RadioGroup group, int checkedId) {
								
								if(checkedId!=R.id.yes2&&checkedId!=R.id.no2){
									something1 = 0;
//									button2.setClickable(false);
//									UIHelper.showToastMessage(activity, UNSELECTED);
								}else{
									something1 = 1;
									hasBaby = checkedId==R.id.yes2?true:false;
								}
								
								
								if(!hasBaby){
									pr2.setVisibility(View.GONE);
									pr3.setVisibility(View.GONE);
									editText.setVisibility(View.GONE);
								}
								else{
									pr2.setVisibility(View.VISIBLE);
									pr3.setVisibility(View.VISIBLE);
									editText.setVisibility(View.VISIBLE);
								}
							}
						});
					
						editText = (EditText) dateTimeLayout1.findViewById(R.id.edittext);
						button2 = (Button) dateTimeLayout1.findViewById(R.id.id_next2);
						button2.setOnClickListener(new android.view.View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								
								while(toNext){
									radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
										
										@Override
										public void onCheckedChanged(RadioGroup group, int checkedId) {
											
											if(checkedId!=R.id.yes1&&checkedId!=R.id.no1){
												something = 0;
											}else{
												something = 1;
												isMarried = checkedId==R.id.yes1?true:false;
											}
											if(!isMarried){
												radioButton1.setChecked(false);
												radioButton2.setChecked(true);
												radioGroup1.setVisibility(View.GONE);
												pr1.setVisibility(View.GONE);
												pr2.setVisibility(View.GONE);
												pr3.setVisibility(View.GONE);
												editText.setVisibility(View.GONE);
											}
											else{
												radioGroup1.setVisibility(View.VISIBLE);
												pr1.setVisibility(View.VISIBLE);
//												pr2.setVisibility(View.VISIBLE);
//												pr3.setVisibility(View.VISIBLE);
//												editText.setVisibility(View.VISIBLE);
											}
										}
									});
									
									if(something == 0){
										UIHelper.showToastMessage( UNSELECTED);
										button2.setClickable(false);
									}
									else
										toNext = true;
									
								}
								
								if(something == 0){
									UIHelper.showToastMessage(UNSELECTED);
//									button2.setClickable(false);
								}
								
								if(something == 1){
									
									if(!isMarried){
										
										button2.setClickable(true);
										ad1.dismiss();
										handler1.post(new Runnable() {
											
											@Override
											public void run() {
												
												//获取小孩子的年龄
												if(hasBaby){
													babyAge = editText.getText().toString().trim();
													ageOfBaby = Integer.parseInt(babyAge);
													Log.d("debug", "babyAge"+babyAge);
												}
												LinearLayout dateTimeLayout2 = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.test_dialog3, null);
												mRootView = dateTimeLayout2.findViewById(R.id.rootView);
												mTView = (TextView) dateTimeLayout2.findViewById(R.id.value);
												mIView = (ImageView) dateTimeLayout2.findViewById(R.id.bt_down);
												mIView.setOnClickListener(DateTimePickDialogUtil.this);
												
												String[] names = DateTimePickDialogUtil.this.activity.getResources().getStringArray(R.array.money_list);
												for(int i=0;i<names.length;i++){
													CustomObject object = new CustomObject();
													object.data = names[i];
													nameList.add(object);
												}
												
												mAdapter = new CustemSpinerAdapter(DateTimePickDialogUtil.this.activity);
												mAdapter.refreshData(nameList, 0);
												
												mSpinerPopWindow = new SpinerPopWindow(activity);
												mSpinerPopWindow.setAdapter(mAdapter);
												mSpinerPopWindow.setItemListener(DateTimePickDialogUtil.this);
												ad2 = new AlertDialog.Builder(activity).setView(dateTimeLayout2).show();
												
												button3 = (Button) dateTimeLayout2.findViewById(R.id.id_next3);
												button3.setOnClickListener(new android.view.View.OnClickListener() {
													
													@Override
													public void onClick(View v) {
														
														index = getPostion();
														Log.d("debug", "get index"+index);
														
														if(index == 0){
															UIHelper.showToastMessage(UNSELECTED);
														}
														
														else{
															ad2.dismiss();
															handler2.post(new Runnable() {
																
																@Override
																public void run() {
																	//获取选择的收入等级
																	index = getPostion();
																	Log.d("debug", "get index"+index);
																	
																	if(index == 0){
																		UIHelper.showToastMessage(UNSELECTED);
																	}
																	
																	LinearLayout dateTimeLayout3 = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.test_result, null);
																	
																	//呈现测试结果
																	percent = (TextView) dateTimeLayout3.findViewById(R.id.id_percent);
																	accident = (TextView) dateTimeLayout3.findViewById(R.id.id_accident);
																	health = (TextView) dateTimeLayout3.findViewById(R.id.id_health);
																	old = (TextView) dateTimeLayout3.findViewById(R.id.id_old);
																	invest = (TextView) dateTimeLayout3.findViewById(R.id.id_invest);
																	person = (TextView) dateTimeLayout3.findViewById(R.id.id_person);
																	one = (TextView) dateTimeLayout3.findViewById(R.id.id_one);
																	two = (TextView) dateTimeLayout3.findViewById(R.id.id_two);
																	three = (TextView) dateTimeLayout3.findViewById(R.id.id_three);
																	four = (TextView) dateTimeLayout3.findViewById(R.id.id_four);
																	detail = (TextView) dateTimeLayout3.findViewById(R.id.detail);
																	
																	tru = new TestResultUtil();
																	
																	if(age>=18&&age<=28){
																		if(!isMarried){
																			
																			if(index==1){
																				percent.setText(tru.PERCENT1);
																				accident.setText(tru.ACCIDENT1);
																				health.setText(tru.HEALTH1);
																				old.setText(tru.OLD1);
																				invest.setText(tru.INVEST1);
																				person.setText(tru.PERSON1);
																				one.setText(tru.ONE1);
																				two.setText(tru.TWO1);
																				three.setText(tru.THREE1);
																				four.setText(tru.FOUR1);
																				detail.setText(tru.DETAIL1);
																				
																			}
																			else if(index==2){
																				percent.setText(tru.PERCENT2);
																				accident.setText(tru.ACCIDENT2);
																				health.setText(tru.HEALTH2);
																				old.setText(tru.OLD2);
																				invest.setText(tru.INVEST2);
																				person.setText(tru.PERSON2);
																				one.setText(tru.ONE2);
																				two.setText(tru.TWO2);
																				three.setText(tru.THREE2);
																				four.setText(tru.FOUR2);
																				detail.setText(tru.DETAIL2);
																				
																			}
																			else if(index==3){
																				percent.setText(tru.PERCENT3);
																				accident.setText(tru.ACCIDENT3);
																				health.setText(tru.HEALTH3);
																				old.setText(tru.OLD3);
																				invest.setText(tru.INVEST3);
																				person.setText(tru.PERSON3);
																				one.setText(tru.ONE3);
																				two.setText(tru.TWO3);
																				three.setText(tru.THREE3);
																				four.setText(tru.FOUR3);
																				detail.setText(tru.DETAIL3);
																				
																			}
																			else if(index==4){
																				percent.setText(tru.PERCENT4);
																				accident.setText(tru.ACCIDENT4);
																				health.setText(tru.HEALTH4);
																				old.setText(tru.OLD4);
																				invest.setText(tru.INVEST4);
																				person.setText(tru.PERSON4);
																				one.setText(tru.ONE4);
																				two.setText(tru.TWO4);
																				three.setText(tru.THREE4);
																				four.setText(tru.FOUR4);
																				detail.setText(tru.DETAIL4);
																				
																			}
																			else if(index==5){
																				percent.setText(tru.PERCENT5);
																				accident.setText(tru.ACCIDENT5);
																				health.setText(tru.HEALTH5);
																				old.setText(tru.OLD5);
																				invest.setText(tru.INVEST5);
																				person.setText(tru.PERSON5);
																				one.setText(tru.ONE5);
																				two.setText(tru.TWO5);
																				three.setText(tru.THREE5);
																				four.setText(tru.FOUR5);
																				detail.setText(tru.DETAIL5);
																				
																			}
																		}else if(!hasBaby){
																			if(index==1){
																				percent.setText(tru.PERCENT1);
																				accident.setText(tru.ACCIDENT1);
																				health.setText(tru.HEALTH1);
																				old.setText(tru.OLD1);
																				invest.setText(tru.INVEST1);
																				person.setText(tru.PERSON1);
																				one.setText(tru.ONE1);
																				two.setText(tru.TWO1);
																				three.setText(tru.THREE1);
																				four.setText(tru.FOUR1);
																				detail.setText(tru.DETAIL1);
																				
																			}
																			else if(index==2){
																				percent.setText(tru.PERCENT2);
																				accident.setText(tru.ACCIDENT2);
																				health.setText(tru.HEALTH2);
																				old.setText(tru.OLD2);
																				invest.setText(tru.INVEST2);
																				person.setText(tru.PERSON2);
																				one.setText(tru.ONE2);
																				two.setText(tru.TWO2);
																				three.setText(tru.THREE2);
																				four.setText(tru.FOUR2);
																				detail.setText(tru.DETAIL2);
																				
																			}
																			else if(index==3){
																				percent.setText(tru.PERCENT3);
																				accident.setText(tru.ACCIDENT3);
																				health.setText(tru.HEALTH3);
																				old.setText(tru.OLD3);
																				invest.setText(tru.INVEST3);
																				person.setText(tru.PERSON3);
																				one.setText(tru.ONE3);
																				two.setText(tru.TWO3);
																				three.setText(tru.THREE3);
																				four.setText(tru.FOUR3);
																				detail.setText(tru.DETAIL3);
																				
																			}
																			else if(index==4){
																				percent.setText(tru.PERCENT4);
																				accident.setText(tru.ACCIDENT4);
																				health.setText(tru.HEALTH4);
																				old.setText(tru.OLD4);
																				invest.setText(tru.INVEST4);
																				person.setText(tru.PERSON4);
																				one.setText(tru.ONE4);
																				two.setText(tru.TWO4);
																				three.setText(tru.THREE4);
																				four.setText(tru.FOUR4);
																				detail.setText(tru.DETAIL4);
																				
																			}
																			else if(index==5){
																				percent.setText(tru.PERCENT5);
																				accident.setText(tru.ACCIDENT5);
																				health.setText(tru.HEALTH5);
																				old.setText(tru.OLD5);
																				invest.setText(tru.INVEST5);
																				person.setText(tru.PERSON5);
																				one.setText(tru.ONE5);
																				two.setText(tru.TWO5);
																				three.setText(tru.THREE5);
																				four.setText(tru.FOUR5);
																				detail.setText(tru.DETAIL5);
																				
																			}
																			
																		}else if(hasBaby){
																			if(ageOfBaby>0&&ageOfBaby<=6){
																				if(index==1){
																					percent.setText(tru.PERCENT6);
																					accident.setText(tru.ACCIDENT6);
																					health.setText(tru.HEALTH6);
																					old.setText(tru.OLD6);
																					invest.setText(tru.INVEST6);
																					person.setText(tru.PERSON6);
																					one.setText(tru.ONE6);
																					two.setText(tru.TWO6);
																					three.setText(tru.THREE6);
																					four.setText(tru.FOUR6);
																					detail.setText(tru.DETAIL6);
																					
																				}
																				else if(index==2){
																					percent.setText(tru.PERCENT7);
																					accident.setText(tru.ACCIDENT7);
																					health.setText(tru.HEALTH7);
																					old.setText(tru.OLD7);
																					invest.setText(tru.INVEST7);
																					person.setText(tru.PERSON7);
																					one.setText(tru.ONE7);
																					two.setText(tru.TWO7);
																					three.setText(tru.THREE7);
																					four.setText(tru.FOUR7);
																					detail.setText(tru.DETAIL7);
																					
																				}
																				else if(index==3){
																					percent.setText(tru.PERCENT8);
																					accident.setText(tru.ACCIDENT8);
																					health.setText(tru.HEALTH8);
																					old.setText(tru.OLD8);
																					invest.setText(tru.INVEST8);
																					person.setText(tru.PERSON8);
																					one.setText(tru.ONE8);
																					two.setText(tru.TWO8);
																					three.setText(tru.THREE8);
																					four.setText(tru.FOUR8);
																					detail.setText(tru.DETAIL8);
																					
																				}
																				else if(index==4){
																					percent.setText(tru.PERCENT9);
																					accident.setText(tru.ACCIDENT9);
																					health.setText(tru.HEALTH9);
																					old.setText(tru.OLD9);
																					invest.setText(tru.INVEST9);
																					person.setText(tru.PERSON9);
																					one.setText(tru.ONE9);
																					two.setText(tru.TWO9);
																					three.setText(tru.THREE9);
																					four.setText(tru.FOUR9);
																					detail.setText(tru.DETAIL9);
																					
																				}
																				else if(index==5){
																					percent.setText(tru.PERCENT10);
																					accident.setText(tru.ACCIDENT10);
																					health.setText(tru.HEALTH10);
																					old.setText(tru.OLD10);
																					invest.setText(tru.INVEST10);
																					person.setText(tru.PERSON10);
																					one.setText(tru.ONE10);
																					two.setText(tru.TWO10);
																					three.setText(tru.THREE10);
																					four.setText(tru.FOUR10);
																					detail.setText(tru.DETAIL10);
																					
																				}
																			}
																			else if(ageOfBaby>=7&&ageOfBaby<=22){
																				if(index==1){
																					percent.setText(tru.PERCENT6);
																					accident.setText(tru.ACCIDENT6);
																					health.setText(tru.HEALTH6);
																					old.setText(tru.OLD6);
																					invest.setText(tru.INVEST6);
																					person.setText(tru.PERSON6);
																					one.setText(tru.ONE6);
																					two.setText(tru.TWO6);
																					three.setText(tru.THREE6);
																					four.setText(tru.FOUR6);
																					detail.setText(tru.DETAIL6);
																					
																				}
																				else if(index==2){
																					percent.setText(tru.PERCENT7);
																					accident.setText(tru.ACCIDENT7);
																					health.setText(tru.HEALTH7);
																					old.setText(tru.OLD7);
																					invest.setText(tru.INVEST7);
																					person.setText(tru.PERSON7);
																					one.setText(tru.ONE7);
																					two.setText(tru.TWO7);
																					three.setText(tru.THREE7);
																					four.setText(tru.FOUR7);
																					detail.setText(tru.DETAIL7);
																					
																				}
																				else if(index==3){
																					percent.setText(tru.PERCENT8);
																					accident.setText(tru.ACCIDENT8);
																					health.setText(tru.HEALTH8);
																					old.setText(tru.OLD8);
																					invest.setText(tru.INVEST8);
																					person.setText(tru.PERSON8);
																					one.setText(tru.ONE8);
																					two.setText(tru.TWO8);
																					three.setText(tru.THREE8);
																					four.setText(tru.FOUR8);
																					detail.setText(tru.DETAIL8);
																					
																				}
																				else if(index==4){
																					percent.setText(tru.PERCENT9);
																					accident.setText(tru.ACCIDENT9);
																					health.setText(tru.HEALTH9);
																					old.setText(tru.OLD9);
																					invest.setText(tru.INVEST9);
																					person.setText(tru.PERSON9);
																					one.setText(tru.ONE9);
																					two.setText(tru.TWO9);
																					three.setText(tru.THREE9);
																					four.setText(tru.FOUR9);
																					detail.setText(tru.DETAIL9);
																					
																				}
																				else if(index==5){
																					percent.setText(tru.PERCENT10);
																					accident.setText(tru.ACCIDENT10);
																					health.setText(tru.HEALTH10);
																					old.setText(tru.OLD10);
																					invest.setText(tru.INVEST10);
																					person.setText(tru.PERSON10);
																					one.setText(tru.ONE10);
																					two.setText(tru.TWO10);
																					three.setText(tru.THREE10);
																					four.setText(tru.FOUR10);
																					detail.setText(tru.DETAIL10);
																					
																				}
																			}
																		}
																	}
																	else if(age>28&&age<=35){
																		if(!isMarried){
																			if(index==1){
																				percent.setText(tru.PERCENT1);
																				accident.setText(tru.ACCIDENT1);
																				health.setText(tru.HEALTH1);
																				old.setText(tru.OLD1);
																				invest.setText(tru.INVEST1);
																				person.setText(tru.PERSON1);
																				one.setText(tru.ONE1);
																				two.setText(tru.TWO1);
																				three.setText(tru.THREE1);
																				four.setText(tru.FOUR1);
																				detail.setText(tru.DETAIL1);
																				
																			}
																			else if(index==2){
																				percent.setText(tru.PERCENT2);
																				accident.setText(tru.ACCIDENT2);
																				health.setText(tru.HEALTH2);
																				old.setText(tru.OLD2);
																				invest.setText(tru.INVEST2);
																				person.setText(tru.PERSON2);
																				one.setText(tru.ONE2);
																				two.setText(tru.TWO2);
																				three.setText(tru.THREE2);
																				four.setText(tru.FOUR2);
																				detail.setText(tru.DETAIL2);
																				
																			}
																			else if(index==3){
																				percent.setText(tru.PERCENT3);
																				accident.setText(tru.ACCIDENT3);
																				health.setText(tru.HEALTH3);
																				old.setText(tru.OLD3);
																				invest.setText(tru.INVEST3);
																				person.setText(tru.PERSON3);
																				one.setText(tru.ONE3);
																				two.setText(tru.TWO3);
																				three.setText(tru.THREE3);
																				four.setText(tru.FOUR3);
																				detail.setText(tru.DETAIL3);
																				
																			}
																			else if(index==4){
																				percent.setText(tru.PERCENT4);
																				accident.setText(tru.ACCIDENT4);
																				health.setText(tru.HEALTH4);
																				old.setText(tru.OLD4);
																				invest.setText(tru.INVEST4);
																				person.setText(tru.PERSON4);
																				one.setText(tru.ONE4);
																				two.setText(tru.TWO4);
																				three.setText(tru.THREE4);
																				four.setText(tru.FOUR4);
																				detail.setText(tru.DETAIL4);
																				
																			}
																			else if(index==5){
																				percent.setText(tru.PERCENT5);
																				accident.setText(tru.ACCIDENT5);
																				health.setText(tru.HEALTH5);
																				old.setText(tru.OLD5);
																				invest.setText(tru.INVEST5);
																				person.setText(tru.PERSON5);
																				one.setText(tru.ONE5);
																				two.setText(tru.TWO5);
																				three.setText(tru.THREE5);
																				four.setText(tru.FOUR5);
																				detail.setText(tru.DETAIL5);
																				
																			}
																		}
																		else if(!hasBaby){
																			if(index==1){
																				percent.setText(tru.PERCENT11);
																				accident.setText(tru.ACCIDENT11);
																				health.setText(tru.HEALTH11);
																				old.setText(tru.OLD11);
																				invest.setText(tru.INVEST11);
																				person.setText(tru.PERSON11);
																				one.setText(tru.ONE11);
																				two.setText(tru.TWO1);
																				three.setText(tru.THREE11);
																				four.setText(tru.FOUR11);
																				detail.setText(tru.DETAIL11);
																				
																			}
																			else if(index==2){
																				percent.setText(tru.PERCENT12);
																				accident.setText(tru.ACCIDENT12);
																				health.setText(tru.HEALTH12);
																				old.setText(tru.OLD12);
																				invest.setText(tru.INVEST12);
																				person.setText(tru.PERSON12);
																				one.setText(tru.ONE12);
																				two.setText(tru.TWO12);
																				three.setText(tru.THREE12);
																				four.setText(tru.FOUR12);
																				detail.setText(tru.DETAIL12);
																				
																			}
																			else if(index==3){
																				percent.setText(tru.PERCENT13);
																				accident.setText(tru.ACCIDENT13);
																				health.setText(tru.HEALTH13);
																				old.setText(tru.OLD13);
																				invest.setText(tru.INVEST13);
																				person.setText(tru.PERSON13);
																				one.setText(tru.ONE13);
																				two.setText(tru.TWO13);
																				three.setText(tru.THREE13);
																				four.setText(tru.FOUR13);
																				detail.setText(tru.DETAIL13);
																				
																			}
																			else if(index==4){
																				percent.setText(tru.PERCENT14);
																				accident.setText(tru.ACCIDENT14);
																				health.setText(tru.HEALTH14);
																				old.setText(tru.OLD14);
																				invest.setText(tru.INVEST14);
																				person.setText(tru.PERSON14);
																				one.setText(tru.ONE14);
																				two.setText(tru.TWO14);
																				three.setText(tru.THREE14);
																				four.setText(tru.FOUR14);
																				detail.setText(tru.DETAIL14);
																				
																			}
																			else if(index==5){
																				percent.setText(tru.PERCENT15);
																				accident.setText(tru.ACCIDENT15);
																				health.setText(tru.HEALTH15);
																				old.setText(tru.OLD15);
																				invest.setText(tru.INVEST15);
																				person.setText(tru.PERSON15);
																				one.setText(tru.ONE15);
																				two.setText(tru.TWO15);
																				three.setText(tru.THREE15);
																				four.setText(tru.FOUR15);
																				detail.setText(tru.DETAIL15);
																				
																			}
																		}else if(ageOfBaby>0&&ageOfBaby<=6){
																			if(index==1){
																				percent.setText(tru.PERCENT6);
																				accident.setText(tru.ACCIDENT6);
																				health.setText(tru.HEALTH6);
																				old.setText(tru.OLD6);
																				invest.setText(tru.INVEST6);
																				person.setText(tru.PERSON6);
																				one.setText(tru.ONE6);
																				two.setText(tru.TWO6);
																				three.setText(tru.THREE6);
																				four.setText(tru.FOUR6);
																				detail.setText(tru.DETAIL6);
																				
																			}
																			else if(index==2){
																				percent.setText(tru.PERCENT7);
																				accident.setText(tru.ACCIDENT7);
																				health.setText(tru.HEALTH7);
																				old.setText(tru.OLD7);
																				invest.setText(tru.INVEST7);
																				person.setText(tru.PERSON7);
																				one.setText(tru.ONE7);
																				two.setText(tru.TWO7);
																				three.setText(tru.THREE7);
																				four.setText(tru.FOUR7);
																				detail.setText(tru.DETAIL7);
																				
																			}
																			else if(index==3){
																				percent.setText(tru.PERCENT8);
																				accident.setText(tru.ACCIDENT8);
																				health.setText(tru.HEALTH8);
																				old.setText(tru.OLD8);
																				invest.setText(tru.INVEST8);
																				person.setText(tru.PERSON8);
																				one.setText(tru.ONE8);
																				two.setText(tru.TWO8);
																				three.setText(tru.THREE8);
																				four.setText(tru.FOUR8);
																				detail.setText(tru.DETAIL8);
																				
																			}
																			else if(index==4){
																				percent.setText(tru.PERCENT9);
																				accident.setText(tru.ACCIDENT9);
																				health.setText(tru.HEALTH9);
																				old.setText(tru.OLD9);
																				invest.setText(tru.INVEST9);
																				person.setText(tru.PERSON9);
																				one.setText(tru.ONE9);
																				two.setText(tru.TWO9);
																				three.setText(tru.THREE9);
																				four.setText(tru.FOUR9);
																				detail.setText(tru.DETAIL9);
																				
																			}
																			else if(index==5){
																				percent.setText(tru.PERCENT10);
																				accident.setText(tru.ACCIDENT10);
																				health.setText(tru.HEALTH10);
																				old.setText(tru.OLD10);
																				invest.setText(tru.INVEST10);
																				person.setText(tru.PERSON10);
																				one.setText(tru.ONE10);
																				two.setText(tru.TWO10);
																				three.setText(tru.THREE10);
																				four.setText(tru.FOUR10);
																				detail.setText(tru.DETAIL10);
																				
																			}
																		}else if(ageOfBaby>6&&ageOfBaby<=22){
																			if(index==1){
																				percent.setText(tru.PERCENT16);
																				accident.setText(tru.ACCIDENT16);
																				health.setText(tru.HEALTH16);
																				old.setText(tru.OLD16);
																				invest.setText(tru.INVEST16);
																				person.setText(tru.PERSON16);
																				one.setText(tru.ONE16);
																				two.setText(tru.TWO16);
																				three.setText(tru.THREE16);
																				four.setText(tru.FOUR16);
																				detail.setText(tru.DETAIL16);
																				
																			}
																			else if(index==2){
																				percent.setText(tru.PERCENT17);
																				accident.setText(tru.ACCIDENT17);
																				health.setText(tru.HEALTH17);
																				old.setText(tru.OLD17);
																				invest.setText(tru.INVEST17);
																				person.setText(tru.PERSON17);
																				one.setText(tru.ONE17);
																				two.setText(tru.TWO17);
																				three.setText(tru.THREE17);
																				four.setText(tru.FOUR17);
																				detail.setText(tru.DETAIL17);
																				
																			}
																			else if(index==3){
																				percent.setText(tru.PERCENT18);
																				accident.setText(tru.ACCIDENT18);
																				health.setText(tru.HEALTH18);
																				old.setText(tru.OLD18);
																				invest.setText(tru.INVEST18);
																				person.setText(tru.PERSON18);
																				one.setText(tru.ONE18);
																				two.setText(tru.TWO18);
																				three.setText(tru.THREE18);
																				four.setText(tru.FOUR18);
																				detail.setText(tru.DETAIL18);
																				
																			}
																			else if(index==4){
																				percent.setText(tru.PERCENT19);
																				accident.setText(tru.ACCIDENT19);
																				health.setText(tru.HEALTH19);
																				old.setText(tru.OLD19);
																				invest.setText(tru.INVEST19);
																				person.setText(tru.PERSON19);
																				one.setText(tru.ONE19);
																				two.setText(tru.TWO19);
																				three.setText(tru.THREE19);
																				four.setText(tru.FOUR19);
																				detail.setText(tru.DETAIL19);
																				
																			}
																			else if(index==5){
																				percent.setText(tru.PERCENT20);
																				accident.setText(tru.ACCIDENT20);
																				health.setText(tru.HEALTH20);
																				old.setText(tru.OLD20);
																				invest.setText(tru.INVEST20);
																				person.setText(tru.PERSON20);
																				one.setText(tru.ONE20);
																				two.setText(tru.TWO20);
																				three.setText(tru.THREE20);
																				four.setText(tru.FOUR20);
																				detail.setText(tru.DETAIL20);
																				
																			}
																		}
																	}else if(age>=35&&age<=50){
																		if(!isMarried){
																			if(index==1){
																				percent.setText(tru.PERCENT21);
																				accident.setText(tru.ACCIDENT21);
																				health.setText(tru.HEALTH21);
																				old.setText(tru.OLD21);
																				invest.setText(tru.INVEST21);
																				person.setText(tru.PERSON21);
																				one.setText(tru.ONE21);
																				two.setText(tru.TWO21);
																				three.setText(tru.THREE21);
																				four.setText(tru.FOUR21);
																				detail.setText(tru.DETAIL21);
																				
																			}
																			else if(index==2){
																				percent.setText(tru.PERCENT22);
																				accident.setText(tru.ACCIDENT22);
																				health.setText(tru.HEALTH22);
																				old.setText(tru.OLD22);
																				invest.setText(tru.INVEST22);
																				person.setText(tru.PERSON22);
																				one.setText(tru.ONE22);
																				two.setText(tru.TWO22);
																				three.setText(tru.THREE22);
																				four.setText(tru.FOUR22);
																				detail.setText(tru.DETAIL22);
																				
																			}
																			else if(index==3){
																				percent.setText(tru.PERCENT23);
																				accident.setText(tru.ACCIDENT23);
																				health.setText(tru.HEALTH23);
																				old.setText(tru.OLD23);
																				invest.setText(tru.INVEST23);
																				person.setText(tru.PERSON23);
																				one.setText(tru.ONE23);
																				two.setText(tru.TWO23);
																				three.setText(tru.THREE23);
																				four.setText(tru.FOUR23);
																				detail.setText(tru.DETAIL23);
																				
																			}
																			else if(index==4){
																				percent.setText(tru.PERCENT24);
																				accident.setText(tru.ACCIDENT24);
																				health.setText(tru.HEALTH24);
																				old.setText(tru.OLD24);
																				invest.setText(tru.INVEST24);
																				person.setText(tru.PERSON24);
																				one.setText(tru.ONE24);
																				two.setText(tru.TWO24);
																				three.setText(tru.THREE24);
																				four.setText(tru.FOUR24);
																				detail.setText(tru.DETAIL24);
																				
																			}
																			else if(index==5){
																				percent.setText(tru.PERCENT25);
																				accident.setText(tru.ACCIDENT25);
																				health.setText(tru.HEALTH25);
																				old.setText(tru.OLD25);
																				invest.setText(tru.INVEST25);
																				person.setText(tru.PERSON25);
																				one.setText(tru.ONE25);
																				two.setText(tru.TWO25);
																				three.setText(tru.THREE25);
																				four.setText(tru.FOUR25);
																				detail.setText(tru.DETAIL25);
																				
																			}
																		}else if(!hasBaby){
																			if(index==1){
																				percent.setText(tru.PERCENT26);
																				accident.setText(tru.ACCIDENT26);
																				health.setText(tru.HEALTH26);
																				old.setText(tru.OLD26);
																				invest.setText(tru.INVEST26);
																				person.setText(tru.PERSON26);
																				one.setText(tru.ONE26);
																				two.setText(tru.TWO26);
																				three.setText(tru.THREE26);
																				four.setText(tru.FOUR26);
																				detail.setText(tru.DETAIL26);
																				
																			}
																			else if(index==2){
																				percent.setText(tru.PERCENT27);
																				accident.setText(tru.ACCIDENT27);
																				health.setText(tru.HEALTH27);
																				old.setText(tru.OLD27);
																				invest.setText(tru.INVEST27);
																				person.setText(tru.PERSON27);
																				one.setText(tru.ONE27);
																				two.setText(tru.TWO27);
																				three.setText(tru.THREE27);
																				four.setText(tru.FOUR27);
																				detail.setText(tru.DETAIL27);
																				
																			}
																			else if(index==3){
																				percent.setText(tru.PERCENT28);
																				accident.setText(tru.ACCIDENT28);
																				health.setText(tru.HEALTH28);
																				old.setText(tru.OLD28);
																				invest.setText(tru.INVEST28);
																				person.setText(tru.PERSON28);
																				one.setText(tru.ONE28);
																				two.setText(tru.TWO28);
																				three.setText(tru.THREE28);
																				four.setText(tru.FOUR28);
																				detail.setText(tru.DETAIL28);
																				
																			}
																			else if(index==4){
																				percent.setText(tru.PERCENT29);
																				accident.setText(tru.ACCIDENT29);
																				health.setText(tru.HEALTH29);
																				old.setText(tru.OLD29);
																				invest.setText(tru.INVEST29);
																				person.setText(tru.PERSON29);
																				one.setText(tru.ONE29);
																				two.setText(tru.TWO29);
																				three.setText(tru.THREE29);
																				four.setText(tru.FOUR29);
																				detail.setText(tru.DETAIL29);
																				
																			}
																			else if(index==5){
																				percent.setText(tru.PERCENT30);
																				accident.setText(tru.ACCIDENT30);
																				health.setText(tru.HEALTH30);
																				old.setText(tru.OLD30);
																				invest.setText(tru.INVEST30);
																				person.setText(tru.PERSON30);
																				one.setText(tru.ONE30);
																				two.setText(tru.TWO30);
																				three.setText(tru.THREE30);
																				four.setText(tru.FOUR30);
																				detail.setText(tru.DETAIL30);
																				
																			}
																		}else{
																			if(ageOfBaby>0&&ageOfBaby<=6){
																				if(index==1){
																					percent.setText(tru.PERCENT31);
																					accident.setText(tru.ACCIDENT31);
																					health.setText(tru.HEALTH31);
																					old.setText(tru.OLD31);
																					invest.setText(tru.INVEST31);
																					person.setText(tru.PERSON31);
																					one.setText(tru.ONE31);
																					two.setText(tru.TWO31);
																					three.setText(tru.THREE31);
																					four.setText(tru.FOUR31);
																					detail.setText(tru.DETAIL31);
																					
																				}
																				else if(index==2){
																					percent.setText(tru.PERCENT32);
																					accident.setText(tru.ACCIDENT32);
																					health.setText(tru.HEALTH32);
																					old.setText(tru.OLD32);
																					invest.setText(tru.INVEST32);
																					person.setText(tru.PERSON32);
																					one.setText(tru.ONE32);
																					two.setText(tru.TWO32);
																					three.setText(tru.THREE32);
																					four.setText(tru.FOUR32);
																					detail.setText(tru.DETAIL32);
																					
																				}
																				else if(index==3){
																					percent.setText(tru.PERCENT33);
																					accident.setText(tru.ACCIDENT33);
																					health.setText(tru.HEALTH33);
																					old.setText(tru.OLD33);
																					invest.setText(tru.INVEST33);
																					person.setText(tru.PERSON33);
																					one.setText(tru.ONE33);
																					two.setText(tru.TWO33);
																					three.setText(tru.THREE33);
																					four.setText(tru.FOUR33);
																					detail.setText(tru.DETAIL33);
																					
																				}
																				else if(index==4){
																					percent.setText(tru.PERCENT34);
																					accident.setText(tru.ACCIDENT34);
																					health.setText(tru.HEALTH34);
																					old.setText(tru.OLD34);
																					invest.setText(tru.INVEST34);
																					person.setText(tru.PERSON34);
																					one.setText(tru.ONE34);
																					two.setText(tru.TWO34);
																					three.setText(tru.THREE34);
																					four.setText(tru.FOUR34);
																					detail.setText(tru.DETAIL34);
																					
																				}
																				else if(index==5){
																					percent.setText(tru.PERCENT35);
																					accident.setText(tru.ACCIDENT35);
																					health.setText(tru.HEALTH35);
																					old.setText(tru.OLD35);
																					invest.setText(tru.INVEST35);
																					person.setText(tru.PERSON35);
																					one.setText(tru.ONE35);
																					two.setText(tru.TWO35);
																					three.setText(tru.THREE35);
																					four.setText(tru.FOUR35);
																					detail.setText(tru.DETAIL35);
																					
																				}
																			}else if(ageOfBaby>6&&ageOfBaby<22){
																				if(index==1){
																					percent.setText(tru.PERCENT36);
																					accident.setText(tru.ACCIDENT36);
																					health.setText(tru.HEALTH36);
																					old.setText(tru.OLD36);
																					invest.setText(tru.INVEST36);
																					person.setText(tru.PERSON36);
																					one.setText(tru.ONE36);
																					two.setText(tru.TWO36);
																					three.setText(tru.THREE36);
																					four.setText(tru.FOUR36);
																					detail.setText(tru.DETAIL36);
																					
																				}
																				else if(index==2){
																					percent.setText(tru.PERCENT37);
																					accident.setText(tru.ACCIDENT37);
																					health.setText(tru.HEALTH37);
																					old.setText(tru.OLD37);
																					invest.setText(tru.INVEST37);
																					person.setText(tru.PERSON37);
																					one.setText(tru.ONE37);
																					two.setText(tru.TWO37);
																					three.setText(tru.THREE37);
																					four.setText(tru.FOUR37);
																					detail.setText(tru.DETAIL37);
																					
																				}
																				else if(index==3){
																					percent.setText(tru.PERCENT38);
																					accident.setText(tru.ACCIDENT38);
																					health.setText(tru.HEALTH38);
																					old.setText(tru.OLD38);
																					invest.setText(tru.INVEST38);
																					person.setText(tru.PERSON38);
																					one.setText(tru.ONE38);
																					two.setText(tru.TWO38);
																					three.setText(tru.THREE38);
																					four.setText(tru.FOUR38);
																					detail.setText(tru.DETAIL38);
																					
																				}
																				else if(index==4){
																					percent.setText(tru.PERCENT39);
																					accident.setText(tru.ACCIDENT39);
																					health.setText(tru.HEALTH39);
																					old.setText(tru.OLD39);
																					invest.setText(tru.INVEST39);
																					person.setText(tru.PERSON39);
																					one.setText(tru.ONE39);
																					two.setText(tru.TWO39);
																					three.setText(tru.THREE39);
																					four.setText(tru.FOUR39);
																					detail.setText(tru.DETAIL39);
																					
																				}
																				else if(index==5){
																					percent.setText(tru.PERCENT40);
																					accident.setText(tru.ACCIDENT40);
																					health.setText(tru.HEALTH40);
																					old.setText(tru.OLD40);
																					invest.setText(tru.INVEST40);
																					person.setText(tru.PERSON40);
																					one.setText(tru.ONE40);
																					two.setText(tru.TWO40);
																					three.setText(tru.THREE40);
																					four.setText(tru.FOUR40);
																					detail.setText(tru.DETAIL40);
																					
																				}
																			}else if(ageOfBaby>22&&ageOfBaby<33){
																				if(index==1){
																					percent.setText(tru.PERCENT41);
																					accident.setText(tru.ACCIDENT41);
																					health.setText(tru.HEALTH41);
																					old.setText(tru.OLD41);
																					invest.setText(tru.INVEST41);
																					person.setText(tru.PERSON41);
																					one.setText(tru.ONE41);
																					two.setText(tru.TWO41);
																					three.setText(tru.THREE41);
																					four.setText(tru.FOUR41);
																					detail.setText(tru.DETAIL41);
																					
																				}
																				else if(index==2){
																					percent.setText(tru.PERCENT42);
																					accident.setText(tru.ACCIDENT42);
																					health.setText(tru.HEALTH42);
																					old.setText(tru.OLD42);
																					invest.setText(tru.INVEST42);
																					person.setText(tru.PERSON42);
																					one.setText(tru.ONE42);
																					two.setText(tru.TWO42);
																					three.setText(tru.THREE42);
																					four.setText(tru.FOUR42);
																					detail.setText(tru.DETAIL42);
																					
																				}
																				else if(index==3){
																					percent.setText(tru.PERCENT43);
																					accident.setText(tru.ACCIDENT43);
																					health.setText(tru.HEALTH43);
																					old.setText(tru.OLD43);
																					invest.setText(tru.INVEST43);
																					person.setText(tru.PERSON43);
																					one.setText(tru.ONE43);
																					two.setText(tru.TWO43);
																					three.setText(tru.THREE43);
																					four.setText(tru.FOUR43);
																					detail.setText(tru.DETAIL43);
																					
																				}
																				else if(index==4){
																					percent.setText(tru.PERCENT44);
																					accident.setText(tru.ACCIDENT44);
																					health.setText(tru.HEALTH44);
																					old.setText(tru.OLD44);
																					invest.setText(tru.INVEST44);
																					person.setText(tru.PERSON44);
																					one.setText(tru.ONE44);
																					two.setText(tru.TWO44);
																					three.setText(tru.THREE44);
																					four.setText(tru.FOUR44);
																					detail.setText(tru.DETAIL44);
																					
																				}
																				else if(index==5){
																					percent.setText(tru.PERCENT45);
																					accident.setText(tru.ACCIDENT45);
																					health.setText(tru.HEALTH45);
																					old.setText(tru.OLD45);
																					invest.setText(tru.INVEST45);
																					person.setText(tru.PERSON45);
																					one.setText(tru.ONE45);
																					two.setText(tru.TWO45);
																					three.setText(tru.THREE45);
																					four.setText(tru.FOUR45);
																					detail.setText(tru.DETAIL45);
																					
																				}
																			}
																		}
																	}else if(age>50&&age<=60){
																		if(!isMarried){
																			if(index==1){
																				percent.setText(tru.PERCENT46);
																				accident.setText(tru.ACCIDENT46);
																				health.setText(tru.HEALTH46);
																				old.setText(tru.OLD46);
																				invest.setText(tru.INVEST46);
																				person.setText(tru.PERSON46);
																				one.setText(tru.ONE46);
																				two.setText(tru.TWO46);
																				three.setText(tru.THREE46);
																				four.setText(tru.FOUR46);
																				detail.setText(tru.DETAIL46);
																				
																			}
																			else if(index==2){
																				percent.setText(tru.PERCENT47);
																				accident.setText(tru.ACCIDENT47);
																				health.setText(tru.HEALTH47);
																				old.setText(tru.OLD47);
																				invest.setText(tru.INVEST47);
																				person.setText(tru.PERSON47);
																				one.setText(tru.ONE47);
																				two.setText(tru.TWO47);
																				three.setText(tru.THREE47);
																				four.setText(tru.FOUR47);
																				detail.setText(tru.DETAIL47);
																				
																			}
																			else if(index==3){
																				percent.setText(tru.PERCENT48);
																				accident.setText(tru.ACCIDENT48);
																				health.setText(tru.HEALTH48);
																				old.setText(tru.OLD48);
																				invest.setText(tru.INVEST48);
																				person.setText(tru.PERSON48);
																				one.setText(tru.ONE48);
																				two.setText(tru.TWO48);
																				three.setText(tru.THREE48);
																				four.setText(tru.FOUR48);
																				detail.setText(tru.DETAIL48);
																				
																			}
																			else if(index==4){
																				percent.setText(tru.PERCENT49);
																				accident.setText(tru.ACCIDENT49);
																				health.setText(tru.HEALTH49);
																				old.setText(tru.OLD49);
																				invest.setText(tru.INVEST49);
																				person.setText(tru.PERSON49);
																				one.setText(tru.ONE49);
																				two.setText(tru.TWO49);
																				three.setText(tru.THREE49);
																				four.setText(tru.FOUR49);
																				detail.setText(tru.DETAIL49);
																				
																			}
																			else if(index==5){
																				percent.setText(tru.PERCENT50);
																				accident.setText(tru.ACCIDENT50);
																				health.setText(tru.HEALTH50);
																				old.setText(tru.OLD50);
																				invest.setText(tru.INVEST50);
																				person.setText(tru.PERSON50);
																				one.setText(tru.ONE50);
																				two.setText(tru.TWO50);
																				three.setText(tru.THREE50);
																				four.setText(tru.FOUR50);
																				detail.setText(tru.DETAIL50);
																				
																			}
																		}else if(!hasBaby){
																			if(index==1){
																				percent.setText(tru.PERCENT51);
																				accident.setText(tru.ACCIDENT51);
																				health.setText(tru.HEALTH51);
																				old.setText(tru.OLD51);
																				invest.setText(tru.INVEST51);
																				person.setText(tru.PERSON51);
																				one.setText(tru.ONE51);
																				two.setText(tru.TWO51);
																				three.setText(tru.THREE51);
																				four.setText(tru.FOUR51);
																				detail.setText(tru.DETAIL51);
																				
																			}
																			else if(index==2){
																				percent.setText(tru.PERCENT52);
																				accident.setText(tru.ACCIDENT52);
																				health.setText(tru.HEALTH52);
																				old.setText(tru.OLD52);
																				invest.setText(tru.INVEST52);
																				person.setText(tru.PERSON52);
																				one.setText(tru.ONE52);
																				two.setText(tru.TWO52);
																				three.setText(tru.THREE52);
																				four.setText(tru.FOUR52);
																				detail.setText(tru.DETAIL52);
																				
																			}
																			else if(index==3){
																				percent.setText(tru.PERCENT53);
																				accident.setText(tru.ACCIDENT53);
																				health.setText(tru.HEALTH53);
																				old.setText(tru.OLD53);
																				invest.setText(tru.INVEST53);
																				person.setText(tru.PERSON53);
																				one.setText(tru.ONE53);
																				two.setText(tru.TWO53);
																				three.setText(tru.THREE53);
																				four.setText(tru.FOUR53);
																				detail.setText(tru.DETAIL53);
																				
																			}
																			else if(index==4){
																				percent.setText(tru.PERCENT54);
																				accident.setText(tru.ACCIDENT54);
																				health.setText(tru.HEALTH54);
																				old.setText(tru.OLD54);
																				invest.setText(tru.INVEST54);
																				person.setText(tru.PERSON54);
																				one.setText(tru.ONE54);
																				two.setText(tru.TWO54);
																				three.setText(tru.THREE54);
																				four.setText(tru.FOUR54);
																				detail.setText(tru.DETAIL54);
																				
																			}
																			else if(index==5){
																				percent.setText(tru.PERCENT55);
																				accident.setText(tru.ACCIDENT55);
																				health.setText(tru.HEALTH55);
																				old.setText(tru.OLD55);
																				invest.setText(tru.INVEST55);
																				person.setText(tru.PERSON55);
																				one.setText(tru.ONE55);
																				two.setText(tru.TWO55);
																				three.setText(tru.THREE55);
																				four.setText(tru.FOUR55);
																				detail.setText(tru.DETAIL55);
																				
																			}
																		}else{
																			if(ageOfBaby>0&&ageOfBaby<=6){
																				if(index==1){
																					percent.setText(tru.PERCENT56);
																					accident.setText(tru.ACCIDENT56);
																					health.setText(tru.HEALTH56);
																					old.setText(tru.OLD56);
																					invest.setText(tru.INVEST56);
																					person.setText(tru.PERSON56);
																					one.setText(tru.ONE56);
																					two.setText(tru.TWO56);
																					three.setText(tru.THREE56);
																					four.setText(tru.FOUR56);
																					detail.setText(tru.DETAIL56);
																					
																				}
																				else if(index==2){
																					percent.setText(tru.PERCENT57);
																					accident.setText(tru.ACCIDENT57);
																					health.setText(tru.HEALTH57);
																					old.setText(tru.OLD57);
																					invest.setText(tru.INVEST57);
																					person.setText(tru.PERSON57);
																					one.setText(tru.ONE57);
																					two.setText(tru.TWO57);
																					three.setText(tru.THREE57);
																					four.setText(tru.FOUR57);
																					detail.setText(tru.DETAIL57);
																					
																				}
																				else if(index==3){
																					percent.setText(tru.PERCENT58);
																					accident.setText(tru.ACCIDENT58);
																					health.setText(tru.HEALTH58);
																					old.setText(tru.OLD58);
																					invest.setText(tru.INVEST58);
																					person.setText(tru.PERSON58);
																					one.setText(tru.ONE58);
																					two.setText(tru.TWO58);
																					three.setText(tru.THREE58);
																					four.setText(tru.FOUR58);
																					detail.setText(tru.DETAIL58);
																					
																				}
																				else if(index==4){
																					percent.setText(tru.PERCENT59);
																					accident.setText(tru.ACCIDENT59);
																					health.setText(tru.HEALTH59);
																					old.setText(tru.OLD59);
																					invest.setText(tru.INVEST59);
																					person.setText(tru.PERSON59);
																					one.setText(tru.ONE59);
																					two.setText(tru.TWO59);
																					three.setText(tru.THREE59);
																					four.setText(tru.FOUR59);
																					detail.setText(tru.DETAIL59);
																					
																				}
																				else if(index==5){
																					percent.setText(tru.PERCENT60);
																					accident.setText(tru.ACCIDENT60);
																					health.setText(tru.HEALTH60);
																					old.setText(tru.OLD60);
																					invest.setText(tru.INVEST60);
																					person.setText(tru.PERSON60);
																					one.setText(tru.ONE60);
																					two.setText(tru.TWO60);
																					three.setText(tru.THREE60);
																					four.setText(tru.FOUR60);
																					detail.setText(tru.DETAIL60);
																					
																				}
																			}else if(ageOfBaby>6&&ageOfBaby<22){
																				if(index==1){
																					percent.setText(tru.PERCENT61);
																					accident.setText(tru.ACCIDENT61);
																					health.setText(tru.HEALTH61);
																					old.setText(tru.OLD61);
																					invest.setText(tru.INVEST61);
																					person.setText(tru.PERSON61);
																					one.setText(tru.ONE61);
																					two.setText(tru.TWO61);
																					three.setText(tru.THREE61);
																					four.setText(tru.FOUR61);
																					detail.setText(tru.DETAIL61);
																					
																				}
																				else if(index==2){
																					percent.setText(tru.PERCENT62);
																					accident.setText(tru.ACCIDENT62);
																					health.setText(tru.HEALTH62);
																					old.setText(tru.OLD62);
																					invest.setText(tru.INVEST62);
																					person.setText(tru.PERSON62);
																					one.setText(tru.ONE62);
																					two.setText(tru.TWO62);
																					three.setText(tru.THREE62);
																					four.setText(tru.FOUR62);
																					detail.setText(tru.DETAIL62);
																					
																				}
																				else if(index==3){
																					percent.setText(tru.PERCENT63);
																					accident.setText(tru.ACCIDENT63);
																					health.setText(tru.HEALTH63);
																					old.setText(tru.OLD63);
																					invest.setText(tru.INVEST63);
																					person.setText(tru.PERSON63);
																					one.setText(tru.ONE63);
																					two.setText(tru.TWO63);
																					three.setText(tru.THREE63);
																					four.setText(tru.FOUR63);
																					detail.setText(tru.DETAIL63);
																					
																				}
																				else if(index==4){
																					percent.setText(tru.PERCENT64);
																					accident.setText(tru.ACCIDENT64);
																					health.setText(tru.HEALTH64);
																					old.setText(tru.OLD64);
																					invest.setText(tru.INVEST64);
																					person.setText(tru.PERSON64);
																					one.setText(tru.ONE64);
																					two.setText(tru.TWO64);
																					three.setText(tru.THREE64);
																					four.setText(tru.FOUR64);
																					detail.setText(tru.DETAIL64);
																					
																				}
																				else if(index==5){
																					percent.setText(tru.PERCENT65);
																					accident.setText(tru.ACCIDENT65);
																					health.setText(tru.HEALTH65);
																					old.setText(tru.OLD65);
																					invest.setText(tru.INVEST65);
																					person.setText(tru.PERSON65);
																					one.setText(tru.ONE65);
																					two.setText(tru.TWO65);
																					three.setText(tru.THREE65);
																					four.setText(tru.FOUR65);
																					detail.setText(tru.DETAIL65);
																					
																				}
																			}else if(ageOfBaby>22&&ageOfBaby<33){
																				if(index==1){
																					percent.setText(tru.PERCENT66);
																					accident.setText(tru.ACCIDENT66);
																					health.setText(tru.HEALTH66);
																					old.setText(tru.OLD66);
																					invest.setText(tru.INVEST66);
																					person.setText(tru.PERSON66);
																					one.setText(tru.ONE66);
																					two.setText(tru.TWO66);
																					three.setText(tru.THREE66);
																					four.setText(tru.FOUR66);
																					detail.setText(tru.DETAIL66);
																					
																				}
																				else if(index==2){
																					percent.setText(tru.PERCENT67);
																					accident.setText(tru.ACCIDENT67);
																					health.setText(tru.HEALTH67);
																					old.setText(tru.OLD67);
																					invest.setText(tru.INVEST67);
																					person.setText(tru.PERSON67);
																					one.setText(tru.ONE67);
																					two.setText(tru.TWO67);
																					three.setText(tru.THREE67);
																					four.setText(tru.FOUR67);
																					detail.setText(tru.DETAIL67);
																					
																				}
																				else if(index==3){
																					percent.setText(tru.PERCENT68);
																					accident.setText(tru.ACCIDENT68);
																					health.setText(tru.HEALTH68);
																					old.setText(tru.OLD68);
																					invest.setText(tru.INVEST68);
																					person.setText(tru.PERSON68);
																					one.setText(tru.ONE68);
																					two.setText(tru.TWO68);
																					three.setText(tru.THREE68);
																					four.setText(tru.FOUR68);
																					detail.setText(tru.DETAIL68);
																					
																				}
																				else if(index==4){
																					percent.setText(tru.PERCENT69);
																					accident.setText(tru.ACCIDENT69);
																					health.setText(tru.HEALTH69);
																					old.setText(tru.OLD69);
																					invest.setText(tru.INVEST69);
																					person.setText(tru.PERSON69);
																					one.setText(tru.ONE69);
																					two.setText(tru.TWO69);
																					three.setText(tru.THREE69);
																					four.setText(tru.FOUR69);
																					detail.setText(tru.DETAIL69);
																					
																				}
																				else if(index==5){
																					percent.setText(tru.PERCENT70);
																					accident.setText(tru.ACCIDENT70);
																					health.setText(tru.HEALTH70);
																					old.setText(tru.OLD70);
																					invest.setText(tru.INVEST70);
																					person.setText(tru.PERSON70);
																					one.setText(tru.ONE70);
																					two.setText(tru.TWO70);
																					three.setText(tru.THREE70);
																					four.setText(tru.FOUR70);
																					detail.setText(tru.DETAIL70);
																					
																				}
																			}
																		}
																	}
																	
																	ad3 = new AlertDialog.Builder(activity).setView(dateTimeLayout3).show();
																	
																	ok = (Button) dateTimeLayout3.findViewById(R.id.finish);
																	ok.setOnClickListener(new OnClickListener() {
																		
																		@Override
																		public void onClick(View v) {
																			ad3.dismiss();
																		}
																	});
																}
															});
														}
														
													}
												});
											}
										});
										
									}
									else if(isMarried){
									
									while(toNext1){
										radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
											
											@Override
											public void onCheckedChanged(RadioGroup group, int checkedId) {
												
												if(checkedId!=R.id.yes2&&checkedId!=R.id.no2){
													something1 = 0;
//													button2.setClickable(false);
//													UIHelper.showToastMessage(activity, UNSELECTED);
												}else{
													something1 = 1;
													hasBaby = checkedId==R.id.yes2?true:false;
												}
												
												
												if(!hasBaby){
													pr2.setVisibility(View.GONE);
													pr3.setVisibility(View.GONE);
													editText.setVisibility(View.GONE);
												}
												else{
													pr2.setVisibility(View.VISIBLE);
													pr3.setVisibility(View.VISIBLE);
													editText.setVisibility(View.VISIBLE);
												}
											}
										});
										
										if(something1 == 0){
											UIHelper.showToastMessage( UNSELECTED);
											button2.setClickable(false);
										}
										else
											toNext1 = true;
										
									}
									if(something1 == 0){
										UIHelper.showToastMessage( UNSELECTED);
//										button2.setClickable(false);
									}
									else if(something1 == 1){
										if(!hasBaby){
											button2.setClickable(true);
											ad1.dismiss();
											handler1.post(new Runnable() {
												
												@Override
												public void run() {
													
													//获取小孩子的年龄
													if(hasBaby){
														babyAge = editText.getText().toString().trim();
														ageOfBaby = Integer.parseInt(babyAge);
														Log.d("debug", "babyAge"+babyAge);
													}
													LinearLayout dateTimeLayout2 = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.test_dialog3, null);
													mRootView = dateTimeLayout2.findViewById(R.id.rootView);
													mTView = (TextView) dateTimeLayout2.findViewById(R.id.value);
													mIView = (ImageView) dateTimeLayout2.findViewById(R.id.bt_down);
													mIView.setOnClickListener(DateTimePickDialogUtil.this);
													
													String[] names = DateTimePickDialogUtil.this.activity.getResources().getStringArray(R.array.money_list);
													for(int i=0;i<names.length;i++){
														CustomObject object = new CustomObject();
														object.data = names[i];
														nameList.add(object);
													}
													
													mAdapter = new CustemSpinerAdapter(DateTimePickDialogUtil.this.activity);
													mAdapter.refreshData(nameList, 0);
													
													mSpinerPopWindow = new SpinerPopWindow(activity);
													mSpinerPopWindow.setAdapter(mAdapter);
													mSpinerPopWindow.setItemListener(DateTimePickDialogUtil.this);
													ad2 = new AlertDialog.Builder(activity).setView(dateTimeLayout2).show();
													
													button3 = (Button) dateTimeLayout2.findViewById(R.id.id_next3);
													button3.setOnClickListener(new android.view.View.OnClickListener() {
														
														@Override
														public void onClick(View v) {
															
															index = getPostion();
															Log.d("debug", "get index"+index);
															
															if(index == 0){
																UIHelper.showToastMessage(UNSELECTED);
															}
															else{
																ad2.dismiss();
																handler2.post(new Runnable() {
																	
																	@Override
																	public void run() {
																		//获取选择的收入等级
																		index = getPostion();
																		Log.d("debug", "get index"+index);
																		
																		LinearLayout dateTimeLayout3 = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.test_result, null);
																		
																		//呈现测试结果
																		percent = (TextView) dateTimeLayout3.findViewById(R.id.id_percent);
																		accident = (TextView) dateTimeLayout3.findViewById(R.id.id_accident);
																		health = (TextView) dateTimeLayout3.findViewById(R.id.id_health);
																		old = (TextView) dateTimeLayout3.findViewById(R.id.id_old);
																		invest = (TextView) dateTimeLayout3.findViewById(R.id.id_invest);
																		person = (TextView) dateTimeLayout3.findViewById(R.id.id_person);
																		one = (TextView) dateTimeLayout3.findViewById(R.id.id_one);
																		two = (TextView) dateTimeLayout3.findViewById(R.id.id_two);
																		three = (TextView) dateTimeLayout3.findViewById(R.id.id_three);
																		four = (TextView) dateTimeLayout3.findViewById(R.id.id_four);
																		detail = (TextView) dateTimeLayout3.findViewById(R.id.detail);
																		
																		tru = new TestResultUtil();
																		
																		if(age>=18&&age<=28){
																			if(!isMarried){
																				
																				if(index==1){
																					percent.setText(tru.PERCENT1);
																					accident.setText(tru.ACCIDENT1);
																					health.setText(tru.HEALTH1);
																					old.setText(tru.OLD1);
																					invest.setText(tru.INVEST1);
																					person.setText(tru.PERSON1);
																					one.setText(tru.ONE1);
																					two.setText(tru.TWO1);
																					three.setText(tru.THREE1);
																					four.setText(tru.FOUR1);
																					detail.setText(tru.DETAIL1);
																					
																				}
																				else if(index==2){
																					percent.setText(tru.PERCENT2);
																					accident.setText(tru.ACCIDENT2);
																					health.setText(tru.HEALTH2);
																					old.setText(tru.OLD2);
																					invest.setText(tru.INVEST2);
																					person.setText(tru.PERSON2);
																					one.setText(tru.ONE2);
																					two.setText(tru.TWO2);
																					three.setText(tru.THREE2);
																					four.setText(tru.FOUR2);
																					detail.setText(tru.DETAIL2);
																					
																				}
																				else if(index==3){
																					percent.setText(tru.PERCENT3);
																					accident.setText(tru.ACCIDENT3);
																					health.setText(tru.HEALTH3);
																					old.setText(tru.OLD3);
																					invest.setText(tru.INVEST3);
																					person.setText(tru.PERSON3);
																					one.setText(tru.ONE3);
																					two.setText(tru.TWO3);
																					three.setText(tru.THREE3);
																					four.setText(tru.FOUR3);
																					detail.setText(tru.DETAIL3);
																					
																				}
																				else if(index==4){
																					percent.setText(tru.PERCENT4);
																					accident.setText(tru.ACCIDENT4);
																					health.setText(tru.HEALTH4);
																					old.setText(tru.OLD4);
																					invest.setText(tru.INVEST4);
																					person.setText(tru.PERSON4);
																					one.setText(tru.ONE4);
																					two.setText(tru.TWO4);
																					three.setText(tru.THREE4);
																					four.setText(tru.FOUR4);
																					detail.setText(tru.DETAIL4);
																					
																				}
																				else if(index==5){
																					percent.setText(tru.PERCENT5);
																					accident.setText(tru.ACCIDENT5);
																					health.setText(tru.HEALTH5);
																					old.setText(tru.OLD5);
																					invest.setText(tru.INVEST5);
																					person.setText(tru.PERSON5);
																					one.setText(tru.ONE5);
																					two.setText(tru.TWO5);
																					three.setText(tru.THREE5);
																					four.setText(tru.FOUR5);
																					detail.setText(tru.DETAIL5);
																					
																				}
																			}else if(!hasBaby){
																				if(index==1){
																					percent.setText(tru.PERCENT1);
																					accident.setText(tru.ACCIDENT1);
																					health.setText(tru.HEALTH1);
																					old.setText(tru.OLD1);
																					invest.setText(tru.INVEST1);
																					person.setText(tru.PERSON1);
																					one.setText(tru.ONE1);
																					two.setText(tru.TWO1);
																					three.setText(tru.THREE1);
																					four.setText(tru.FOUR1);
																					detail.setText(tru.DETAIL1);
																					
																				}
																				else if(index==2){
																					percent.setText(tru.PERCENT2);
																					accident.setText(tru.ACCIDENT2);
																					health.setText(tru.HEALTH2);
																					old.setText(tru.OLD2);
																					invest.setText(tru.INVEST2);
																					person.setText(tru.PERSON2);
																					one.setText(tru.ONE2);
																					two.setText(tru.TWO2);
																					three.setText(tru.THREE2);
																					four.setText(tru.FOUR2);
																					detail.setText(tru.DETAIL2);
																					
																				}
																				else if(index==3){
																					percent.setText(tru.PERCENT3);
																					accident.setText(tru.ACCIDENT3);
																					health.setText(tru.HEALTH3);
																					old.setText(tru.OLD3);
																					invest.setText(tru.INVEST3);
																					person.setText(tru.PERSON3);
																					one.setText(tru.ONE3);
																					two.setText(tru.TWO3);
																					three.setText(tru.THREE3);
																					four.setText(tru.FOUR3);
																					detail.setText(tru.DETAIL3);
																					
																				}
																				else if(index==4){
																					percent.setText(tru.PERCENT4);
																					accident.setText(tru.ACCIDENT4);
																					health.setText(tru.HEALTH4);
																					old.setText(tru.OLD4);
																					invest.setText(tru.INVEST4);
																					person.setText(tru.PERSON4);
																					one.setText(tru.ONE4);
																					two.setText(tru.TWO4);
																					three.setText(tru.THREE4);
																					four.setText(tru.FOUR4);
																					detail.setText(tru.DETAIL4);
																					
																				}
																				else if(index==5){
																					percent.setText(tru.PERCENT5);
																					accident.setText(tru.ACCIDENT5);
																					health.setText(tru.HEALTH5);
																					old.setText(tru.OLD5);
																					invest.setText(tru.INVEST5);
																					person.setText(tru.PERSON5);
																					one.setText(tru.ONE5);
																					two.setText(tru.TWO5);
																					three.setText(tru.THREE5);
																					four.setText(tru.FOUR5);
																					detail.setText(tru.DETAIL5);
																					
																				}
																				
																			}else if(hasBaby){
																				if(ageOfBaby>0&&ageOfBaby<=6){
																					if(index==1){
																						percent.setText(tru.PERCENT6);
																						accident.setText(tru.ACCIDENT6);
																						health.setText(tru.HEALTH6);
																						old.setText(tru.OLD6);
																						invest.setText(tru.INVEST6);
																						person.setText(tru.PERSON6);
																						one.setText(tru.ONE6);
																						two.setText(tru.TWO6);
																						three.setText(tru.THREE6);
																						four.setText(tru.FOUR6);
																						detail.setText(tru.DETAIL6);
																						
																					}
																					else if(index==2){
																						percent.setText(tru.PERCENT7);
																						accident.setText(tru.ACCIDENT7);
																						health.setText(tru.HEALTH7);
																						old.setText(tru.OLD7);
																						invest.setText(tru.INVEST7);
																						person.setText(tru.PERSON7);
																						one.setText(tru.ONE7);
																						two.setText(tru.TWO7);
																						three.setText(tru.THREE7);
																						four.setText(tru.FOUR7);
																						detail.setText(tru.DETAIL7);
																						
																					}
																					else if(index==3){
																						percent.setText(tru.PERCENT8);
																						accident.setText(tru.ACCIDENT8);
																						health.setText(tru.HEALTH8);
																						old.setText(tru.OLD8);
																						invest.setText(tru.INVEST8);
																						person.setText(tru.PERSON8);
																						one.setText(tru.ONE8);
																						two.setText(tru.TWO8);
																						three.setText(tru.THREE8);
																						four.setText(tru.FOUR8);
																						detail.setText(tru.DETAIL8);
																						
																					}
																					else if(index==4){
																						percent.setText(tru.PERCENT9);
																						accident.setText(tru.ACCIDENT9);
																						health.setText(tru.HEALTH9);
																						old.setText(tru.OLD9);
																						invest.setText(tru.INVEST9);
																						person.setText(tru.PERSON9);
																						one.setText(tru.ONE9);
																						two.setText(tru.TWO9);
																						three.setText(tru.THREE9);
																						four.setText(tru.FOUR9);
																						detail.setText(tru.DETAIL9);
																						
																					}
																					else if(index==5){
																						percent.setText(tru.PERCENT10);
																						accident.setText(tru.ACCIDENT10);
																						health.setText(tru.HEALTH10);
																						old.setText(tru.OLD10);
																						invest.setText(tru.INVEST10);
																						person.setText(tru.PERSON10);
																						one.setText(tru.ONE10);
																						two.setText(tru.TWO10);
																						three.setText(tru.THREE10);
																						four.setText(tru.FOUR10);
																						detail.setText(tru.DETAIL10);
																						
																					}
																				}
																				else if(ageOfBaby>=7&&ageOfBaby<=22){
																					if(index==1){
																						percent.setText(tru.PERCENT6);
																						accident.setText(tru.ACCIDENT6);
																						health.setText(tru.HEALTH6);
																						old.setText(tru.OLD6);
																						invest.setText(tru.INVEST6);
																						person.setText(tru.PERSON6);
																						one.setText(tru.ONE6);
																						two.setText(tru.TWO6);
																						three.setText(tru.THREE6);
																						four.setText(tru.FOUR6);
																						detail.setText(tru.DETAIL6);
																						
																					}
																					else if(index==2){
																						percent.setText(tru.PERCENT7);
																						accident.setText(tru.ACCIDENT7);
																						health.setText(tru.HEALTH7);
																						old.setText(tru.OLD7);
																						invest.setText(tru.INVEST7);
																						person.setText(tru.PERSON7);
																						one.setText(tru.ONE7);
																						two.setText(tru.TWO7);
																						three.setText(tru.THREE7);
																						four.setText(tru.FOUR7);
																						detail.setText(tru.DETAIL7);
																						
																					}
																					else if(index==3){
																						percent.setText(tru.PERCENT8);
																						accident.setText(tru.ACCIDENT8);
																						health.setText(tru.HEALTH8);
																						old.setText(tru.OLD8);
																						invest.setText(tru.INVEST8);
																						person.setText(tru.PERSON8);
																						one.setText(tru.ONE8);
																						two.setText(tru.TWO8);
																						three.setText(tru.THREE8);
																						four.setText(tru.FOUR8);
																						detail.setText(tru.DETAIL8);
																						
																					}
																					else if(index==4){
																						percent.setText(tru.PERCENT9);
																						accident.setText(tru.ACCIDENT9);
																						health.setText(tru.HEALTH9);
																						old.setText(tru.OLD9);
																						invest.setText(tru.INVEST9);
																						person.setText(tru.PERSON9);
																						one.setText(tru.ONE9);
																						two.setText(tru.TWO9);
																						three.setText(tru.THREE9);
																						four.setText(tru.FOUR9);
																						detail.setText(tru.DETAIL9);
																						
																					}
																					else if(index==5){
																						percent.setText(tru.PERCENT10);
																						accident.setText(tru.ACCIDENT10);
																						health.setText(tru.HEALTH10);
																						old.setText(tru.OLD10);
																						invest.setText(tru.INVEST10);
																						person.setText(tru.PERSON10);
																						one.setText(tru.ONE10);
																						two.setText(tru.TWO10);
																						three.setText(tru.THREE10);
																						four.setText(tru.FOUR10);
																						detail.setText(tru.DETAIL10);
																						
																					}
																				}
																			}
																		}
																		else if(age>28&&age<=35){
																			if(!isMarried){
																				if(index==1){
																					percent.setText(tru.PERCENT1);
																					accident.setText(tru.ACCIDENT1);
																					health.setText(tru.HEALTH1);
																					old.setText(tru.OLD1);
																					invest.setText(tru.INVEST1);
																					person.setText(tru.PERSON1);
																					one.setText(tru.ONE1);
																					two.setText(tru.TWO1);
																					three.setText(tru.THREE1);
																					four.setText(tru.FOUR1);
																					detail.setText(tru.DETAIL1);
																					
																				}
																				else if(index==2){
																					percent.setText(tru.PERCENT2);
																					accident.setText(tru.ACCIDENT2);
																					health.setText(tru.HEALTH2);
																					old.setText(tru.OLD2);
																					invest.setText(tru.INVEST2);
																					person.setText(tru.PERSON2);
																					one.setText(tru.ONE2);
																					two.setText(tru.TWO2);
																					three.setText(tru.THREE2);
																					four.setText(tru.FOUR2);
																					detail.setText(tru.DETAIL2);
																					
																				}
																				else if(index==3){
																					percent.setText(tru.PERCENT3);
																					accident.setText(tru.ACCIDENT3);
																					health.setText(tru.HEALTH3);
																					old.setText(tru.OLD3);
																					invest.setText(tru.INVEST3);
																					person.setText(tru.PERSON3);
																					one.setText(tru.ONE3);
																					two.setText(tru.TWO3);
																					three.setText(tru.THREE3);
																					four.setText(tru.FOUR3);
																					detail.setText(tru.DETAIL3);
																					
																				}
																				else if(index==4){
																					percent.setText(tru.PERCENT4);
																					accident.setText(tru.ACCIDENT4);
																					health.setText(tru.HEALTH4);
																					old.setText(tru.OLD4);
																					invest.setText(tru.INVEST4);
																					person.setText(tru.PERSON4);
																					one.setText(tru.ONE4);
																					two.setText(tru.TWO4);
																					three.setText(tru.THREE4);
																					four.setText(tru.FOUR4);
																					detail.setText(tru.DETAIL4);
																					
																				}
																				else if(index==5){
																					percent.setText(tru.PERCENT5);
																					accident.setText(tru.ACCIDENT5);
																					health.setText(tru.HEALTH5);
																					old.setText(tru.OLD5);
																					invest.setText(tru.INVEST5);
																					person.setText(tru.PERSON5);
																					one.setText(tru.ONE5);
																					two.setText(tru.TWO5);
																					three.setText(tru.THREE5);
																					four.setText(tru.FOUR5);
																					detail.setText(tru.DETAIL5);
																					
																				}
																			}
																			else if(!hasBaby){
																				if(index==1){
																					percent.setText(tru.PERCENT11);
																					accident.setText(tru.ACCIDENT11);
																					health.setText(tru.HEALTH11);
																					old.setText(tru.OLD11);
																					invest.setText(tru.INVEST11);
																					person.setText(tru.PERSON11);
																					one.setText(tru.ONE11);
																					two.setText(tru.TWO1);
																					three.setText(tru.THREE11);
																					four.setText(tru.FOUR11);
																					detail.setText(tru.DETAIL11);
																					
																				}
																				else if(index==2){
																					percent.setText(tru.PERCENT12);
																					accident.setText(tru.ACCIDENT12);
																					health.setText(tru.HEALTH12);
																					old.setText(tru.OLD12);
																					invest.setText(tru.INVEST12);
																					person.setText(tru.PERSON12);
																					one.setText(tru.ONE12);
																					two.setText(tru.TWO12);
																					three.setText(tru.THREE12);
																					four.setText(tru.FOUR12);
																					detail.setText(tru.DETAIL12);
																					
																				}
																				else if(index==3){
																					percent.setText(tru.PERCENT13);
																					accident.setText(tru.ACCIDENT13);
																					health.setText(tru.HEALTH13);
																					old.setText(tru.OLD13);
																					invest.setText(tru.INVEST13);
																					person.setText(tru.PERSON13);
																					one.setText(tru.ONE13);
																					two.setText(tru.TWO13);
																					three.setText(tru.THREE13);
																					four.setText(tru.FOUR13);
																					detail.setText(tru.DETAIL13);
																					
																				}
																				else if(index==4){
																					percent.setText(tru.PERCENT14);
																					accident.setText(tru.ACCIDENT14);
																					health.setText(tru.HEALTH14);
																					old.setText(tru.OLD14);
																					invest.setText(tru.INVEST14);
																					person.setText(tru.PERSON14);
																					one.setText(tru.ONE14);
																					two.setText(tru.TWO14);
																					three.setText(tru.THREE14);
																					four.setText(tru.FOUR14);
																					detail.setText(tru.DETAIL14);
																					
																				}
																				else if(index==5){
																					percent.setText(tru.PERCENT15);
																					accident.setText(tru.ACCIDENT15);
																					health.setText(tru.HEALTH15);
																					old.setText(tru.OLD15);
																					invest.setText(tru.INVEST15);
																					person.setText(tru.PERSON15);
																					one.setText(tru.ONE15);
																					two.setText(tru.TWO15);
																					three.setText(tru.THREE15);
																					four.setText(tru.FOUR15);
																					detail.setText(tru.DETAIL15);
																					
																				}
																			}else if(ageOfBaby>0&&ageOfBaby<=6){
																				if(index==1){
																					percent.setText(tru.PERCENT6);
																					accident.setText(tru.ACCIDENT6);
																					health.setText(tru.HEALTH6);
																					old.setText(tru.OLD6);
																					invest.setText(tru.INVEST6);
																					person.setText(tru.PERSON6);
																					one.setText(tru.ONE6);
																					two.setText(tru.TWO6);
																					three.setText(tru.THREE6);
																					four.setText(tru.FOUR6);
																					detail.setText(tru.DETAIL6);
																					
																				}
																				else if(index==2){
																					percent.setText(tru.PERCENT7);
																					accident.setText(tru.ACCIDENT7);
																					health.setText(tru.HEALTH7);
																					old.setText(tru.OLD7);
																					invest.setText(tru.INVEST7);
																					person.setText(tru.PERSON7);
																					one.setText(tru.ONE7);
																					two.setText(tru.TWO7);
																					three.setText(tru.THREE7);
																					four.setText(tru.FOUR7);
																					detail.setText(tru.DETAIL7);
																					
																				}
																				else if(index==3){
																					percent.setText(tru.PERCENT8);
																					accident.setText(tru.ACCIDENT8);
																					health.setText(tru.HEALTH8);
																					old.setText(tru.OLD8);
																					invest.setText(tru.INVEST8);
																					person.setText(tru.PERSON8);
																					one.setText(tru.ONE8);
																					two.setText(tru.TWO8);
																					three.setText(tru.THREE8);
																					four.setText(tru.FOUR8);
																					detail.setText(tru.DETAIL8);
																					
																				}
																				else if(index==4){
																					percent.setText(tru.PERCENT9);
																					accident.setText(tru.ACCIDENT9);
																					health.setText(tru.HEALTH9);
																					old.setText(tru.OLD9);
																					invest.setText(tru.INVEST9);
																					person.setText(tru.PERSON9);
																					one.setText(tru.ONE9);
																					two.setText(tru.TWO9);
																					three.setText(tru.THREE9);
																					four.setText(tru.FOUR9);
																					detail.setText(tru.DETAIL9);
																					
																				}
																				else if(index==5){
																					percent.setText(tru.PERCENT10);
																					accident.setText(tru.ACCIDENT10);
																					health.setText(tru.HEALTH10);
																					old.setText(tru.OLD10);
																					invest.setText(tru.INVEST10);
																					person.setText(tru.PERSON10);
																					one.setText(tru.ONE10);
																					two.setText(tru.TWO10);
																					three.setText(tru.THREE10);
																					four.setText(tru.FOUR10);
																					detail.setText(tru.DETAIL10);
																					
																				}
																			}else if(ageOfBaby>6&&ageOfBaby<=22){
																				if(index==1){
																					percent.setText(tru.PERCENT16);
																					accident.setText(tru.ACCIDENT16);
																					health.setText(tru.HEALTH16);
																					old.setText(tru.OLD16);
																					invest.setText(tru.INVEST16);
																					person.setText(tru.PERSON16);
																					one.setText(tru.ONE16);
																					two.setText(tru.TWO16);
																					three.setText(tru.THREE16);
																					four.setText(tru.FOUR16);
																					detail.setText(tru.DETAIL16);
																					
																				}
																				else if(index==2){
																					percent.setText(tru.PERCENT17);
																					accident.setText(tru.ACCIDENT17);
																					health.setText(tru.HEALTH17);
																					old.setText(tru.OLD17);
																					invest.setText(tru.INVEST17);
																					person.setText(tru.PERSON17);
																					one.setText(tru.ONE17);
																					two.setText(tru.TWO17);
																					three.setText(tru.THREE17);
																					four.setText(tru.FOUR17);
																					detail.setText(tru.DETAIL17);
																					
																				}
																				else if(index==3){
																					percent.setText(tru.PERCENT18);
																					accident.setText(tru.ACCIDENT18);
																					health.setText(tru.HEALTH18);
																					old.setText(tru.OLD18);
																					invest.setText(tru.INVEST18);
																					person.setText(tru.PERSON18);
																					one.setText(tru.ONE18);
																					two.setText(tru.TWO18);
																					three.setText(tru.THREE18);
																					four.setText(tru.FOUR18);
																					detail.setText(tru.DETAIL18);
																					
																				}
																				else if(index==4){
																					percent.setText(tru.PERCENT19);
																					accident.setText(tru.ACCIDENT19);
																					health.setText(tru.HEALTH19);
																					old.setText(tru.OLD19);
																					invest.setText(tru.INVEST19);
																					person.setText(tru.PERSON19);
																					one.setText(tru.ONE19);
																					two.setText(tru.TWO19);
																					three.setText(tru.THREE19);
																					four.setText(tru.FOUR19);
																					detail.setText(tru.DETAIL19);
																					
																				}
																				else if(index==5){
																					percent.setText(tru.PERCENT20);
																					accident.setText(tru.ACCIDENT20);
																					health.setText(tru.HEALTH20);
																					old.setText(tru.OLD20);
																					invest.setText(tru.INVEST20);
																					person.setText(tru.PERSON20);
																					one.setText(tru.ONE20);
																					two.setText(tru.TWO20);
																					three.setText(tru.THREE20);
																					four.setText(tru.FOUR20);
																					detail.setText(tru.DETAIL20);
																					
																				}
																			}
																		}else if(age>=35&&age<=50){
																			if(!isMarried){
																				if(index==1){
																					percent.setText(tru.PERCENT21);
																					accident.setText(tru.ACCIDENT21);
																					health.setText(tru.HEALTH21);
																					old.setText(tru.OLD21);
																					invest.setText(tru.INVEST21);
																					person.setText(tru.PERSON21);
																					one.setText(tru.ONE21);
																					two.setText(tru.TWO21);
																					three.setText(tru.THREE21);
																					four.setText(tru.FOUR21);
																					detail.setText(tru.DETAIL21);
																					
																				}
																				else if(index==2){
																					percent.setText(tru.PERCENT22);
																					accident.setText(tru.ACCIDENT22);
																					health.setText(tru.HEALTH22);
																					old.setText(tru.OLD22);
																					invest.setText(tru.INVEST22);
																					person.setText(tru.PERSON22);
																					one.setText(tru.ONE22);
																					two.setText(tru.TWO22);
																					three.setText(tru.THREE22);
																					four.setText(tru.FOUR22);
																					detail.setText(tru.DETAIL22);
																					
																				}
																				else if(index==3){
																					percent.setText(tru.PERCENT23);
																					accident.setText(tru.ACCIDENT23);
																					health.setText(tru.HEALTH23);
																					old.setText(tru.OLD23);
																					invest.setText(tru.INVEST23);
																					person.setText(tru.PERSON23);
																					one.setText(tru.ONE23);
																					two.setText(tru.TWO23);
																					three.setText(tru.THREE23);
																					four.setText(tru.FOUR23);
																					detail.setText(tru.DETAIL23);
																					
																				}
																				else if(index==4){
																					percent.setText(tru.PERCENT24);
																					accident.setText(tru.ACCIDENT24);
																					health.setText(tru.HEALTH24);
																					old.setText(tru.OLD24);
																					invest.setText(tru.INVEST24);
																					person.setText(tru.PERSON24);
																					one.setText(tru.ONE24);
																					two.setText(tru.TWO24);
																					three.setText(tru.THREE24);
																					four.setText(tru.FOUR24);
																					detail.setText(tru.DETAIL24);
																					
																				}
																				else if(index==5){
																					percent.setText(tru.PERCENT25);
																					accident.setText(tru.ACCIDENT25);
																					health.setText(tru.HEALTH25);
																					old.setText(tru.OLD25);
																					invest.setText(tru.INVEST25);
																					person.setText(tru.PERSON25);
																					one.setText(tru.ONE25);
																					two.setText(tru.TWO25);
																					three.setText(tru.THREE25);
																					four.setText(tru.FOUR25);
																					detail.setText(tru.DETAIL25);
																					
																				}
																			}else if(!hasBaby){
																				if(index==1){
																					percent.setText(tru.PERCENT26);
																					accident.setText(tru.ACCIDENT26);
																					health.setText(tru.HEALTH26);
																					old.setText(tru.OLD26);
																					invest.setText(tru.INVEST26);
																					person.setText(tru.PERSON26);
																					one.setText(tru.ONE26);
																					two.setText(tru.TWO26);
																					three.setText(tru.THREE26);
																					four.setText(tru.FOUR26);
																					detail.setText(tru.DETAIL26);
																					
																				}
																				else if(index==2){
																					percent.setText(tru.PERCENT27);
																					accident.setText(tru.ACCIDENT27);
																					health.setText(tru.HEALTH27);
																					old.setText(tru.OLD27);
																					invest.setText(tru.INVEST27);
																					person.setText(tru.PERSON27);
																					one.setText(tru.ONE27);
																					two.setText(tru.TWO27);
																					three.setText(tru.THREE27);
																					four.setText(tru.FOUR27);
																					detail.setText(tru.DETAIL27);
																					
																				}
																				else if(index==3){
																					percent.setText(tru.PERCENT28);
																					accident.setText(tru.ACCIDENT28);
																					health.setText(tru.HEALTH28);
																					old.setText(tru.OLD28);
																					invest.setText(tru.INVEST28);
																					person.setText(tru.PERSON28);
																					one.setText(tru.ONE28);
																					two.setText(tru.TWO28);
																					three.setText(tru.THREE28);
																					four.setText(tru.FOUR28);
																					detail.setText(tru.DETAIL28);
																					
																				}
																				else if(index==4){
																					percent.setText(tru.PERCENT29);
																					accident.setText(tru.ACCIDENT29);
																					health.setText(tru.HEALTH29);
																					old.setText(tru.OLD29);
																					invest.setText(tru.INVEST29);
																					person.setText(tru.PERSON29);
																					one.setText(tru.ONE29);
																					two.setText(tru.TWO29);
																					three.setText(tru.THREE29);
																					four.setText(tru.FOUR29);
																					detail.setText(tru.DETAIL29);
																					
																				}
																				else if(index==5){
																					percent.setText(tru.PERCENT30);
																					accident.setText(tru.ACCIDENT30);
																					health.setText(tru.HEALTH30);
																					old.setText(tru.OLD30);
																					invest.setText(tru.INVEST30);
																					person.setText(tru.PERSON30);
																					one.setText(tru.ONE30);
																					two.setText(tru.TWO30);
																					three.setText(tru.THREE30);
																					four.setText(tru.FOUR30);
																					detail.setText(tru.DETAIL30);
																					
																				}
																			}else{
																				if(ageOfBaby>0&&ageOfBaby<=6){
																					if(index==1){
																						percent.setText(tru.PERCENT31);
																						accident.setText(tru.ACCIDENT31);
																						health.setText(tru.HEALTH31);
																						old.setText(tru.OLD31);
																						invest.setText(tru.INVEST31);
																						person.setText(tru.PERSON31);
																						one.setText(tru.ONE31);
																						two.setText(tru.TWO31);
																						three.setText(tru.THREE31);
																						four.setText(tru.FOUR31);
																						detail.setText(tru.DETAIL31);
																						
																					}
																					else if(index==2){
																						percent.setText(tru.PERCENT32);
																						accident.setText(tru.ACCIDENT32);
																						health.setText(tru.HEALTH32);
																						old.setText(tru.OLD32);
																						invest.setText(tru.INVEST32);
																						person.setText(tru.PERSON32);
																						one.setText(tru.ONE32);
																						two.setText(tru.TWO32);
																						three.setText(tru.THREE32);
																						four.setText(tru.FOUR32);
																						detail.setText(tru.DETAIL32);
																						
																					}
																					else if(index==3){
																						percent.setText(tru.PERCENT33);
																						accident.setText(tru.ACCIDENT33);
																						health.setText(tru.HEALTH33);
																						old.setText(tru.OLD33);
																						invest.setText(tru.INVEST33);
																						person.setText(tru.PERSON33);
																						one.setText(tru.ONE33);
																						two.setText(tru.TWO33);
																						three.setText(tru.THREE33);
																						four.setText(tru.FOUR33);
																						detail.setText(tru.DETAIL33);
																						
																					}
																					else if(index==4){
																						percent.setText(tru.PERCENT34);
																						accident.setText(tru.ACCIDENT34);
																						health.setText(tru.HEALTH34);
																						old.setText(tru.OLD34);
																						invest.setText(tru.INVEST34);
																						person.setText(tru.PERSON34);
																						one.setText(tru.ONE34);
																						two.setText(tru.TWO34);
																						three.setText(tru.THREE34);
																						four.setText(tru.FOUR34);
																						detail.setText(tru.DETAIL34);
																						
																					}
																					else if(index==5){
																						percent.setText(tru.PERCENT35);
																						accident.setText(tru.ACCIDENT35);
																						health.setText(tru.HEALTH35);
																						old.setText(tru.OLD35);
																						invest.setText(tru.INVEST35);
																						person.setText(tru.PERSON35);
																						one.setText(tru.ONE35);
																						two.setText(tru.TWO35);
																						three.setText(tru.THREE35);
																						four.setText(tru.FOUR35);
																						detail.setText(tru.DETAIL35);
																						
																					}
																				}else if(ageOfBaby>6&&ageOfBaby<22){
																					if(index==1){
																						percent.setText(tru.PERCENT36);
																						accident.setText(tru.ACCIDENT36);
																						health.setText(tru.HEALTH36);
																						old.setText(tru.OLD36);
																						invest.setText(tru.INVEST36);
																						person.setText(tru.PERSON36);
																						one.setText(tru.ONE36);
																						two.setText(tru.TWO36);
																						three.setText(tru.THREE36);
																						four.setText(tru.FOUR36);
																						detail.setText(tru.DETAIL36);
																						
																					}
																					else if(index==2){
																						percent.setText(tru.PERCENT37);
																						accident.setText(tru.ACCIDENT37);
																						health.setText(tru.HEALTH37);
																						old.setText(tru.OLD37);
																						invest.setText(tru.INVEST37);
																						person.setText(tru.PERSON37);
																						one.setText(tru.ONE37);
																						two.setText(tru.TWO37);
																						three.setText(tru.THREE37);
																						four.setText(tru.FOUR37);
																						detail.setText(tru.DETAIL37);
																						
																					}
																					else if(index==3){
																						percent.setText(tru.PERCENT38);
																						accident.setText(tru.ACCIDENT38);
																						health.setText(tru.HEALTH38);
																						old.setText(tru.OLD38);
																						invest.setText(tru.INVEST38);
																						person.setText(tru.PERSON38);
																						one.setText(tru.ONE38);
																						two.setText(tru.TWO38);
																						three.setText(tru.THREE38);
																						four.setText(tru.FOUR38);
																						detail.setText(tru.DETAIL38);
																						
																					}
																					else if(index==4){
																						percent.setText(tru.PERCENT39);
																						accident.setText(tru.ACCIDENT39);
																						health.setText(tru.HEALTH39);
																						old.setText(tru.OLD39);
																						invest.setText(tru.INVEST39);
																						person.setText(tru.PERSON39);
																						one.setText(tru.ONE39);
																						two.setText(tru.TWO39);
																						three.setText(tru.THREE39);
																						four.setText(tru.FOUR39);
																						detail.setText(tru.DETAIL39);
																						
																					}
																					else if(index==5){
																						percent.setText(tru.PERCENT40);
																						accident.setText(tru.ACCIDENT40);
																						health.setText(tru.HEALTH40);
																						old.setText(tru.OLD40);
																						invest.setText(tru.INVEST40);
																						person.setText(tru.PERSON40);
																						one.setText(tru.ONE40);
																						two.setText(tru.TWO40);
																						three.setText(tru.THREE40);
																						four.setText(tru.FOUR40);
																						detail.setText(tru.DETAIL40);
																						
																					}
																				}else if(ageOfBaby>22&&ageOfBaby<33){
																					if(index==1){
																						percent.setText(tru.PERCENT41);
																						accident.setText(tru.ACCIDENT41);
																						health.setText(tru.HEALTH41);
																						old.setText(tru.OLD41);
																						invest.setText(tru.INVEST41);
																						person.setText(tru.PERSON41);
																						one.setText(tru.ONE41);
																						two.setText(tru.TWO41);
																						three.setText(tru.THREE41);
																						four.setText(tru.FOUR41);
																						detail.setText(tru.DETAIL41);
																						
																					}
																					else if(index==2){
																						percent.setText(tru.PERCENT42);
																						accident.setText(tru.ACCIDENT42);
																						health.setText(tru.HEALTH42);
																						old.setText(tru.OLD42);
																						invest.setText(tru.INVEST42);
																						person.setText(tru.PERSON42);
																						one.setText(tru.ONE42);
																						two.setText(tru.TWO42);
																						three.setText(tru.THREE42);
																						four.setText(tru.FOUR42);
																						detail.setText(tru.DETAIL42);
																						
																					}
																					else if(index==3){
																						percent.setText(tru.PERCENT43);
																						accident.setText(tru.ACCIDENT43);
																						health.setText(tru.HEALTH43);
																						old.setText(tru.OLD43);
																						invest.setText(tru.INVEST43);
																						person.setText(tru.PERSON43);
																						one.setText(tru.ONE43);
																						two.setText(tru.TWO43);
																						three.setText(tru.THREE43);
																						four.setText(tru.FOUR43);
																						detail.setText(tru.DETAIL43);
																						
																					}
																					else if(index==4){
																						percent.setText(tru.PERCENT44);
																						accident.setText(tru.ACCIDENT44);
																						health.setText(tru.HEALTH44);
																						old.setText(tru.OLD44);
																						invest.setText(tru.INVEST44);
																						person.setText(tru.PERSON44);
																						one.setText(tru.ONE44);
																						two.setText(tru.TWO44);
																						three.setText(tru.THREE44);
																						four.setText(tru.FOUR44);
																						detail.setText(tru.DETAIL44);
																						
																					}
																					else if(index==5){
																						percent.setText(tru.PERCENT45);
																						accident.setText(tru.ACCIDENT45);
																						health.setText(tru.HEALTH45);
																						old.setText(tru.OLD45);
																						invest.setText(tru.INVEST45);
																						person.setText(tru.PERSON45);
																						one.setText(tru.ONE45);
																						two.setText(tru.TWO45);
																						three.setText(tru.THREE45);
																						four.setText(tru.FOUR45);
																						detail.setText(tru.DETAIL45);
																						
																					}
																				}
																			}
																		}else if(age>50&&age<=60){
																			if(!isMarried){
																				if(index==1){
																					percent.setText(tru.PERCENT46);
																					accident.setText(tru.ACCIDENT46);
																					health.setText(tru.HEALTH46);
																					old.setText(tru.OLD46);
																					invest.setText(tru.INVEST46);
																					person.setText(tru.PERSON46);
																					one.setText(tru.ONE46);
																					two.setText(tru.TWO46);
																					three.setText(tru.THREE46);
																					four.setText(tru.FOUR46);
																					detail.setText(tru.DETAIL46);
																					
																				}
																				else if(index==2){
																					percent.setText(tru.PERCENT47);
																					accident.setText(tru.ACCIDENT47);
																					health.setText(tru.HEALTH47);
																					old.setText(tru.OLD47);
																					invest.setText(tru.INVEST47);
																					person.setText(tru.PERSON47);
																					one.setText(tru.ONE47);
																					two.setText(tru.TWO47);
																					three.setText(tru.THREE47);
																					four.setText(tru.FOUR47);
																					detail.setText(tru.DETAIL47);
																					
																				}
																				else if(index==3){
																					percent.setText(tru.PERCENT48);
																					accident.setText(tru.ACCIDENT48);
																					health.setText(tru.HEALTH48);
																					old.setText(tru.OLD48);
																					invest.setText(tru.INVEST48);
																					person.setText(tru.PERSON48);
																					one.setText(tru.ONE48);
																					two.setText(tru.TWO48);
																					three.setText(tru.THREE48);
																					four.setText(tru.FOUR48);
																					detail.setText(tru.DETAIL48);
																					
																				}
																				else if(index==4){
																					percent.setText(tru.PERCENT49);
																					accident.setText(tru.ACCIDENT49);
																					health.setText(tru.HEALTH49);
																					old.setText(tru.OLD49);
																					invest.setText(tru.INVEST49);
																					person.setText(tru.PERSON49);
																					one.setText(tru.ONE49);
																					two.setText(tru.TWO49);
																					three.setText(tru.THREE49);
																					four.setText(tru.FOUR49);
																					detail.setText(tru.DETAIL49);
																					
																				}
																				else if(index==5){
																					percent.setText(tru.PERCENT50);
																					accident.setText(tru.ACCIDENT50);
																					health.setText(tru.HEALTH50);
																					old.setText(tru.OLD50);
																					invest.setText(tru.INVEST50);
																					person.setText(tru.PERSON50);
																					one.setText(tru.ONE50);
																					two.setText(tru.TWO50);
																					three.setText(tru.THREE50);
																					four.setText(tru.FOUR50);
																					detail.setText(tru.DETAIL50);
																					
																				}
																			}else if(!hasBaby){
																				if(index==1){
																					percent.setText(tru.PERCENT51);
																					accident.setText(tru.ACCIDENT51);
																					health.setText(tru.HEALTH51);
																					old.setText(tru.OLD51);
																					invest.setText(tru.INVEST51);
																					person.setText(tru.PERSON51);
																					one.setText(tru.ONE51);
																					two.setText(tru.TWO51);
																					three.setText(tru.THREE51);
																					four.setText(tru.FOUR51);
																					detail.setText(tru.DETAIL51);
																					
																				}
																				else if(index==2){
																					percent.setText(tru.PERCENT52);
																					accident.setText(tru.ACCIDENT52);
																					health.setText(tru.HEALTH52);
																					old.setText(tru.OLD52);
																					invest.setText(tru.INVEST52);
																					person.setText(tru.PERSON52);
																					one.setText(tru.ONE52);
																					two.setText(tru.TWO52);
																					three.setText(tru.THREE52);
																					four.setText(tru.FOUR52);
																					detail.setText(tru.DETAIL52);
																					
																				}
																				else if(index==3){
																					percent.setText(tru.PERCENT53);
																					accident.setText(tru.ACCIDENT53);
																					health.setText(tru.HEALTH53);
																					old.setText(tru.OLD53);
																					invest.setText(tru.INVEST53);
																					person.setText(tru.PERSON53);
																					one.setText(tru.ONE53);
																					two.setText(tru.TWO53);
																					three.setText(tru.THREE53);
																					four.setText(tru.FOUR53);
																					detail.setText(tru.DETAIL53);
																					
																				}
																				else if(index==4){
																					percent.setText(tru.PERCENT54);
																					accident.setText(tru.ACCIDENT54);
																					health.setText(tru.HEALTH54);
																					old.setText(tru.OLD54);
																					invest.setText(tru.INVEST54);
																					person.setText(tru.PERSON54);
																					one.setText(tru.ONE54);
																					two.setText(tru.TWO54);
																					three.setText(tru.THREE54);
																					four.setText(tru.FOUR54);
																					detail.setText(tru.DETAIL54);
																					
																				}
																				else if(index==5){
																					percent.setText(tru.PERCENT55);
																					accident.setText(tru.ACCIDENT55);
																					health.setText(tru.HEALTH55);
																					old.setText(tru.OLD55);
																					invest.setText(tru.INVEST55);
																					person.setText(tru.PERSON55);
																					one.setText(tru.ONE55);
																					two.setText(tru.TWO55);
																					three.setText(tru.THREE55);
																					four.setText(tru.FOUR55);
																					detail.setText(tru.DETAIL55);
																					
																				}
																			}else{
																				if(ageOfBaby>0&&ageOfBaby<=6){
																					if(index==1){
																						percent.setText(tru.PERCENT56);
																						accident.setText(tru.ACCIDENT56);
																						health.setText(tru.HEALTH56);
																						old.setText(tru.OLD56);
																						invest.setText(tru.INVEST56);
																						person.setText(tru.PERSON56);
																						one.setText(tru.ONE56);
																						two.setText(tru.TWO56);
																						three.setText(tru.THREE56);
																						four.setText(tru.FOUR56);
																						detail.setText(tru.DETAIL56);
																						
																					}
																					else if(index==2){
																						percent.setText(tru.PERCENT57);
																						accident.setText(tru.ACCIDENT57);
																						health.setText(tru.HEALTH57);
																						old.setText(tru.OLD57);
																						invest.setText(tru.INVEST57);
																						person.setText(tru.PERSON57);
																						one.setText(tru.ONE57);
																						two.setText(tru.TWO57);
																						three.setText(tru.THREE57);
																						four.setText(tru.FOUR57);
																						detail.setText(tru.DETAIL57);
																						
																					}
																					else if(index==3){
																						percent.setText(tru.PERCENT58);
																						accident.setText(tru.ACCIDENT58);
																						health.setText(tru.HEALTH58);
																						old.setText(tru.OLD58);
																						invest.setText(tru.INVEST58);
																						person.setText(tru.PERSON58);
																						one.setText(tru.ONE58);
																						two.setText(tru.TWO58);
																						three.setText(tru.THREE58);
																						four.setText(tru.FOUR58);
																						detail.setText(tru.DETAIL58);
																						
																					}
																					else if(index==4){
																						percent.setText(tru.PERCENT59);
																						accident.setText(tru.ACCIDENT59);
																						health.setText(tru.HEALTH59);
																						old.setText(tru.OLD59);
																						invest.setText(tru.INVEST59);
																						person.setText(tru.PERSON59);
																						one.setText(tru.ONE59);
																						two.setText(tru.TWO59);
																						three.setText(tru.THREE59);
																						four.setText(tru.FOUR59);
																						detail.setText(tru.DETAIL59);
																						
																					}
																					else if(index==5){
																						percent.setText(tru.PERCENT60);
																						accident.setText(tru.ACCIDENT60);
																						health.setText(tru.HEALTH60);
																						old.setText(tru.OLD60);
																						invest.setText(tru.INVEST60);
																						person.setText(tru.PERSON60);
																						one.setText(tru.ONE60);
																						two.setText(tru.TWO60);
																						three.setText(tru.THREE60);
																						four.setText(tru.FOUR60);
																						detail.setText(tru.DETAIL60);
																						
																					}
																				}else if(ageOfBaby>6&&ageOfBaby<22){
																					if(index==1){
																						percent.setText(tru.PERCENT61);
																						accident.setText(tru.ACCIDENT61);
																						health.setText(tru.HEALTH61);
																						old.setText(tru.OLD61);
																						invest.setText(tru.INVEST61);
																						person.setText(tru.PERSON61);
																						one.setText(tru.ONE61);
																						two.setText(tru.TWO61);
																						three.setText(tru.THREE61);
																						four.setText(tru.FOUR61);
																						detail.setText(tru.DETAIL61);
																						
																					}
																					else if(index==2){
																						percent.setText(tru.PERCENT62);
																						accident.setText(tru.ACCIDENT62);
																						health.setText(tru.HEALTH62);
																						old.setText(tru.OLD62);
																						invest.setText(tru.INVEST62);
																						person.setText(tru.PERSON62);
																						one.setText(tru.ONE62);
																						two.setText(tru.TWO62);
																						three.setText(tru.THREE62);
																						four.setText(tru.FOUR62);
																						detail.setText(tru.DETAIL62);
																						
																					}
																					else if(index==3){
																						percent.setText(tru.PERCENT63);
																						accident.setText(tru.ACCIDENT63);
																						health.setText(tru.HEALTH63);
																						old.setText(tru.OLD63);
																						invest.setText(tru.INVEST63);
																						person.setText(tru.PERSON63);
																						one.setText(tru.ONE63);
																						two.setText(tru.TWO63);
																						three.setText(tru.THREE63);
																						four.setText(tru.FOUR63);
																						detail.setText(tru.DETAIL63);
																						
																					}
																					else if(index==4){
																						percent.setText(tru.PERCENT64);
																						accident.setText(tru.ACCIDENT64);
																						health.setText(tru.HEALTH64);
																						old.setText(tru.OLD64);
																						invest.setText(tru.INVEST64);
																						person.setText(tru.PERSON64);
																						one.setText(tru.ONE64);
																						two.setText(tru.TWO64);
																						three.setText(tru.THREE64);
																						four.setText(tru.FOUR64);
																						detail.setText(tru.DETAIL64);
																						
																					}
																					else if(index==5){
																						percent.setText(tru.PERCENT65);
																						accident.setText(tru.ACCIDENT65);
																						health.setText(tru.HEALTH65);
																						old.setText(tru.OLD65);
																						invest.setText(tru.INVEST65);
																						person.setText(tru.PERSON65);
																						one.setText(tru.ONE65);
																						two.setText(tru.TWO65);
																						three.setText(tru.THREE65);
																						four.setText(tru.FOUR65);
																						detail.setText(tru.DETAIL65);
																						
																					}
																				}else if(ageOfBaby>22&&ageOfBaby<33){
																					if(index==1){
																						percent.setText(tru.PERCENT66);
																						accident.setText(tru.ACCIDENT66);
																						health.setText(tru.HEALTH66);
																						old.setText(tru.OLD66);
																						invest.setText(tru.INVEST66);
																						person.setText(tru.PERSON66);
																						one.setText(tru.ONE66);
																						two.setText(tru.TWO66);
																						three.setText(tru.THREE66);
																						four.setText(tru.FOUR66);
																						detail.setText(tru.DETAIL66);
																						
																					}
																					else if(index==2){
																						percent.setText(tru.PERCENT67);
																						accident.setText(tru.ACCIDENT67);
																						health.setText(tru.HEALTH67);
																						old.setText(tru.OLD67);
																						invest.setText(tru.INVEST67);
																						person.setText(tru.PERSON67);
																						one.setText(tru.ONE67);
																						two.setText(tru.TWO67);
																						three.setText(tru.THREE67);
																						four.setText(tru.FOUR67);
																						detail.setText(tru.DETAIL67);
																						
																					}
																					else if(index==3){
																						percent.setText(tru.PERCENT68);
																						accident.setText(tru.ACCIDENT68);
																						health.setText(tru.HEALTH68);
																						old.setText(tru.OLD68);
																						invest.setText(tru.INVEST68);
																						person.setText(tru.PERSON68);
																						one.setText(tru.ONE68);
																						two.setText(tru.TWO68);
																						three.setText(tru.THREE68);
																						four.setText(tru.FOUR68);
																						detail.setText(tru.DETAIL68);
																						
																					}
																					else if(index==4){
																						percent.setText(tru.PERCENT69);
																						accident.setText(tru.ACCIDENT69);
																						health.setText(tru.HEALTH69);
																						old.setText(tru.OLD69);
																						invest.setText(tru.INVEST69);
																						person.setText(tru.PERSON69);
																						one.setText(tru.ONE69);
																						two.setText(tru.TWO69);
																						three.setText(tru.THREE69);
																						four.setText(tru.FOUR69);
																						detail.setText(tru.DETAIL69);
																						
																					}
																					else if(index==5){
																						percent.setText(tru.PERCENT70);
																						accident.setText(tru.ACCIDENT70);
																						health.setText(tru.HEALTH70);
																						old.setText(tru.OLD70);
																						invest.setText(tru.INVEST70);
																						person.setText(tru.PERSON70);
																						one.setText(tru.ONE70);
																						two.setText(tru.TWO70);
																						three.setText(tru.THREE70);
																						four.setText(tru.FOUR70);
																						detail.setText(tru.DETAIL70);
																						
																					}
																				}
																			}
																		}
																		
																		ad3 = new AlertDialog.Builder(activity).setView(dateTimeLayout3).show();
																		
																		ok = (Button) dateTimeLayout3.findViewById(R.id.finish);
																		ok.setOnClickListener(new OnClickListener() {
																			
																			@Override
																			public void onClick(View v) {
																				ad3.dismiss();
																			}
																		});
																	}
																});
															}
															
															
														}
													});
												}
											});
											
										}
										else if(hasBaby){
											
											babyAge = editText.getText().toString().trim();
//											ageOfBaby = Integer.parseInt(babyAge);
											Log.d("debug", "babyAge"+babyAge);
											if("".equals(babyAge)||babyAge==null){
												UIHelper.showToastMessage( UNSELECTED);
											}
											else{
												button2.setClickable(true);
												ad1.dismiss();
												handler1.post(new Runnable() {
													
													@Override
													public void run() {
														
														//获取小孩子的年龄
														if(hasBaby){
															babyAge = editText.getText().toString().trim();
															ageOfBaby = Integer.parseInt(babyAge);
															Log.d("debug", "babyAge"+babyAge);
														}
														LinearLayout dateTimeLayout2 = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.test_dialog3, null);
														mRootView = dateTimeLayout2.findViewById(R.id.rootView);
														mTView = (TextView) dateTimeLayout2.findViewById(R.id.value);
														mIView = (ImageView) dateTimeLayout2.findViewById(R.id.bt_down);
														mIView.setOnClickListener(DateTimePickDialogUtil.this);
														
														String[] names = DateTimePickDialogUtil.this.activity.getResources().getStringArray(R.array.money_list);
														for(int i=0;i<names.length;i++){
															CustomObject object = new CustomObject();
															object.data = names[i];
															nameList.add(object);
														}
														
														mAdapter = new CustemSpinerAdapter(DateTimePickDialogUtil.this.activity);
														mAdapter.refreshData(nameList, 0);
														
														mSpinerPopWindow = new SpinerPopWindow(activity);
														mSpinerPopWindow.setAdapter(mAdapter);
														mSpinerPopWindow.setItemListener(DateTimePickDialogUtil.this);
														ad2 = new AlertDialog.Builder(activity).setView(dateTimeLayout2).show();
														
														button3 = (Button) dateTimeLayout2.findViewById(R.id.id_next3);
														button3.setOnClickListener(new android.view.View.OnClickListener() {
															
															@Override
															public void onClick(View v) {
																//获取选择的收入等级
																index = getPostion();
																Log.d("debug", "get index"+index);
																if(index == 0){
																	UIHelper.showToastMessage( UNSELECTED);
																}
																else{
																	ad2.dismiss();
																	handler2.post(new Runnable() {
																		
																		@Override
																		public void run() {
																			//获取选择的收入等级
																			index = getPostion();
																			Log.d("debug", "get index"+index);
																			
																			LinearLayout dateTimeLayout3 = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.test_result, null);
																			
																			//呈现测试结果
																			percent = (TextView) dateTimeLayout3.findViewById(R.id.id_percent);
																			accident = (TextView) dateTimeLayout3.findViewById(R.id.id_accident);
																			health = (TextView) dateTimeLayout3.findViewById(R.id.id_health);
																			old = (TextView) dateTimeLayout3.findViewById(R.id.id_old);
																			invest = (TextView) dateTimeLayout3.findViewById(R.id.id_invest);
																			person = (TextView) dateTimeLayout3.findViewById(R.id.id_person);
																			one = (TextView) dateTimeLayout3.findViewById(R.id.id_one);
																			two = (TextView) dateTimeLayout3.findViewById(R.id.id_two);
																			three = (TextView) dateTimeLayout3.findViewById(R.id.id_three);
																			four = (TextView) dateTimeLayout3.findViewById(R.id.id_four);
																			detail = (TextView) dateTimeLayout3.findViewById(R.id.detail);
																			
																			tru = new TestResultUtil();
																			
																			if(age>=18&&age<=28){
																				if(!isMarried){
																					
																					if(index==1){
																						percent.setText(tru.PERCENT1);
																						accident.setText(tru.ACCIDENT1);
																						health.setText(tru.HEALTH1);
																						old.setText(tru.OLD1);
																						invest.setText(tru.INVEST1);
																						person.setText(tru.PERSON1);
																						one.setText(tru.ONE1);
																						two.setText(tru.TWO1);
																						three.setText(tru.THREE1);
																						four.setText(tru.FOUR1);
																						detail.setText(tru.DETAIL1);
																						
																					}
																					else if(index==2){
																						percent.setText(tru.PERCENT2);
																						accident.setText(tru.ACCIDENT2);
																						health.setText(tru.HEALTH2);
																						old.setText(tru.OLD2);
																						invest.setText(tru.INVEST2);
																						person.setText(tru.PERSON2);
																						one.setText(tru.ONE2);
																						two.setText(tru.TWO2);
																						three.setText(tru.THREE2);
																						four.setText(tru.FOUR2);
																						detail.setText(tru.DETAIL2);
																						
																					}
																					else if(index==3){
																						percent.setText(tru.PERCENT3);
																						accident.setText(tru.ACCIDENT3);
																						health.setText(tru.HEALTH3);
																						old.setText(tru.OLD3);
																						invest.setText(tru.INVEST3);
																						person.setText(tru.PERSON3);
																						one.setText(tru.ONE3);
																						two.setText(tru.TWO3);
																						three.setText(tru.THREE3);
																						four.setText(tru.FOUR3);
																						detail.setText(tru.DETAIL3);
																						
																					}
																					else if(index==4){
																						percent.setText(tru.PERCENT4);
																						accident.setText(tru.ACCIDENT4);
																						health.setText(tru.HEALTH4);
																						old.setText(tru.OLD4);
																						invest.setText(tru.INVEST4);
																						person.setText(tru.PERSON4);
																						one.setText(tru.ONE4);
																						two.setText(tru.TWO4);
																						three.setText(tru.THREE4);
																						four.setText(tru.FOUR4);
																						detail.setText(tru.DETAIL4);
																						
																					}
																					else if(index==5){
																						percent.setText(tru.PERCENT5);
																						accident.setText(tru.ACCIDENT5);
																						health.setText(tru.HEALTH5);
																						old.setText(tru.OLD5);
																						invest.setText(tru.INVEST5);
																						person.setText(tru.PERSON5);
																						one.setText(tru.ONE5);
																						two.setText(tru.TWO5);
																						three.setText(tru.THREE5);
																						four.setText(tru.FOUR5);
																						detail.setText(tru.DETAIL5);
																						
																					}
																				}else if(!hasBaby){
																					if(index==1){
																						percent.setText(tru.PERCENT1);
																						accident.setText(tru.ACCIDENT1);
																						health.setText(tru.HEALTH1);
																						old.setText(tru.OLD1);
																						invest.setText(tru.INVEST1);
																						person.setText(tru.PERSON1);
																						one.setText(tru.ONE1);
																						two.setText(tru.TWO1);
																						three.setText(tru.THREE1);
																						four.setText(tru.FOUR1);
																						detail.setText(tru.DETAIL1);
																						
																					}
																					else if(index==2){
																						percent.setText(tru.PERCENT2);
																						accident.setText(tru.ACCIDENT2);
																						health.setText(tru.HEALTH2);
																						old.setText(tru.OLD2);
																						invest.setText(tru.INVEST2);
																						person.setText(tru.PERSON2);
																						one.setText(tru.ONE2);
																						two.setText(tru.TWO2);
																						three.setText(tru.THREE2);
																						four.setText(tru.FOUR2);
																						detail.setText(tru.DETAIL2);
																						
																					}
																					else if(index==3){
																						percent.setText(tru.PERCENT3);
																						accident.setText(tru.ACCIDENT3);
																						health.setText(tru.HEALTH3);
																						old.setText(tru.OLD3);
																						invest.setText(tru.INVEST3);
																						person.setText(tru.PERSON3);
																						one.setText(tru.ONE3);
																						two.setText(tru.TWO3);
																						three.setText(tru.THREE3);
																						four.setText(tru.FOUR3);
																						detail.setText(tru.DETAIL3);
																						
																					}
																					else if(index==4){
																						percent.setText(tru.PERCENT4);
																						accident.setText(tru.ACCIDENT4);
																						health.setText(tru.HEALTH4);
																						old.setText(tru.OLD4);
																						invest.setText(tru.INVEST4);
																						person.setText(tru.PERSON4);
																						one.setText(tru.ONE4);
																						two.setText(tru.TWO4);
																						three.setText(tru.THREE4);
																						four.setText(tru.FOUR4);
																						detail.setText(tru.DETAIL4);
																						
																					}
																					else if(index==5){
																						percent.setText(tru.PERCENT5);
																						accident.setText(tru.ACCIDENT5);
																						health.setText(tru.HEALTH5);
																						old.setText(tru.OLD5);
																						invest.setText(tru.INVEST5);
																						person.setText(tru.PERSON5);
																						one.setText(tru.ONE5);
																						two.setText(tru.TWO5);
																						three.setText(tru.THREE5);
																						four.setText(tru.FOUR5);
																						detail.setText(tru.DETAIL5);
																						
																					}
																					
																				}else if(hasBaby){
																					if(ageOfBaby>0&&ageOfBaby<=6){
																						if(index==1){
																							percent.setText(tru.PERCENT6);
																							accident.setText(tru.ACCIDENT6);
																							health.setText(tru.HEALTH6);
																							old.setText(tru.OLD6);
																							invest.setText(tru.INVEST6);
																							person.setText(tru.PERSON6);
																							one.setText(tru.ONE6);
																							two.setText(tru.TWO6);
																							three.setText(tru.THREE6);
																							four.setText(tru.FOUR6);
																							detail.setText(tru.DETAIL6);
																							
																						}
																						else if(index==2){
																							percent.setText(tru.PERCENT7);
																							accident.setText(tru.ACCIDENT7);
																							health.setText(tru.HEALTH7);
																							old.setText(tru.OLD7);
																							invest.setText(tru.INVEST7);
																							person.setText(tru.PERSON7);
																							one.setText(tru.ONE7);
																							two.setText(tru.TWO7);
																							three.setText(tru.THREE7);
																							four.setText(tru.FOUR7);
																							detail.setText(tru.DETAIL7);
																							
																						}
																						else if(index==3){
																							percent.setText(tru.PERCENT8);
																							accident.setText(tru.ACCIDENT8);
																							health.setText(tru.HEALTH8);
																							old.setText(tru.OLD8);
																							invest.setText(tru.INVEST8);
																							person.setText(tru.PERSON8);
																							one.setText(tru.ONE8);
																							two.setText(tru.TWO8);
																							three.setText(tru.THREE8);
																							four.setText(tru.FOUR8);
																							detail.setText(tru.DETAIL8);
																							
																						}
																						else if(index==4){
																							percent.setText(tru.PERCENT9);
																							accident.setText(tru.ACCIDENT9);
																							health.setText(tru.HEALTH9);
																							old.setText(tru.OLD9);
																							invest.setText(tru.INVEST9);
																							person.setText(tru.PERSON9);
																							one.setText(tru.ONE9);
																							two.setText(tru.TWO9);
																							three.setText(tru.THREE9);
																							four.setText(tru.FOUR9);
																							detail.setText(tru.DETAIL9);
																							
																						}
																						else if(index==5){
																							percent.setText(tru.PERCENT10);
																							accident.setText(tru.ACCIDENT10);
																							health.setText(tru.HEALTH10);
																							old.setText(tru.OLD10);
																							invest.setText(tru.INVEST10);
																							person.setText(tru.PERSON10);
																							one.setText(tru.ONE10);
																							two.setText(tru.TWO10);
																							three.setText(tru.THREE10);
																							four.setText(tru.FOUR10);
																							detail.setText(tru.DETAIL10);
																							
																						}
																					}
																					else if(ageOfBaby>=7&&ageOfBaby<=22){
																						if(index==1){
																							percent.setText(tru.PERCENT6);
																							accident.setText(tru.ACCIDENT6);
																							health.setText(tru.HEALTH6);
																							old.setText(tru.OLD6);
																							invest.setText(tru.INVEST6);
																							person.setText(tru.PERSON6);
																							one.setText(tru.ONE6);
																							two.setText(tru.TWO6);
																							three.setText(tru.THREE6);
																							four.setText(tru.FOUR6);
																							detail.setText(tru.DETAIL6);
																							
																						}
																						else if(index==2){
																							percent.setText(tru.PERCENT7);
																							accident.setText(tru.ACCIDENT7);
																							health.setText(tru.HEALTH7);
																							old.setText(tru.OLD7);
																							invest.setText(tru.INVEST7);
																							person.setText(tru.PERSON7);
																							one.setText(tru.ONE7);
																							two.setText(tru.TWO7);
																							three.setText(tru.THREE7);
																							four.setText(tru.FOUR7);
																							detail.setText(tru.DETAIL7);
																							
																						}
																						else if(index==3){
																							percent.setText(tru.PERCENT8);
																							accident.setText(tru.ACCIDENT8);
																							health.setText(tru.HEALTH8);
																							old.setText(tru.OLD8);
																							invest.setText(tru.INVEST8);
																							person.setText(tru.PERSON8);
																							one.setText(tru.ONE8);
																							two.setText(tru.TWO8);
																							three.setText(tru.THREE8);
																							four.setText(tru.FOUR8);
																							detail.setText(tru.DETAIL8);
																							
																						}
																						else if(index==4){
																							percent.setText(tru.PERCENT9);
																							accident.setText(tru.ACCIDENT9);
																							health.setText(tru.HEALTH9);
																							old.setText(tru.OLD9);
																							invest.setText(tru.INVEST9);
																							person.setText(tru.PERSON9);
																							one.setText(tru.ONE9);
																							two.setText(tru.TWO9);
																							three.setText(tru.THREE9);
																							four.setText(tru.FOUR9);
																							detail.setText(tru.DETAIL9);
																							
																						}
																						else if(index==5){
																							percent.setText(tru.PERCENT10);
																							accident.setText(tru.ACCIDENT10);
																							health.setText(tru.HEALTH10);
																							old.setText(tru.OLD10);
																							invest.setText(tru.INVEST10);
																							person.setText(tru.PERSON10);
																							one.setText(tru.ONE10);
																							two.setText(tru.TWO10);
																							three.setText(tru.THREE10);
																							four.setText(tru.FOUR10);
																							detail.setText(tru.DETAIL10);
																							
																						}
																					}
																				}
																			}
																			else if(age>28&&age<=35){
																				if(!isMarried){
																					if(index==1){
																						percent.setText(tru.PERCENT1);
																						accident.setText(tru.ACCIDENT1);
																						health.setText(tru.HEALTH1);
																						old.setText(tru.OLD1);
																						invest.setText(tru.INVEST1);
																						person.setText(tru.PERSON1);
																						one.setText(tru.ONE1);
																						two.setText(tru.TWO1);
																						three.setText(tru.THREE1);
																						four.setText(tru.FOUR1);
																						detail.setText(tru.DETAIL1);
																						
																					}
																					else if(index==2){
																						percent.setText(tru.PERCENT2);
																						accident.setText(tru.ACCIDENT2);
																						health.setText(tru.HEALTH2);
																						old.setText(tru.OLD2);
																						invest.setText(tru.INVEST2);
																						person.setText(tru.PERSON2);
																						one.setText(tru.ONE2);
																						two.setText(tru.TWO2);
																						three.setText(tru.THREE2);
																						four.setText(tru.FOUR2);
																						detail.setText(tru.DETAIL2);
																						
																					}
																					else if(index==3){
																						percent.setText(tru.PERCENT3);
																						accident.setText(tru.ACCIDENT3);
																						health.setText(tru.HEALTH3);
																						old.setText(tru.OLD3);
																						invest.setText(tru.INVEST3);
																						person.setText(tru.PERSON3);
																						one.setText(tru.ONE3);
																						two.setText(tru.TWO3);
																						three.setText(tru.THREE3);
																						four.setText(tru.FOUR3);
																						detail.setText(tru.DETAIL3);
																						
																					}
																					else if(index==4){
																						percent.setText(tru.PERCENT4);
																						accident.setText(tru.ACCIDENT4);
																						health.setText(tru.HEALTH4);
																						old.setText(tru.OLD4);
																						invest.setText(tru.INVEST4);
																						person.setText(tru.PERSON4);
																						one.setText(tru.ONE4);
																						two.setText(tru.TWO4);
																						three.setText(tru.THREE4);
																						four.setText(tru.FOUR4);
																						detail.setText(tru.DETAIL4);
																						
																					}
																					else if(index==5){
																						percent.setText(tru.PERCENT5);
																						accident.setText(tru.ACCIDENT5);
																						health.setText(tru.HEALTH5);
																						old.setText(tru.OLD5);
																						invest.setText(tru.INVEST5);
																						person.setText(tru.PERSON5);
																						one.setText(tru.ONE5);
																						two.setText(tru.TWO5);
																						three.setText(tru.THREE5);
																						four.setText(tru.FOUR5);
																						detail.setText(tru.DETAIL5);
																						
																					}
																				}
																				else if(!hasBaby){
																					if(index==1){
																						percent.setText(tru.PERCENT11);
																						accident.setText(tru.ACCIDENT11);
																						health.setText(tru.HEALTH11);
																						old.setText(tru.OLD11);
																						invest.setText(tru.INVEST11);
																						person.setText(tru.PERSON11);
																						one.setText(tru.ONE11);
																						two.setText(tru.TWO1);
																						three.setText(tru.THREE11);
																						four.setText(tru.FOUR11);
																						detail.setText(tru.DETAIL11);
																						
																					}
																					else if(index==2){
																						percent.setText(tru.PERCENT12);
																						accident.setText(tru.ACCIDENT12);
																						health.setText(tru.HEALTH12);
																						old.setText(tru.OLD12);
																						invest.setText(tru.INVEST12);
																						person.setText(tru.PERSON12);
																						one.setText(tru.ONE12);
																						two.setText(tru.TWO12);
																						three.setText(tru.THREE12);
																						four.setText(tru.FOUR12);
																						detail.setText(tru.DETAIL12);
																						
																					}
																					else if(index==3){
																						percent.setText(tru.PERCENT13);
																						accident.setText(tru.ACCIDENT13);
																						health.setText(tru.HEALTH13);
																						old.setText(tru.OLD13);
																						invest.setText(tru.INVEST13);
																						person.setText(tru.PERSON13);
																						one.setText(tru.ONE13);
																						two.setText(tru.TWO13);
																						three.setText(tru.THREE13);
																						four.setText(tru.FOUR13);
																						detail.setText(tru.DETAIL13);
																						
																					}
																					else if(index==4){
																						percent.setText(tru.PERCENT14);
																						accident.setText(tru.ACCIDENT14);
																						health.setText(tru.HEALTH14);
																						old.setText(tru.OLD14);
																						invest.setText(tru.INVEST14);
																						person.setText(tru.PERSON14);
																						one.setText(tru.ONE14);
																						two.setText(tru.TWO14);
																						three.setText(tru.THREE14);
																						four.setText(tru.FOUR14);
																						detail.setText(tru.DETAIL14);
																						
																					}
																					else if(index==5){
																						percent.setText(tru.PERCENT15);
																						accident.setText(tru.ACCIDENT15);
																						health.setText(tru.HEALTH15);
																						old.setText(tru.OLD15);
																						invest.setText(tru.INVEST15);
																						person.setText(tru.PERSON15);
																						one.setText(tru.ONE15);
																						two.setText(tru.TWO15);
																						three.setText(tru.THREE15);
																						four.setText(tru.FOUR15);
																						detail.setText(tru.DETAIL15);
																						
																					}
																				}else if(ageOfBaby>0&&ageOfBaby<=6){
																					if(index==1){
																						percent.setText(tru.PERCENT6);
																						accident.setText(tru.ACCIDENT6);
																						health.setText(tru.HEALTH6);
																						old.setText(tru.OLD6);
																						invest.setText(tru.INVEST6);
																						person.setText(tru.PERSON6);
																						one.setText(tru.ONE6);
																						two.setText(tru.TWO6);
																						three.setText(tru.THREE6);
																						four.setText(tru.FOUR6);
																						detail.setText(tru.DETAIL6);
																						
																					}
																					else if(index==2){
																						percent.setText(tru.PERCENT7);
																						accident.setText(tru.ACCIDENT7);
																						health.setText(tru.HEALTH7);
																						old.setText(tru.OLD7);
																						invest.setText(tru.INVEST7);
																						person.setText(tru.PERSON7);
																						one.setText(tru.ONE7);
																						two.setText(tru.TWO7);
																						three.setText(tru.THREE7);
																						four.setText(tru.FOUR7);
																						detail.setText(tru.DETAIL7);
																						
																					}
																					else if(index==3){
																						percent.setText(tru.PERCENT8);
																						accident.setText(tru.ACCIDENT8);
																						health.setText(tru.HEALTH8);
																						old.setText(tru.OLD8);
																						invest.setText(tru.INVEST8);
																						person.setText(tru.PERSON8);
																						one.setText(tru.ONE8);
																						two.setText(tru.TWO8);
																						three.setText(tru.THREE8);
																						four.setText(tru.FOUR8);
																						detail.setText(tru.DETAIL8);
																						
																					}
																					else if(index==4){
																						percent.setText(tru.PERCENT9);
																						accident.setText(tru.ACCIDENT9);
																						health.setText(tru.HEALTH9);
																						old.setText(tru.OLD9);
																						invest.setText(tru.INVEST9);
																						person.setText(tru.PERSON9);
																						one.setText(tru.ONE9);
																						two.setText(tru.TWO9);
																						three.setText(tru.THREE9);
																						four.setText(tru.FOUR9);
																						detail.setText(tru.DETAIL9);
																						
																					}
																					else if(index==5){
																						percent.setText(tru.PERCENT10);
																						accident.setText(tru.ACCIDENT10);
																						health.setText(tru.HEALTH10);
																						old.setText(tru.OLD10);
																						invest.setText(tru.INVEST10);
																						person.setText(tru.PERSON10);
																						one.setText(tru.ONE10);
																						two.setText(tru.TWO10);
																						three.setText(tru.THREE10);
																						four.setText(tru.FOUR10);
																						detail.setText(tru.DETAIL10);
																						
																					}
																				}else if(ageOfBaby>6&&ageOfBaby<=22){
																					if(index==1){
																						percent.setText(tru.PERCENT16);
																						accident.setText(tru.ACCIDENT16);
																						health.setText(tru.HEALTH16);
																						old.setText(tru.OLD16);
																						invest.setText(tru.INVEST16);
																						person.setText(tru.PERSON16);
																						one.setText(tru.ONE16);
																						two.setText(tru.TWO16);
																						three.setText(tru.THREE16);
																						four.setText(tru.FOUR16);
																						detail.setText(tru.DETAIL16);
																						
																					}
																					else if(index==2){
																						percent.setText(tru.PERCENT17);
																						accident.setText(tru.ACCIDENT17);
																						health.setText(tru.HEALTH17);
																						old.setText(tru.OLD17);
																						invest.setText(tru.INVEST17);
																						person.setText(tru.PERSON17);
																						one.setText(tru.ONE17);
																						two.setText(tru.TWO17);
																						three.setText(tru.THREE17);
																						four.setText(tru.FOUR17);
																						detail.setText(tru.DETAIL17);
																						
																					}
																					else if(index==3){
																						percent.setText(tru.PERCENT18);
																						accident.setText(tru.ACCIDENT18);
																						health.setText(tru.HEALTH18);
																						old.setText(tru.OLD18);
																						invest.setText(tru.INVEST18);
																						person.setText(tru.PERSON18);
																						one.setText(tru.ONE18);
																						two.setText(tru.TWO18);
																						three.setText(tru.THREE18);
																						four.setText(tru.FOUR18);
																						detail.setText(tru.DETAIL18);
																						
																					}
																					else if(index==4){
																						percent.setText(tru.PERCENT19);
																						accident.setText(tru.ACCIDENT19);
																						health.setText(tru.HEALTH19);
																						old.setText(tru.OLD19);
																						invest.setText(tru.INVEST19);
																						person.setText(tru.PERSON19);
																						one.setText(tru.ONE19);
																						two.setText(tru.TWO19);
																						three.setText(tru.THREE19);
																						four.setText(tru.FOUR19);
																						detail.setText(tru.DETAIL19);
																						
																					}
																					else if(index==5){
																						percent.setText(tru.PERCENT20);
																						accident.setText(tru.ACCIDENT20);
																						health.setText(tru.HEALTH20);
																						old.setText(tru.OLD20);
																						invest.setText(tru.INVEST20);
																						person.setText(tru.PERSON20);
																						one.setText(tru.ONE20);
																						two.setText(tru.TWO20);
																						three.setText(tru.THREE20);
																						four.setText(tru.FOUR20);
																						detail.setText(tru.DETAIL20);
																						
																					}
																				}
																			}else if(age>=35&&age<=50){
																				if(!isMarried){
																					if(index==1){
																						percent.setText(tru.PERCENT21);
																						accident.setText(tru.ACCIDENT21);
																						health.setText(tru.HEALTH21);
																						old.setText(tru.OLD21);
																						invest.setText(tru.INVEST21);
																						person.setText(tru.PERSON21);
																						one.setText(tru.ONE21);
																						two.setText(tru.TWO21);
																						three.setText(tru.THREE21);
																						four.setText(tru.FOUR21);
																						detail.setText(tru.DETAIL21);
																						
																					}
																					else if(index==2){
																						percent.setText(tru.PERCENT22);
																						accident.setText(tru.ACCIDENT22);
																						health.setText(tru.HEALTH22);
																						old.setText(tru.OLD22);
																						invest.setText(tru.INVEST22);
																						person.setText(tru.PERSON22);
																						one.setText(tru.ONE22);
																						two.setText(tru.TWO22);
																						three.setText(tru.THREE22);
																						four.setText(tru.FOUR22);
																						detail.setText(tru.DETAIL22);
																						
																					}
																					else if(index==3){
																						percent.setText(tru.PERCENT23);
																						accident.setText(tru.ACCIDENT23);
																						health.setText(tru.HEALTH23);
																						old.setText(tru.OLD23);
																						invest.setText(tru.INVEST23);
																						person.setText(tru.PERSON23);
																						one.setText(tru.ONE23);
																						two.setText(tru.TWO23);
																						three.setText(tru.THREE23);
																						four.setText(tru.FOUR23);
																						detail.setText(tru.DETAIL23);
																						
																					}
																					else if(index==4){
																						percent.setText(tru.PERCENT24);
																						accident.setText(tru.ACCIDENT24);
																						health.setText(tru.HEALTH24);
																						old.setText(tru.OLD24);
																						invest.setText(tru.INVEST24);
																						person.setText(tru.PERSON24);
																						one.setText(tru.ONE24);
																						two.setText(tru.TWO24);
																						three.setText(tru.THREE24);
																						four.setText(tru.FOUR24);
																						detail.setText(tru.DETAIL24);
																						
																					}
																					else if(index==5){
																						percent.setText(tru.PERCENT25);
																						accident.setText(tru.ACCIDENT25);
																						health.setText(tru.HEALTH25);
																						old.setText(tru.OLD25);
																						invest.setText(tru.INVEST25);
																						person.setText(tru.PERSON25);
																						one.setText(tru.ONE25);
																						two.setText(tru.TWO25);
																						three.setText(tru.THREE25);
																						four.setText(tru.FOUR25);
																						detail.setText(tru.DETAIL25);
																						
																					}
																				}else if(!hasBaby){
																					if(index==1){
																						percent.setText(tru.PERCENT26);
																						accident.setText(tru.ACCIDENT26);
																						health.setText(tru.HEALTH26);
																						old.setText(tru.OLD26);
																						invest.setText(tru.INVEST26);
																						person.setText(tru.PERSON26);
																						one.setText(tru.ONE26);
																						two.setText(tru.TWO26);
																						three.setText(tru.THREE26);
																						four.setText(tru.FOUR26);
																						detail.setText(tru.DETAIL26);
																						
																					}
																					else if(index==2){
																						percent.setText(tru.PERCENT27);
																						accident.setText(tru.ACCIDENT27);
																						health.setText(tru.HEALTH27);
																						old.setText(tru.OLD27);
																						invest.setText(tru.INVEST27);
																						person.setText(tru.PERSON27);
																						one.setText(tru.ONE27);
																						two.setText(tru.TWO27);
																						three.setText(tru.THREE27);
																						four.setText(tru.FOUR27);
																						detail.setText(tru.DETAIL27);
																						
																					}
																					else if(index==3){
																						percent.setText(tru.PERCENT28);
																						accident.setText(tru.ACCIDENT28);
																						health.setText(tru.HEALTH28);
																						old.setText(tru.OLD28);
																						invest.setText(tru.INVEST28);
																						person.setText(tru.PERSON28);
																						one.setText(tru.ONE28);
																						two.setText(tru.TWO28);
																						three.setText(tru.THREE28);
																						four.setText(tru.FOUR28);
																						detail.setText(tru.DETAIL28);
																						
																					}
																					else if(index==4){
																						percent.setText(tru.PERCENT29);
																						accident.setText(tru.ACCIDENT29);
																						health.setText(tru.HEALTH29);
																						old.setText(tru.OLD29);
																						invest.setText(tru.INVEST29);
																						person.setText(tru.PERSON29);
																						one.setText(tru.ONE29);
																						two.setText(tru.TWO29);
																						three.setText(tru.THREE29);
																						four.setText(tru.FOUR29);
																						detail.setText(tru.DETAIL29);
																						
																					}
																					else if(index==5){
																						percent.setText(tru.PERCENT30);
																						accident.setText(tru.ACCIDENT30);
																						health.setText(tru.HEALTH30);
																						old.setText(tru.OLD30);
																						invest.setText(tru.INVEST30);
																						person.setText(tru.PERSON30);
																						one.setText(tru.ONE30);
																						two.setText(tru.TWO30);
																						three.setText(tru.THREE30);
																						four.setText(tru.FOUR30);
																						detail.setText(tru.DETAIL30);
																						
																					}
																				}else{
																					if(ageOfBaby>0&&ageOfBaby<=6){
																						if(index==1){
																							percent.setText(tru.PERCENT31);
																							accident.setText(tru.ACCIDENT31);
																							health.setText(tru.HEALTH31);
																							old.setText(tru.OLD31);
																							invest.setText(tru.INVEST31);
																							person.setText(tru.PERSON31);
																							one.setText(tru.ONE31);
																							two.setText(tru.TWO31);
																							three.setText(tru.THREE31);
																							four.setText(tru.FOUR31);
																							detail.setText(tru.DETAIL31);
																							
																						}
																						else if(index==2){
																							percent.setText(tru.PERCENT32);
																							accident.setText(tru.ACCIDENT32);
																							health.setText(tru.HEALTH32);
																							old.setText(tru.OLD32);
																							invest.setText(tru.INVEST32);
																							person.setText(tru.PERSON32);
																							one.setText(tru.ONE32);
																							two.setText(tru.TWO32);
																							three.setText(tru.THREE32);
																							four.setText(tru.FOUR32);
																							detail.setText(tru.DETAIL32);
																							
																						}
																						else if(index==3){
																							percent.setText(tru.PERCENT33);
																							accident.setText(tru.ACCIDENT33);
																							health.setText(tru.HEALTH33);
																							old.setText(tru.OLD33);
																							invest.setText(tru.INVEST33);
																							person.setText(tru.PERSON33);
																							one.setText(tru.ONE33);
																							two.setText(tru.TWO33);
																							three.setText(tru.THREE33);
																							four.setText(tru.FOUR33);
																							detail.setText(tru.DETAIL33);
																							
																						}
																						else if(index==4){
																							percent.setText(tru.PERCENT34);
																							accident.setText(tru.ACCIDENT34);
																							health.setText(tru.HEALTH34);
																							old.setText(tru.OLD34);
																							invest.setText(tru.INVEST34);
																							person.setText(tru.PERSON34);
																							one.setText(tru.ONE34);
																							two.setText(tru.TWO34);
																							three.setText(tru.THREE34);
																							four.setText(tru.FOUR34);
																							detail.setText(tru.DETAIL34);
																							
																						}
																						else if(index==5){
																							percent.setText(tru.PERCENT35);
																							accident.setText(tru.ACCIDENT35);
																							health.setText(tru.HEALTH35);
																							old.setText(tru.OLD35);
																							invest.setText(tru.INVEST35);
																							person.setText(tru.PERSON35);
																							one.setText(tru.ONE35);
																							two.setText(tru.TWO35);
																							three.setText(tru.THREE35);
																							four.setText(tru.FOUR35);
																							detail.setText(tru.DETAIL35);
																							
																						}
																					}else if(ageOfBaby>6&&ageOfBaby<22){
																						if(index==1){
																							percent.setText(tru.PERCENT36);
																							accident.setText(tru.ACCIDENT36);
																							health.setText(tru.HEALTH36);
																							old.setText(tru.OLD36);
																							invest.setText(tru.INVEST36);
																							person.setText(tru.PERSON36);
																							one.setText(tru.ONE36);
																							two.setText(tru.TWO36);
																							three.setText(tru.THREE36);
																							four.setText(tru.FOUR36);
																							detail.setText(tru.DETAIL36);
																							
																						}
																						else if(index==2){
																							percent.setText(tru.PERCENT37);
																							accident.setText(tru.ACCIDENT37);
																							health.setText(tru.HEALTH37);
																							old.setText(tru.OLD37);
																							invest.setText(tru.INVEST37);
																							person.setText(tru.PERSON37);
																							one.setText(tru.ONE37);
																							two.setText(tru.TWO37);
																							three.setText(tru.THREE37);
																							four.setText(tru.FOUR37);
																							detail.setText(tru.DETAIL37);
																							
																						}
																						else if(index==3){
																							percent.setText(tru.PERCENT38);
																							accident.setText(tru.ACCIDENT38);
																							health.setText(tru.HEALTH38);
																							old.setText(tru.OLD38);
																							invest.setText(tru.INVEST38);
																							person.setText(tru.PERSON38);
																							one.setText(tru.ONE38);
																							two.setText(tru.TWO38);
																							three.setText(tru.THREE38);
																							four.setText(tru.FOUR38);
																							detail.setText(tru.DETAIL38);
																							
																						}
																						else if(index==4){
																							percent.setText(tru.PERCENT39);
																							accident.setText(tru.ACCIDENT39);
																							health.setText(tru.HEALTH39);
																							old.setText(tru.OLD39);
																							invest.setText(tru.INVEST39);
																							person.setText(tru.PERSON39);
																							one.setText(tru.ONE39);
																							two.setText(tru.TWO39);
																							three.setText(tru.THREE39);
																							four.setText(tru.FOUR39);
																							detail.setText(tru.DETAIL39);
																							
																						}
																						else if(index==5){
																							percent.setText(tru.PERCENT40);
																							accident.setText(tru.ACCIDENT40);
																							health.setText(tru.HEALTH40);
																							old.setText(tru.OLD40);
																							invest.setText(tru.INVEST40);
																							person.setText(tru.PERSON40);
																							one.setText(tru.ONE40);
																							two.setText(tru.TWO40);
																							three.setText(tru.THREE40);
																							four.setText(tru.FOUR40);
																							detail.setText(tru.DETAIL40);
																							
																						}
																					}else if(ageOfBaby>22&&ageOfBaby<33){
																						if(index==1){
																							percent.setText(tru.PERCENT41);
																							accident.setText(tru.ACCIDENT41);
																							health.setText(tru.HEALTH41);
																							old.setText(tru.OLD41);
																							invest.setText(tru.INVEST41);
																							person.setText(tru.PERSON41);
																							one.setText(tru.ONE41);
																							two.setText(tru.TWO41);
																							three.setText(tru.THREE41);
																							four.setText(tru.FOUR41);
																							detail.setText(tru.DETAIL41);
																							
																						}
																						else if(index==2){
																							percent.setText(tru.PERCENT42);
																							accident.setText(tru.ACCIDENT42);
																							health.setText(tru.HEALTH42);
																							old.setText(tru.OLD42);
																							invest.setText(tru.INVEST42);
																							person.setText(tru.PERSON42);
																							one.setText(tru.ONE42);
																							two.setText(tru.TWO42);
																							three.setText(tru.THREE42);
																							four.setText(tru.FOUR42);
																							detail.setText(tru.DETAIL42);
																							
																						}
																						else if(index==3){
																							percent.setText(tru.PERCENT43);
																							accident.setText(tru.ACCIDENT43);
																							health.setText(tru.HEALTH43);
																							old.setText(tru.OLD43);
																							invest.setText(tru.INVEST43);
																							person.setText(tru.PERSON43);
																							one.setText(tru.ONE43);
																							two.setText(tru.TWO43);
																							three.setText(tru.THREE43);
																							four.setText(tru.FOUR43);
																							detail.setText(tru.DETAIL43);
																							
																						}
																						else if(index==4){
																							percent.setText(tru.PERCENT44);
																							accident.setText(tru.ACCIDENT44);
																							health.setText(tru.HEALTH44);
																							old.setText(tru.OLD44);
																							invest.setText(tru.INVEST44);
																							person.setText(tru.PERSON44);
																							one.setText(tru.ONE44);
																							two.setText(tru.TWO44);
																							three.setText(tru.THREE44);
																							four.setText(tru.FOUR44);
																							detail.setText(tru.DETAIL44);
																							
																						}
																						else if(index==5){
																							percent.setText(tru.PERCENT45);
																							accident.setText(tru.ACCIDENT45);
																							health.setText(tru.HEALTH45);
																							old.setText(tru.OLD45);
																							invest.setText(tru.INVEST45);
																							person.setText(tru.PERSON45);
																							one.setText(tru.ONE45);
																							two.setText(tru.TWO45);
																							three.setText(tru.THREE45);
																							four.setText(tru.FOUR45);
																							detail.setText(tru.DETAIL45);
																							
																						}
																					}
																				}
																			}else if(age>50&&age<=60){
																				if(!isMarried){
																					if(index==1){
																						percent.setText(tru.PERCENT46);
																						accident.setText(tru.ACCIDENT46);
																						health.setText(tru.HEALTH46);
																						old.setText(tru.OLD46);
																						invest.setText(tru.INVEST46);
																						person.setText(tru.PERSON46);
																						one.setText(tru.ONE46);
																						two.setText(tru.TWO46);
																						three.setText(tru.THREE46);
																						four.setText(tru.FOUR46);
																						detail.setText(tru.DETAIL46);
																						
																					}
																					else if(index==2){
																						percent.setText(tru.PERCENT47);
																						accident.setText(tru.ACCIDENT47);
																						health.setText(tru.HEALTH47);
																						old.setText(tru.OLD47);
																						invest.setText(tru.INVEST47);
																						person.setText(tru.PERSON47);
																						one.setText(tru.ONE47);
																						two.setText(tru.TWO47);
																						three.setText(tru.THREE47);
																						four.setText(tru.FOUR47);
																						detail.setText(tru.DETAIL47);
																						
																					}
																					else if(index==3){
																						percent.setText(tru.PERCENT48);
																						accident.setText(tru.ACCIDENT48);
																						health.setText(tru.HEALTH48);
																						old.setText(tru.OLD48);
																						invest.setText(tru.INVEST48);
																						person.setText(tru.PERSON48);
																						one.setText(tru.ONE48);
																						two.setText(tru.TWO48);
																						three.setText(tru.THREE48);
																						four.setText(tru.FOUR48);
																						detail.setText(tru.DETAIL48);
																						
																					}
																					else if(index==4){
																						percent.setText(tru.PERCENT49);
																						accident.setText(tru.ACCIDENT49);
																						health.setText(tru.HEALTH49);
																						old.setText(tru.OLD49);
																						invest.setText(tru.INVEST49);
																						person.setText(tru.PERSON49);
																						one.setText(tru.ONE49);
																						two.setText(tru.TWO49);
																						three.setText(tru.THREE49);
																						four.setText(tru.FOUR49);
																						detail.setText(tru.DETAIL49);
																						
																					}
																					else if(index==5){
																						percent.setText(tru.PERCENT50);
																						accident.setText(tru.ACCIDENT50);
																						health.setText(tru.HEALTH50);
																						old.setText(tru.OLD50);
																						invest.setText(tru.INVEST50);
																						person.setText(tru.PERSON50);
																						one.setText(tru.ONE50);
																						two.setText(tru.TWO50);
																						three.setText(tru.THREE50);
																						four.setText(tru.FOUR50);
																						detail.setText(tru.DETAIL50);
																						
																					}
																				}else if(!hasBaby){
																					if(index==1){
																						percent.setText(tru.PERCENT51);
																						accident.setText(tru.ACCIDENT51);
																						health.setText(tru.HEALTH51);
																						old.setText(tru.OLD51);
																						invest.setText(tru.INVEST51);
																						person.setText(tru.PERSON51);
																						one.setText(tru.ONE51);
																						two.setText(tru.TWO51);
																						three.setText(tru.THREE51);
																						four.setText(tru.FOUR51);
																						detail.setText(tru.DETAIL51);
																						
																					}
																					else if(index==2){
																						percent.setText(tru.PERCENT52);
																						accident.setText(tru.ACCIDENT52);
																						health.setText(tru.HEALTH52);
																						old.setText(tru.OLD52);
																						invest.setText(tru.INVEST52);
																						person.setText(tru.PERSON52);
																						one.setText(tru.ONE52);
																						two.setText(tru.TWO52);
																						three.setText(tru.THREE52);
																						four.setText(tru.FOUR52);
																						detail.setText(tru.DETAIL52);
																						
																					}
																					else if(index==3){
																						percent.setText(tru.PERCENT53);
																						accident.setText(tru.ACCIDENT53);
																						health.setText(tru.HEALTH53);
																						old.setText(tru.OLD53);
																						invest.setText(tru.INVEST53);
																						person.setText(tru.PERSON53);
																						one.setText(tru.ONE53);
																						two.setText(tru.TWO53);
																						three.setText(tru.THREE53);
																						four.setText(tru.FOUR53);
																						detail.setText(tru.DETAIL53);
																						
																					}
																					else if(index==4){
																						percent.setText(tru.PERCENT54);
																						accident.setText(tru.ACCIDENT54);
																						health.setText(tru.HEALTH54);
																						old.setText(tru.OLD54);
																						invest.setText(tru.INVEST54);
																						person.setText(tru.PERSON54);
																						one.setText(tru.ONE54);
																						two.setText(tru.TWO54);
																						three.setText(tru.THREE54);
																						four.setText(tru.FOUR54);
																						detail.setText(tru.DETAIL54);
																						
																					}
																					else if(index==5){
																						percent.setText(tru.PERCENT55);
																						accident.setText(tru.ACCIDENT55);
																						health.setText(tru.HEALTH55);
																						old.setText(tru.OLD55);
																						invest.setText(tru.INVEST55);
																						person.setText(tru.PERSON55);
																						one.setText(tru.ONE55);
																						two.setText(tru.TWO55);
																						three.setText(tru.THREE55);
																						four.setText(tru.FOUR55);
																						detail.setText(tru.DETAIL55);
																						
																					}
																				}else{
																					if(ageOfBaby>0&&ageOfBaby<=6){
																						if(index==1){
																							percent.setText(tru.PERCENT56);
																							accident.setText(tru.ACCIDENT56);
																							health.setText(tru.HEALTH56);
																							old.setText(tru.OLD56);
																							invest.setText(tru.INVEST56);
																							person.setText(tru.PERSON56);
																							one.setText(tru.ONE56);
																							two.setText(tru.TWO56);
																							three.setText(tru.THREE56);
																							four.setText(tru.FOUR56);
																							detail.setText(tru.DETAIL56);
																							
																						}
																						else if(index==2){
																							percent.setText(tru.PERCENT57);
																							accident.setText(tru.ACCIDENT57);
																							health.setText(tru.HEALTH57);
																							old.setText(tru.OLD57);
																							invest.setText(tru.INVEST57);
																							person.setText(tru.PERSON57);
																							one.setText(tru.ONE57);
																							two.setText(tru.TWO57);
																							three.setText(tru.THREE57);
																							four.setText(tru.FOUR57);
																							detail.setText(tru.DETAIL57);
																							
																						}
																						else if(index==3){
																							percent.setText(tru.PERCENT58);
																							accident.setText(tru.ACCIDENT58);
																							health.setText(tru.HEALTH58);
																							old.setText(tru.OLD58);
																							invest.setText(tru.INVEST58);
																							person.setText(tru.PERSON58);
																							one.setText(tru.ONE58);
																							two.setText(tru.TWO58);
																							three.setText(tru.THREE58);
																							four.setText(tru.FOUR58);
																							detail.setText(tru.DETAIL58);
																							
																						}
																						else if(index==4){
																							percent.setText(tru.PERCENT59);
																							accident.setText(tru.ACCIDENT59);
																							health.setText(tru.HEALTH59);
																							old.setText(tru.OLD59);
																							invest.setText(tru.INVEST59);
																							person.setText(tru.PERSON59);
																							one.setText(tru.ONE59);
																							two.setText(tru.TWO59);
																							three.setText(tru.THREE59);
																							four.setText(tru.FOUR59);
																							detail.setText(tru.DETAIL59);
																							
																						}
																						else if(index==5){
																							percent.setText(tru.PERCENT60);
																							accident.setText(tru.ACCIDENT60);
																							health.setText(tru.HEALTH60);
																							old.setText(tru.OLD60);
																							invest.setText(tru.INVEST60);
																							person.setText(tru.PERSON60);
																							one.setText(tru.ONE60);
																							two.setText(tru.TWO60);
																							three.setText(tru.THREE60);
																							four.setText(tru.FOUR60);
																							detail.setText(tru.DETAIL60);
																							
																						}
																					}else if(ageOfBaby>6&&ageOfBaby<22){
																						if(index==1){
																							percent.setText(tru.PERCENT61);
																							accident.setText(tru.ACCIDENT61);
																							health.setText(tru.HEALTH61);
																							old.setText(tru.OLD61);
																							invest.setText(tru.INVEST61);
																							person.setText(tru.PERSON61);
																							one.setText(tru.ONE61);
																							two.setText(tru.TWO61);
																							three.setText(tru.THREE61);
																							four.setText(tru.FOUR61);
																							detail.setText(tru.DETAIL61);
																							
																						}
																						else if(index==2){
																							percent.setText(tru.PERCENT62);
																							accident.setText(tru.ACCIDENT62);
																							health.setText(tru.HEALTH62);
																							old.setText(tru.OLD62);
																							invest.setText(tru.INVEST62);
																							person.setText(tru.PERSON62);
																							one.setText(tru.ONE62);
																							two.setText(tru.TWO62);
																							three.setText(tru.THREE62);
																							four.setText(tru.FOUR62);
																							detail.setText(tru.DETAIL62);
																							
																						}
																						else if(index==3){
																							percent.setText(tru.PERCENT63);
																							accident.setText(tru.ACCIDENT63);
																							health.setText(tru.HEALTH63);
																							old.setText(tru.OLD63);
																							invest.setText(tru.INVEST63);
																							person.setText(tru.PERSON63);
																							one.setText(tru.ONE63);
																							two.setText(tru.TWO63);
																							three.setText(tru.THREE63);
																							four.setText(tru.FOUR63);
																							detail.setText(tru.DETAIL63);
																							
																						}
																						else if(index==4){
																							percent.setText(tru.PERCENT64);
																							accident.setText(tru.ACCIDENT64);
																							health.setText(tru.HEALTH64);
																							old.setText(tru.OLD64);
																							invest.setText(tru.INVEST64);
																							person.setText(tru.PERSON64);
																							one.setText(tru.ONE64);
																							two.setText(tru.TWO64);
																							three.setText(tru.THREE64);
																							four.setText(tru.FOUR64);
																							detail.setText(tru.DETAIL64);
																							
																						}
																						else if(index==5){
																							percent.setText(tru.PERCENT65);
																							accident.setText(tru.ACCIDENT65);
																							health.setText(tru.HEALTH65);
																							old.setText(tru.OLD65);
																							invest.setText(tru.INVEST65);
																							person.setText(tru.PERSON65);
																							one.setText(tru.ONE65);
																							two.setText(tru.TWO65);
																							three.setText(tru.THREE65);
																							four.setText(tru.FOUR65);
																							detail.setText(tru.DETAIL65);
																							
																						}
																					}else if(ageOfBaby>22&&ageOfBaby<33){
																						if(index==1){
																							percent.setText(tru.PERCENT66);
																							accident.setText(tru.ACCIDENT66);
																							health.setText(tru.HEALTH66);
																							old.setText(tru.OLD66);
																							invest.setText(tru.INVEST66);
																							person.setText(tru.PERSON66);
																							one.setText(tru.ONE66);
																							two.setText(tru.TWO66);
																							three.setText(tru.THREE66);
																							four.setText(tru.FOUR66);
																							detail.setText(tru.DETAIL66);
																							
																						}
																						else if(index==2){
																							percent.setText(tru.PERCENT67);
																							accident.setText(tru.ACCIDENT67);
																							health.setText(tru.HEALTH67);
																							old.setText(tru.OLD67);
																							invest.setText(tru.INVEST67);
																							person.setText(tru.PERSON67);
																							one.setText(tru.ONE67);
																							two.setText(tru.TWO67);
																							three.setText(tru.THREE67);
																							four.setText(tru.FOUR67);
																							detail.setText(tru.DETAIL67);
																							
																						}
																						else if(index==3){
																							percent.setText(tru.PERCENT68);
																							accident.setText(tru.ACCIDENT68);
																							health.setText(tru.HEALTH68);
																							old.setText(tru.OLD68);
																							invest.setText(tru.INVEST68);
																							person.setText(tru.PERSON68);
																							one.setText(tru.ONE68);
																							two.setText(tru.TWO68);
																							three.setText(tru.THREE68);
																							four.setText(tru.FOUR68);
																							detail.setText(tru.DETAIL68);
																							
																						}
																						else if(index==4){
																							percent.setText(tru.PERCENT69);
																							accident.setText(tru.ACCIDENT69);
																							health.setText(tru.HEALTH69);
																							old.setText(tru.OLD69);
																							invest.setText(tru.INVEST69);
																							person.setText(tru.PERSON69);
																							one.setText(tru.ONE69);
																							two.setText(tru.TWO69);
																							three.setText(tru.THREE69);
																							four.setText(tru.FOUR69);
																							detail.setText(tru.DETAIL69);
																							
																						}
																						else if(index==5){
																							percent.setText(tru.PERCENT70);
																							accident.setText(tru.ACCIDENT70);
																							health.setText(tru.HEALTH70);
																							old.setText(tru.OLD70);
																							invest.setText(tru.INVEST70);
																							person.setText(tru.PERSON70);
																							one.setText(tru.ONE70);
																							two.setText(tru.TWO70);
																							three.setText(tru.THREE70);
																							four.setText(tru.FOUR70);
																							detail.setText(tru.DETAIL70);
																							
																						}
																					}
																				}
																			}
																			
																			ad3 = new AlertDialog.Builder(activity).setView(dateTimeLayout3).show();
																			
																			ok = (Button) dateTimeLayout3.findViewById(R.id.finish);
																			ok.setOnClickListener(new OnClickListener() {
																				
																				@Override
																				public void onClick(View v) {
																					ad3.dismiss();
																				}
																			});
																		}
																	});
																}
																
																
															}
														});
													}
												});
											}
										}
										
									}
									}
									
									
									
								}
								
								
								
							}
						});
					}
				});
				//new AlertDialog.Builder(activity).setTitle("Hello").setPositiveButton("Yes", null).setNegativeButton("No", null).show();
			}
		});
		onDateChanged(null,0,0,0);
		
		return ad;
	}
	
	private Handler handler = new Handler();
	private Handler handler1 = new Handler();
	private Handler handler2 = new Handler();
	
	/**
	 * 根据用户生日计算年龄
	 */
	public static int getAgeByBirthday(Date birthday){
		
		Calendar cal = Calendar.getInstance();
		if(cal.before(birthday)){
			throw new IllegalArgumentException("The birthDay is before Now,It's unbelievable!");
		}
		
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH)+1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		
		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH)+1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		
		int age = yearNow - yearBirth;
		if(monthNow<=monthBirth){
			if(monthNow == monthBirth){
				if(dayOfMonthNow <dayOfMonthBirth){
					age--;
				}
			}else{
				age--;
			}
		}
		return age;
		
	}
	
	private Date getDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(datePicker.getYear(), datePicker.getMonth(),datePicker.getDayOfMonth());
		Date date = calendar.getTime();
		
		return date;
	}
	
	
	
	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		//获得日历实例
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(datePicker.getYear(), datePicker.getMonth(),datePicker.getDayOfMonth());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Date date = calendar.getTime();
		dateTime = sdf.format(date);
		textView.setText(dateTime);
		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(20);
	}
	
	private Calendar getCalendarByInitData(String initDateTime){
		Calendar calendar = Calendar.getInstance();
		
		String date = spliteString(initDateTime,"日","index","front");
		
		String yearStr = spliteString(date, "年", "index", "front");
		String monthAndDay = spliteString(date, "年", "index", "back");
		
		String monthStr = spliteString(monthAndDay, "月", "index", "front");
		String dayStr = spliteString(monthAndDay, "月", "index", "back");
		
		int currentYear = Integer.valueOf(yearStr.trim()).intValue();
		int currentMonth = Integer.valueOf(monthStr.trim()).intValue()-1;
		int currentDay = Integer.valueOf(dayStr.trim()).intValue();
		
		calendar.set(currentYear, currentMonth, currentDay);
		return calendar;
		
	}

	private String spliteString(String srcStr, String pattern,
			String indexOrLast, String frontOrBack) {
		String result = "";
		int loc = -1;
		if(indexOrLast.equalsIgnoreCase("index")){
			loc = srcStr.indexOf(pattern);
		}else{
			loc = srcStr.lastIndexOf(pattern);
		}
		if(frontOrBack.equalsIgnoreCase("front")){
			if(loc!=-1)
				result = srcStr.substring(0,loc);
		}else{
			if(loc!=-1)
				result = srcStr.substring(loc+1,srcStr.length());
		}
		return result;
	}

	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private SpinerPopWindow mSpinerPopWindow;
	private void showSpinWindow(){
		Log.e("debug", "showSpinWindow");
		mSpinerPopWindow.setWidth(mTView.getWidth());
		mSpinerPopWindow.showAsDropDown(mTView);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_down:
			showSpinWindow();
			break;
		}
	}
	
	private void setMoneyList(int pos){
		if(pos>=0&&pos<=nameList.size()){
			CustomObject value = nameList.get(pos);
			
			mTView.setText(value.toString());
		}
	}
	
	
	
	
	private int getPostion(){
		int ins = 0;
		String str = mTView.getText().toString().trim();
		if(str.equalsIgnoreCase("3万至8万")){
			ins = 1;
		}else if(str.equalsIgnoreCase("8万至15万")){
			ins = 2;
		}else if(str.equalsIgnoreCase("15万至30万")){
			ins = 3;
		}else if(str.equalsIgnoreCase("30万至100万")){
			ins = 4;
		}else if(str.equalsIgnoreCase("100万以上")){
			ins = 5;
		}
		return ins;
	}
	
	public void onItemClick(int pos){
		setMoneyList(pos);
	}

   public static  class CustomObject {
		
		public String data = "";
		
		@Override
		public String toString() {
			return data;
		}

	}
}

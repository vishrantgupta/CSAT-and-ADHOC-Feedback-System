package com.vishrant.customer.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.vishrant.customer.R;
import com.vishrant.customer.beans.ActionItems;
import com.vishrant.customer.constant.ApplicationGlobalVariable;
import com.vishrant.customer.tabActivity.FetchQuestionsActivity;
import com.vishrant.customer.utils.Utils;

public class FeedbackStatusActivity extends Activity {

	private SimpleDateFormat dateFormat = new SimpleDateFormat();
	private LinearLayout linearLayout = null;
	private View view = null;
	private ScrollView scrollView = null;
	
	private ArrayList<View> generatedQuestionViews = new ArrayList<View>();
	
	ArrayList<ActionItems> actionItems = new ArrayList<ActionItems>();
	
	private String feedbackType = null;
	private Integer projectId = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		linearLayout = new LinearLayout(this);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	    view = inflater.inflate(R.layout.activity_feedback_status, null);

	    // Find the ScrollView
	    scrollView = (ScrollView) view.findViewById(R.id.scrollViewFeedbackStatus);

	    scrollView.setBackgroundColor(Color.rgb(221, 221, 221));

	    // Create a LinearLayout element
	    linearLayout.setOrientation(LinearLayout.VERTICAL);

	    LayoutParams layoutParamsLinearLayout = new ScrollView.LayoutParams(
	    		ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.MATCH_PARENT);
	    
	    layoutParamsLinearLayout.setMargins(20, 10, 20, 10);
	    linearLayout.setLayoutParams(layoutParamsLinearLayout);

		Bundle bundle = getIntent().getExtras();
		String message = bundle.getString("feedbackStatus");

		try {
			if (message != null && message.length() > 0) {
				String[] details = message.trim().split("#");

				dateFormat.applyPattern("MMM - yyyy");
				String monthYear = dateFormat.format(new Date());
				
				TextView monthTv = new TextView(FeedbackStatusActivity.this);
				
				//csatMonth.setPadding(25, 20, 20, 15);
				monthTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
				monthTv.setTypeface(null, Typeface.BOLD);
				
				setMargins(monthTv, 25, 20, 25, 15);
				
				monthTv.setText(monthYear.toUpperCase());
				linearLayout.addView(monthTv);
				
				SimpleDateFormat sdf = new SimpleDateFormat("MMddyy");

				for (String str : details) {

					String[] values = str.trim().split("~");

					Integer questionId = Integer.parseInt(values[0]);
					String feedbackTxt = values[1];
					feedbackType = values[2];
					String questionTxt = values[3];

					Date month = sdf.parse(values[4]);

					Integer displayOrder = Integer.parseInt(values[5]);
					String questionCategory = values[6];
					String actionItemMgrTxt = values[7];
					
					String feedbackId = values[8];
					
					Integer projectId = Integer.parseInt(values[9]);
					
					ActionItems actionItem = new ActionItems();
					
					actionItem.setFeedbackId(feedbackId);
					actionItem.setQuestionId(questionId);
					actionItem.setFeedbackTxt(feedbackTxt);
					actionItem.setFeedbackType(feedbackType);
					actionItem.setQuestionTxt(questionTxt);
					actionItem.setMonth(month);
					actionItem.setDisplayOrder(displayOrder);
					actionItem.setQuestionCategory(questionCategory);
					actionItem.setActionItemMgrTxt(actionItemMgrTxt);
					actionItem.setProjectId(projectId);
					
					actionItems.add(actionItem);
				}
				
				// Adding elements
				if (actionItems != null && actionItems.size() > 0) {

					int count = 1;
					
					String previousQuestionCategory = "";
					
					projectId = actionItems.get(0).getProjectId();
					
					for (ActionItems question : actionItems) {
						
						if (!previousQuestionCategory.equalsIgnoreCase(question.getQuestionCategory())) {
							createQuestionCategoryHeading(question);
							previousQuestionCategory = question.getQuestionCategory();
						}
						
					    createQuestionLabel(question, count);

					    createQuestionFeedbackTxt(question);
					    
					    createActionItemTxt(question);
					    
						count++;
					}
					
					createEditTextIduHeadComment();

					createRadioBtnQuestion();
					
					createSubmitBtn();
					
					scrollView.addView(linearLayout);
				    setContentView(view);
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// setContentView(R.layout.activity_feedback_status);
	}
	
	private void setMargins (View v, int left, int top, int right, int bottom) {
		android.widget.LinearLayout.LayoutParams layoutparams = null;
		// for RatingBar
		if (v instanceof RatingBar) {
			layoutparams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutparams.setMargins(left, top, right, bottom);
			v.setLayoutParams(layoutparams);
		} else if (v  instanceof View) { // for views other then RatingBar
			layoutparams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			layoutparams.setMargins(left, top, right, bottom);
			v.setLayoutParams(layoutparams);
	    }
	}
	
	private void createQuestionCategoryHeading(ActionItems question) {
		
		TextView questionCategoryHeading = new TextView(FeedbackStatusActivity.this);
		
		//questionLabel.setPadding(20, 10, 20, 10);
		questionCategoryHeading.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		questionCategoryHeading.setTypeface(null, Typeface.BOLD);
		
		questionCategoryHeading.setText(question.getQuestionCategory());
		setMargins(questionCategoryHeading, 25, 15, 25, 15);
		
	    linearLayout.addView(questionCategoryHeading);
		
	}
	
	private void createQuestionLabel(ActionItems question, int count) {
		
		TextView questionLabel = new TextView(FeedbackStatusActivity.this);
		
		//questionLabel.setPadding(20, 10, 20, 10);
		questionLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		questionLabel.setTypeface(null, Typeface.BOLD);
		
		questionLabel.setText(count + ". " + question.getQuestionTxt());
		
		setMargins(questionLabel, 25, 15, 25, 15);
		
	    linearLayout.addView(questionLabel);
		
	}
	
	private void createQuestionFeedbackTxt(ActionItems question) {
		
		TextView questionLabel = new TextView(FeedbackStatusActivity.this);
		
		//questionLabel.setPadding(20, 10, 20, 10);
		questionLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		//questionLabel.setTypeface(null, Typeface.BOLD);
		questionLabel.setText("FEEDBACK:\n" + question.getFeedbackTxt());
		
		setMargins(questionLabel, 25, 5, 25, 15);
		
	    linearLayout.addView(questionLabel);
		
	}
	
	private void createActionItemTxt(ActionItems question) {
		
		TextView questionLabel = new TextView(FeedbackStatusActivity.this);
		
		//questionLabel.setPadding(20, 10, 20, 10);
		questionLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		//questionLabel.setTypeface(null, Typeface.BOLD);
		questionLabel.setText("ACTION ITEM: " + question.getActionItemMgrTxt());
		
		setMargins(questionLabel, 25, 5, 25, 15);
		
	    linearLayout.addView(questionLabel);
		
	}
	
	private void createEditTextIduHeadComment() {

		EditText editText = new EditText(FeedbackStatusActivity.this);
		
		Integer editTxtId = 2000;
		 
		// TODO check if 1000 need to add in id
		//editTxtId += 1000;
		editText.setId(editTxtId);
		
		editText.setPadding(55, 35, 55, 35);
		editText.setMaxLines(3);
		editText.setSingleLine(false);
		editText.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
		editText.setLines(3);
		editText.setTextColor(Color.BLACK);
	    //editText.setBackgroundResource(R.drawable.editbox_bg);
		editText.setBackgroundResource(android.R.drawable.edit_text);
		editText.setGravity(Gravity.TOP | Gravity.LEFT);
	    
		int maxLength = 150;
		InputFilter[] fArray = new InputFilter[1];
		fArray[0] = new InputFilter.LengthFilter(maxLength);
		editText.setFilters(fArray);
		
		setMargins(editText, 15, 15, 15, 15);
		
		/*if (question.getQuestionTypeHint() != null && !question.getQuestionTypeHint().equalsIgnoreCase("null")) {
			editText.setHint(question.getQuestionTypeHint());
		} else {*/
		editText.setHint("Comments");
		//}
		
		generatedQuestionViews.add(editText);
		linearLayout.addView(editText);
		
		return;
	}
	
	private void createRadioBtnQuestion(){
		
		RadioGroup group = new RadioGroup(FeedbackStatusActivity.this);
		group.setOrientation(RadioGroup.HORIZONTAL);
		
		group.setId(2001);
		
		setMargins(group, 35, 10, 35, 10);
		
		String options[] = {"Approve", "Reject"};
		
		if (options != null && options.length > 0) {
			for (String optionLabel : options) {
				RadioButton btn = new RadioButton(FeedbackStatusActivity.this);
				btn.setText(optionLabel);
				
				group.addView(btn);
			}
			generatedQuestionViews.add(group);
			linearLayout.addView(group);
		}
		
		// TODO UNCOMMENT BELOW CODE
		// createEditTextActionItem(question);
		
		return;
	}
	
	private void createSubmitBtn(){
		Button submitBtn = new Button(FeedbackStatusActivity.this);
		submitBtn.setText("Submit");
		
		setMargins(submitBtn, 25, 15, 25, 40);
		
		submitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				submitFeedback();
			}
		});
		
		linearLayout.addView(submitBtn);
	}
	
	private void submitFeedback() {
		
		// comments
		String comments = ((EditText)findViewById(2000)).getText().toString().replaceAll("#", " ").replaceAll("~", " ");

		// approval status
		RadioGroup radioGroup = (RadioGroup) findViewById(2001);
		int id = radioGroup.getCheckedRadioButtonId();
		View radioButton = radioGroup.findViewById(id);
		int radioId = radioGroup.indexOfChild(radioButton);
		RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
		
		String approvalStaus = ((String) btn.getText()).replaceAll("#", " ").replaceAll("~", " ");
		String feedbackId = actionItems.get(0).getFeedbackId();
		
		String userId = ((ApplicationGlobalVariable) getApplication()).getUserId();
		
		final String feedbackString = comments + "#" + approvalStaus + "#" + feedbackType + "#" + userId /*+ "#" + projectName */+ "#" + feedbackId + "#" + projectId;
			
			new AlertDialog.Builder(this)
			.setTitle("Conform")
			.setMessage("Are you sure you want to submit?")
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int whichButton) {
			    	/*Utils utils = new Utils();
			    	utils.submitFeedback(feedbackString);*/
			    	
			    	SubmitFeedback submitFeedback = new SubmitFeedback(feedbackString);
			    	submitFeedback.execute(new String[]{new String("")});
			    	
			    	//Toast.makeText(FetchQuestionsActivity.this, "Yes selected", Toast.LENGTH_SHORT).show();
			    }})
			 .setNegativeButton(android.R.string.no, null).show();
		// }
		
	}
	
	private class SubmitFeedback extends AsyncTask<String, Void, Boolean> {

		ProgressDialog progressDialog;
		String actionItemString = null;
		Boolean result = null;
		
		public SubmitFeedback(String feedbackString) {
			this.actionItemString = feedbackString;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			progressDialog = new ProgressDialog(FeedbackStatusActivity.this);
	        progressDialog.setMessage("Please wait...");
	        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progressDialog.setCancelable(false);
	        progressDialog.show();
			
		}

		@Override
		protected Boolean doInBackground(String... params) {
			
			Utils utils = new Utils();
			this.result = utils.submitActionItem(actionItemString);
			
			return result;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {

			Log.v("result", "" + this.result);
			
			if (this.result) {
				
				Intent slideactivity = new Intent(FeedbackStatusActivity.this, NoRecordsFoundActivity.class);
				slideactivity.putExtra("message", "Thanks you.");
				startActivity(slideactivity);
				finish();
				
			} else {
				Toast.makeText(getApplicationContext(), "Fail.", Toast.LENGTH_LONG).show();
			}
			
	        progressDialog.dismiss();
		}

	}

}

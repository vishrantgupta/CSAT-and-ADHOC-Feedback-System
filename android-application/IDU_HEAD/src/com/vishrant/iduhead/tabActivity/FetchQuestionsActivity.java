package com.vishrant.iduhead.tabActivity;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vishrant.iduhead.R;
import com.vishrant.iduhead.activity.NoRecordsFoundActivity;
import com.vishrant.iduhead.beans.ActionItems;
import com.vishrant.iduhead.beans.Question;
import com.vishrant.iduhead.constant.ApplicationGlobalVariable;
import com.vishrant.iduhead.utils.Utils;

public class FetchQuestionsActivity extends Activity {

	private ArrayList<ActionItems> actionItem = null;
	
	private View view = null;
	private LinearLayout linearLayout = null;
	private ScrollView scrollView = null;
	private String feedbackType = null;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat();
	
	private ArrayList<View> generatedQuestionViews = new ArrayList<View>();
	
	private String projectName = null;
	
	private String iduHeadId;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);

			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

			Bundle bundle = getIntent().getExtras();
			this.feedbackType = bundle.getString("selectedFeedback");
			this.projectName = bundle.getString("selectedProject");
			
			iduHeadId = ((ApplicationGlobalVariable) getApplication()).getUserId();
			
			linearLayout = new LinearLayout(this);
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
		    view = inflater.inflate(R.layout.activity_fetch_questions, null);

		    // Find the ScrollView
		    scrollView = (ScrollView) view.findViewById(R.id.scrollView1);

		    scrollView.setBackgroundColor(Color.rgb(221, 221, 221));

		    // Create a LinearLayout element
		    linearLayout.setOrientation(LinearLayout.VERTICAL);

		    LayoutParams layoutParamsLinearLayout = new ScrollView.LayoutParams(
		    		ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.MATCH_PARENT);
		    
		    layoutParamsLinearLayout.setMargins(20, 10, 20, 10);
		    linearLayout.setLayoutParams(layoutParamsLinearLayout);
		    
		    //FetchCsatQuestions fetchCsatQuestions = new FetchCsatQuestions(AppConstants.FEEDBACK_TYPE_CSAT);
		    FetchQuestions fetchQuestions = new FetchQuestions(this.feedbackType);
		    fetchQuestions.execute(new String[]{new String("")});
		    
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Some exception occurs.", Toast.LENGTH_LONG).show();
			finish();
			e.printStackTrace();
		}
		
	}
	
	private class FetchQuestions extends AsyncTask<String, Void, ArrayList<ActionItems>> {

		ProgressDialog progressDialog;
		String feedbackType = null;
		
		public FetchQuestions(String type) {
			this.feedbackType = type;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			actionItem = null;
			
			progressDialog = new ProgressDialog(FetchQuestionsActivity.this);
	        progressDialog.setMessage("Please wait...");
	        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progressDialog.setCancelable(false);
	        progressDialog.show();
			
		}

		@Override
		protected ArrayList<ActionItems> doInBackground(String... params) {
			Utils utils = new Utils();
			
			if (this.feedbackType.equalsIgnoreCase("CSAT")) {
				dateFormat.applyPattern("MMM");
			} else {
				dateFormat.applyPattern("yyyy");
			}
			
			String date = dateFormat.format(new Date());
			Log.v("Month>>>>>>", date);

			actionItem = utils.fetchActionItems(iduHeadId, projectName, feedbackType);
			
			//return utils.fetchQuestion(type, date);
			return actionItem;
		}
		
		@Override
		protected void onPostExecute(ArrayList<ActionItems> result) {

			dateFormat.applyPattern("MMM - yyyy");
			String monthYear = dateFormat.format(new Date());
			
			TextView csatMonth = new TextView(FetchQuestionsActivity.this);
			
			//csatMonth.setPadding(25, 20, 20, 15);
			csatMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			csatMonth.setTypeface(null, Typeface.BOLD);
			
			setMargins(csatMonth, 25, 20, 25, 15);
			
			csatMonth.setText(monthYear.toUpperCase());
			linearLayout.addView(csatMonth);
			
			// Adding elements
			if (actionItem != null && actionItem.size() > 0) {

				int count = 1;
				
				String previousQuestionCategory = "";
				
				for (ActionItems question : actionItem) {
					
					if (!previousQuestionCategory.equalsIgnoreCase(question.getQuestionCategory())) {
						createQuestionCategoryHeading(question);
						previousQuestionCategory = question.getQuestionCategory();
					}
					
				    createQuestionLabel(question, count);

				    createQuestionFeedbackTxt(question);
				    
				    createActionItemTxt(question);
				    
				    // createEditTextActionItem(question);
				    
				    // createCloseCheckbox(question);
				    
				    // DisplayType type = new DisplayType(question.getQuestionDisplayType());

				    /*switch (type.getDisplayType() != null ? type.getDisplayType() : 0) {
						case 1:
							//TEXT BOX
							createEditTextQuestion(question);
							break;
						case 2:
							//RADIO BUTTON
							createRadioBtnQuestion(question);
							break;
						case 3:
							//SELECT BOX
							createSelectBoxQuestion(question);
							break;
						case 4:
							//RAITING
							createRatingQuestion(question);
							break;
						default:
							Toast.makeText(getApplicationContext(), "Invalid data, please contact system admin.", Toast.LENGTH_LONG).show();
							finish();
							return;
					}*/

					count++;
				}
				
				createEditTextIduHeadComment();

				createRadioBtnQuestion();
				
				createSubmitBtn();
				
				scrollView.addView(linearLayout);
			    setContentView(view);
			} else {
				Toast.makeText(getApplicationContext(), "Questions are not set, please try after some time or contact system admin.", Toast.LENGTH_LONG).show();
			}
			
	        progressDialog.dismiss();
			
		}

	}
	
	private void createQuestionCategoryHeading(ActionItems question) {
		
		TextView questionCategoryHeading = new TextView(FetchQuestionsActivity.this);
		
		//questionLabel.setPadding(20, 10, 20, 10);
		questionCategoryHeading.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		questionCategoryHeading.setTypeface(null, Typeface.BOLD);
		
		questionCategoryHeading.setText(question.getQuestionCategory());
		setMargins(questionCategoryHeading, 25, 15, 25, 15);
		
	    linearLayout.addView(questionCategoryHeading);
		
	}
	
	private void createQuestionLabel(ActionItems question, int count) {
		
		TextView questionLabel = new TextView(FetchQuestionsActivity.this);
		
		//questionLabel.setPadding(20, 10, 20, 10);
		questionLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		questionLabel.setTypeface(null, Typeface.BOLD);
		
		questionLabel.setText(count + ". " + question.getQuestionTxt());
		
		setMargins(questionLabel, 25, 15, 25, 15);
		
	    linearLayout.addView(questionLabel);
		
	}
	
	private void createQuestionFeedbackTxt(ActionItems question) {
		
		TextView questionLabel = new TextView(FetchQuestionsActivity.this);
		
		//questionLabel.setPadding(20, 10, 20, 10);
		questionLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		//questionLabel.setTypeface(null, Typeface.BOLD);
		questionLabel.setText("FEEDBACK:\n" + question.getFeedbackTxt());
		
		setMargins(questionLabel, 25, 5, 25, 15);
		
	    linearLayout.addView(questionLabel);
		
	}
	
	private void createActionItemTxt(ActionItems question) {
		
		TextView questionLabel = new TextView(FetchQuestionsActivity.this);
		
		//questionLabel.setPadding(20, 10, 20, 10);
		questionLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		//questionLabel.setTypeface(null, Typeface.BOLD);
		questionLabel.setText("ACTION ITEM: " + question.getActionItemMgrTxt());
		
		setMargins(questionLabel, 25, 5, 25, 15);
		
	    linearLayout.addView(questionLabel);
		
	}
	
	private void createCloseCheckbox(ActionItems question) {

		CheckBox closeCheckBox = new CheckBox(FetchQuestionsActivity.this);
		setMargins(closeCheckBox, 10, 0, 10, 0);
		
		Integer closeCheckBoxId = question.getQuestionId() + 1000;
		closeCheckBox.setId(closeCheckBoxId);
		
		closeCheckBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		closeCheckBox.setText("Close");
		
		linearLayout.addView(closeCheckBox);
	}
	
	private void createEditTextActionItem(ActionItems question) {

		EditText editText = new EditText(FetchQuestionsActivity.this);
		
		Integer editTxtId = question.getQuestionId();
		 
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
		
		setMargins(editText, 15, 0, 15, 0);
		
		/*if (question.getQuestionTypeHint() != null && !question.getQuestionTypeHint().equalsIgnoreCase("null")) {
			editText.setHint(question.getQuestionTypeHint());
		} else {*/
		editText.setHint("Action Item");
		//}
		
		generatedQuestionViews.add(editText);
		linearLayout.addView(editText);
		
		return;
	}
	
	private void createEditTextIduHeadComment() {

		EditText editText = new EditText(FetchQuestionsActivity.this);
		
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
		
		RadioGroup group = new RadioGroup(FetchQuestionsActivity.this);
		group.setOrientation(RadioGroup.HORIZONTAL);
		
		group.setId(2001);
		
		setMargins(group, 35, 10, 35, 10);
		
		String options[] = {"Approve", "Reject"};
		
		if (options != null && options.length > 0) {
			for (String optionLabel : options) {
				RadioButton btn = new RadioButton(FetchQuestionsActivity.this);
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
	
	private void createSelectBoxQuestion(Question question) {
		
		String options[] = question.getQuestionTypeOptions().split("#");
		ArrayList<String> spinnerArray = null; 
		
		if (options != null && options.length > 0) {
			spinnerArray = new ArrayList<String>();
			
			for (String optionLabel : options) {
				spinnerArray.add(optionLabel);
			}
			
			Spinner spinner = new Spinner(FetchQuestionsActivity.this);
			spinner.setId(question.getQuestionId());
			
		    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
		    spinner.setAdapter(spinnerArrayAdapter);
		    
		    setMargins(spinner, 45, 0, 45, 10);
		    
		    generatedQuestionViews.add(spinner);
		    linearLayout.addView(spinner);
		    
			// TODO UNCOMMENT BELOW CODE
		    // createEditTextActionItem(question);
		}

		return;
	}
	
	private void createRatingQuestion(Question question) {

		RatingBar ratingBar = new RatingBar(FetchQuestionsActivity.this);
		ratingBar.setId(question.getQuestionId());
		
		ratingBar.setNumStars(question.getRatingMaxValue() != null ? question.getRatingMaxValue() : 5); // taken default as 5
        ratingBar.setStepSize((float) 0.5);
        
		setMargins(ratingBar, 35, 0, 35, 10);
		
		generatedQuestionViews.add(ratingBar);
		linearLayout.addView(ratingBar);
		
		// TODO UNCOMMENT BELOW CODE
		// createEditTextActionItem(question);
		return;
				
	}

	private void createSubmitBtn(){
		Button submitBtn = new Button(FetchQuestionsActivity.this);
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
		String feedbackId = actionItem.get(0).getFeedbackId();
		
		final String feedbackString = comments + "#" + approvalStaus + "#" + feedbackType + "#" + iduHeadId + "#" + projectName + "#" + feedbackId;
			
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
			
			progressDialog = new ProgressDialog(FetchQuestionsActivity.this);
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
				
				Intent slideactivity = new Intent(FetchQuestionsActivity.this, NoRecordsFoundActivity.class);
				slideactivity.putExtra("message", "Thanks you.");
				startActivity(slideactivity);
				finish();
				
			} else {
				Toast.makeText(getApplicationContext(), "Fail.", Toast.LENGTH_LONG).show();
			}
			
	        progressDialog.dismiss();
		}

	}
	
	
	@Override
	protected void onResume() {
	
		Log.v(">>>>>>>>>", "Csat resumed");
		
		super.onResume();
	}
	
}
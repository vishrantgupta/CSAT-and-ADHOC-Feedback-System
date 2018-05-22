package com.vishrant.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.vishrant.bean.Question;
import com.vishrant.service.FetchQuestionsService;

public class FetchQuestionsBO {

	public String fetchQuestions(String questionType, String date) {

		try {
			FetchQuestionsService questionsService = new FetchQuestionsService();
			ArrayList<Question> questions = questionsService.fetchQuestions(
					questionType, date);

			StringBuffer sb = new StringBuffer();

			SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
			
			for (Question question : questions) {
				sb.append(question.getQuestionId() + ";");
				sb.append(question.getQuestionTxt() + ";");
				sb.append(question.getQuestionTypeOptions() + ";");
				sb.append(question.getQuestionTypeHint() + ";");
				sb.append(question.getQuestionType() + ";");
				sb.append(format.format(question.getMonth()) + ";");
				sb.append(question.getQuestionDisplayType() + ";");
				sb.append(question.getRatingMaxValue() + ";");
				sb.append(question.getQuestionCategory() + ";");
				sb.append(question.getQuestionDisplayOrder() + ";~");
			}

			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}

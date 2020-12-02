package com.sss.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sss.repository.MonthlyRepository;

@Service
public class MonthlyService {
	
	@Autowired
	MonthlyRepository repository;

	public int[][] createMonthArray(String str) {
		
		Calendar rightNow = createSpecifiedMonth(str);
		Integer year = rightNow.get(Calendar.YEAR);
		Integer month = rightNow.get(Calendar.MONTH);
		
		int startWeek = rightNow.get(Calendar.DAY_OF_WEEK);
		/* 今月末日 */
		rightNow.set(year, month + 1, 0);
		int thisMonthlastDay = rightNow.get(Calendar.DATE);

		int[][] monthArray = new int[6][7];

		// 今月分を初期化
		for (int i = 0, d = 1; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				// 1日は曜日をずらす
				if (d == 1) {
					j = startWeek - 1;
				}
				if (d <= thisMonthlastDay) {
					monthArray[i][j] = d;
					d++;
				}
			}
		}

		/* 先月末日 */
		rightNow.set(year, month, 0);
		int beforeMonthlastDay = rightNow.get(Calendar.DATE);

		// 先月分を初期化
		for (int i = startWeek - 2, count = 0; i >= 0; i--, count++) {
			monthArray[0][i] = beforeMonthlastDay - count;
		}

		// 来月分を初期化
		for (int i = 4; i < 6; i++) {
			for (int j = 0, count = 1; j < 7; j++) {
				// 今月末日にあたるまではスルー
				if (monthArray[i][j] <= thisMonthlastDay && monthArray[i][j] != 0) {
					continue;
				}
				if (j == 0) break;
				rightNow.set(year, month, thisMonthlastDay + count);
				monthArray[i][j] = rightNow.get(Calendar.DATE);
				count++;
			}
		}
		return monthArray;
	}

	public String[][] createMonthStringArray(String str) {
		
		Calendar rightNow = createSpecifiedMonth(str);
		Integer year = rightNow.get(Calendar.YEAR);
		Integer month = rightNow.get(Calendar.MONTH);
		
		Integer startWeek = rightNow.get(Calendar.DAY_OF_WEEK);
		/* 今月末日 */
		rightNow.set(year, month + 1, 0);
		Integer thisMonthlastDay = rightNow.get(Calendar.DATE);

		String[][] monthArray = new String[6][7];

		// 今月分を初期化
		Integer d = 1;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				// 1日は曜日をずらす
				if (d == 1) {
					j = startWeek - 1;
				}
				if (d <= thisMonthlastDay) {
					monthArray[i][j] = year.toString() + "-" + (month + 1) + "-" + d.toString();
					d++;
				}
			}
		}

		/* 先月末日 */
		rightNow.set(year, month, 0);
		Integer beforeMonthlastDay = rightNow.get(Calendar.DATE);
		Integer count = 0;

		// 先月分を初期化
		for (int i = startWeek - 2; i >= 0; i--, count++) {
			monthArray[0][i] = (Integer) rightNow.get(Calendar.YEAR) + "-"
					+ (Integer) (rightNow.get(Calendar.MONTH) + 1) + "-" + (beforeMonthlastDay - count);
		}

		// 来月分を初期化
		count = 1;
		for (int i = 4; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				// 今月末日にあたるまではスルー
				if (monthArray[i][j] != null) {
					continue;
				}
				if (j == 0)
					break;
				rightNow.set(year, month, thisMonthlastDay + count);
				monthArray[i][j] = (Integer) rightNow.get(Calendar.YEAR) + "-"
						+ (Integer) (rightNow.get(Calendar.MONTH) + 1) + "-" + rightNow.get(Calendar.DATE);
				count++;
			}
		}
		return monthArray;
	}

	/**
	 * YYYY-MM形式の文字列からカレンダー作成に必要なCalendar型の変数を生成する
	 * 
	 * @param str
	 * @return
	 */
	public Calendar createSpecifiedMonth(String str) {
		
		Calendar rightNow = Calendar.getInstance();
		if(str.equals("")) {
			Integer year = rightNow.get(Calendar.YEAR);
			Integer month = rightNow.get(Calendar.MONTH);
			rightNow.set(year, month, 1);
			return rightNow;
		}
		
		String[] monthAndDay = str.split("-");

		Integer year = Integer.valueOf(monthAndDay[0]);
		Integer month = Integer.valueOf(monthAndDay[1]) - 1;
		rightNow.set(year, month, 1);
		
		return rightNow;
	}
	
	public String initialMonth(String[][] monthArray) {
		
		String[] splitMonth = monthArray[1][0].split("-");
		
		return splitMonth[0] + "-" + splitMonth[1];
	}
	
	public String[][] createDailyTotal(String[][] monthStringArray,String userId) throws ParseException{
		
		String[][] dailyTotal = new String[6][7];
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i = 0;i < 6;i++) {
			for(int j = 0;j < 7;j++) {
				if(monthStringArray[i][j] != null) {
					//1日の合計金額を計算
					Date date = format.parse(monthStringArray[i][j]);
					int total = repository.getDailyTotal(date,userId);
					//1日の合計金額を変換して格納
					dailyTotal[i][j] = "¥" + String.format("%,d", total);
				}
			}
		}
		
		return dailyTotal;
	}
}

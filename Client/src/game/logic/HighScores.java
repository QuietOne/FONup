package game.logic;

import domain.Category;
import domain.Result;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for storing all types of high scores for specific client.
 *
 * @author Tihomir Radosavljević
 * @version 1.1
 */
public final class HighScores {

    private Result yearlyHighScore;
    private Result monthlyHighScore;
    private Result todayHighScore;
    private Result allTimeHighScore;
    private List<Result> categoryHighScore;
    private List<Result> resultsList;
    private List<Result> otherBestHighScores;

    public HighScores(List<Result> resultsList) {
        this.resultsList = resultsList;
        calculateCategoryHighScore();
        calculateAllTimeHighScore();
        calculateMonthlyHighScore();
        calculateTodayHighScore();
        calculateYearlyHighScore();
        updateOtherBestHighScores();
    }

    public List<Result> getResultsList() {
        return resultsList;
    }

    public Result getYearlyHighScore() {
        return yearlyHighScore;
    }

    public Result getMonthlyHighScore() {
        return monthlyHighScore;
    }

    public Result getTodayHighScore() {
        return todayHighScore;
    }

    public List<Result> getCategoryHighScore() {
        return categoryHighScore;
    }

    public Result getAllTimeHighScore() {
        return allTimeHighScore;
    }

    private void calculateTodayHighScore() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMyyyy");
        List<Result> list = new LinkedList<>();
        Date currentDate = new Date();
        String currentDateString = sdf.format(currentDate);
        for (Result result : resultsList) {
            String comparable = sdf.format(result.getDate());
            if (currentDateString.equals(comparable)) {
                list.add(result);
            }
        }
        todayHighScore = max(list);
    }

    private void calculateMonthlyHighScore() {
        SimpleDateFormat sdf = new SimpleDateFormat("Myyyy");
        List<Result> list = new LinkedList<>();
        Date currentDate = new Date();
        String currentDateString = sdf.format(currentDate);
        for (Result result : resultsList) {
            String comparable = sdf.format(result.getDate());
            if (currentDateString.equals(comparable)) {
                list.add(result);
            }
        }
        monthlyHighScore = max(list);
    }

    private void calculateYearlyHighScore() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        List<Result> list = new LinkedList<>();
        Date currentDate = new Date();
        String currentDateString = sdf.format(currentDate);
        for (Result result : resultsList) {
            String comparable = sdf.format(result.getDate());
            if (currentDateString.equals(comparable)) {
                list.add(result);
            }
        }
        yearlyHighScore = max(list);
    }

    private void calculateAllTimeHighScore() {
        allTimeHighScore = max(resultsList);
    }

    private void calculateCategoryHighScore() {
        categoryHighScore = new ArrayList<>();
        List<Category> categoryList = new ArrayList<>();
        for (Result result : resultsList) {
            if (!categoryList.contains(result.getTest().getCategory())) {
                categoryList.add(result.getTest().getCategory());
            }
        }
        List<Result> tempList;
        for (Category category : categoryList) {
            tempList = new LinkedList<>();
            for (Result result : resultsList) {
                if (result.getTest().getCategory().equals(category)) {
                    tempList.add(result);
                }
            }
            categoryHighScore.add(max(tempList));
        }
    }

    private Result max(List<Result> results) {
        if (results == null || results.isEmpty()) {
            return null;
        }
        Result max = results.get(0);
        for (int i = 1; i < results.size(); i++) {
            if (results.get(i).getValue() > max.getValue()) {
                max = results.get(i);
            }
        }
        return max;
    }

    /**
     * Adding result for this client.
     *
     * @param result that has been made
     */
    public void addResult(Result result) {
        Controller.getInstance().addResult(result);
        resultsList.add(result);
        //calculating high scores
        if (todayHighScore.getValue() < result.getValue()) {
            todayHighScore = result;
        }
        if (allTimeHighScore.getValue() < result.getValue()) {
            allTimeHighScore = result;
        }
        if (monthlyHighScore.getValue() < result.getValue()) {
            monthlyHighScore = result;
        }

        for (int i = 0; i < categoryHighScore.size(); i++) {
            if (categoryHighScore.get(i).getTest().getCategory().equals(result.getTest().getCategory())) {
                if (categoryHighScore.get(i).getValue() < result.getValue()) {
                    categoryHighScore.remove(i);
                    categoryHighScore.add(result);
                }
                return;
            }
        }
        categoryHighScore.add(result);
    }

    /**
     * Method for updating best high scores of all players.
     */
    public void updateOtherBestHighScores() {
        otherBestHighScores = Controller.getInstance().loadTopHighScores();
    }

    public List<Result> getOtherBestHighScores() {
        return otherBestHighScores;
    }

}

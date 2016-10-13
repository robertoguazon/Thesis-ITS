import com.westlyf.database.*;

import com.westlyf.domain.exercise.Exercise;
import com.westlyf.domain.exercise.practical.*;

import com.westlyf.domain.exercise.practical.DataType;
import com.westlyf.domain.exercise.practical.PracticalPrintExercise;
import com.westlyf.domain.exercise.practical.PracticalReturnExercise;
import com.westlyf.domain.exercise.practical.PracticalReturnValidator;
import com.westlyf.domain.exercise.quiz.Exam;
import com.westlyf.domain.exercise.quiz.QuizExercise;
import com.westlyf.domain.exercise.quiz.QuizType;
import com.westlyf.domain.lesson.TextLesson;
import com.westlyf.domain.lesson.VideoLesson;
import com.westlyf.user.Users;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by robertoguazon on 24/07/2016.
 */
public class DatabaseMain {

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        ExerciseDatabase.createQuizExerciseTable();
        ExerciseDatabase.createPracticalExerciseTable();
        ExerciseDatabase.createVideoPracticalExerciseTable();
        LessonDatabase.createTextLessonTable();
        LessonDatabase.createVideoLessonTable();
        ExamDatabase.createExamTable();
        UserDatabase.createUsersTable();
        UserDatabase.createUserExercisesTable();
        UserDatabase.createExamGradesTable();

        Connection user = DatabaseConnection.getUserConnection();
        Connection lesson = DatabaseConnection.getLessonConn();
        Connection exercise = DatabaseConnection.getExerciseConn();
        Connection exam = DatabaseConnection.getExamConn();

        System.out.println("userDB: " + user);
        System.out.println("lessonDB: " + lesson);
        System.out.println("exerciseDB: " + exercise);
        System.out.println("exam: " + exercise);


        testExamGrades();

        //testUser();
        //testPush();
        //testPull();
        testCGroup();

        try {
            user.close();
            lesson.close();
            exercise.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void testCGroup() {
        PracticalExercise pe = ExerciseDatabase.getPracticalExerciseUsingLID("lid1513096105856044");
        System.out.println(pe.getCGroup());
    }

    private static void testExamGrades(){
        int userId = 1;
        String exam_title = "module3";
        int grade = 85;
        UserDatabase.addExamGrade(userId, exam_title, grade);
    }

    private static void testUser() {
        String currentModule = "module1";
        String currentLesson = "lesson1";
        String currentExam = null;
        String username = "user";
        String password = "pass123";
        String name = "Hello World";
        String school = "UST";
        int age = 18;
        String sex = "male";
        String yearLevel = "4th year";
        String profilePicturePath = null;
        UserDatabase.addUser(
                encapsulateUser(currentModule, currentLesson, currentExam,
                        username, password, name, age, sex, school, yearLevel, profilePicturePath));
    }

    private static void testPush() {
        ArrayList<StringProperty> tagList = new ArrayList<>();

        TextLesson textLesson = new TextLesson();
        textLesson.setTitle("sample test");
        textLesson.setText("This is a sample test. keep in touch with the program so it is easy to find the errors");
        tagList.add(new SimpleStringProperty("sample"));
        tagList.add(new SimpleStringProperty("test"));
        textLesson.setTags(tagList);
        textLesson.makeID();
        LessonDatabase.storeData(textLesson);

        textLesson.removeTags();
        textLesson.setTitle("sjfl");
        textLesson.setText("a simple master and slave text");
        textLesson.addTag("master");
        textLesson.addTag("sample");
        textLesson.addTag("slave");
        textLesson.makeID();
        LessonDatabase.storeData(textLesson);

        textLesson.removeTags();
        textLesson.setTitle("1231371");
        textLesson.setText("numbers are crazy");
        textLesson.addTag("master");
        textLesson.addTag("sample");
        textLesson.addTag("test");
        textLesson.makeID();
        LessonDatabase.storeData(textLesson);

        tagList.clear();

        VideoLesson videoLesson = new VideoLesson();
        videoLesson.setTitle("sample test");
        videoLesson.setPathLocation("C:\\Users\\whatever");
        tagList.add(new SimpleStringProperty("video"));
        tagList.add(new SimpleStringProperty("sample"));
        videoLesson.setTags(tagList);
        videoLesson.makeID();
        LessonDatabase.storeData(videoLesson);

        videoLesson.removeTags();
        videoLesson.setTitle("129367912379127398");
        videoLesson.setPathLocation("123:\\sasdasdasders\\whatever");
        videoLesson.addTag("sample");
        videoLesson.addTag("test");
        videoLesson.makeID();
        LessonDatabase.storeData(videoLesson);

        videoLesson.removeTags();
        videoLesson.setTitle("fkjashdkfjhskfhiwue");
        videoLesson.setPathLocation("to:\\thepath\\of nowhere");
        videoLesson.addTag("sample");
        videoLesson.addTag("quiz");
        videoLesson.makeID();
        LessonDatabase.storeData(videoLesson);

        QuizExercise quizExercise = new QuizExercise();
        quizExercise.setTitle("sample test");
        quizExercise.addTag("sample");
        quizExercise.addTag("exercise");
        quizExercise.addTag("quiz");
        ArrayList<String> choices = new ArrayList<>();
        choices.add("game");
        choices.add("sample test");
        choices.add("master");
        ArrayList<String> validAnswers = new ArrayList();
        validAnswers.add("sample test");
        quizExercise.addItem(QuizType.RADIOBUTTON, "what is this?", choices, validAnswers);
        quizExercise.makeID();
        ExerciseDatabase.storeData(quizExercise);

        quizExercise.removeTags();
        quizExercise.setTitle("aaaaaaaa");
        quizExercise.addTag("sample");
        quizExercise.addTag("test");
        quizExercise.addTag("sweet");
        quizExercise.makeID();
        ExerciseDatabase.storeData(quizExercise);

        quizExercise.removeTags();
        quizExercise.setTitle("s11111111111");
        quizExercise.addTag("sample");
        quizExercise.addTag("12");
        quizExercise.addTag("101");
        quizExercise.makeID();
        ExerciseDatabase.storeData(quizExercise);

        PracticalPrintExercise practicalPrintExercise = new PracticalPrintExercise();
        practicalPrintExercise.setTitle("sample practical print exercise");
        practicalPrintExercise.addTag("sample");
        practicalPrintExercise.addTag("practical");
        practicalPrintExercise.addTag("exercise");

        practicalPrintExercise.setInstructions("just do the following: run the code to see the output of the sample.");
        practicalPrintExercise.setCode("public class Sample {\n" +
                "   public static void main(String[] args) {\n" +
                "       System.out.println(\"Hello World\");\n" +
                "   }\n" +
                "}");
        practicalPrintExercise.setClassName("Sample");
        practicalPrintExercise.setMethodName("main");

        practicalPrintExercise.setPrintValidator("Hello World");
        practicalPrintExercise.setMustMatch(false);
        practicalPrintExercise.makeID();
        ExerciseDatabase.storeData(practicalPrintExercise);


        //practical return exercise push
        PracticalReturnExercise practicalReturnExercise = new PracticalReturnExercise();
        practicalReturnExercise.setTitle("sample practical exercise");
        practicalReturnExercise.addTag("sample");
        practicalReturnExercise.addTag("exercise");
        practicalReturnExercise.addTag("practical");

        practicalReturnExercise.setInstructions("make a method that accepts a string variable as parameter and returns the" +
                "last character");
        practicalReturnExercise.setClassName("Sample");
        practicalReturnExercise.setMethodName("run");

        practicalReturnExercise.setCode(
                "public class Sample {\n" +
                        "   public char run(String value) {\n" +
                        "       return value.charAt(value.length() - 1);\n" +
                        "   }\n" +
                        "}"
        );

        practicalReturnExercise.addParameterType(DataType.STRING);
        practicalReturnExercise.setReturnType(DataType.CHAR);

        PracticalReturnValidator p1 = new PracticalReturnValidator();
        p1.addInput("master",DataType.STRING);
        p1.setExpectedReturn("r");

        PracticalReturnValidator p2 = new PracticalReturnValidator();
        p2.addInput("books1",DataType.STRING);
        p2.setExpectedReturn("1");

        practicalReturnExercise.addReturnValidator(p1);
        practicalReturnExercise.addReturnValidator(p2);
        practicalReturnExercise.makeID();
        ExerciseDatabase.storeData(practicalReturnExercise);

        Exam exam = new Exam();
        exam.setTitle("sample test exam");
        exam.addTag("sample");
        exam.addTag("exercise");
        exam.addTag("exam");
        ArrayList<String> choices2 = new ArrayList<>();
        choices2.add("game 123");
        choices2.add("sample test 123");
        choices2.add("master 123");
        ArrayList<String> validAnswers2 = new ArrayList();
        validAnswers2.add("sample test");
        exam.addItem(QuizType.RADIOBUTTON, "what is this?", choices2, validAnswers2);
        exam.makeID();
        ExamDatabase.storeData(exam);
    }

    private static void testPull() {
        System.out.println();
        System.out.println("trying to load sample text lesson using lid...");
        System.out.println(LessonDatabase.getTextLessonUsingLID("lid627925056887413"));

        System.out.println();
        System.out.println("trying to load sample video lesson using lid...");
        System.out.println(LessonDatabase.getVideoLessonUsingLID("lid627925277121613"));

        System.out.println();
        System.out.println("trying to load sample text lesson using title...");
        System.out.println(LessonDatabase.getTextLessonUsingTitle("sample test"));

        System.out.println();
        System.out.println("trying to load sample video lesson using title...");
        System.out.println(LessonDatabase.getVideoLessonUsingTitle("sample test"));

        System.out.println();
        System.out.println("trying to load sample quiz exercise using lid...");
        System.out.println(ExerciseDatabase.getQuizExerciseUsingLID("lid632146417557684"));

        System.out.println();
        System.out.println("trying to load sample quiz exercise using title...");
        System.out.println(ExerciseDatabase.getQuizExerciseUsingTitle("sample test"));

        System.out.println();
        System.out.println("trying to load sample quiz exercise using tags exactly...");
        System.out.println(ExerciseDatabase.getQuizExercisesUsingTagsExactly("money,play,").get(0));

        System.out.println();
        System.out.println("trying to load sample text lessons using tags exactly...");
        System.out.println(LessonDatabase.getTextLessonsUsingTagsExactly("sample,test,").get(0));

        System.out.println();
        System.out.println("trying to load sample video lessons using tags exactly...");
        System.out.println(LessonDatabase.getVideoLessonsUsingTagsExactly("sample,video,").get(0));

        System.out.println();
        System.out.println("trying to load sample text lessons using tags contains...");
        System.out.println(LessonDatabase.getTextLessonsUsingTagsContains("sample", "test"));

        System.out.println();
        System.out.println("trying to load sample video lessons using tags contains...");
        System.out.println(LessonDatabase.getVideoLessonsUsingTagsContains("sample", "quiz"));

        System.out.println();
        System.out.println("trying to load sample quiz exercises using tags contains...");
        System.out.println(ExerciseDatabase.getQuizExercisesUsingTagsContains("sample"));

        System.out.println();
        System.out.println("trying to load sample practical exercise using lid...");
        System.out.println(ExerciseDatabase.getPracticalExerciseUsingLID("lid2110480049967968"));

        System.out.println();
        System.out.println("trying to load sample practical exercise using title...");
        System.out.println(ExerciseDatabase.getPracticalExerciseUsingTitle("sample practical print exercise"));

        System.out.println();
        System.out.println("trying to load sample practical exercise using tags exactly...");
        System.out.println(ExerciseDatabase.getPracticalExercisesUsingTagsExactly("exercise,practical,sample,").get(0));

        System.out.println();
        System.out.println("trying to load sample practical exercises using tags contains...");
        System.out.println(ExerciseDatabase.getQuizExercisesUsingTagsContains("exercise"));

        System.out.println();
        System.out.println("trying to load sample exam using lid...");
        System.out.println(ExamDatabase.getExamUsingLID("lid295074264552203"));

        System.out.println();
        System.out.println("trying to load sample exam using title...");
        System.out.println(ExamDatabase.getExamUsingTitle("sample test exam"));

        System.out.println();
        System.out.println("trying to load sample exam using tags exactly...");
        System.out.println(ExamDatabase.getExamsUsingTagsExactly("exam,exercise,sample,"));

        System.out.println();
        System.out.println("trying to load sample exam using tags contains...");
        System.out.println(ExamDatabase.getExamsUsingTagsContains("exam"));

        System.out.println(ExerciseDatabase.getQuizExerciseUsingLID("lid494855960641737"));

        System.out.println();
        System.out.println("trying to load sample video practical using lid...");
        System.out.println(ExerciseDatabase.getVideoPracticalExerciseUsingLID("lid49075321933783"));

        System.out.println();
        System.out.println("trying to load sample video practical using title...");
        System.out.println(ExerciseDatabase.getVideoPracticalExerciseUsingTitle("Sample Video Practical"));

        System.out.println();
        System.out.println("trying to load sample video practical using tags exactly...");
        System.out.println(ExerciseDatabase.getVideoPracticalExercisesUsingTagsExactly("practical,video,"));

        System.out.println();
        System.out.println("trying to load sample video practical using tags contains...");
        System.out.println(ExerciseDatabase.getVideoPracticalExercisesUsingTagsContains("practical"));


    }

    public static Users encapsulateUser(String currentModuleId, String currentLessonId, String currentExamId,
                                 String username, String password, String name, int age, String sex,
                                 String school, String yearLevel, String profilePicturePath){
        Users user = new Users();
        user.setCurrentModuleId(currentModuleId);
        user.setCurrentLessonId(currentLessonId);
        user.setCurrentExamId(currentExamId);
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setAge(age);
        user.setSex(sex);
        user.setSchool(school);
        user.setYearLevel(yearLevel);
        user.setProfilePicturePath(profilePicturePath);
        return user;
    }
}

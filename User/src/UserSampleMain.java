import com.westlyf.domain.lesson.Level;
import com.westlyf.domain.lesson.TextLesson;
import com.westlyf.user.User;

/**
 * Created by robertoguazon on 09/09/2016.
 */
public class UserSampleMain {

    public static void main(String[] args) {

        test1(); //TODO -delete
    }

    private static void test1() {
        Level root  = new Level();
        root.addTag("root");

        Level _1_1 = new Level();
        _1_1.addTag("Strings");
        _1_1.setTitle("Strings and things");
        _1_1.setLevel("1.1");
        Level _1_2 = new Level();
        _1_2.addTag("Variancy of variables");
        _1_2.setLevel("1.2");

        root.addSubLevel(_1_1);
        root.addSubLevel(_1_2);

        TextLesson lessonStrings = new TextLesson();
        lessonStrings.addTag("Strings");
        lessonStrings.setID("lid911231231890");
        lessonStrings.setTitle("Strings tutorial");
        lessonStrings.setText("String string = \"Master\"");

        TextLesson lessonVariables = new TextLesson();
        lessonVariables.addTag("variables");
        lessonVariables.setID("lid123123125464");
        lessonVariables.setTitle("Variables tutorial");
        lessonVariables.setText("int is short for Integers");

        User sampleUser = new User();
        sampleUser.addHistory(_1_1, lessonStrings, 0,0);
        sampleUser.addHistory(_1_2, lessonVariables, 0,0);

        System.out.println(sampleUser);
    }
}

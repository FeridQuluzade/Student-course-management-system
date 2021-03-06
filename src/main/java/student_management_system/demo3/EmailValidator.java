package student_management_system.demo3;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;
import java.util.regex.Pattern;
@Component
public class EmailValidator implements Predicate<String> {
    private static  final Predicate<String> IS_EMAIL_VALID=
            Pattern.compile(
                    "^[A-Za-z0-9+_.-]+@(.+)$",
                    Pattern.CASE_INSENSITIVE
            ).asPredicate();

    @Override
    public boolean test(String s) {
        return false;
    }
}

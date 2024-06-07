package auth.dtos.utils;

public class AuthRequestUtil {
    // Проверка формата электронной почты
    public final static String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    // Пароль должен содержать по крайней мере одну цифру, одну строчную и одну заглавную букву
    public final static String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
}

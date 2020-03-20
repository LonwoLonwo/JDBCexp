import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/skillbox"; //можно не указывать в этой строке название БД, а использовать команду use, но обычно в одном приложении
        //одна БД, поэтому её сразу указывают в адресе
        String user = "root";//в MySQL можно самостоятельно задавать пользователей с разными настройками доступа к БД
        String pass = "root02"; //not my password
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement()) {

            statement.execute("UPDATE Courses SET name='Веб-разработчик с нуля до PRO' WHERE id = 1");//просто изменяем строку без вывода информации в консоль
            //метод выше возвращает булеан, поэтому удобно использовать его для обновления информации, например, а не для вывода информации

            //ResultSet resultSet = statement.executeQuery("SELECT * FROM Courses");
            ResultSet resultSet = statement.executeQuery("SELECT Courses.name, MONTHNAME(Subscriptions.subscription_date), COUNT(Subscriptions.subscription_date)" +
                    " FROM Courses JOIN Subscriptions ON Courses.id = Subscriptions.course_id GROUP BY Courses.name, MONTH(Subscriptions.subscription_date) " +
                    "ORDER BY Courses.name, Subscriptions.subscription_date");
            //любой SQL запрос возвращает таблицу из столбцов и строк, ResultSet как раз идёт по строчкам, а из строчек можно получать данные
            System.out.println("Название курса" + "\t" + "Месяц" + "\t" + "Количество покупок за месяц");
            while(resultSet.next()) {
                String courseName = resultSet.getString("name");//получаем название курса, указывая название колонки
                String monthDate = resultSet.getString(2);
                String subscriptionsCount = resultSet.getString(3);
                System.out.println(courseName + " " + monthDate + " " + subscriptionsCount);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

# MyBank — лабораторні роботи Java

Проєкт Banking для лабораторних робіт №1–6. Модуль `bank` завантажує демонстраційні дані з `test.dat`, а модуль `interfaces` містить шість варіантів інтерфейсу.

- [Короткі звіти до лабораторних робіт №1–6](LABS.md)

## Як запустити

Потрібні JDK 17 або 21 і Maven 3.9. Для першої збірки Maven має завантажити залежності. Команди нижче виконуються з теки, у якій лежить цей `README.md`.

```powershell
mvn -pl interfaces -am install
```

| № | Технологія | Команда запуску |
|---|---|---|
| 1 | Jexer | `mvn -f interfaces/pom.xml "-Dexec.mainClass=com.mybank.tui.TUIdemo" exec:java` |
| 2 | JLine 3 | `mvn -f interfaces/pom.xml "-Dexec.mainClass=com.mybank.tui.CLIdemo" exec:java` |
| 3 | Swing | `mvn -f interfaces/pom.xml "-Dexec.mainClass=com.mybank.gui.SWINGdemo" exec:java` |
| 4 | Matisse | `mvn -f interfaces/pom.xml "-Dexec.mainClass=com.mybank.gui.MatisseDemo" exec:java` |
| 5 | JavaFX | `mvn -f interfaces/pom.xml javafx:run` |
| 6 | Monet / Scene Builder | `mvn -f interfaces/pom.xml "-Djavafx.mainClass=com.mybank.gui.MonetDemo" javafx:run` |

У роботі №4 файл `MatisseDemo.form` розташовано поруч із `MatisseDemo.java`. У NetBeans відкрийте модуль `interfaces` і виберіть вкладку Design для цієї форми. Макет для №6 міститься у `interfaces/src/main/resources/com/mybank/gui/MonetDemo.fxml` і відкривається в JavaFX Scene Builder.

У роботі №2 введіть `help`, `customers`, `customer 0`, `report` або `exit`. Ідентифікатори з оригінального файла мають значення від 0 до 3.

За замовчуванням дані читаються з `test.dat`, вбудованого в модуль `bank`. Під час запуску JVM можна вказати інший файл властивістю `-Dbank.data=шлях-до-файла`. Одиниця вимірювання залишків у навчальному наборі — долар.

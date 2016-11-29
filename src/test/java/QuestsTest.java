import org.junit.Test;
import ru.seet61.quests.Quests;
import static org.junit.Assert.assertEquals;

/**
 * Тестовые проверки для класса
 *
 * По всей видимости ошибка в примере по 1 пункту.
 * Исправил пример по логике булевой алгебры
 *
 * Created by seet61 on 29.11.2016.
 */
public class QuestsTest {
    //true tests
    @Test
    public void checkQuestsTrue1() throws Exception {
        String quest = "(5127|6127)&(6099 & 5098)";
        Quests quests = new Quests();
        String res = quests.checkQuests(quest);
        assertEquals("true", res);
    }

    @Test
    public void checkQuestsTrue2() throws Exception {
        String quest = "(5127|6127)|(!6099 & !5098)";
        Quests quests = new Quests();
        String res = quests.checkQuests(quest);
        assertEquals("true", res);
    }

    @Test
    public void checkQuestsTrue3() throws Exception {
        String quest = "(5127|6127)";
        Quests quests = new Quests();
        String res = quests.checkQuests(quest);
        assertEquals("true", res);
    }

    @Test
    public void checkQuestsTrue4() throws Exception {
        String quest = "(5127)";
        Quests quests = new Quests();
        String res = quests.checkQuests(quest);
        assertEquals("true", res);
    }


    //false tests
    @Test
    public void checkQuestsFalse1() throws Exception {
        String quest = "(5127|!6127)&(!6099 & 5098)";
        Quests quests = new Quests();
        String res = quests.checkQuests(quest);
        assertEquals("false", res);
    }

    @Test
    public void checkQuestsFalse2() throws Exception {
        String quest = "(!5127|!6127)&(!6099 & !5098)";
        Quests quests = new Quests();
        String res = quests.checkQuests(quest);
        assertEquals("false", res);
    }

    @Test
    public void checkQuestsFalse3() throws Exception {
        String quest = "(!5127|!6127)|(!6099 & !5098)";
        Quests quests = new Quests();
        String res = quests.checkQuests(quest);
        assertEquals("false", res);
    }

    @Test
    public void checkQuestsFalse4() throws Exception {
        String quest = "(!5127)";
        Quests quests = new Quests();
        String res = quests.checkQuests(quest);
        assertEquals("false", res);
    }


    //error tests
    @Test
    public void checkQuestsError1() throws Exception {
        String quest = "(5127|6127) (!6099&(!5098)&";
        Quests quests = new Quests();
        String res = quests.checkQuests(quest);
        assertEquals("error", res);
    }

    @Test
    public void checkQuestsError2() throws Exception {
        String quest = "(5127|6127) (!6099&(!5098))&";
        Quests quests = new Quests();
        String res = quests.checkQuests(quest);
        assertEquals("error", res);
    }

    @Test
    public void checkQuestsError3() throws Exception {
        String quest = "(5127|6127) (!6099&(!5098))";
        Quests quests = new Quests();
        String res = quests.checkQuests(quest);
        assertEquals("error", res);
    }

    @Test
    public void checkQuestsError4() throws Exception {
        String quest = "(5127|6127)b(!6099&(!5098))";
        Quests quests = new Quests();
        String res = quests.checkQuests(quest);
        assertEquals("error", res);
    }
}

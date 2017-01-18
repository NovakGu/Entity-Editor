import org.junit.Test;

import static org.junit.Assert.*;

public class ERModelTest {
    @Test
    public void getNumNewClass() throws Exception {
        ERModel m = new ERModel(10);
        assertEquals(10, m.getNum());
    }

    @Test
    public void setNum() throws Exception {
        ERModel m = new ERModel(10);
        m.setNum(11);
        assertEquals(10, m.getNum());
    }
    @Test
    public void newEntityTest() throws Exception{
        ERModel m = new ERModel(10);
        m.newEntity("New Entity", 100, 120);
        assertEquals("New Entity", m.entityList.get(0).getName());
        assertEquals(100, m.entityList.get(0).getX());
        assertEquals(120, m.entityList.get(0).getY());
    } 
    @Test
    public void newNameTest() throws Exception{
        ERModel m = new ERModel(10);
        m.newEntity("New Entity", 100, 120);
        m.entityList.get(0).setName("SetName");
        assertEquals("SetName", m.entityList.get(0).getName());
    }
    @Test
    public void newArrowTest() throws Exception{
        ERModel m = new ERModel(10);
        m.newEntity("New Entity1", 100, 120);
        m.newEntity("New Entity2", 300, 120);
        m.newArrow(0, 1);
        assertEquals(1,m.arrowList.get(0).getRoot());
        assertEquals(0,m.arrowList.get(0).getTarget());
    }
    @Test
    public void moveEntityTest() throws Exception{
        ERModel m = new ERModel(10);
        m.newEntity("New Entity1", 100, 120);
        m.newEntity("New Entity2", 300, 120);
        m.newArrow(0, 1);
        m.newArrow(1, 0);
        m.entityList.get(1).newPosition(400, 400);
        assertEquals(1,m.arrowList.get(0).getRoot());
        assertEquals(0,m.arrowList.get(0).getTarget());
        assertEquals(0,m.arrowList.get(1).getRoot());
        assertEquals(1,m.arrowList.get(1).getTarget());
        assertEquals(400, m.entityList.get(1).getX());
        assertEquals(400, m.entityList.get(1).getY());   
    }
    @Test
    public void selectEntityTest() throws Exception{
        ERModel m = new ERModel(10);
        m.newEntity("New Entity1", 100, 120);
        m.newEntity("New Entity2", 300, 120);
        m.newEntity("New Entity3", 300, 300);
        m.newArrow(0, 1);
        m.newArrow(1, 0);
        m.newArrow(2, 0);
        int[] arrow = m.EntityClicked(0);
        System.out.println("==========");
        System.out.println(arrow[0]);
        System.out.println(arrow[1]);
        System.out.println(arrow[2]);
        System.out.println("=========");

        assertEquals(true,m.entityList.get(0).getSelected());
        assertEquals(1,arrow[0]);
        assertEquals(2,arrow[1]);
        assertEquals(0,arrow[2]);
    }
    @Test
    public void selectArrowTest() throws Exception{
        ERModel m = new ERModel(10);
        m.newEntity("New Entity1", 100, 120);
        m.newEntity("New Entity2", 300, 120);
        m.newEntity("New Entity3", 300, 300);
        m.newArrow(0, 1);
        m.newArrow(1, 0);
        m.newArrow(2, 0);
        int[] entity = m.arrowClicked(0);
        assertEquals(true,m.entityList.get(0).getSelected());
        assertEquals(true,m.entityList.get(1).getSelected());
        assertEquals(0,entity[1]);
        assertEquals(1,entity[0]);
    }
}
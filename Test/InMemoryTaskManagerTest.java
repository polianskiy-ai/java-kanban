import org.junit.jupiter.api.BeforeEach;
import service.InMemoryTaskManager;

class InMemoryTaskManagerTest {
    @BeforeEach
    public void beforeEach() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    }
}
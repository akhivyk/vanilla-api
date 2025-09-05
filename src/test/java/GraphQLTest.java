import com.solvd.api.service.GraphQLApiService;
import com.solvd.entity.User;
import com.solvd.utils.ConfigReader;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GraphQLTest {

    private final GraphQLApiService apiService = new GraphQLApiService(ConfigReader.getConfigValue("token"));

    @Test
    public void createUserAndGetAllUsersTest() {
        User generatedUser = User.generateUser();
        User created = apiService.createUser(generatedUser);

        Assert.assertNotNull(created.getId(), "User ID should be generated");
        Assert.assertEquals(created.getName(), generatedUser.getName(), "Created user name should match input");
        Assert.assertEquals(created.getEmail(), generatedUser.getEmail(), "Created user email should match input");

        List<User> users = apiService.getAllUsers();
        boolean exists = users.stream().anyMatch(u -> u.getId().equals(created.getId()));
        Assert.assertTrue(exists, "User list should contain created user ID");
    }

    @Test
    public void createUserAndGetByIdTest() {
        User generatedUser = User.generateUser();
        User created = apiService.createUser(generatedUser);

        User retrieved = apiService.getUserById(created.getId());
        Assert.assertEquals(retrieved.getId(), created.getId(), "Retrieved user ID should equal created user ID");
    }

    @Test
    public void createAndUpdateUserTest() {
        User generatedUser = User.generateUser();
        User created = apiService.createUser(generatedUser);

        String updatedName = "Updated Name" + RandomStringUtils.randomAlphabetic(3);
        created.setName(updatedName);

        User updated = apiService.updateUser(created);

        Assert.assertEquals(updated.getId(), created.getId(), "Updated user ID should equal original user ID");
        Assert.assertEquals(updated.getName(), updatedName, "Updated user name should match expected value");
    }

    @Test
    public void createAndDeleteUserTest() {
        User generatedUser = User.generateUser();
        User created = apiService.createUser(generatedUser);

        User deleted = apiService.deleteUser(created.getId());
        Assert.assertEquals(deleted.getId(), created.getId(), "Deleted user ID should equal expected ID");

        User afterDelete = apiService.getUserById(created.getId());
        Assert.assertNull(afterDelete, "User should not be retrievable after deletion");
    }
}

package api.repository;

import Api.model.User;
import Api.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertNotNull;

@WithMockUser(username = "rachel@gmail.com")
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

 @Test
    public void saveUser() {
        User user = new User(2,"Rachel", "rachel@gmail.com","1234567890","99999999", "123456");
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser);
    }
}

// package com.umass.hangout.user_registeration.repository;

// import com.umass.hangout.user_registeration.entity.User;
// import com.umass.hangout.user_registeration.entity.UserProfile;
// import com.umass.hangout.user_registeration.repository.UserProfileRepository;
// import com.umass.hangout.user_registeration.repository.UserRepository;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

// import java.util.Optional;

// import static org.assertj.core.api.Assertions.assertThat;
// @ExtendWith(MockitoExtension.class)
// @DataJpaTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// public class UserRegistrationRepositoryTests {
//     @Autowired
//     private TestEntityManager entityManager;

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private UserProfileRepository userProfileRepository;

//     private User user;
//     private UserProfile userProfile;

//     @BeforeEach
//     public void setUp() {
//         // Set up sample data for the User and UserProfile entities
//         user = new User();
//         user.setId(1L);
//         user.setEmail("test@example.com");
//         user.setPassword("password123");

//         // Persist the user first
//         user = entityManager.merge(user);  // Merge to ensure it's managed, this will save it to the database
//         entityManager.flush();  // Ensure it's persisted before we fetch it

//         userProfile = new UserProfile();
//         userProfile.setFirstName("test");
//         userProfile.setMiddleName("middle");
//         userProfile.setLastName("lastname");
//         userProfile.setDepartment("computer science");
//         userProfile.setBio("Its hangout time");
//         userProfile.setGraduationYear(2024);
//         User savedUser = entityManager.find(User.class, user.getId());
//         userProfile.setUser(savedUser);
//         userProfile.setUserId(savedUser.getId());
//         userProfile.setEmail(savedUser.getEmail());

//         // Persist the user and userProfile to the database

//         entityManager.persist(userProfile);
//         entityManager.flush();
//     }

//     @Test
//     public void testUserRepositoryFindByEmail() {
//         // Retrieve the user by email
//         Optional<User> retrievedUser = userRepository.findByEmail("test@example.com");

//         // Verify that the user is found and has the correct email
//         assertThat(retrievedUser).isPresent();
//         assertThat(retrievedUser.get().getEmail()).isEqualTo("test@example.com");
//     }

//     @Test
//     public void testUserRepositoryFindById() {
//         // Retrieve the user by ID
//         Optional<User> retrievedUser = userRepository.findById(user.getId());

//         // Verify that the user is found and matches the ID
//         assertThat(retrievedUser).isPresent();
//         assertThat(retrievedUser.get().getId()).isEqualTo(user.getId());
//     }

//     @Test
//     public void testUserProfileRepositoryFindByEmail() {
//         // Retrieve the user profile by email
//         Optional<UserProfile> retrievedProfile = userProfileRepository.findByEmail("test@example.com");

//         // Verify that the user profile is found and has the correct email
//         assertThat(retrievedProfile).isPresent();
//         assertThat(retrievedProfile.get().getEmail()).isEqualTo("test@example.com");
//     }

//     @Test
//     public void testUserProfileRepositoryFindById() {
//         // Retrieve the user profile by ID
//         Optional<UserProfile> retrievedProfile = userProfileRepository.findById(userProfile.getUserId());

//         // Verify that the user profile is found and matches the ID
//         assertThat(retrievedProfile).isPresent();
//         assertThat(retrievedProfile.get().getUserId()).isEqualTo(userProfile.getUserId());
//     }

//     @Test
//     public void testUserRepositorySave() {
//         // Create a new user
//         User newUser = new User();
//         newUser.setEmail("newuser@example.com");
//         newUser.setPassword("newpassword");

//         // Save the new user to the repository
//         User savedUser = userRepository.save(newUser);

//         // Verify that the user is saved correctly
//         assertThat(savedUser).isNotNull();
//         assertThat(savedUser.getId()).isGreaterThan(0);
//         assertThat(savedUser.getEmail()).isEqualTo("newuser@example.com");
//     }

//     @Test
//     public void testUserProfileRepositorySave() {
//         // Create a new user profile
//         UserProfile newProfile = new UserProfile();
//         newProfile.setUser(user);
//         newProfile.setUserId(user.getId());
//         newProfile.setEmail("test@example.com");
//         newProfile.setFirstName("Alice");
//         newProfile.setLastName("Smith");
//         newProfile.setGraduationYear(2025);
//         newProfile.setBio("Engineer");

//         // Save the new user profile to the repository
//         UserProfile savedProfile = userProfileRepository.save(newProfile);

//         // Verify that the user profile is saved correctly
//         assertThat(savedProfile).isNotNull();
//         assertThat(savedProfile.getUserId()).isGreaterThan(0);
//         assertThat(savedProfile.getEmail()).isEqualTo("test@example.com");
//         assertThat(savedProfile.getFirstName()).isEqualTo("Alice");
//         assertThat(savedProfile.getLastName()).isEqualTo("Smith");
//         assertThat(savedProfile.getBio()).isEqualTo("Engineer");

//     }

//     @Test
//     public void testUserRepositoryDelete() {
//         // Delete the user by ID
//         userRepository.delete(user);

//         // Verify that the user is deleted
//         Optional<User> deletedUser = userRepository.findById(user.getId());
//         assertThat(deletedUser).isNotPresent();
//     }

//     @Test
//     public void testUserProfileRepositoryDelete() {
//         // Delete the user profile by ID
//         userProfileRepository.delete(userProfile);

//         // Verify that the user profile is deleted
//         Optional<UserProfile> deletedProfile = userProfileRepository.findById(userProfile.getUserId());
//         assertThat(deletedProfile).isNotPresent();
//     }
// }

package rest.test;

import com.petstore.models.Pet;
import com.petstore.models.Status;
import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.listeners.LogListener;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Listeners({LogListener.class})
public class PetTests extends BaseTest {

    @Description("Add a new pet to the store")
    @Test
    public void addNewPet() {
        String name = "Cerberus";
        Pet pet = petController.createPet(name);
        Pet response = petController.addNewPet(pet);

        assertThat(response.getName(), equalTo(name));
    }

    @Description("Find pet by ID")
    @Test
    public void findPetById() {
        String name = "Laika";
        Pet pet = petController.createPet(name);
        petController.addNewPet(pet);
        Pet response = petController.findPet(pet);

        assertThat(response.getName(), equalTo(name));
        assertThat(response.getId(), equalTo(pet.getId()));
    }

    @Description("Find pet by ID")
    @Test
    public void findPetsByStatus() {
        List<Pet> response = petController.findPetsByStatus();
        Pet pendingPets = response.stream().filter(pet -> Status.pending.equals(pet.getStatus())).findFirst().orElse(null);
        Pet soldPets = response.stream().filter(pet -> Status.sold.equals(pet.getStatus())).findFirst().orElse(null);

        assertThat(pendingPets, equalTo(null));
        assertThat(soldPets, equalTo(null));
    }

    @Description("Update an existing pet")
    @Test
    public void updatePet() {
        String name = "Belka";
        String newName = "Strelka";
        Pet pet = petController.createPet(name);
        petController.addNewPet(pet);
        // update name
        pet.setName(newName);

        Pet response = petController.updatePet(pet);

        assertThat(response.getName(), equalTo(newName));
    }

    @Description("Delete a pet")
    @Test
    public void deletePet() {
        String name = "Hatiko";
        Pet pet = petController.createPet(name);
        petController.addNewPet(pet);
        petController.deletePet(pet);
        petController.verifyPetDeleted(pet);
    }
}
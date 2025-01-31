"""
✅ Creates an owner and stores the ownerId
✅ Creates a pet under the owner and stores the petId
✅ Deletes the pet using the stored petId
✅ Deletes the owner after testing
✅ Assertions ensure correct status codes and expected data
"""

from locust import HttpUser, task, between
from faker import Faker
from locust import HttpUser, task, between
from requests import Response
import re
fake = Faker()


class PetclinicUser(HttpUser):
    wait_time = between(1, 3)  # Wait time between requests
    def on_start(self):
        """ Runs when a user starts executing tasks. Creates an owner and pet, storing their IDs. """
        self.owner_id = self.create_owner()
        self.pet_id = self.create_pet()

    def create_owner(self):
        try:
            headers = {
                "Accept": "application/json, text/plain, */*",
                "Content-Type": "application/json",
            }
            payload = {
                "id": None,
                "firstName": "Vishal",
                "lastName": "Mishra",
                "address": "2602, Sadaf 2",
                "city": "Pune",
                "telephone": "0564375618"
                }
            payload1 = {
            "id": None,
            "firstName": fake.first_name(),
            "lastName": fake.last_name(),
            "address": fake.street_address(),
            "city": fake.city(),
            "telephone": re.sub(r'\D', '', fake.phone_number())[:10]  # Ensure numeric-only and 10 digits
            }
            response = self.client.post("/petclinic/api/owners", json=payload1,headers=headers, name="Create Owner")
            print(response)
            response.raise_for_status()
            owner_data = response.json()
            owner_id = owner_data.get("id")
            assert owner_id, "Owner ID missing in response"
            return owner_id
        except Exception as e:
            print(f"Error creating owner: {e}")
            return None


    def create_pet(self):
        """ Creates a pet for the stored owner ID (if available) and extracts pet ID. """
        
        
        
        if not self.owner_id:
            return None
        
        headers = {
            "Accept": "application/json, text/plain, */*",
            "Content-Type": "application/json",
        }

        payload = {
            "id": None,
            "owner": {
                "id": self.owner_id,
                "firstName": fake.first_name(),
                "lastName": fake.last_name(),
                "address": fake.street_address(),
                "city": fake.city(),
                "telephone": re.sub(r'\D', '', fake.phone_number())[:10],
                "pets": []
            },
            "name": fake.first_name(),
            "birthDate": fake.date_of_birth(minimum_age=0, maximum_age=15).strftime("%Y-%m-%d"),
            "type": {
                "id": 3,  # Assuming pet types are numbered
                "name": "lizard"
            }
        }
        
        try:
            response = self.client.post(f"/petclinic/api/owners/{self.owner_id}/pets",json=payload, headers=headers, name="Create Pet")
            response.raise_for_status()
            pet_data = response.json()
            pet_id = pet_data.get("id")
            assert pet_id, "Pet ID missing in response"
            return pet_id

        except Exception as e:
            print(f"Error creating pet: {e}")
            return None

    @task
    def get_owners(self):
        """ Fetches all owners and asserts response """
        try:
            response = self.client.get("/petclinic/api/owners", name="Get Owners")
            response.raise_for_status()
            assert isinstance(response.json(), list), "Owners response is not a list"
        except Exception as e:
            print(f"Error fetching owners: {e}")

    @task
    def get_pets(self):
        """ Fetches all pets and asserts response """
        try:
            response = self.client.get("/petclinic/api/pets", name="Get Pets")
            response.raise_for_status()
            assert isinstance(response.json(), list), "Pets response is not a list"
        except Exception as e:
            print(f"Error fetching pets: {e}")

    @task
    def get_vets(self):
        """ Fetches all vets and asserts response """
        try:
            response = self.client.get("/petclinic/api/vets", name="Get Vets")
            response.raise_for_status()
            assert isinstance(response.json(), list), "Vets response is not a list"
        except Exception as e:
            print(f"Error fetching vets: {e}")

    @task
    def get_visits(self):
        """ Fetches all visits and asserts response """
        try:
            response = self.client.get("/petclinic/api/visits", name="Get Visits")
            response.raise_for_status()
            assert isinstance(response.json(), list), "Visits response is not a list"
        except Exception as e:
            print(f"Error fetching visits: {e}")

    @task
    def get_health(self):
        """ Fetches system health status """
        try:
            response = self.client.get("/petclinic/actuator/health", name="Get Health")
            response.raise_for_status()
            assert "status" in response.json(), "Health response missing 'status'"
        except Exception as e:
            print(f"Error fetching health status: {e}")

    @task
    def delete_pet(self):
        """ Deletes the created pet and asserts response """
        if not self.pet_id:
            return
        try:
            response = self.client.delete(f"/petclinic/api/pets/{self.pet_id}", name="Delete Pet")
            response.raise_for_status()
            assert response.status_code in [200, 204], f"Unexpected status code: {response.status_code}"
        except Exception as e:
            print(f"Error deleting pet: {e}")

    @task
    def delete_owner(self):
        """ Deletes the created owner and asserts response """
        if not self.owner_id:
            return
        try:
            response = self.client.delete(f"/petclinic/api/owners/{self.owner_id}", name="Delete Owner")
            response.raise_for_status()
            assert response.status_code in [200, 204], f"Unexpected status code: {response.status_code}"
        except Exception as e:
            print(f"Error deleting owner: {e}")
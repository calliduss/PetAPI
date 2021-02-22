package com.petstore.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pet {

    private long id;
    private Category category;
    private String name;
    private List<String> photoUrls = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();
    private Status status;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public Category getCategory() { return category; }

    public void setCategory(Category category) { this.category = category; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<String> getPhotoUrls() { return photoUrls; }

    public void setPhotoUrls(List<String> photoUrls) { this.photoUrls = photoUrls; }

    public List<Tag> getTags() { return tags; }

    public void setTags(List<Tag> tags) { this.tags = tags; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return id == pet.id &&
                Objects.equals(category, pet.category) &&
                Objects.equals(name, pet.name) &&
                Objects.equals(photoUrls, pet.photoUrls) &&
                Objects.equals(tags, pet.tags) &&
                status == pet.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, name, photoUrls, tags, status);
    }

    public static class Builder {

        private long id;
        private Category category;
        private String name;
        private List<String> photoUrls = new ArrayList<>();
        private List<Tag> tags = new ArrayList<>();
        private Status status;

        public Builder() {}

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder inCategory(Category category) {
            this.category = category;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPhotoUrls(List<String> photoUrls) {
            this.photoUrls = photoUrls;
            return this;
        }

        public Builder withTags(List<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public Builder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public Pet build() {
            Pet pet = new Pet();
            pet.id = this.id;
            pet.name = this.name;
            pet.category = this.category;
            pet.photoUrls = this.photoUrls;
            pet.tags = this.tags;
            pet.status = this.status;
            return pet;
        }
    }
}
